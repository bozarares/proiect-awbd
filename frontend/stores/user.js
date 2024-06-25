import { defineStore } from 'pinia'
import { useMyFetch } from '../composables/useMyFetch'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: null,
    user: null
  }),
  actions: {
    setToken(token) {
      this.token = token
    },
    setUser(user) {
      this.user = user
    },
    clearUser() {
      this.token = null
      this.user = null
    },
    async logout() {
      try {
        await useMyFetch('/auth/logout', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${this.token}`
          }
        })
      } catch (error) {
        console.error('Error logging out:', error)
      } finally {
        this.clearUser()
      }
    }
  },
persist: true,
})
