<script setup>
import {computed, onMounted, ref} from "vue";
import { apiFetch } from '../lib/api';
import { getClientId } from "../lib/token"
import router from "../router/index.js";


const warenkorb = ref([])
const products = ref([])
const productIds = ref([])
const error = ref(null)
const loading = ref(true)
const paying = ref(false)


async function fetchWarenkorb() {
  const items = await apiFetch('/api/warenkorb');
  warenkorb.value = items;

  productIds.value = [...new Set(items.map(item => item.productId))];
}


async function fetchProducts(productIds) {
  if (productIds.length ===0) {return; }

  const results = await Promise.all(productIds.map(async (id) => {
    const product = await apiFetch(`/api/products/${id}`);
    return product;

  }))
  products.value = results.filter(Boolean);


}

const combined = computed(() =>
    warenkorb.value.map(item => {
      const product = products.value.find(p => p.id === item.productId);
      return {
        cartId: item?.id,
        productId: item?.productId,
        size: item?.size ?? null,
        name: product?.name ?? "Unbekannt",
        price: product?.price ?? 0,
        imageUrl: product?.imageUrl ?? null,
      };
    })
);

async function deleteFromCart(cartId) {
  try {
    await apiFetch(`/api/warenkorb/${cartId}`, { method: 'DELETE' });


    await fetchWarenkorb()
    await fetchProducts(productIds.value);
  } catch (error) {
    console.log(error)
    alert("Produkt konnte nicht entfernt werden", error.message)
  }
}


const totalCartPrice = computed(() =>
    combined.value.reduce((sum, item) => sum + Number(item.price ?? 0),0)
)

async function startPayPalCheckout() {
  console.log("X-Client-Id:", getClientId())
  error.value = null
  paying.value = true
  try {

    const res = await apiFetch("/api/checkout/paypal/create", {
      method: "POST",
      headers: {
        "X-Client-Id": getClientId()
      }
    })



    if (!res?.url) throw new Error("Antwort ohne URL")
    // Browser zu PayPal schicken
    window.location.href = res.url
  } catch (e) {
    console.error(e)
    error.value = "Checkout konnte nicht gestartet werden."
  } finally {
    paying.value = false
  }
}






onMounted(async () => {
  try{
    await fetchWarenkorb()
    await fetchProducts(productIds.value)

    console.log("Products:", products.value);

  }catch(error){
      error.value = error instanceof Error ? error.message:String(error)
  }finally {
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
    <h1>Warenkorb:</h1>
    <h2 v-if="totalCartPrice >= 0">Zwischensumme: {{totalCartPrice}} $</h2>
    <p v-if="loading">Lade Warenkorb...</p>
    <p v-if="combined.length===0">Der Warenkorb ist leider leer......</p>
    <p v-else-if="error">{{error}}</p>


    <div class="products-grid">
      <div v-for="p in combined"  :key="p.cartId"  class="produktkarte">
        <img class="products-img"
             :src="p.imageUrl"
             :alt="p.name"
             @error="e => (e.target.src = '/images/placeholder.png')"
        />
        <h3>{{p.name}}</h3>
        <p>{{p.price}} $</p>
        <p>Ausgewählte Größe: {{p.size}}</p>
        <button @click="deleteFromCart(p.cartId)" class="button">Entfernen</button>

      </div>


    </div>

    <button class="button" :disabled="paying" @click="startPayPalCheckout">
      {{ paying ? "Weiterleitung zu PayPal..." : "Mit PayPal zahlen" }}
    </button>






  </main>

</template>

<style scoped>

</style>