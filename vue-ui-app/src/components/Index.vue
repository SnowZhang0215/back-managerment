<template>
    <div>{{name}}</div>
</template>

<script>
    export default {
        name: "Index",
        data(){
          return{
            name:'Index'
          }
        },
        methods:{
          initMenuAndRouter(menuData){
            const childrenRouter = [];
            const result = [{
              path:'/',
              component: () => import('../components/App.vue'),
              children: childrenRouter
            }];
            menuData.forEach(item => {
              generateRoutes(childrenRouter,item)
            });

            this.$router.addRoutes(result);

            function generateRoutes(childrenRouter,item){
              if (item.children){
                item.children.forEach(e =>{
                  generateRoutes(childrenRouter,e)
                })
              }
              if (item.url) {
                childrenRouter.push({
                  path: item.url,
                  // component:  () => import('../components/SystemIntroduce'+'.vue')
                  component:  () => import('../components' + item.component)
                });
              }
            }
          }
        },
        created(){
          if (this.$storage.getValue("userMenus")) {
            const menuData = this.$storage.getValue("userMenus");
            this.initMenuAndRouter(menuData)
          } else {
            this.$axios.get("api/user-service/menu/default/menus").then(
              response => this.initMenuAndRouter(response.data)
            ).catch(error => this.$Message.error(error.toString()))
          }
        }
    }
</script>

<style scoped>

</style>
