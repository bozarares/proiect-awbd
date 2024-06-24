<template>
  <div class="flex items-start min-h-screen">
    <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
      <h1 class="text-2xl font-bold mb-6 text-center">Login</h1>
      <form @submit.prevent="login" class="space-y-4">
        <div>
          <label for="username" class="block text-gray-700">Username</label>
          <input 
            v-model="username" 
            id="username" 
            placeholder="Username" 
            class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <div>
          <label for="password" class="block text-gray-700">Password</label>
          <input 
            type="password" 
            v-model="password" 
            id="password" 
            placeholder="Password" 
            class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <button 
          type="submit" 
          class="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-lg transition duration-300"
        >
          Login
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useState } from '#app'
import { useUserStore } from '~/stores/user'
import { useMyFetch } from '~/composables/useMyFetch'

const username = ref('')
const password = ref('')
const user = useState('user')
const router = useRouter()
const userStore = useUserStore()

const login = async () => {
  try {
    const { data } = await useMyFetch('/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username: username.value, password: password.value })
    })
    userStore.setToken(data.value.token)
    userStore.setUser({ username: username.value })
    router.push('/')
    user.value = data.value
  } catch (error) {
    console.error(error)
  }
}
</script>

<style scoped>
</style>
