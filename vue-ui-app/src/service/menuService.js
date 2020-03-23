import storage from './storge'
import axios from './httputil'
import api from '../api/api.info'
export function loaduserMenu(onSuccess) {
    console.log("load user menus") 
    if (storage.getValue("userMenus")) {
        const menuData = storage.getValue("userMenus")
        onSuccess(menuData)
      } else {
         axios.get(api.userMenus).then(
          response => getUserMenus(response.data,onSuccess)
        ).catch(error => console.error(error.toString()))
    }
    function getUserMenus(response,onSuccess){
        storage.setValue("userMenus",response)
        onSuccess(response);
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