<template>
  <el-container>
    <el-row>
      <el-col :xs="0" :sm="6" :md="6" :lg="8" :xl="8">&nbsp;</el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
        <el-card class="login" shadow="hover">
          <div slot="header" class="clearfix">
            <span>登录</span>
          </div>
          <el-form :model="loginInfo" :rules="rules" ref="loginInfo" label-width="100px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="loginInfo.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="loginInfo.password"></el-input>
            </el-form-item>
            <el-row>
              <el-button
                type="success"
                size="medium"
                @click="onLogin('loginInfo')"
                icon="el-icon-check"
              >登录</el-button>
              <el-button size="medium" icon="el-icon-close" @click="cancel">取消</el-button>
            </el-row>
            <el-row>
              <router-link to="recover">忘记密码</router-link>
            </el-row>
            <el-row>
              <p>
                还没有账号？
                <router-link to="/regester">点击注册</router-link>
              </p>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="0" :sm="6" :md="6" :lg="8" :xl="8">&nbsp;</el-col>
    </el-row>
    <el-footer></el-footer>
  </el-container>
</template>
<script>
import { login } from "../service/login.service";
import { getUserInfo } from "../service/userinfo.service";
import {loaduserMenu} from '../service/menuService'
import store from "../store";
export default {
  data() {
    return {
      loginInfo: {
        username: "",
        password: "",
        grant_type: "password",
        client_id: "app",
        client_secret: "app",
        scope: "app"
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      }
    };
  },
  created(){
      this.$storage.clear()
  },
  methods: {
    onLoginSuccess(data) {
      this.$storage.setValue("access_token", data);
      this.$storage.deleteItem("userMenus")
      loaduserMenu()
      getUserInfo(this.getUserInfoOk, this.getUserInfoError);
      this.$router.push({
        name: "/"
      });
    },
    getUserInfoOk(data) {
      this.$storage.setValue("userInfo",data);
      this.$store.dispatch("setUserInfo", data);
    },
    getUserInfoError(data) {
      console.log(data);
      this.$storage.deleteItem("userInfo");
      this.$store.dispatch("setUserInfo", null);
    },
    onLoginFailed(data) {
      console.log(data);
    },
    onLogin(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          login(this.loginInfo, this.onLoginSuccess, this.onLoginFailed);
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    cancel() {
      this.$router.push({
        name: "/"
      });
    }
  }
};
</script>
<style>
.login {
  margin-top: 20%;
}
a {
  color: #2d8cf0;
  font-size: 14px;
}
p {
  font-size: 14px;
}
</style>