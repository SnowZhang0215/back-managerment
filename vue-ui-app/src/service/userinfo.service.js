import axios from './httputil'
import api from '../api/api.info'
import qs from 'qs'
import { noticeMsg } from '../common/common.service'


export function getUserInfo(onUserInfoOk, onUserInfoError) {
    axios.get(api.userInfo)
        .then(response => onUserInfoOk(response.data))
        .catch(error => onUserInfoError(error))
}
export function listUserPage(opt) {
    axios
        .get(api.userManagerList + '?' + qs.stringify(opt.params))
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}

export function getUserDetail(opt) {
    axios.get(api.userManagerDetail + '/' + opt.id)
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}