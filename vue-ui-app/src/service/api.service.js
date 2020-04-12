import axios from './httputil'
import api from '../api/api.info'
import qs from 'qs'

export function queryApiByDesc(query){
    axios
    .get(api.apiManagerQuery+'?' + qs.stringify(query.params))
    .then(response => query.onSuccess(response))
    .catch(error => query.onFaild(error))
}
export function listApiEntity(opt){
    axios
    .get(api.apiManagerList+'?' + qs.stringify(opt.params))
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild(error))
}

export function createApiEntity(opt){
    axios
    .post(api.apiManagerAdd,opt.params)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild(error))
}

export function editApiEntity(opt){
    axios
    .post(api.apiManagerEdit,opt.params)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild(error))
}