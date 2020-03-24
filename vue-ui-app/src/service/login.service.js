import storage from './storge'
import qs from 'qs'
import axios from './httputil'
import api from '../api/api.info'
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