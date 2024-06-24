<template>
  <div class="py-8 bg-gray-100 min-h-screen px-12">
    <div class="max-w-7xl mx-auto">
      <div v-if="project" class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Left Column: Project Details -->
        <div class="lg:col-span-1 bg-white rounded-lg shadow-md p-6 self-start">
          <h1 class="text-3xl font-bold mb-4">{{ project.name }}</h1>
          <p class="text-gray-600 mb-4">{{ project.description }}</p>
          <p class="text-gray-500 mb-2">Start Date: {{ new Date(project.startDate).toLocaleDateString() }}</p>
          <p class="text-gray-500 mb-2">End Date: {{ new Date(project.endDate).toLocaleDateString() }}</p>
          <p class="text-gray-500 mb-6">Number of Tasks: {{ project.tasks.length }}</p>
          <NuxtLink :to="`/tasks/create?projectId=${projectId}`">
            <button class="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded mt-4">Create Task</button>
          </NuxtLink>
        </div>

        <!-- Right Column: Tasks -->
        <div class="lg:col-span-2 bg-white rounded-lg shadow-md p-6">
          <h2 class="text-2xl font-semibold mb-3">Tasks</h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-4">
            <NuxtLink v-for="task in project.tasks" :to="`/tasks/${task.id}`" :key="task.id" class="border p-4 rounded-lg shadow-md">
              <h3 class="text-xl font-bold mb-2">{{ task.title }}</h3>
              <p class="text-gray-600 mb-2">{{ task.description }}</p>
              <p class="text-gray-500 mb-2">Created Date: {{ new Date(task.createdDate).toLocaleDateString() }}</p>
              <p class="text-gray-500 mb-2">Due Date: {{ new Date(task.dueDate).toLocaleDateString() }}</p>
              <p class="text-gray-500 mb-2">Priority: {{ task.priority }}</p>
              <p class="text-gray-500">Status: {{ task.status }}</p>
            </NuxtLink>
          </div>

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
const projectId = route.params.id

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
