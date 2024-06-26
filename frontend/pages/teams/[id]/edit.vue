<template>
  <div class="p-8 bg-gray-100 min-h-screen relative">
    <button 
      @click="goBack" 
      class="text-sm"
    >
      Go Back
    </button>
    <div class="max-w-lg mx-auto bg-white p-6 rounded-lg shadow-md">
      <h1 class="text-2xl font-bold mb-6">Edit Team</h1>
      <form @submit.prevent="updateTeam">
        <div class="mb-4">
          <label for="name" class="block text-gray-700 font-semibold mb-2">Name:</label>
          <input v-model="name" id="name" required class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500" />
        </div>
        <div class="mb-4">
          <label for="description" class="block text-gray-700 font-semibold mb-2">Description:</label>
          <textarea v-model="description" id="description" required class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"></textarea>
        </div>
        <button type="submit" class="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg w-full">Update Team</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, watchEffect } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '~/stores/user'
import { useMyFetch } from '~/composables/useMyFetch'
import { useAsyncData } from '#app'

const userStore = useUserStore()
const router = useRouter()
const route = useRoute()
const name = ref('')
const description = ref('')

// Fetch team data using useAsyncData
const { data: team, pending, error } = await useAsyncData(`team-${route.params.id}`, async () => {
  const { data, error } = await useMyFetch(`/teams/${route.params.id}`, {
    headers: {
      'Authorization': `Bearer ${userStore.token}`
    }
  })
  if (error.value) {
    console.error('Error fetching team data:', error.value)
    throw new Error('Error fetching team data')
  }
  return data.value
})

// Watch team data and update form fields
watchEffect(() => {
  if (team.value) {
    name.value = team.value.name
    description.value = team.value.description
  }
})

const updateTeam = async () => {
  try {
    const { data, error } = await useMyFetch(`/teams/${route.params.id}`, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${userStore.token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ name: name.value, description: description.value })
    })

    if (error.value) {
      console.error('Error updating team:', error.value)
    } else {
      router.push(`/teams/${route.params.id}`)
    }
  } catch (error) {
    console.error('Fetch error: ', error)
  }
}

const goBack = () => {
  router.push(`/teams/${route.params.id}`)
}
</script>