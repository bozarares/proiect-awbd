<template>
  <div class="py-8 bg-gray-100 min-h-screen px-12">
    <div class="max-w-7xl mx-auto">
      <div v-if="team" class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Left Column: Team Details -->
        <div class="lg:col-span-1 bg-white rounded-lg shadow-md p-6 self-start">
          <h1 class="text-3xl font-bold mb-4">{{ team.name }}</h1>
          <p class="text-gray-600 mb-6">{{ team.description }}</p>
          
          <h2 class="text-2xl font-semibold mb-3">Team Members</h2>
          <ul class="list-disc pl-5 mb-6">
            <li v-for="member in team.teamMembers" :key="member.id" class="mb-2">
              <div class="flex justify-between items-center">
                <p>{{ member.user.username }} ({{ member.user.email }})</p>
                <button v-if="team.user.id === userStore.user.id && member.user.id !== userStore.user.id" @click="deleteMember(member.id)" class="text-red-500 hover:text-red-700">Delete</button>
              </div>
            </li>
          </ul>

          <div class="flex mb-6">
            <input v-model="email" placeholder="Enter user email" class="border p-2 flex-grow rounded-l-lg" />
            <button @click="addMemberByEmail" class="bg-blue-500 hover:bg-blue-600 text-white px-4 rounded-r-lg">Add</button>
          </div>

          <NuxtLink :to="`/projects/create?teamId=${team.id}`">
            <button class="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded">Create Project</button>
          </NuxtLink>
        </div>

        <!-- Right Column: Projects -->
        <div class="lg:col-span-2 bg-white rounded-lg shadow-md p-6">
          <h2 class="text-2xl font-semibold mb-3">Projects</h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-4">
            <NuxtLink v-for="project in team.projects" :key="project.id" :to="`/projects/${project.id}`" class="border p-4 rounded-lg shadow-md">
              <h3 class="text-xl font-bold mb-2">{{ project.name }}</h3>
              <p class="text-gray-600 mb-2">{{ project.description }}</p>
              <p class="text-gray-500 mb-2">Start Date: {{ project.startDate }}</p>
              <p class="text-gray-500 mb-2">End Date: {{ project.endDate }}</p>
              <p class="text-gray-500">Number of Tasks: {{ project.tasks.length }}</p>
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
const team = ref(null)
const userStore = useUserStore()
const email = ref('')

onMounted(async () => {
  const { data, error } = await useMyFetch(`/teams/${route.params.id}`, {
    headers: {
      'Authorization': `Bearer ${userStore.token}`
    }
  })

  if (error.value) {
    console.error(error.value)
  } else {
    team.value = data.value
  }
})

const deleteMember = async (memberId) => {
  try {
    const { error } = await useMyFetch(`/teamMembers/${memberId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${userStore.token}`
      }
    })

    if (error.value) {
      console.error(error.value)
    } else {
      // Actualizează starea echipei după ștergerea unui membru
      const updatedTeamMembers = team.value.teamMembers.filter(member => member.id !== memberId)
      team.value = { ...team.value, teamMembers: updatedTeamMembers }
    }
  } catch (error) {
    console.error('Fetch error: ', error)
  }
}

const addMemberByEmail = async () => {
  try {
    const { data, error } = await useMyFetch(`/teamMembers/addByEmail`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${userStore.token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ teamId: team.value.id, email: email.value })
    })

    if (error.value) {
      console.error(error.value)
    } else {
      team.value.teamMembers.push(data.value)
      email.value = ''
    }
  } catch (error) {
    console.error('Fetch error: ', error)
  }
}
</script>
