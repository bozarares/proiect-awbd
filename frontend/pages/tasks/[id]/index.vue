<template>
  <div class="py-8 bg-gray-100 min-h-screen px-4 md:px-12">
    <div class="max-w-7xl mx-auto">
      <div v-if="pendingTask || pendingComments" class="text-center">
        <p>Loading...</p>
      </div>
      <div v-else-if="task" class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Left Column: Task Details -->
        <div class="lg:col-span-1 bg-white rounded-lg shadow-md p-6 self-start">
          <div class="flex items-center justify-between mb-4 gap-8">
            <h1 class="text-3xl font-bold">{{ task.title }}</h1>
            <NuxtLink :to="`/tasks/${taskId}/edit`">
              <button class="bg-blue-500 hover:bg-blue-600 text-white px-2 py-1 rounded">
                Edit
              </button>
            </NuxtLink>
          </div>
          <p class="text-gray-600 mb-4">{{ task.description }}</p>
          <p class="text-gray-500 mb-2">Created Date: {{ new Date(task.createdDate).toLocaleDateString() }}</p>
          <p class="text-gray-500 mb-2">Due Date: {{ new Date(task.dueDate).toLocaleDateString() }}</p>
          <p class="text-gray-500 mb-2">Priority: {{ task.priority }}</p>
          <p class="text-gray-500 mb-2">Status: {{ task.status }}</p>
          
          <!-- Labels Section -->
          <div class="mt-4">
            <h2 class="text-2xl font-semibold mb-3">Labels</h2>
            <div class="flex flex-wrap mb-4">
              <span v-for="label in task.labels" :key="label.id" :style="{ backgroundColor: label.color }" class="text-white px-3 py-1 rounded-lg mr-2 mb-2">
                {{ label.name }}
                <button @click="removeLabel(label.id)" class="ml-2 text-red-500 hover:text-red-700">x</button>
              </span>
            </div>
            <form @submit.prevent="addLabel" class="flex flex-col space-y-4">
              <div class="flex flex-col md:flex-row md:items-center space-y-4 md:space-y-0 md:space-x-4">
                <input v-model="newLabelName" placeholder="Label Name" required class="px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500 w-full md:w-auto"/>
                <input v-model="newLabelColor" type="color" required class="h-10 w-10 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"/>
              </div>
              <button type="submit" class="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg">Add Label</button>
            </form>
          </div>
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
            <li v-for="comment in comments" :key="comment.id" class="mb-4 flex justify-between">
              <div>
                <p class="text-gray-700"><strong>{{ comment.user.username }}:</strong> {{ comment.content }}</p>
                <p class="text-gray-500">{{ new Date(comment.createdDate).toLocaleDateString() }}</p>
              </div>
              <div v-if="comment.user.id === userStore.user.id" class="ml-4">
                <button @click="deleteComment(comment.id)" class="text-red-500 hover:text-red-700">Delete</button>
              </div>
            </li>
          </ul>
          <button v-if="comments.length < totalComments" @click="loadMoreComments" class="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg w-full mt-4">Load More</button>
        </div>
      </div>
      <div v-else class="text-center">
        <p>No task data available.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '~/stores/user'
import { useAsyncData } from '#app'

const route = useRoute()
const userStore = useUserStore()
const taskId = route.params.id
const commentText = ref('')

const newLabelName = ref('')
const newLabelColor = ref('#000000')
const comments = ref([])
const totalComments = ref(0)
const currentPage = ref(0)

// Fetching task data
const { data: task, pending: pendingTask, error: errorTask } = await useAsyncData(`task-${taskId}`, async () => {
  if (userStore.token) {
    const { data, error } = await useMyFetch(`/tasks/${taskId}`, {
      headers: {
        'Authorization': `Bearer ${userStore.token}`
      }
    })
    if (error.value) {
      console.error('Error fetching task data:', error.value)
      return {}
    }
    return data.value ?? {}
  }
  return {}
})

// Fetching initial comments data
const { data: initialCommentData, pending: pendingComments, error: errorComments } = await useAsyncData(
  `comments-task-${taskId}-page-0`,
  async () => {
    if (userStore.token) {
      const { data, error } = await useMyFetch(`/comments/task/${taskId}?page=0&size=5`, {
        headers: {
          'Authorization': `Bearer ${userStore.token}`
        }
      })
      if (error.value) {
        console.error('Error fetching comments:', error.value)
        return { content: [], totalElements: 0 }
      }
      return data.value ?? { content: [], totalElements: 0 }
    }
    return { content: [], totalElements: 0 }
  }
)

// Handling initial comments loading
if (initialCommentData.value) {
  comments.value = initialCommentData.value.content
  totalComments.value = initialCommentData.value.totalElements
  currentPage.value = 1
}

// Function to load more comments
const loadMoreComments = async () => {
  try {
    const { data, error } = await useMyFetch(`/comments/task/${taskId}?page=${currentPage.value}&size=5`, {
      headers: {
        'Authorization': `Bearer ${userStore.token}`
      }
    })
    if (error.value) {
      console.error('Error fetching comments:', error.value)
    } else {
      comments.value = [...comments.value, ...data.value.content]
      totalComments.value = data.value.totalElements
      currentPage.value += 1
    }
  } catch (error) {
    console.error('Fetch error:', error)
  }
}

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
      comments.value.push(data.value)
      commentText.value = ''
    }
  } catch (error) {
    console.error('Fetch error:', error)
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
      comments.value = comments.value.filter(comment => comment.id !== commentId)
      totalComments.value -= 1
    }
  } catch (error) {
    console.error('Fetch error:', error)
  }
}

const addLabel = async () => {
  try {
    const { data, error } = await useMyFetch('/labels', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${userStore.token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        name: newLabelName.value,
        color: newLabelColor.value,
        taskId: taskId
      })
    })

    if (error.value) {
      console.error(error.value)
    } else {
      task.value.labels.push(data.value)
      newLabelName.value = ''
      newLabelColor.value = '#000000'
    }
  } catch (error) {
    console.error('Fetch error:', error)
  }
}

const removeLabel = async (labelId) => {
  try {
    const { error } = await useMyFetch(`/labels/${labelId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${userStore.token}`
      }
    })

    if (error.value) {
      console.error(error.value)
    } else {
      task.value.labels = task.value.labels.filter(label => label.id !== labelId)
    }
  } catch (error) {
    console.error('Fetch error:', error)
  }
}
</script>
