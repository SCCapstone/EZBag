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
    component: () => import('@/components/EmptyRouterView.vue'),
    children: [
      {
        path: '',
        components: {
          navigation: () => import('@/components/SplashNavigation.vue'),
          default: () => import('@/views/Splash.vue')
        },
      },
      {
        path: 'about',
        components: {
          navigation: () => import('@/components/SplashNavigation.vue'),
          default: () => import('@/views/OurTeam.vue')
        },
      },
      {
        path: 'login',
        name: 'login',
        components: {
          navigation: () => import('@/components/SplashNavigation.vue'),
          default: () => import('@/views/merchant/BusinessLogin.vue')
        },
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
        path: 'register',
        components: {
          navigation: () => import('@/components/SplashNavigation.vue'),
          default: () => import('@/views/merchant/BusinessRegister.vue')
        },
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
    ]
  },
  {
    path: '/customer/:id/',
    component: () => import('@/components/EmptyRouterView.vue'),
    children: [
      {
        path: 'scan',
        components: {
          navigation: () => import('@/components/CustomerNavigation.vue'),
          default: () => import('@/views/customer/Scan.vue')
        },
      },
      {
        path: 'cart',
        components: {
          navigation: () => import('@/components/CustomerNavigation.vue'),
          default: () => import('@/views/customer/Cart.vue')
        },
      },
      {
        path: 'receipt',
        component: () => import('@/views/customer/ReceiptPage.vue')
      }
    ]
  },
  {
    path: '/merchant/:id/',
    component: () => import('@/components/EmptyRouterView.vue'),
    children: [
      {
        path: 'statistics',
        components: {
          navigation: () => import('@/components/MerchantNavigation.vue'),
          default: () => import('@/views/merchant/SalesDashboard.vue')
        },
      },
      {
        path: 'store',
        name: 'store',
        components: {
          navigation: () => import('@/components/MerchantNavigation.vue'),
          default: () => import('@/views/merchant/Store.vue')
        },
      },
      {
        path: 'products',
        components: {
          navigation: () => import('@/components/MerchantNavigation.vue'),
          default: () => import('@/views/merchant/Scanner.vue')
        },
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
    path: '/404',
    name: '404',
    component: () => import('@/views/customer/404.vue')
  },
  {
    path: '/NotAuthorized',
    name: 'NotAuthorized',
    component: () => import('@/views/customer/NotAuth.vue')
  },
]

const router = new VueRouter({
  routes
})



export default router
