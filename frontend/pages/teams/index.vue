<template>
	<div class="p-8 min-h-screen">
		<div class="max-w-7xl mx-auto">
			<h1 class="text-3xl font-bold mb-6 text-center">Teams</h1>
			<NuxtLink to="/teams/create" class="button mb-6">Create new team</NuxtLink>
			<div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
				<div v-if="pending" class="text-center col-span-full">
					<p>Loading...</p>
				</div>
				<div
					v-else
					v-for="team in teams"
					:key="team.id"
					class="bg-white rounded-lg shadow-md p-6"
				>
					<NuxtLink
						:to="`/teams/${team.id}`"
						class="text-lg font-semibold hover:underline"
					>
						{{ team.name }}
					</NuxtLink>
					<p class="text-gray-600 mt-2">{{ team.description }}</p>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, watchEffect } from "vue";
import { navigateTo, defineNuxtRouteMiddleware, useAsyncData } from "#app";
import { useUserStore } from "~/stores/user";

const userStore = useUserStore();

defineNuxtRouteMiddleware((to, from) => {
	if (!userStore.token) {
		return navigateTo("/login");
	}
});

const {
	data: teams,
	pending,
	error,
} = await useAsyncData("fetch-teams", async () => {
	if (userStore.token) {
		const { data, error } = await useMyFetch("/teams", {
			headers: {
				Authorization: `Bearer ${userStore.token}`,
			},
		});

		if (error.value) {
			console.error("Error fetching teams:", error.value);
			throw new Error("Error fetching teams");
		}

		return data.value;
	}
});

watchEffect(() => {
	if (error.value) {
		console.error("Error during fetch:", error.value);
	}
});
</script>

<style scoped>
.button {
	display: inline-block;
	padding: 10px 20px;
	margin-bottom: 20px;
	background-color: #007bff;
	color: white;
	text-align: center;
	border-radius: 5px;
	text-decoration: none;
}
</style>
