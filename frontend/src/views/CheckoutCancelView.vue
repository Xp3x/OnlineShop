<script setup>
import { useRoute } from "vue-router"
const route = useRoute()
</script>

<template>
  <main class="container">
    <h1>Zahlung abgebrochen</h1>
    <p>Bestellvorgang wurde abgebrochen (Order-ID: {{ route.query.orderId }}).</p>
    <router-link class="button" to="/cart">Zurück zum Warenkorb</router-link>
  </main>
</template>
