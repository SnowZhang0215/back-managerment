const commonMethods = {
    getChildFromRootByCode(result,rootData,name){
        rootData.forEach(element => {
            this.findParams(result,element,name);
        });
        return result;
    },
    findParams(result,arrayData,name){
        if(arrayData.children){
           arrayData.children.forEach(item =>{
              this.findParams(result,item,name);
            })
        }
        if(arrayData.code === name){
          // result = arrayData.children
         if(arrayData.children){
           arrayData.children.forEach(element => {
             console.log(element);
             result.push(element);
           });
         }
        }
      },
}
export default commonMethods;
