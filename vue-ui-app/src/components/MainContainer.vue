// 系统主页面
<template>
  <el-container>
    <el-header class="header">
      <div>
        <img class="logo" src="../assets/logo.png" />
      </div>
      <div class="right">
        <el-menu
          class="top-menu"
          :default-active="activeCode"
          mode="horizontal"
          @select="onMenuSelected"
        >
          <el-menu-item v-for="item in menuData" :key="item.id" :index="item.url">{{item.name}}</el-menu-item>
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
import { showMsgBox,noticeMsg } from "../common/common.service";
import {logout} from '../service/login.service'
// import commonService from './common/commonService'
export default {
  data() {
    return {
      msg: "Hello MainContainer"
      // userInfo:"登录"
    };
  },
  created: function() {
    // noticeMsg("xxxx",true)
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
    if(!this.$storage.getValue("access_token")){
           this.$store.dispatch("setUserInfo", null);
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
        // console.log("can not load user menu");
        // noticeMsg("can not load user menu",true)
        loaduserMenu();
      }
    },
    getUserInfoOk(data) {
      console.log(data);
      this.$storage.setVaule("userInfo", data);
      this.$store.dispatch("setUserInfo", data);
    },
    getUserInfoError(data) {
      console.log(data);
      if (data.status === 401) {
        this.$router.push({
          name: "/login"
        });
      } else {
        console.error(data.errorMsg);
      }
      this.$storage.deleteItem("userInfo");
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
      } else {
        showMsgBox("提示","确定退出？",this.onLogout);
      }
    },
    onLogout(){
        // console.log("logout")
        // this.$router.push({
        //   name: "login"
        // });
        logout()
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
.logo {
  width: 40px;
  height: 40px;
}
</style>