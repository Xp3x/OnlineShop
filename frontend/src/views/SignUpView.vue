<script setup>
import { apiFetch } from "../lib/api.js";
import { ref } from "vue";
import { useRouter } from "vue-router";

const email = ref("");
const password = ref("");
const name = ref("");
const onlineStatus = ref(false);
const router = useRouter();

async function handleSubmit() {
  if (!email.value || !name.value || !password.value) {
    alert("Bitte alle Felder ausfüllen");
    return;
  }
  try {
    await apiFetch("/api/user", {
      method: "POST",
      body: JSON.stringify({
        email: email.value,
        password: password.value,
        name: name.value,
        onlineStatus: onlineStatus.value,
      }),
    });
    alert("User wurde erfolgreich angelegt!");
    router.back();
  } catch (e) {
    console.error(e);
    alert("Anlegen fehlgeschlagen");
  }
}
</script>

<template>
  <main>
    <div class="login">
      <form class="Login" @submit.prevent="handleSubmit">
        <label for="email">E-mail:</label>
        <input v-model="email" type="email" id="email" placeholder="E-mail" />
        <label for="name">Name:</label>
        <input v-model="name" type="text" id="name" placeholder="Name" />
        <label for="password">Passwort:</label>
        <input v-model="password" type="password" id="password" placeholder="Password" />
        <button class="button" type="submit">Anlegen</button>
      </form>
    </div>
  </main>
</template>
