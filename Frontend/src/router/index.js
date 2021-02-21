import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Scan',
    component: () => import('@/views/customer/Scan.vue')
  },
  {
    path: '/cart',
    name: 'Cart',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/customer/Cart.vue')
  },
  {
    path: '/receipt',
    name: 'Receipt',
    component: () => import('@/views/customer/ReceiptPage.vue')
  },
  {
    path: '/registrationSuccess',
    name: 'SuccessRegister',
    component: () => import('@/views/merchant/SignupSuccess.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/merchant/BusinessLogin.vue')
  },
  {
    path: '/onboard',
    name: 'Onboard',
    component: () => import('@/views/merchant/Onboard.vue')
  },
  {
    path: '/statistics',
    name: 'Statistics',
    component: () => import('@/views/merchant/Stats.vue')
  },
  {
    path: '/store',
    name: 'Store',
    component: () => import('@/views/merchant/Store.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
