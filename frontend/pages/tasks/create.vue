<template>
	<div class="p-8 bg-gray-100 min-h-screen">
		<NuxtLink :to="`/projects/${projectId}`" class="text-sm">Go Back</NuxtLink>
		<div class="max-w-lg mx-auto bg-white p-6 rounded-lg shadow-md">
			<h1 class="text-2xl font-bold mb-6">Create New Task</h1>
			<form @submit.prevent="createTask">
				<div class="mb-4">
					<label for="title" class="block text-gray-700 font-semibold mb-2">Title:</label>
					<input
						v-model="title"
						id="title"
						required
						class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
					/>
				</div>
				<div class="mb-4">
					<label for="description" class="block text-gray-700 font-semibold mb-2"
						>Description:</label
					>
					<textarea
						v-model="description"
						id="description"
						required
						class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
					></textarea>
				</div>
				<div class="mb-4">
					<label for="createdDate" class="block text-gray-700 font-semibold mb-2"
						>Created Date:</label
					>
					<input
						type="date"
						v-model="createdDate"
						id="createdDate"
						required
						class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
					/>
				</div>
				<div class="mb-4">
					<label for="dueDate" class="block text-gray-700 font-semibold mb-2"
						>Due Date:</label
					>
					<input
						type="date"
						v-model="dueDate"
						id="dueDate"
						required
						class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
					/>
				</div>
				<div class="mb-4">
					<label for="priority" class="block text-gray-700 font-semibold mb-2"
						>Priority:</label
					>
					<select
						v-model="priority"
						id="priority"
						required
						class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
					>
						<option value="">Select Priority</option>
						<option value="Urgent">Urgent</option>
						<option value="High">High</option>
						<option value="Moderate">Moderate</option>
						<option value="Low">Low</option>
					</select>
				</div>
				<div class="mb-4">
					<label for="status" class="block text-gray-700 font-semibold mb-2"
						>Status:</label
					>
					<select
						v-model="status"
						id="status"
						required
						class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
					>
						<option value="">Select Status</option>
						<option value="Pending">Pending</option>
						<option value="In Progress">In Progress</option>
						<option value="Completed">Completed</option>
					</select>
				</div>
				<button
					type="submit"
					class="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg w-full"
				>
					Create Task
				</button>
			</form>
		</div>
	</div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useUserStore } from "~/stores/user";
import { useMyFetch } from "~/composables/useMyFetch";

const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

const title = ref("");
const description = ref("");
const createdDate = ref("");
const dueDate = ref("");
const priority = ref("");
const status = ref("");
const projectId = route.query.projectId;

const createTask = async () => {
	try {
		const { data, error } = await useMyFetch("/tasks", {
			method: "POST",
			headers: {
				Authorization: `Bearer ${userStore.token}`,
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				title: title.value,
				description: description.value,
				createdDate: createdDate.value,
				dueDate: dueDate.value,
				priority: priority.value,
				status: status.value,
				project: { id: projectId }, // Include projectId in the request payload
			}),
		});

		if (error.value) {
			console.error(error.value);
		} else {
			router.push("/projects/" + projectId);
		}
	} catch (error) {
		console.error("Fetch error: ", error);
	}
};
</script>

