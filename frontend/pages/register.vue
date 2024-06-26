<template>
  <div class="flex items-start min-h-screen">
    <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
      <h1 class="text-2xl font-bold mb-6 text-center">Register</h1>
      <form @submit.prevent="register" class="space-y-4">
        <div>
          <label for="username" class="block text-gray-700">Username</label>
          <input 
          required
            v-model="username" 
            id="username" 
            placeholder="Username" 
            class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <p v-if="errors.username" class="text-red-500 text-sm mt-1">{{ errors.username }}</p>
        </div>
        <div>
          <label for="email" class="block text-gray-700">Email</label>
          <input 
          required
            type="email" 
            v-model="email" 
            id="email" 
            placeholder="Email" 
            class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <p v-if="errors.email" class="text-red-500 text-sm mt-1">{{ errors.email }}</p>
        </div>
        <div>
          <label for="password" class="block text-gray-700">Password</label>
          <input 
          required
            type="password" 
            v-model="password" 
            id="password" 
            placeholder="Password" 
            class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <p v-if="errors.password" class="text-red-500 text-sm mt-1">{{ errors.password }}</p>
        </div>
        <div>
          <label for="password_confirmation" class="block text-gray-700">Confirm Password</label>
          <input 
          required
            type="password" 
            v-model="passwordConfirmation" 
            id="password_confirmation" 
            placeholder="Confirm Password" 
            class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <p v-if="errors.password_confirmation" class="text-red-500 text-sm mt-1">{{ errors.password_confirmation }}</p>
          <p v-if="errors.general" class="text-red-500 text-sm mt-1">{{ errors.general }}</p>
        </div>
        <button 
          type="submit" 
          class="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-lg transition duration-300"
        >
          Register
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

const username = ref('')
const email = ref('')
const password = ref('')
const passwordConfirmation = ref('')
const errors = ref({
  username: '',
  email: '',
  password: '',
  password_confirmation: '',
  general: ''
})
const user = useState('user')
const router = useRouter()
const userStore = useUserStore()

const register = async () => {
  errors.value = {
    username: '',
    email: '',
    password: '',
    password_confirmation: '',
    general: ''
  }

  if (password.value !== passwordConfirmation.value) {
    errors.value.password_confirmation = 'Passwords do not match'
    return
  }

  try {
    const response = await $fetch('http://localhost:8080/api/auth/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username: username.value, email: email.value, password: password.value })
    })
    userStore.setToken(response.token)
    userStore.setUser(response.user)
    router.push('/')
    user.value = response
  } catch (error) {
    if (error.data && error.data) {
      const errorData = error.data
      if (errorData.username) {
        errors.value.username = errorData.username
      }
      if (errorData.email) {
        errors.value.email = errorData.email
      }
      if (errorData.password) {
        errors.value.password = errorData.password
      }
      if (errorData.error) {
        errors.value.general = errorData.error
      }
    } else {
      errors.value.general = 'An unexpected error occurred'
    }
  }
}
</script>

<style scoped>
</style>
