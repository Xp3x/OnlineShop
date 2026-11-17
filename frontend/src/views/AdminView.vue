<script setup>
import {ref, onMounted} from "vue";
import {apiFetch} from "../lib/api.js";


const products = ref([])
const loading = ref(true);



onMounted(async() => {
  try{
    const productData = await apiFetch(`/api/products`)
    products.value = productData
  }catch(error){
    error.value = error instanceof Error ? error.message : String(error)
    console.error(error)
  }finally {
    loading.value = false
  }
})

async function changeContent(product) {
  const payload = {
    name:  product.newName ?? undefined,
    price: product.newPrice ?? undefined,
  };

  if (payload.name === undefined && payload.price === undefined) return;

  const updated = await apiFetch(`/api/products/${product.id}`, {
    method: "PATCH",
    body: JSON.stringify(payload),
  });

  if (updated) {
    if (payload.name !== undefined)  product.name  = updated.name  ?? product.newName;
    if (payload.price !== undefined) product.price = updated.price ?? product.newPrice;
  } else {

    if (payload.name !== undefined)  product.name  = product.newName;
    if (payload.price !== undefined) product.price = product.newPrice;
  }

  product.newName = "";
  product.newPrice = null;
}


</script>

<template>
<main>
  <h1>Control-Panel</h1>

  <p v-if="loading">Lade Produkte....</p>

  <div class="products-grid">
    <div v-for="product in products" class="produktkarte">
      <img class="products-img"
           :src="product.imageUrl"
           :alt="product.name"
           @error="e => (e.target.src = '/images/placeholder.png')"
      />
      <h3>{{product.name}}</h3>
      <p>{{product.price}} $</p>

      <input v-model="product.newName" placeholder="Name ändern..." />
      <input v-model.number="product.newPrice" placeholder="Preis ändern..." />

      <button class="button" @click="changeContent(product)">
        Produkt anpassen
      </button>


    </div>

  </div>







</main>



</template>

<style scoped>

</style>