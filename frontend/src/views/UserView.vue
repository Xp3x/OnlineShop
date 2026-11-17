<script setup>
import { ref, onMounted } from "vue";
import { apiFetch } from "../lib/api";

const loading = ref(true);
const login = ref(false);
const email = ref("");
const password = ref("");
const user = ref(null);


async function getUser() {
  user.value = await apiFetch("/api/user");
  login.value = user.value.onlineStatus;
}

async function loginInUser() {
  try {
    const res = await apiFetch('/api/auth/login', {
      method: 'POST',
      body: JSON.stringify({
        email: email.value,
        password: password.value
      })
    });

    window.location.reload();
  } catch (e) {
    console.error(e);
    alert("E-Mail oder Passwort falsch.");
  }
}


async function loginOutUser() {
  try {
    await apiFetch('/api/auth/logout', { method: 'POST' });
    window.location.reload();
  } catch (e) {
    console.error(e);
    alert("Abmelden fehlgeschlagen.");
  }
}


onMounted(async () => {
  try {
    await getUser();
  } finally {
    loading.value = false;
  }
});
</script>

<template>
  <main>
    <p v-if="loading">Lade…</p>

    <div v-if="!login" class="login">
      <form class="Login" @submit.prevent="loginInUser">
        <label for="email">E-mail:</label>
        <input v-model="email" id="email" type="email" placeholder="E-mail" required />

        <label for="password">Password:</label>
        <input v-model="password" id="password" type="password" placeholder="Password" required />

        <button class="button" type="submit">Anmelden</button>

        <RouterLink class="button" :to="{ name: `signup` }">Registrieren</RouterLink>
      </form>
    </div>

    <div v-if="login === true">
      <h1>Benutzer Profil</h1>
      <h2>Deine persönlichen Daten</h2>
      <h3>Name: {{user.name}}</h3>
      <h3>E-Mail: {{user.email}}</h3>
      <button class="button" @click="loginOutUser()">Abmelden</button>
    </div>
  </main>
</template>
