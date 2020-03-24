import Vue from 'vue'
import Router from 'vue-router'
import MainContainer from '@/components/MainContainer'
import Login from '@/components/Login'

Vue.use(Router)
import store from '../store'

let router =  new Router({
  routes: [
    {
      path: '/',
      name: '/',
      redirect: '/index',
      component: MainContainer
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    }
  ]
})
router.beforeEach((to, from, next) => {
      next()
});
router.afterEach((to,from) =>{
    const result = to.fullPath.split('/');
    if(result.length>2){
      store.dispatch('asideActiveCode',result[2]);
    }
});
export default router;
