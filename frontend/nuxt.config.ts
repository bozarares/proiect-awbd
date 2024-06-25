export default defineNuxtConfig({
  devtools: { enabled: true },

  runtimeConfig: {
    public: {
      baseURL: 'http://localhost:8080/api'
    }
  },

  modules: ['@pinia/nuxt','@pinia-plugin-persistedstate/nuxt', '@nuxtjs/tailwindcss'],
  

})
