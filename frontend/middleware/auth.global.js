import { useUserStore } from '~/stores/user'

export default defineNuxtRouteMiddleware((to, from) => {
  const userStore = useUserStore()

  const publicPages = ['/', '/login', '/register']
  const authRequired = !publicPages.includes(to.path)

  if (authRequired && !userStore.token) {
    return navigateTo('/login')
  }
})
