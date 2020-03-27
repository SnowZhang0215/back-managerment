import axios from 'axios';
import router from '../router';
import storage from './storge';
import {noticeMsg} from '../common/common.service'

axios.defaults.timeout = 5000;
axios.defaults.baseURL ='';


//http request 拦截器
axios.interceptors.request.use(
  config => {
    // const token = getCookie('名称');注意使用的时候需要引入cookie方法，推荐js-cookie
    // config.data = JSON.stringify(config.data);
    // config.headers = {
    //   'Content-Type':'application/x-www-form-urlencoded'
    // }
    const token = storage.getValue("access_token");
    if (token!=undefined){
      config.headers = {
        Authorization: token.token_type + " " + token.access_token
      }
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);


//http response 拦截器
axios.interceptors.response.use(
  response => {
    if (response.status === 200) {
      return Promise.resolve(response.data);
    } else {
      return Promise.reject(response);
    }
  },
  error => {
    if (error.response.status){
      console.log(error.response)
      if(!error.response){
        noticeMsg(error)
      }
        switch (error.response.status) {
        case 401:
          router.replace({
            path: '/login',
            query: {
              redirect: router.currentRoute.fullPath
            }
          });
          break;
          
          
        // case 403:
        //   router.replace({
        //     path: '/forbidden',
        //     query: {
        //       redirect: router.currentRoute.fullPath
        //     }
        //   });
        //   break;
        // case 404:
        //   router.replace({
        //     path: '/notfound',
        //     query: {
        //       redirect: router.currentRoute.fullPath
        //     }
        //   });
        //   break;
        // case 500:
          
          // router.replace({
          //   path: '/error',
          //   query: {
          //     redirect: router.currentRoute.fullPath
          //   }
          // });
          // console.error(error)
          // break;
      }
      noticeMsg(error,true)
    }
    return Promise.reject(error)
  }
);
export default axios
