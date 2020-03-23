// 系统主页面
<template>
    <el-container>
        <el-header class="header">
            <el-menu class="top-menu" :default-active="activeCode" mode="horizontal" @select="onMenuSelected">
                <el-menu-item  v-for="item in menuData" :key="item.id" :index="item.code">{{item.name}}</el-menu-item>
            </el-menu>
        </el-header>
        <el-container class="main-container">
            <el-main>
                <router-view/>
            </el-main>
        </el-container>
        <el-footer class="footer-class">boot</el-footer>
    </el-container>
</template>
<script>
import {loaduserMenu,getSubMenuByParentCode} from '../service/menuService'
export default {
    data(){
        return {
            msg: "Hello MainContainer",
            // activeCode: "",
            // menuData: []
            // menuData : menuDataFromState
        }
    },
    created: function(){
        this.initMenu()
         const currentPath = this.$router.currentRoute.path;
         let result = currentPath.split('/');
         if(result.length > 1){
             //一级菜单
            //  this.activeCode = result[1]
            let params = getSubMenuByParentCode(this.$store.getters.getMenuData,result[1]);
            console.log("params:",params);
            let stateData = {"subMenu":params,topActiveCode:result[1]}
            this.$store.dispatch('switchMenu',stateData);
         }
    },
    computed:{
        activeCode(){
            return this.$store.getters.getTopActiveCode;
        },
        menuData(){
            return this.$store.getters.getMenuData;
        }
    },
    methods:{
        initMenu: function(){
            if (this.$storage.getValue("userMenus")){
                const menuData = this.$storage.getValue("userMenus")
                this.$store.dispatch("menuData",menuData)
                console.log(menuData)
            } else {
                // this.$Message.error("can not load menus");
                console.log("can not load user menu")
            }
        },
        
        onMenuSelected(key,keyPath){
            console.log(key, keyPath);
            let params = getSubMenuByParentCode(this.menuData,key);
            console.log("params:",params);
            let stateData = {"subMenu":params,topActiveCode:key}
            this.$store.dispatch('switchMenu',stateData);
            this.$router.push({
                name: key,
            })
        //     const currentPath = this.$router.currentRoute.path;
        //     let result = currentPath.split('/');
        //     if(result.length > 2){
        //      //二级菜单
        //      const subActiveCode = result[2]
        //      this.$store.dispatch('asideActiveCode',subActiveCode);
        //  }
        }
    }
}
</script>
<style>
    .header{
        border-bottom: solid 1px #e6e6e6;
    }
    .top-menu{
        float: right;
    }
    .main-container{
        min-height: 540px;
        max-height: 600px;
        padding: 3px;
    }
    .footer-class{
        border-top: solid 1px #e6e6e6;
    }
    .aside-class{
    border-right: solid 1px #e6e6e6;
    width: 250px;
  }
</style>