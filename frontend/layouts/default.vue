<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '~/stores/user'

const router = useRouter()
const userStore = useUserStore()

const logout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<template>
  <div>
    <!-- Navbar -->
    <nav class="bg-gray-800 text-white px-4 py-2 flex justify-between items-center shadow-lg">
      <div class="flex items-center space-x-4">
        <NuxtLink href="/" class="text-lg font-semibold hover:text-gray-400 transition duration-300">AWBD</NuxtLink>

        <NuxtLink v-if="userStore.token" href="/teams" class="hover:text-gray-400 transition duration-300">Teams</NuxtLink>
      </div>
      <div v-if="userStore.token" class="flex items-center space-x-4">
        <span class="text-sm">Welcome, {{ userStore.user.username }}</span>
        <button @click="logout" class="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded transition duration-300">Logout</button>
      </div>
      <div v-else class="flex items-center space-x-4"><NuxtLink v-if="!userStore.token" href="/login" class="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded transition duration-300">Login</NuxtLink><NuxtLink v-if="!userStore.token" href="/register" class="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded transition duration-300">Register</NuxtLink></div>
    </nav>

    <!-- Main Content -->
    <div class="bg-zinc-100">
      <div class="flex h-auto w-full flex-row justify-center gap-8">
        <main class="flex min-h-screen w-full flex-col items-center rounded px-4 py-8 shadow-md">
          <slot />
        </main>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Add your custom styles here */
</style>
