import storage from './storge'
import qs from 'qs'
import axios from './httputil'
import api from '../api/api.info'
import router from '../router/index'
import {clearRouter} from '../router/router.service'
export function login(loginInfo,onLoginSuccess,onLoginFailed){
    if (storage.getValue("access_token")) {
        storage.deleteItem("access_token")
    }
    const paramsStr = qs.stringify(loginInfo);
    console.log(paramsStr)
    axios.post(api.loginUrl,paramsStr)
    .then(response => onLoginSuccess(response))
    .catch(error => onLoginFailed(error));
}
export function logout(){
    if (!storage.getValue("access_token")) {
        router.push({
            name: 'login'
        })
    }else{
        axios.post(api.logout)
        .then(response => logoutOk(response))
        .catch(error => onLogoutError(error))
    }
    function logoutOk(data){
        console.log(data)
        if(data.errorCode === 200){
            storage.deleteItem("access_token")
            storage.deleteItem("userInfo")
            storage.deleteItem("userMenus")
            router.push({
                name: 'login'
            })
        }
    }
    function onLogoutError(error){
        storage.deleteItem("access_token")
        storage.deleteItem("userInfo")
        storage.deleteItem("userMenus")
        router.push({
            name: 'login'
        })
    }
}