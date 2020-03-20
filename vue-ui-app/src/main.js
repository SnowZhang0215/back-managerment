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
        generateRoutes(childrenRouter,item,"")
      });

      this.$router.addRoutes(result);
      

      console.log(childrenRouter)

      // function getStrTargetCount(str,target){
      //   var obj = {};
      //   for (var i = 0; i < str.length; i++) {
      //   var key = str[i];
      //   if (obj[key]) {
      //       obj[key]++;
      //     } else {
      //       obj[key] = 1;
      //     }
      //   }
      //   if(obj[target]){
      //     return obj[target]
      //   }else{
      //     return 0;
      //   }
      // }

      function generateRoutes(childrenRouter,item,parentPath){
        // console.log(getStrTargetCount(parentPath,'/'))
        if (item.children){ 
          parentPath = parentPath + "/" + item.url

          item.children.forEach(e =>{
            generateRoutes(childrenRouter,e,parentPath)
          })
        }
        if (item.url) {
          childrenRouter.push({
            path: parentPath ,
            name: item.code,
            component:  () => import('./components' + item.component)
          });
        }
      }
    }
  }
});






