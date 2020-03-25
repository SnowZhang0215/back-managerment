// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'
import axios from './service/httputil'
import storage from './service/storge'
import {loaduserMenu} from './service/menuService'
import store from './store'

Vue.config.productionTip = false

Vue.use(ElementUI)

Vue.prototype.$axios = axios;
Vue.prototype.$storage = storage;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>',
  created: function(){
    loaduserMenu()
    if(this.$storage.getValue("userInfo")){
      store.dispatch("setUserInfo", this.$storage.getValue("userInfo"));
    }
  },
  methods:{
   
  }
})
