<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
  }
  .layout-logo{
    width: 100px;
    height: 30px;
    /*background: #5b6270;*/
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
  }
  .layout-nav{
    width: 500px;
    margin: 0 20px 0 auto;
  }

  .info-menu-sign-in {
    cursor: pointer;
    display: inline-block;
    margin-right: 8px;
  }

  .info-menu {
    display: inline-block;
    margin-left: 5px;
    float:right;
  }

  .ivu-avatar>* {
    line-height: 32px;
  }

  .layout-logo-img {
    width: 30px;
    height: 30px;
  }
</style>
<template>
  <div class="layout">
    <Layout>
      <Header>
        <Menu ref="defaultMenu" mode="horizontal" theme="dark" :active-name="activeCode" @on-select="onMenuSelect">
          <div class="layout-logo">
            <img src="../assets/logo.png" class="layout-logo-img"/>
          </div>
          <div class="layout-nav">
            <MenuItem :name="menu.code" v-for="menu in menuData" :key="menu.id" :to="menu.url">
              <Icon :type="menu.icon"></Icon>
              {{menu.name}}
            </MenuItem>
          </div>
          <div class="info-menu">
            <div class="info-menu-sign-in">
              <span>
                <Button icon="ios-person" to="/login"><span>{{signIn}}</span></Button>
              </span>
            </div>
          </div>
        </Menu>
      </Header>
      <Layout>
        <Sider hide-trigger :style="{background: '#fff'}">
          <Menu theme="light" width="auto">
            <Submenu :name="subItem.parent + '-' + subItem.code" v-for="subItem in subMenus" :key="subItem.id">
              <template slot="title">
                <Icon :type="subItem.icon"></Icon>
                {{subItem.name}}
              </template>
              <MenuItem :name="item.parent + '-' + item.code" v-if="subItem.children.length>0" v-for="item in subItem.children" :key="item.id" :to="item.url">
                {{item.name}}
              </MenuItem>
            </Submenu>
          </Menu>
        </Sider>
        <Layout :style="{padding: '0 24px 24px'}">
          <Breadcrumb :style="{margin: '24px 0'}">
            <BreadcrumbItem>{{currentMainMenu}}</BreadcrumbItem>
            <BreadcrumbItem>{{currentMainMenu}}</BreadcrumbItem>
            <BreadcrumbItem>{{currentMainMenu}}</BreadcrumbItem>
          </Breadcrumb>
          <Content :style="{padding: '24px', minHeight: '400px', background: '#fff'}">
            <router-view/>
          </Content>
        </Layout>
      </Layout>
    </Layout>
  </div>
</template>

<script>
export default {
  name: 'App',
  created(){
    console.log("vue created");
    this.$axios.get("api/user-service/menu/default/menus").then(
      // response => console.log(response)
      response => this.initMenuAndRouter(response.data)
    ).catch(error =>  this.$Message.error(error.toString()))

  },
  data(){
    return{
      signIn: '登录',
      menuData: [],
      subMenus: [],
      activeCode:'index',
      leftActiveMenu:'',
      openNames:[],
      currentMainMenu:""
    }
  },
  methods:{
    initMenuAndRouter(menuData){
      const childrenRouter = [
      //   {
      //   path: '/one',
      //   component: () => import('../components/LoginComponent.vue')
      // },
      //   {
      //     path: '/system',
      //     component: () => import('../components/SignUpComponent.vue')
      //   }
      ];
      const result = [{
        path:'/',
        component: () => import('../components/App.vue'),
        children: childrenRouter
      }];


      this.menuData = menuData;
      console.log("init ");

      menuData.forEach(item => {
        generateRoutes(childrenRouter,item)
      });

      this.$router.addRoutes(result);
      
      function generateRoutes(childrenRouter,item){
        console.log(item);
        if (item.children){
          item.children.forEach(e =>{
            generateRoutes(childrenRouter,e)
          })
        }
        if (item.url) {
          childrenRouter.push({
            path: item.url,
            component: () => import('../components/LoginComponent.vue')
          });
        }
        console.log(childrenRouter)
      }


      console.log(this.$router);


      this.$nextTick(function() {
        this.$refs.defaultMenu.updateOpened();
        this.$refs.defaultMenu.updateActiveName();
        this.onMenuSelect(this.activeCode);
      });
    },
    onMenuSelect(name){
      console.log(name);
      this.activeCode = name;
      this.subMenus = this.getSubMenuByCurrentKey(name);
      console.log(this.subMenus);
    },
    getSubMenuByCurrentKey(name){
      for (let i = 0; i < this.menuData.length; i++) {
        if (this.menuData[i].code === name) {
          this.currentMainMenu = this.menuData[i].name;
          return this.menuData[i].children;
        }
      }
    }
  }
}
</script>

<style>

</style>
