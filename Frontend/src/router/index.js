import Vue from 'vue'
import VueRouter from 'vue-router'
import debug from '@/mixins/debug'
const { methods } = debug
const { $dbg_console_log } = methods
import store from '../store/index.js'
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
      {
        path: 'statistics', 
        component: () => import('@/views/merchant/SalesDashboard.vue'),
      },
      {
        path: 'store', 
        name: 'store',
        component: () => import('@/views/merchant/Store.vue'),
      },
      {
        path: 'products', 
        component: () => import('@/views/merchant/Products.vue'),
      }
    ],
    beforeEnter: (to, from, next) => {
      $dbg_console_log(`ROUTER MESSAGE: ${from.path} to ${to.path}?`);
      $dbg_console_log(`Cookie set to: ${window.$cookies.get("token")}`);
      // TODO: authentication user
      if (window.$cookies.get("token") !== null) {
        store.dispatch("verifyToken").then( (result) => {
          $dbg_console_log("verifyToken result: ")
          $dbg_console_log(result)
          if (result.status=="success") {
            $dbg_console_log("verified token!")
            next();
          } else {
            $dbg_console_log("Token is not valid, redirecting to login")
            // this.$router.push("/login")
            next({ name: 'login' });
            window.$cookies.remove("token")
          }
        }).catch(error => {    
            $dbg_console_log(error)
            // this.$router.push("/login")
            next({ name: 'login' });
            window.$cookies.remove("token")
        })
      } else {
        $dbg_console_log("Cookie token is null, redirecting to login")
        // this.$router.push("/login")
        next({ name: 'login' });
      }
    },
  },
  {
    path: '/login', 
    name: 'login',
    component: () => import('@/views/merchant/BusinessLogin.vue'),
    beforeEnter: (to, from, next) => {
      console.log("Checking if merchant already logged in...")
      store.dispatch("verifyToken").then( (result) => {
        $dbg_console_log("verifyToken result: ")
        $dbg_console_log(result)
        if (result.status=="success") {
          $dbg_console_log("verified token!");
          next({ name: 'store' });
        } else {
          $dbg_console_log("Token is not valid, redirecting to login")
          window.$cookies.remove("token")
          next();
        }
      }).catch(error => {
          $dbg_console_log(error)
          window.$cookies.remove("token")
          next();       
      })
    }
  },
  {
    path: '/registrationSuccess',
    name: 'SuccessRegister',
    component: () => import('@/views/merchant/SignupSuccess.vue')
  },
  {
    path: '/verified',
    name: 'emailVerifiedSuccess',
    component: () => import('@/views/merchant/VerifiedSuccess.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/merchant/BusinessRegister.vue'),
    beforeEnter: (to, from, next) => {
      console.log("Checking if merchant already logged in...")
      store.dispatch("verifyToken").then( (result) => {
        $dbg_console_log("verifyToken result: ")
        $dbg_console_log(result)
        if (result.status=="success") {
          $dbg_console_log("verified token!");
          next({ name: 'store' });
        } else {
          $dbg_console_log("Token is not valid, redirecting to login")
          window.$cookies.remove("token")
          next();
        }
      }).catch(error => {
          $dbg_console_log(error)
          window.$cookies.remove("token")
          next();       
      })
    }
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/customer/404.vue')
  },
  {
    path: '/NotAuthorized',
    name: 'NotAuthorized',
    component: () => import('@/views/customer/NotAuth.vue')
  }
]

const router = new VueRouter({
  routes
})



export default router
