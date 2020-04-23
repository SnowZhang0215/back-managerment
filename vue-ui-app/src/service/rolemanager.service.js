import axios from './httputil'
import api from '../api/api.info'
import qs from 'qs'
import { noticeMsg } from '../common/common.service'

export function loadAllRoles(opt){
    axios.get(api.roleManagerList+'?' + qs.stringify(opt.params))
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}

export function getSelectRole(opt){
    axios.get(api.roleGerRoleForUser)
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}

export function createRole(opt){
    axios
    .post(api.roleManagerAdd,opt.params)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}

export function editRole(opt){
    axios
    .post(api.roleManagerUpdate,opt.params)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}

export function loadRolePermission(opt){
    axios
    .get(api.roleManagerGetRolePermission + '/' + opt.params.id)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}

export function submitAuthRole(opt){
    axios
    .post(api.roleManagerAuthRole,opt.params)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}