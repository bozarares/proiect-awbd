<template>
	<div class="flex items-start min-h-screen">
		<div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
			<h1 class="text-2xl font-bold mb-6 text-center">Login</h1>
			<form @submit.prevent="login" class="space-y-4">
				<div>
					<label for="username" class="block text-gray-700">Username</label>
					<input
						v-model="username"
						id="username"
						placeholder="Username"
						class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
					/>
					<p v-if="errors.username" class="text-red-500 text-sm mt-1">
						{{ errors.username }}
					</p>
				</div>
				<div>
					<label for="password" class="block text-gray-700">Password</label>
					<input
						type="password"
						v-model="password"
						id="password"
						placeholder="Password"
						class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
					/>
					<p v-if="errors.password" class="text-red-500 text-sm mt-1">
						{{ errors.password }}
					</p>
					<p v-if="errors.general" class="text-red-500 text-sm mt-1">
						{{ errors.general }}
					</p>
				</div>
				<button
					type="submit"
					class="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-lg transition duration-300"
				>
					Login
				</button>
			</form>
		</div>
	</div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useState } from "#app";
import { useUserStore } from "~/stores/user";

const username = ref("");
const password = ref("");
const errors = ref({
	username: "",
	password: "",
	general: "",
});
const user = useState("user");
const router = useRouter();
const userStore = useUserStore();

const login = async () => {
	errors.value = {
		username: "",
		password: "",
		general: "",
	};

	try {
		const response = await $fetch("	http://localhost:8080/api/auth/login", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({ username: username.value, password: password.value }),
		});
		userStore.setToken(response.token);
		userStore.setUser(response.user);
		router.push("/");
		user.value = response;
	} catch (error) {
		if (error.data && error.data) {
			const errorData = error.data;
			if (errorData.username) {
				errors.value.username = errorData.username;
			}
			if (errorData.password) {
				errors.value.password = errorData.password;
			}
			if (errorData.error) {
				errors.value.general = errorData.error;
			}
		} else {
			errors.value.general = "An unexpected error occurred";
		}
	}
};
</script>