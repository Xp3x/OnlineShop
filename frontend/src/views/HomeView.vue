<script setup>
import {ref, onMounted} from "vue";
import { apiFetch } from '../lib/api';


const products = ref([])
const loading =ref(true)
const error=ref(null)


onMounted(async () => {
  try {
    const data = await apiFetch(`/api/products`);
    products.value = data;

  } catch (err) {
    error.value = err instanceof Error ? err.message : String(err)
    console.error(err)
  } finally {
    loading.value = false
  }
})




</script>

<template>
  <main>
    <h1> Running Online-Shop</h1>
    <h2>Im Angebot heißt für dich: Top-Schuhe zum kleinen Preis! Hol dir Markenqualität günstiger, teste neue Modelle und spare dabei bares Geld. So bleibt mehr Budget für deine nächsten Laufziele.</h2>
    <h3>Unsere Angebote:</h3>
    <p v-if="loading" role="status">Angebote werden geladen …</p>
    <p v-else-if="error" role="alert">Angebote konnten nicht geladen werden: {{ error }}</p>
    <div class="products-grid">
      <div v-for="p in products.filter(product => product.angebot === true)"  :key="p.id"  class="produktkarte">
        <img class="products-img"
             :src="p.imageUrl"
             :alt="p.name"
             @error="e => (e.target.src = '/images/placeholder.png')"
        />
        <h3>{{p.name}}</h3>
        <p>{{p.price}}$</p>


        <RouterLink class="button" :to="{name: 'product-detail', params: {id: p.id}} ">
          zum Angebot
        </RouterLink>


      </div>
    </div>

    <section class="container" >
      <h1 style="font-size:1.75rem; margin-bottom:1.5rem;">Release-Radar 2026</h1>
      <div style="display:grid; grid-template-columns:repeat(auto-fill, minmax(280px,1fr)); gap:1rem;">

        <article class="card" >
          <h3 style="margin-top:0;">Asics Trabuco 14</h3>
          <p>Januar 2026 – neues Obermaterial & überarbeitete Sohle.</p>
        </article>

        <article class="card">
          <h3>Asics Nimbus 28</h3>
          <p>Anfang 2026 – klassisches Update.</p>
        </article>

        <article class="card">
          <h3>Asics Magic Speed 5</h3>
          <p>Nachfolger der Version 4.</p>
        </article>

        <article class="card">
          <h3>Mizuno Wave Inspire 22</h3>
          <p>Januar 2026 – stabiler Allrounder.</p>
        </article>

        <article class="card">
          <h3>Mizuno Wave Rebellion Pro 4</h3>
          <p>Performance-Update nach Version 3.</p>
        </article>

        <article class="card">
          <h3>New Balance 880 V16</h3>
          <p>2026 – komplett überarbeitete Konstruktion.</p>
        </article>

        <article class="card">
          <h3>Nike ACG Ultrafly</h3>
          <p>Frühjahr 2026 – Trail-Modell.</p>
        </article>

        <article class="card">
          <h3>La Sportiva Ultra Raptor 3</h3>
          <p>Frühjahr 2026 – neuer Leisten, recyceltes Nylon.</p>
        </article>

        <article class="card">
          <h3>La Sportiva Prodigio 2</h3>
          <p>Frühjahr 2026 – Update im Mountain Running.</p>
        </article>

      </div>
    </section>












  </main>

</template>

<style scoped>

</style>
