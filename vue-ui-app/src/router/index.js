import Vue from 'vue'
import Router from 'vue-router'
import MainContainer from '@/components/MainContainer'
import Login from '@/components/Login'
import Recover from '@/components/RecoverPwd'
import Regester from '@/components/Regester'

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
      meta: {
        title: '登录'
      },
      component: Login
    },
    {
      path: '/recover',
      name: 'recover',
      meta: {
        title: '重置密码'
      },
      component: Recover
    },
    {
      path: '/regester',
      name: 'regester',
      meta: {
        title: '注册'
      },
      component: Regester
    }
  ]
})
router.beforeEach((to, from, next) => {
      next()
});
router.afterEach((to,from) =>{
    if(to.meta.title){
      document.title = to.meta.title
    }
    const result = to.fullPath.split('/');
    if(result.length>2){
      store.dispatch('asideActiveCode',result[2]);
    }
});
export default router;
