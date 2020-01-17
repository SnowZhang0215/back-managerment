import Vue from 'vue'
import Router from 'vue-router'

import App from '@/components/App';

Vue.use(Router)

const router = new Router({
    routes: [{
      path: '/',
      name: 'index',
      component: App,
      children:[]
    }]
  }
);

router.addRoutes([
  {
    path: '/login',
    name: 'login',
    mate: '登录',
    component: () => import('../components/LoginComponent.vue')
  },
  {
    path: '/recover',
    name: 'recover',
    component: () => import('../components/RecoverComponent.vue')
  },
  {
    path: '/signup',
    name: 'signup',
    component: () => import('../components/SignUpComponent.vue')
  }
])

export default router;
