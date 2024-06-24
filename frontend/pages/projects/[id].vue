<template>
  <div class="py-8 bg-gray-100 min-h-screen px-12">
    <div class="max-w-7xl mx-auto">
      <div v-if="project" class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Left Column: Project Details -->
        <div class="lg:col-span-1 bg-white rounded-lg shadow-md p-6">
          <h1 class="text-3xl font-bold mb-4">{{ project.name }}</h1>
          <p class="text-gray-600 mb-4">{{ project.description }}</p>
          <p class="text-gray-500 mb-2">Start Date: {{ new Date(project.startDate).toLocaleDateString() }}</p>
          <p class="text-gray-500 mb-2">End Date: {{ new Date(project.endDate).toLocaleDateString() }}</p>
          <p class="text-gray-500 mb-6">Number of Tasks: {{ project.tasks.length }}</p>
        </div>

        <!-- Right Column: Tasks -->
        <div class="lg:col-span-2 bg-white rounded-lg shadow-md p-6">
          <h2 class="text-2xl font-semibold mb-3">Tasks</h2>
          <ul class="list-disc pl-5 mb-6">
            <li v-for="task in project.tasks" :key="task.id" class="mb-2">
              {{ task.name }} ({{ task.status }})
            </li>
          </ul>
        </div>
      </div>
      <div v-else class="text-center">
        <p>Loading...</p>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '~/stores/user'
import { useMyFetch } from '~/composables/useMyFetch'

const route = useRoute()
const project = ref(null)
const userStore = useUserStore()

onMounted(async () => {
  const { data, error } = await useMyFetch(`/projects/${route.params.id}`, {
    headers: {
      'Authorization': `Bearer ${userStore.token}`
    }
  })

  if (error.value) {
    console.error(error.value)
  } else {
    project.value = data.value
  }
})
</script>

<style scoped>
/* Custom styles here if needed */
</style>
