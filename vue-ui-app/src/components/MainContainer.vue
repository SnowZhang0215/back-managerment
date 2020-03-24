// 系统主页面
<template>
  <el-container>
    <el-header class="header">
      <div>
        <img class="logo" src="../assets/logo.png"/>
      </div>
      <div class="right">
        <el-menu
          class="top-menu"
          :default-active="activeCode"
          mode="horizontal"
          @select="onMenuSelected"
        >
          <el-menu-item v-for="item in menuData" :key="item.id" :index="item.code">{{item.name}}</el-menu-item>
        </el-menu>
        <div class="user-info">
          <i class="el-icon-user-solid"></i>
          <el-button type="text" @click="loginBtnClick">{{userInfo ? userInfo.userName : "登录"}}</el-button>
        </div>
      </div>
    </el-header>
    <el-container class="main-container">
      <el-main>
        <router-view />
      </el-main>
    </el-container>
    <el-footer class="footer-class">boot</el-footer>
  </el-container>
</template>
<script>
import { loaduserMenu, getSubMenuByParentCode } from "../service/menuService";
import { getUserInfo } from "../service/userinfo.service";
export default {
  data() {
    return {
      msg: "Hello MainContainer"
      // userInfo:"登录"
    };
  },
  created: function() {
    this.initMenu();
    const currentPath = this.$router.currentRoute.path;
    let result = currentPath.split("/");
    if (result.length > 1) {
      //一级菜单
      //  this.activeCode = result[1]
      let params = getSubMenuByParentCode(
        this.$store.getters.getMenuData,
        result[1]
      );
      console.log("params:", params);
      let stateData = { subMenu: params, topActiveCode: result[1] };
      this.$store.dispatch("switchMenu", stateData);
    }
    const token = this.$storage.getValue("access_token");
    if (token) {
      getUserInfo(this.getUserInfoOk, this.getUserInfoError);
    }
  },
  computed: {
    activeCode() {
      return this.$store.getters.getTopActiveCode;
    },
    menuData() {
      return this.$store.getters.getMenuData;
    },
    userInfo() {
      return this.$store.getters.getUserInfo;
    }
  },
  methods: {
    initMenu: function() {
      if (this.$storage.getValue("userMenus")) {
        const menuData = this.$storage.getValue("userMenus");
        this.$store.dispatch("menuData", menuData);
        console.log(menuData);
      } else {
        // this.$Message.error("can not load menus");
        console.log("can not load user menu");
      }
    },
    getUserInfoOk(data) {
      console.log(data);
      this.$store.dispatch("setUserInfo", data);
    },
    getUserInfoError(data) {
      console.log(data);
      this.$store.dispatch("setUserInfo", null);
    },

    onMenuSelected(key, keyPath) {
      console.log(key, keyPath);
      let params = getSubMenuByParentCode(this.menuData, key);
      console.log("params:", params);
      let stateData = { subMenu: params, topActiveCode: key };
      this.$store.dispatch("switchMenu", stateData);
      this.$router.push({
        name: key
      });
    },
    loginBtnClick() {
      const token = this.$storage.getValue("access_token");
      if (!token) {
        this.$router.push({
          name: "login"
        });
      }
    }
  }
};
</script>
<style>
.header {
  border-bottom: solid 1px #e6e6e6;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
/* .top-menu {
  float: right;
} */
.main-container {
  min-height: 540px;
  max-height: 600px;
  padding: 3px;
}
.footer-class {
  border-top: solid 1px #e6e6e6;
}
.aside-class {
  border-right: solid 1px #e6e6e6;
  width: 250px;
}
.user-info {
  margin-left: 10px;
}
.right {
  display: flex;
  align-items: center;
}
.logo{
    width: 40px;
    height: 40px;
}
</style>