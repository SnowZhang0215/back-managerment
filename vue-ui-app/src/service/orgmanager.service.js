
import axios from './httputil'
import api from '../api/api.info'
import qs from 'qs'
import { noticeMsg } from '../common/common.service'


export function listOrgPage(opt) {
    axios.get(api.orgManagerList + '?' + qs.stringify(opt.params))
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}

export function createOrg(opt){
    axios
    .post(api.orgManagerAdd,opt.params)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild(error))
}

export function editOrg(opt){
    axios
    .post(api.orgManagerUpdate,opt.params)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild(error))
}

export function queryOrgByName(query){
    axios
    .post(api.orgManagerQuery,query.params)
    .then(response => query.onSuccess(response))
    .catch(error => query.onFaild(error))
}