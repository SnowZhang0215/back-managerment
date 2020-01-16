// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import storage from './storge';
import router from './router';
import qs from 'qs';
// 导入Iview组件库
import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css';

import MainComponent from './components/MainComponent';


Vue.config.productionTip = false
Vue.use(ViewUI);
Vue.prototype.$qs = qs;
import myAxios from '../config/httputil';
// Vue.prototype.$axios = axios;
Vue.prototype.$axios = myAxios;
Vue.prototype.$storage = storage;

/* eslint-disable no-new */


new Vue({
  el: '#app',
  router,
  components: { MainComponent },
  template: '<MainComponent/>',
  created(){
    console.log("on vue create");
    this.$router.addRoutes([
      {
        path: '/login',
        name: 'login',
        mate: '登录',
        component: () => import('./components/LoginComponent.vue')
      },
      {
        path: '/recover',
        name: 'recover',
        component: () => import('./components/RecoverComponent.vue')
      },
      {
        path: '/signup',
        name: 'signup',
        component: () => import('./components/SignUpComponent.vue')
      }
    ])
  }
});






