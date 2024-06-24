<template>
  <div>
    <h1>{{ project.name }}</h1>
    <p>{{ project.description }}</p>
    <p>Start Date: {{ project.startDate }}</p>
    <p>End Date: {{ project.endDate }}</p>
    <h2>Tasks</h2>
    <ul>
      <li v-for="task in project.tasks" :key="task.id">
        {{ task.name }}
      </li>
    </ul>
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
/* Stilurile tale aici */
</style>
