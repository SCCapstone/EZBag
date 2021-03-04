import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('@/views/Splash.vue')
  },
  {
    path: '/customer/:id/',
    component: () => import('@/views/customer/Customer.vue'),
    children: [
      {path: 'scan', component: () => import('@/views/customer/Scan.vue')},
      {path: 'cart', component: () => import('@/views/customer/Cart.vue')},
      {path: 'receipt', component: () => import('@/views/customer/ReceiptPage.vue')}
    ]
  },
  {
    path: '/merchant/:id/',
    component: () => import('@/views/merchant/Merchant.vue'),
    children: [
      {path: 'login', component: () => import('@/views/merchant/BusinessLogin.vue')},
      {path: 'statistics', component: () => import('@/views/merchant/Stats.vue')},
      {path: 'store', component: () => import('@/views/merchant/Store.vue')}
    ]
  },
  {
    path: '/registrationSuccess',
    name: 'SuccessRegister',
    component: () => import('@/views/merchant/SignupSuccess.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/merchant/BusinessRegister.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
