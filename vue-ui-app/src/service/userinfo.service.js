import axios from './httputil'
import api from '../api/api.info'
import store from './storge'
export function getUserInfo(onUserInfoOk,onUserInfoError){
    axios.get(api.userInfo)
    .then(response => onUserInfoOk(response.data))
    .catch(error => onUserInfoError(error))
}