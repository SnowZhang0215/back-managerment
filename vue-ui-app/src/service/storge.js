const vueStorage = {
  setValue(key,value){
    localStorage.setItem(key,JSON.stringify(value));
  },
  getValue(key){
    return JSON.parse(localStorage.getItem(key));
  },
  deleteItem(key){
    localStorage.removeItem(key);
  },
  clear(){
    localStorage.clear();
  }
}
export default vueStorage;
