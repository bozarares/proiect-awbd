<template>
  <div class="py-8 bg-gray-100 min-h-screen px-12">
    <div class="max-w-7xl mx-auto">
      <div v-if="pending" class="text-center">
        <p>Loading...</p>
      </div>
      <div v-else-if="project" class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Left Column: Project Details -->
        <div class="lg:col-span-1 bg-white rounded-lg shadow-md p-6 self-start">
          <div class="flex items-center justify-between mb-4 gap-8">
            <h1 class="text-3xl font-bold">{{ project.name }}</h1>
            <NuxtLink :to="`/projects/${projectId}/edit`">
              <button class="bg-blue-500 hover:bg-blue-600 text-white px-2 py-1 rounded">
                Edit
              </button>
            </NuxtLink>
          </div>
          <p class="text-gray-600 mb-4">{{ project.description }}</p>
          <p class="text-gray-500 mb-2">
            Start Date: {{ new Date(project.startDate).toLocaleDateString() }}
          </p>
          <p class="text-gray-500 mb-2">
            End Date: {{ new Date(project.endDate).toLocaleDateString() }}
          </p>
          <p class="text-gray-500 mb-6">Number of Tasks: {{ project.tasks.length }}</p>
          <NuxtLink :to="`/tasks/create?projectId=${projectId}`">
            <button class="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded mt-4">
              Create Task
            </button>
          </NuxtLink>
        </div>

        <!-- Right Column: Tasks -->
        <div class="lg:col-span-2 bg-white rounded-lg shadow-md p-6">
          <h2 class="text-2xl font-semibold mb-3">Tasks</h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-4">
            <NuxtLink
              v-for="task in project.tasks"
              :to="`/tasks/${task.id}`"
              :key="task.id"
              class="border p-4 rounded-lg shadow-md"
            >
              <h3 class="text-xl font-bold mb-2">{{ task.title }}</h3>
              <p class="text-gray-600 mb-2">{{ task.description }}</p>
              <p class="text-gray-500 mb-2">
                Created Date: {{ new Date(task.createdDate).toLocaleDateString() }}
              </p>
              <p class="text-gray-500 mb-2">
                Due Date: {{ new Date(task.dueDate).toLocaleDateString() }}
              </p>
              <p class="text-gray-500 mb-2">Priority: {{ task.priority }}</p>
              <p class="text-gray-500">Status: {{ task.status }}</p>
            </NuxtLink>
          </div>
        </div>
      </div>
      <div v-else class="text-center">
        <p>No project data available.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAsyncData } from '#app';
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { useUserStore } from '~/stores/user';

const route = useRoute();
const userStore = useUserStore();
const projectId = route.params.id;

const { data: project, pending, error } = await useAsyncData(`project-${projectId}`, async () => {
  if (userStore.token) {
    const { data, error } = await useMyFetch(`/projects/${projectId}`, {
      headers: {
        Authorization: `Bearer ${userStore.token}`,
      },
    });
    if (error.value) {
      console.error('Error fetching project data:', error.value);
      throw new Error('Error fetching project data');
    }
    return data.value;
  }
  return null;
});
</script>

<style scoped>
/* Custom styles here if needed */
</style>
