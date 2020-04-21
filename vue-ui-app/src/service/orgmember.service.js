
import axios from './httputil'
import api from '../api/api.info'
import qs from 'qs'
import { noticeMsg } from '../common/common.service'


export function listOrgMemberPage(opt) {
    axios.get(api.orgMemberList + '?' + qs.stringify(opt.params))
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}

export function createOrgMember(opt){
    axios
    .post(api.orgMemberAdd,opt.params)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild(error))
}

export function editOrgMember(opt){
    axios
    .post(api.orgMemberUpdate,opt.params)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild(error))
}