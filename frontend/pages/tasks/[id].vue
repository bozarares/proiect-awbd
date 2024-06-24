<template>
  <div class="py-8 bg-gray-100 min-h-screen px-12">
    <div class="max-w-7xl mx-auto">
      <div v-if="task" class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Left Column: Task Details -->
        <div class="lg:col-span-1 bg-white rounded-lg shadow-md p-6 self-start">
          <h1 class="text-3xl font-bold mb-4">{{ task.title }}</h1>
          <p class="text-gray-600 mb-4">{{ task.description }}</p>
          <p class="text-gray-500 mb-2">Created Date: {{ new Date(task.createdDate).toLocaleDateString() }}</p>
          <p class="text-gray-500 mb-2">Due Date: {{ new Date(task.dueDate).toLocaleDateString() }}</p>
          <p class="text-gray-500 mb-2">Priority: {{ task.priority }}</p>
          <p class="text-gray-500 mb-2">Status: {{ task.status }}</p>
        </div>

        <!-- Right Column: Comments -->
        <div class="lg:col-span-2 bg-white rounded-lg shadow-md p-6">
          <h2 class="text-2xl font-semibold mb-3">Comments</h2>
          <form @submit.prevent="addComment" class="mb-6">
            <div class="mb-4">
              <label for="commentText" class="block text-gray-700 font-semibold mb-2">Add a comment:</label>
              <textarea v-model="commentText" id="commentText" required class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"></textarea>
            </div>
            <button type="submit" class="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg">Send</button>
          </form>
          <ul class="list-disc pl-5">
            <li v-for="comment in task.comments" :key="comment.id" class="mb-4 flex justify-between">
              <div>
                <p class="text-gray-700"><strong>{{ comment.user.username }}:</strong> {{ comment.content }}</p>
                <p class="text-gray-500">{{ new Date(comment.createdDate).toLocaleDateString() }}</p>
              </div>
              <div v-if="comment.user.id === userStore.user.id" class="ml-4">
                <button @click="deleteComment(comment.id)" class="text-red-500 hover:text-red-700">Delete</button>
              </div>
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
const task = ref(null)
const userStore = useUserStore()
const taskId = route.params.id
const commentText = ref('')

onMounted(async () => {
  const { data, error } = await useMyFetch(`/tasks/${route.params.id}`, {
    headers: {
      'Authorization': `Bearer ${userStore.token}`
    }
  })

  if (error.value) {
    console.error(error.value)
  } else {
    task.value = data.value
  }
})

const addComment = async () => {
  try {
    const { data, error } = await useMyFetch('/comments', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${userStore.token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        content: commentText.value,
        createdDate: new Date().toISOString().split('T')[0],
        task: { id: taskId },
        user: { id: userStore.user.id, username: userStore.user.username }
      })
    })

    if (error.value) {
      console.error(error.value)
    } else {
      task.value.comments.push(data.value)
      commentText.value = ''
    }
  } catch (error) {
    console.error('Fetch error: ', error)
  }
}

const deleteComment = async (commentId) => {
  try {
    const { error } = await useMyFetch(`/comments/${commentId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${userStore.token}`
      }
    })

    if (error.value) {
      console.error(error.value)
    } else {
      task.value.comments = task.value.comments.filter(comment => comment.id !== commentId)
    }
  } catch (error) {
    console.error('Fetch error: ', error)
  }
}
</script>

