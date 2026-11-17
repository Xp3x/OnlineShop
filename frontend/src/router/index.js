import {createRouter ,createWebHistory} from "vue-router";

import HomeView from "../views/HomeView.vue";
import ProductView from "../views/ProductView.vue";
import AboutView from "../views/AboutView.vue";
import ProductDetailView from "../views/ProductDetailView.vue";
import WarenkorbView from "../views/WarenkorbView.vue";
import UserView from "../views/UserView.vue";
import SignUpView from "../views/SignUpView.vue";
import CheckoutSuccessView from "../views/CheckoutSuccessView.vue";
import CheckoutCancelView from "../views/CheckoutCancelView.vue";
import AdminView from "../views/AdminView.vue";

const routes = [
    {path: "/", component: HomeView},
    {path: "/products", component: ProductView},
    {path: "/about", component: AboutView},
    {path: "/product/:id",name: 'product-detail', component: ProductDetailView, props: true},
    {path: "/warenkorb", name: "warenkorb", component: WarenkorbView},
    {path: "/users", name: "users", component: UserView},
    {path: "/signup", name: "signup", component: SignUpView},
    {path: "/success", name: "checkout-success", component: CheckoutSuccessView },
    {path: "/cancel",  name: "checkout-cancel",  component: CheckoutCancelView  },
    {path: "/admin", name: "admin", component: AdminView},
]
const router = createRouter({
    history: createWebHistory(),
    routes,
})
export default router;