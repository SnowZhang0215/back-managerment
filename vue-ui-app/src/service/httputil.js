import axios from 'axios';
import router from '../router';
import storage from './storge';
import { noticeMsg } from '../common/common.service'
import { showLoading, closeLoading } from '../common/loading.service'

axios.defaults.timeout = 5000;
axios.defaults.baseURL = '';

//http request 拦截器
axios.interceptors.request.use(
  config => {
    showLoading()
    const token = storage.getValue("access_token");
    if (token != undefined) {
      config.headers = {
        Authorization: token.token_type + " " + token.access_token
      }
    }
    return config;
  },
  error => {
    closeLoading()
    return Promise.reject(error);
  }
);


//http response 拦截器
axios.interceptors.response.use(
  response => {
    closeLoading()
    if (response.status === 200) {
      return Promise.resolve(response.data);
    } else {
      return Promise.reject(response);
    }
  },
  error => {
    closeLoading()
    if (error.response.status) {
      console.log(error.response)
      noticeMsg(error, true)
    }
    return Promise.reject(error)
  }
);
export default axios
