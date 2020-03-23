<template>
    <el-container>
        <el-aside class="aside-class" v-if="subMenu.length>0">
          <el-menu mode="vertical" :default-active="activeCode" @select="onMenuSelected">
                <el-menu-item  v-for="item in subMenu" :key="item.id" :index="item.code">{{item.name}}</el-menu-item>
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
    export default {
        name: "SystemManage",
        data(){
            return {
                subMenu:[],
            }
        },
        created(){
          console.log("setting pages",this.$store.state.subMenus);
          this.subMenu = this.$store.state.subMenus;
          const currentPath = this.$router.currentRoute.path;
          let result = currentPath.split('/');
          if(result.length > 2){
              this.activeCode = result[2]
          }
        },
        methods:{
          onMenuSelected: function(key,keyPath){
            this.$store.dispatch('changeAsideActive',key);
            this.$router.push({
                name: key,
            })
          }
        },
        
    }
</script>

<style scoped>

</style>
