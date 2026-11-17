<script setup>

import { useRoute } from "vue-router"


const route = useRoute()



</script>

<template>
  <main class="container">
    <h1>Danke! Zahlung erfolgreich 🎉</h1>
    <p>Bestellnummer: <strong>{{ route.query.orderId }}</strong></p>
    <p>Du erhältst in Kürze eine Bestätigung.</p>
    <router-link class="button" to="/">Weiter shoppen</router-link>
  </main>
</template>
