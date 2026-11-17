<script setup>
import {ref, onMounted, computed} from "vue";
import {useRoute, useRouter} from "vue-router"
import { apiFetch } from '../lib/api';
import { getClientId} from "../lib/token.js";

const route = useRoute()
const router = useRouter()
const product= ref(null)
const loading = ref(true)
const error = ref(null)
const id = route.params.id
const comment = ref("")
const commentsAll = ref([])
const usersAll = ref([])



async function getComments() {
  const commentData = await apiFetch(`/api/comment`)
  commentsAll.value = commentData
}

async function getUsers() {
  const usersData = await apiFetch(`/api/user`);

  let result = [];

  if (Array.isArray(usersData)) {
    result = usersData;
  } else if (Array.isArray(usersData?.content)) {
    result = usersData.content;
  } else if (usersData && typeof usersData === 'object') {
    result = [usersData];
  } else {
    console.warn("Unerwartetes User-Format:", usersData);
    result = [];
  }

  usersAll.value = result;
}



const currentUser = computed(() => {
  const list = Array.isArray(usersAll.value) ? usersAll.value : [];
  const cid = String(getClientId());
  return list.find(u => String(u.clientId) === cid) ?? null;
});



onMounted(async () => {
  try {
    const productData = await apiFetch(`/api/products/${id}`)
    product.value = productData ?? null;
    await getComments()
    await getUsers()


  } catch (e) {
    error.value = e instanceof Error ? e.message : String(e) //sinnvoller error auswerfen
  } finally {
    loading.value = false
  }
})




function goBack() {
  router.back()
}

const size = ref(null)

async function addToCart() {
  const productId = product.value.id
  const selectedSize = Number(size.value)

  if (!productId || !selectedSize) {
    console.error('Es fehlt die ProduktId oder Größe')
    return
  }
  await apiFetch('/api/warenkorb', {
    method: 'POST',
    body: JSON.stringify({ productId, size: selectedSize }),
  });
  alert("Produkt wurde dem Warenkorb hinzugefügt!")

}




async function addComment() {
  const text = comment.value.trim();
  if(!text){
    alert("Bitte ein Kommentar eingeben.")
    return;
  }
  if(currentUser.value === null){
    alert("Bitte Registrieren!")
    return;
  }
  if(currentUser.value.onlineStatus === false){
    alert("Bitte melden Sie sich an.")
    return;
  }
  try{
    await apiFetch('/api/comment', {
      method: 'POST',
      body: JSON.stringify({
        comment: text,
        productId: product.value.id
      })
    })
    await getComments()
    comment.value ="";

  } catch (e) {
    console.error(e);
    alert("Kommentar konnte nicht gespeichert werden.")
  }
}



</script>

<template>
  <main>
    <button class="button" @click="goBack">← Zurück</button>

    <p v-if="loading">Lade Produkte....</p>
    <p v-else-if="error"> Fehler: {{error}}</p>

    <div v-if="product" class="product-detail-container">
      <div class="image-price-title">
        <img class="products-img-detail"
             :src="product.imageUrl"
             :alt="product.name"
             @error="e => (e.target.src = '/images/placeholder.png')"
        />
        <div class="title-price">
          <h1> {{product.name}}</h1>
          <h2 class="Preis">{{product.price}}$</h2>
        </div>
      </div>
      <div class="product-info">
        <table class="detail-info">
          <tbody>
          <tr>
            <th>Stackhöhe:</th>
            <td>{{product.stack_heel_mm}} mm</td>
          </tr>
          <tr>
            <th>Drop:</th>
            <td>{{product.drop_mm}} mm</td>
          </tr>
          <tr>
            <th>Gewicht:</th>
            <td>{{product.weight_g}} g</td>
          </tr>
          <tr>
            <th>Geeignet für Untergrund:</th>
            <td>{{product.surface}}</td>
          </tr>

          </tbody>
        </table>
        <div class="groessen-auswahl">

          <label class="groessen-label">Wähle deine Größe </label>
          <select v-model="size" id="size" class="groessen-auswahl-dropdown">
            <option v-for="groesse in product.groesse" :key="groesse" :value="groesse">
              {{groesse}}
            </option>
          </select>

        </div>
        <button @click="addToCart" class="button">Zum Warenkorb hinzufügen</button>



      </div>


    </div>

    <div v-if="product">
      <h2>{{product.beschreibung}}</h2>
    </div>

    <div v-if="loading === false">
      <section class="Comment-form">
        <label for="comment">Hier können Sie einen Kommentar zu den Modelle hinterlassen:</label>
        <input v-model="comment" placeholder="Kommentar">

        <div>
          <button @click="addComment(comment, product)" class="button">Kommentar hinzufügen</button>
        </div>

        <div v-for="c in commentsAll.filter(x => x.productId === product.id) ">
          <h4>{{c.username}}</h4>
          <p>{{c.comment}}</p>

        </div>




      </section>

    </div>













  </main>


</template>

<style scoped>

</style>