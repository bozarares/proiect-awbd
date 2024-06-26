<template>
	<div class="p-8 bg-gray-100 min-h-screen">
		<NuxtLink :to="`/tasks/${taskId}`" class="text-sm">Go Back</NuxtLink>
		<div class="max-w-lg mx-auto bg-white p-6 rounded-lg shadow-md">
			<h1 class="text-2xl font-bold mb-6">Edit Task</h1>
			<form @submit.prevent="editTask">
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
					Save Changes
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
import { useAsyncData } from "#app";

const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

const title = ref("");
const description = ref("");
const createdDate = ref("");
const dueDate = ref("");
const priority = ref("");
const status = ref("");

const taskId = route.params.id;
const projectId = ref(null);

// Fetch task data using useAsyncData
const {
	data: task,
	pending,
	error,
} = await useAsyncData(`task-${taskId}`, async () => {
	const { data, error } = await useMyFetch(`/tasks/${taskId}`, {
		headers: {
			Authorization: `Bearer ${userStore.token}`,
		},
	});
	if (error.value) {
		console.error("Error fetching task data:", error.value);
		throw new Error("Error fetching task data");
	}
	return data.value;
});

// Watch task data and update form fields
watchEffect(() => {
	if (task.value) {
		title.value = task.value.title;
		description.value = task.value.description;
		createdDate.value = task.value.createdDate;
		dueDate.value = task.value.dueDate;
		priority.value = task.value.priority;
		status.value = task.value.status;
		projectId.value = task.value.project.id;
	}
});

const editTask = async () => {
	try {
		const { data, error } = await useMyFetch(`/tasks/${taskId}`, {
			method: "PUT",
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
				project: { id: projectId.value },
			}),
		});

		if (error.value) {
			console.error(error.value);
		} else {
			router.push("/projects/" + projectId.value);
		}
	} catch (error) {
		console.error("Fetch error: ", error);
	}
};
</script>

