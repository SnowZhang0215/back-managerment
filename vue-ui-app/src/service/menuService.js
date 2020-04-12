import storage from './storge'
import axios from './httputil'
import api from '../api/api.info'
import { generateRouter } from '../router/router.service'
import store from '../store/index'
import { noticeMsg } from '../common/common.service'
export function loaduserMenu(callback) {
    console.log("load user menus")
    if (storage.getValue("userMenus")) {
        const menuData = storage.getValue("userMenus")
        generateRouter(menuData)
    } else {
        axios.get(api.userMenus).then(
            response => getUserMenus(response.data)
        ).catch(error => console.error(error.toString()))
    }
    function getUserMenus(response) {
        storage.setValue("userMenus", response)
        store.dispatch("menuData", response);
        generateRouter(response);
        if (callback) {
            callback();
        }
    }
}
export function getPermissionBtns(sourceData, code) {
    let result = []
    sourceData.forEach(element => {
        getSubMenus(result, element, code);
    });
    return result;

    function getSubMenus(result, arrayData, code) {
        if (arrayData.children) {
            arrayData.children.forEach(item => {
                getSubMenus(result, item, code)
            })
        }
        if (arrayData.code === code) {
            if (arrayData.children) {
                arrayData.children.forEach(element => {
                    if (element.permissionType == '2') {
                        result.push(element)
                    }
                })
            }
        }
    }
}
export function getSubMenuByParentCode(sourceData, code) {
    let result = []
    sourceData.forEach(element => {
        getSubMenus(result, element, code);
    });
    return result;

    function getSubMenus(result, arrayData, code) {
        if (arrayData.children) {
            arrayData.children.forEach(item => {
                getSubMenus(result, item, code)
            })
        }
        if (arrayData.code === code) {
            if (arrayData.children) {
                arrayData.children.forEach(element => {
                    if (element.permissionType == '1') {
                        result.push(element)
                    }
                })
            }
        }
    }
}
export function getAllPermission(opt) {
    axios.get(api.menuManageList)
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild(error))
}
export function getSubMenusByParentId(opt) {
    axios.post(api.menuManageSubMenus, opt.params)
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild(error))
}
export function getPermissionDetail(opt) {
    axios.get(api.menuDetail + '/' + opt.id)
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}
export function createPermission(opt) {
    axios.post(api.menuManageAdd, opt.params)
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}

export function updatePermission(opt){
    axios.post(api.menuManageUpdate, opt.params)
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}

export function deletePermission(opt) {
    axios.post(api.menuManageDelete, opt.params)
        .then(response => opt.onSuccess(response))
        .catch(error => opt.onFaild ? opt.onFaild(error) : noticeMsg(error, true))
}