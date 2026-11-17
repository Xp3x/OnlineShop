<template>
  <nav aria-label="Hauptnavigation">
    <img src="/logo.png" alt="logo" class="logo">
    <RouterLink to="/" >Home</RouterLink>
    <RouterLink to="/products">Produkte</RouterLink>
    <RouterLink to="/about">Über uns</RouterLink>

    <div v-if="isAdmin && isOnline ">
      <RouterLink to="/admin">Admin</RouterLink>
    </div>
  </nav>


  <nav>
    <RouterLink class="usericon" aria-label="Benutzerkonto" :to="{name: 'users'}">
      <img src="/Usericon.png" alt="" width="50" height="50">
    </RouterLink>


    <RouterLink class="warenkorb" aria-label="warenkorb"  :to="{name: 'warenkorb' }">
      <img src="/warenkorb.jpg" alt="" width="50" height="50">
    </RouterLink>
  </nav>


  <main>
    <RouterView />
  </main>



</template>

<script setup lang="ts">
import {onMounted, ref, computed} from "vue"
import { apiFetch } from "./lib/api";



const currentUser = ref(null)


onMounted(async () => {
  try{

    const userData = await apiFetch(`/api/user`)
    currentUser.value = userData ?? null;
    console.log("currentUser:", currentUser.value);

  }catch(error){
    console.log(error)
  }
}
)

const isAdmin = computed(() => {
  const u = currentUser.value;
  return !!u && (u.admin === true);
});

const isOnline = computed(() => {
  const u = currentUser.value;
  return !!u && (u.onlineStatus === true);
})


</script>

<style scoped>
nav[aria-label="Hauptnavigation"]{
  width: 200px;
  height: 100vh;
  background-color: #b2b4b2;
  position: fixed;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 10px;
}



.usericon {
  position: fixed;
  top: 2px;
  right: 20px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  cursor: pointer;
}
.warenkorb {
  position: fixed;
  top: 2px;
  right: 70px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  cursor: pointer;
}
.warenkorb.router-link-active {
  background: transparent !important;
  color: inherit !important;
}
.usericon.router-link-exact-active {
  color: inherit !important;
  background: transparent !important;

}




</style>