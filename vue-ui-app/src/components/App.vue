<template>
  <div class="layout">
    <Layout>
      <Header>
        <Menu ref="horizontalMenu" mode="horizontal" theme="light" :active-name="activeCode" @on-select="onMenuSelect">
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
              <span><Avatar style="background-color: #87d068" icon="ios-person" /></span>
              <Button type="text" @click="handleSignBtnClick"><span>{{signIn}}</span></Button>
            </div>
          </div>
        </Menu>
      </Header>
      <Layout>
        <Sider hide-trigger :style="{background: '#fff'}">
          <Menu ref="verticalMenu" theme="light" width="auto" :open-names="openNames" :active-name="verticalMenuActiveCode" @on-select="onVerticalMenuSelect" accordion>
            <Submenu :name="subItem.code" v-for="subItem in subMenus" :key="subItem.id">
              <template slot="title">
                <Icon :type="subItem.icon"></Icon>
                {{subItem.name}}
              </template>
              <MenuItem :name="item.code" v-if="subItem.children.length>0" v-for="item in subItem.children" :key="item.id" :to="item.url">
                {{item.name}}
              </MenuItem>
            </Submenu>
          </Menu>
        </Sider>
        <Layout :style="{padding: '0 10px 10px'}">
          <Breadcrumb :style="{margin: '10px 0'}">
            <BreadcrumbItem>{{currentMainMenu}}</BreadcrumbItem>
            <BreadcrumbItem>{{currentMainMenu}}</BreadcrumbItem>
            <BreadcrumbItem>{{currentMainMenu}}</BreadcrumbItem>
          </Breadcrumb>
          <Content :style="{padding: '5px', minHeight: '540px', background: '#fff'}">
            <router-view/>
          </Content>
        </Layout>
      </Layout>
      <Footer></Footer>
    </Layout>
  </div>
</template>

<script>
export default {
  name: 'App',
  created(){
    console.log("app component created")
    if (this.$storage.getValue("userMenus")){
      this.initMenu(this.$storage.getValue("userMenus"))
    } else {
      this.$Message.error("can not load menus");
    }
    this.loadUserInfo();
  },
  data(){
    return{
      signIn: '登录',
      menuData: [],
      subMenus: [],
      activeCode:'index',
      verticalMenuActiveCode:'list',
      openNames:[],
      currentMainMenu:""
    }
  },
  methods:{
    loadUserInfo(){
      if (this.$storage.getValue("userInfo")){
        const  user = this.$storage.getValue("userInfo");
        this.signIn = user.userName;
      }else {
        this.signIn = '登录';
      }
    },
    initMenu: function (menuData) {
      this.menuData = menuData;
      const currentPath = this.$router.currentRoute.path;
      this.activeCode = currentPath.substring(1, currentPath.length);
      const isTopMenu = checkCurrentMenuIsTop(this.menuData, this.activeCode);
      if (!isTopMenu) {
        this.verticalMenuActiveCode = this.activeCode;
        let topMenu = null;
        for (let i = 0; i < this.menuData.length; i++) {
          topMenu = this.menuData[i];
          if (getTopParentCode(topMenu,this.verticalMenuActiveCode)){
            break;
          }else {
            topMenu = null;
          }
        }
        if (topMenu){
          console.log(topMenu);
          this.activeCode = topMenu.code;
          if (topMenu.children){
            let openVorticalMenu = null;
            for (let i = 0; i < topMenu.children.length; i++) {
               openVorticalMenu = topMenu.children[i];
              if (getTopParentCode(openVorticalMenu,this.verticalMenuActiveCode)){
                break;
              }else{
                openVorticalMenu = null;
              }
            }
            if (openVorticalMenu){
              console.log(openVorticalMenu);
              this.openNames.push(openVorticalMenu.code);
            }
          }
        }
      }
      this.onMenuSelect(this.activeCode);
      this.$nextTick(function () {
        this.$refs.horizontalMenu.updateOpened();
        this.$refs.horizontalMenu.updateActiveName();
        this.$refs.verticalMenu.updateOpened();
        this.$refs.verticalMenu.updateActiveName();
      });

      function getTopParentCode(data, code) {
        if (data.code === code) {
          return true;
        }
        let result = false;
        if (data.children) {
          data.children.forEach(e=>{
           if (getTopParentCode(e,code)){
             result = true;
           }
          })
        }
        return result;
      }

      /**
       * 检查当前menu是否为横向菜单的menu
       */

      function checkCurrentMenuIsTop(data, code) {
        const result = false;
        data.forEach(item => function (result) {
          if (item.code === code) {
            result = true;
          }
          return result;
        });
        return result;
      }
    },
    onMenuSelect(name){
      console.log(name);
      this.activeCode = name;
      this.subMenus = this.getSubMenuByCurrentKey(name);
      console.log(this.subMenus);
    },
    onVerticalMenuSelect(name){
      this.verticalMenuActiveCode = name;
      console.log(name);
    },
    getSubMenuByCurrentKey(name){
      for (let i = 0; i < this.menuData.length; i++) {
        if (this.menuData[i].code === name) {
          this.currentMainMenu = this.menuData[i].name;
          return this.menuData[i].children;
        }
      }
    },
    handleSignBtnClick(){
      if (this.$storage.getValue("userInfo")){
        this.$Modal.confirm({
          title: '确认',
          content: '<p>确定退出吗</p>',
          onOk: () => {
            this.handleLogout();
          }
        });
      }else {
        this.$router.push("/login")
      }
    },
    handleLogout(){
      this.$axios.post(this.$api.logout)
        .then(response => this.onLogoutSuccess(response))
        .catch(error => this.$Message.error(error.toString()))
    },
    onLogoutSuccess(response){
      if (response && response.errorCode === 200){
        this.$storage.clear();
        this.$router.push('/');
        location.reload();
      }
    }
  }
}
</script>
<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
  }
  .ivu-layout-header{
    padding: 0;
    background: #d7dde4;
  }
  .ivu-menu{
    padding: 0 10px;
  }
  .layout-logo{
    width: 100px;
    height: 30px;
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

  .header {
    color: #515a6e;
  }
  .ivu-layout-footer {
    padding: 3px 10px;
    color: #515a6e;
    font-size: 14px;
  }
</style>
