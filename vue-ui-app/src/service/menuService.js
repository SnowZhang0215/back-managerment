import storage from './storge'
import axios from './httputil'
import api from '../api/api.info'
import {generateRouter} from '../router/router.service'
export function loaduserMenu() {
    console.log("load user menus") 
    if (storage.getValue("userMenus")) {
        const menuData = storage.getValue("userMenus")
        generateRouter(menuData)
      } else {
         axios.get(api.userMenus).then(
          response => getUserMenus(response.data)
        ).catch(error => console.error(error.toString()))
    }
    function getUserMenus(response){
        storage.setValue("userMenus",response)
        generateRouter(response);
    }
}
export function getSubMenuByParentCode(sourceData,code){
    let result = []
    sourceData.forEach(element => {
        getSubMenus(result,element,code);
    });
    return result;

    function getSubMenus(result,arrayData,code){
        if(arrayData.children){
            arrayData.children.forEach(item =>{
                getSubMenus(result,item,code)
            })
        }
        if(arrayData.code === code){
            if(arrayData.children){
                arrayData.children.forEach(element => {
                    result.push(element)
                })
            }
        }
    }
}
export function getAllPermission(opt){
    axios.get(api.menuManageList)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild(error))
}
export function getSubMenusByParentId(opt){
    axios.post(api.menuManageSubMenus,opt.params)
    .then(response => opt.onSuccess(response))
    .catch(error => opt.onFaild(error))
}