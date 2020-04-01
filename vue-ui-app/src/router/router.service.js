import router from './index'
export function clearRouter(){
  let result = [{
    path:'/',
        redirect: '/index',
        component: () => import('../components/MainContainer'),
        children: []
  }]
  router.addRoutes(result)
}
export function generateRouter(data){
    const childrenRouter = [];
    const result = [{
        path:'/',
        redirect: '/index',
        component: () => import('../components/MainContainer'),
        children: childrenRouter
      }];
      if(data){
        data.forEach(item => {
          generateTopMenuRouter(childrenRouter,item);
        });
        router.addRoutes(result)
        console.log("childrenRouters:",childrenRouter)
      }else{
        console.error("获取菜单数据失败，不执行生成路由")
      }
      function generateTopMenuRouter(children,menu){
        if(menu.parentId == 0){
            let subChildrenRouter = []
            //头部菜单
            if(menu.url && menu.permissionType == '1'){
              if(menu.children && menu.children.length > 0){
                menu.children.forEach(e => generateSubRoutes(subChildrenRouter,e,menu.url));
                children.push({
                    path: menu.url ,
                    name: menu.url,
                    redirect: menu.url + (menu.children[0] ? '/' + menu.children[0].url : ""),
                    children: subChildrenRouter,
                    meta: {title : menu.name},
                    component:  () => import('../components' + menu.component)
                  });
              }else{
                children.push({
                    path: menu.url ,
                    name: menu.url,
                    children: subChildrenRouter,
                    meta: {title : menu.name},
                    component:  () => import('../components' + menu.component)
                  });
              }
            }
          }
      }
      function generateSubRoutes(childrenRouter,item){
        // console.log(getStrTargetCount(parentPath,'/'))
        if (item.children){ 
        //   parentPath = parentPath + "/" + item.url
          item.children.forEach(e =>{
            generateSubRoutes(childrenRouter,e)
          })
        }
        if (item.url && item.permissionType == '1') {
          childrenRouter.push({
            path: item.url ,
            name: item.url,
            meta: {title : item.name},
            component:  () => import('../components' + item.component)
          });
        }
      }
}