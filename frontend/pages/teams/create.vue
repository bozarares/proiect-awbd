<template>
  <div class="p-8 bg-gray-100 min-h-screen">
    <div class="max-w-lg mx-auto bg-white p-6 rounded-lg shadow-md">
      <h1 class="text-2xl font-bold mb-6">Create New Team</h1>
      <form @submit.prevent="createTeam">
        <div class="mb-4">
          <label for="name" class="block text-gray-700 font-semibold mb-2">Name:</label>
          <input v-model="name" id="name" required class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500" />
        </div>
        <div class="mb-4">
          <label for="description" class="block text-gray-700 font-semibold mb-2">Description:</label>
          <textarea v-model="description" id="description" required class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"></textarea>
        </div>
        <button type="submit" class="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg w-full">Create Team</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '~/stores/user'
import { useMyFetch } from '~/composables/useMyFetch'

const userStore = useUserStore()
const router = useRouter()
const name = ref('')
const description = ref('')

const createTeam = async () => {
  try {
    const { data, error } = await useMyFetch('/teams', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${userStore.token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ name: name.value, description: description.value })
    })

    if (error.value) {
      console.error(error.value)
    } else {
      router.push('/teams')
    }
  } catch (error) {
    console.error('Fetch error: ', error)
  }
}
</script>

<style scoped>
/* Stilurile tale aici */
</style>
