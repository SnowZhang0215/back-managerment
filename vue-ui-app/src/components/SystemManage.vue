<template>
    <Layout :style="{padding: '5px',background: '#fff'}">
        <Sider hide-trigger v-if="menus.length>0">
            <Menu theme="light"  :style="{background: '#fff',minHeight: '540px'}" width="auto" @on-select="onMenuSelect">
              <MenuItem v-for="item in menus" :name="item.code" :key="item.id">
              <Icon :type="item.icon"></Icon>
                  {{item.name}}</MenuItem>
            </Menu>
        </Sider>
        <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
            <CustomerContent/>
        </Content>              
   </Layout>
</template>

<script>
    export default {
        name: "SystemManage",
        data(){
            return {
                menus:[],
                currentPath:""
            }
        },
        created(){
          console.log(this.$route.params);
          console.log(this.$router);
          this.menus = this.$route.params.menuData;
          this.currentPath = this.$route.params.parentCode;
        },
        methods:{
            onMenuSelect(name){
            console.log(name)
           },
        }
    }
</script>

<style scoped>

</style>
