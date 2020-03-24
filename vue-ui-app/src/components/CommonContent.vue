<template>
   <el-container>
        <el-aside class="aside-class" v-if="subMenusFormState.length>0" style="width:250px">
          <el-menu mode="vertical" :default-active="activeCode" @select="onMenuSelected" style="width:250px">
                <el-menu-item  v-for="item in subMenusFormState" :key="item.id" :index="item.code">{{item.name}}</el-menu-item>
            </el-menu>
        </el-aside>
        <el-container class="main-container">      
            <el-main>
                <router-view/>
            </el-main>
        </el-container>
    </el-container>
</template>
<script>
    import {getSubMenuByParentCode} from '../service/menuService'
    export default {
        name: "Setting",
        data(){
          return{
            info:'',
            // activeCode:'lanage',
            // subMenu: subMenusFormState
          }
        },
        methods:{
          onMenuSelected: function(key,keyPath){
            console.log(key)
            this.$store.dispatch('asideActiveCode',key);
            this.$router.push({
                name: key
            })
          }
        },
        computed:{
            subMenusFormState(){
              return this.$store.state.subMenus
            },
            activeCode(){
              return this.$store.state.asideActiveCode
            }
        },
        created(){
          const currentPath = this.$router.currentRoute.path;
          let result = currentPath.split('/');
          if(result.length > 2){
              // this.activeCode = result[2]
              // this.$store.dispatch('asideActiveCode',result[2]);
              let params = getSubMenuByParentCode(this.$store.getters.getMenuData,result[1]);
              console.log("params:",params);
              let stateData = {"subMenu":params,topActiveCode:result[1]}
              this.$store.dispatch('switchMenu',stateData);
          }
          this.subMenu = this.$store.state.subMenus;
          console.log(this.subMenu)
        }
    }
</script>

<style scoped>
.main-container{
        min-height: 540px;
        padding: 3px;
    }
  .aside-class{
    border-right: solid 1px #e6e6e6;
  }
</style>
