<script setup>
import { apiFetch } from '../lib/api';
import {onMounted, ref} from "vue";
import router from "../router/index.js";



const products = ref([])
const loading =ref(true)
const error=ref(null)
const brands = ref([])
const product = ref([])


onMounted(async () => {
  try{
    const data = await apiFetch(`/api/products`);
    products.value = data;

    const uniqueBrands = new Set(products.value.map(p => p.brand));
    brands.value = Array.from(uniqueBrands);

  } catch (error) {
    error.value = error instanceof Error ? error.message : String(error)
    console.error(error)
  } finally {
    loading.value = false
  }

})
function goBack() {
  router.back()
}


</script>

<template>
  <main>
    <button class="button" @click="goBack">← Zurück</button>
    <h1>Unsere Produkte</h1>

    <p v-if="loading">Lade Produkte....</p>
    <p v-else-if="error"> Fehler: {{error}}</p>



    <div v-for="brand in brands" :key="brand">
      <h3>{{brand}}</h3>
      <div class="products-grid">
        <div v-for="product in products.filter(p => p.brand === brand)" class="produktkarte">
          <img class="products-img"
               :src="product.imageUrl"
               :alt="product.name"
               @error="e => (e.target.src = '/images/placeholder.png')"
          />
          <h3>{{product.name}}</h3>
          <p>{{product.price}} $</p>

          <RouterLink class="button" :to="{name: 'product-detail', params: {id: product.id}} ">
            zum Angebot
          </RouterLink>
        </div>

      </div>




    </div>


  </main>
</template>

<style scoped>

</style>