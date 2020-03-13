// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import storage from './storge';
import router from './router';
import commonMethods from './common/commonutils';
import qs from 'qs';
// 导入Iview组件库
import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css';

import MainComponent from './components/MainComponent';


Vue.config.productionTip = false
Vue.use(ViewUI);
Vue.prototype.$qs = qs;
import myAxios from '../config/httputil';
import ApiInfo from '../config/ApiInfo';
// Vue.prototype.$axios = axios;
Vue.prototype.$axios = myAxios;
Vue.prototype.$storage = storage;
Vue.prototype.$api = ApiInfo;
Vue.prototype.$commonMethods = commonMethods;

/* eslint-disable no-new */


new Vue({
  el: '#app',
  router,
  components: { MainComponent },
  template: '<MainComponent/>',
  created: function () {
    if (this.$storage.getValue("userMenus")) {
      const menuData = this.$storage.getValue("userMenus");
      this.initMenuAndRouter(menuData)
    } else {
      this.$axios.get("api/user-service/menu/getUserMenus").then(
        response => this.initMenuAndRouter(response.data)
      ).catch(error => this.$Message.error(error.toString()))
    }
    console.log("on main create");
    console.log(commonMethods)
  },
  methods:{
    initMenuAndRouter(menuData){
      this.$storage.setValue("userMenus",menuData);
      const childrenRouter = [];
      const result = [{
        path:'/',
        component: () => import('./components/App.vue'),
        children: childrenRouter
      }];
      menuData.forEach(item => {
        generateRoutes(childrenRouter,item)
      });

      this.$router.addRoutes(result);

      console.log(childrenRouter)

      function generateRoutes(childrenRouter,item){
        if (item.children){
          item.children.forEach(e =>{
            generateRoutes(childrenRouter,e)
          })
        }
        if (item.url) {
          childrenRouter.push({
            path: item.url,
            name: item.code,
            component:  () => import('./components' + item.component)
          });
        }
      }
    }
  }
});






