<template>
  <div style="width: 30% ; margin-left: 35% ; margin-top: 100px; text-align: center" >
    <div style="background:#eee;padding: 2px">
      <Card :bordered="false">
        <p slot="title">
          <Icon type="ios-log-in" />
          登录
        </p>
        <template>
          <Form ref="formInline" :model="loginInfo" :rules="ruleInline">
            <FormItem prop="user">
              <Input type="text" v-model="loginInfo.username" placeholder="Username">
              <Icon type="ios-person-outline" slot="prepend"></Icon>
              </Input>
            </FormItem>
            <FormItem prop="password">
              <Input type="password" v-model="loginInfo.password" placeholder="Password">
              <Icon type="ios-lock-outline" slot="prepend"></Icon>
              </Input>
            </FormItem>
            <FormItem>
              <Button type="primary" long @click="handleSubmit('formInline')">登录</Button>
              <router-link to="/recover">忘记密码?</router-link>
              <p>尚未拥有账户？<router-link to="/signup">注册</router-link></p>
            </FormItem>
          </Form>
        </template>
      </Card>
    </div>
  </div>
</template>

<script>
    export default {
      name: "LoginComponent",
      methods:{
        login(userInfo){
          if (this.$storage.getValue("access_token")) {
            this.$storage.deleteItem("access_token")
          }
          const params = {};
          params.username = userInfo.username;
          params.password = userInfo.password;
          params.grant_type = 'password';
          params.client_id = 'app';
          params.client_secret = 'app';
          params.scope = 'app';
          const paramsStr = this.$qs.stringify(params);
          this.$axios.post(this.$api.loginUrl,paramsStr)
            .then(response => this.onLoginSuccess(response))
            .catch(error =>  this.$Message.error(error.toString()))
        },
        handleSubmit(name) {
          this.$refs[name].validate((valid) => {
            if (valid) {
              this.$Message.success('Success!');
              this.login(this.loginInfo);
            } else {
              this.$Message.error('Fail!');
            }
          })
        },
        initMenuAndRouter(menuData){
          this.$storage.setValue("userMenus",menuData);
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
                component:  () => import('../components' + item.component)
              });
            }
          }
          const query = this.$router.currentRoute.query;
          console.log(query);
          if (query.redirect){
            if (query.redirect === '/login'){
              this.$router.push({
                path: '/'
              })
            } else {
              this.$router.push({
                path: query.redirect
              })
            }
          }else {
            this.$router.push({
              path: '/'
            })
          }
        },
        onLoginSuccess(response){
          this.$storage.setValue("access_token",response);
          if (this.$storage.getValue("userMenus")) {
            const menuData = this.$storage.getValue("userMenus");
            this.initMenuAndRouter(menuData)
          } else {
            this.$axios.get(this.$api.userMenus).then(
              response => this.initMenuAndRouter(response.data)
            ).catch(error => this.$Message.error(error.toString()))
          }
          if (this.$storage.getValue("access_token")){
            this.$axios.get(this.$api.userInfo)
              .then(response => this.handleUserInfo(response.data))
              .catch(error => this.handleErrorResponse(error))
          }
        },
        handleUserInfo(info){
          this.$storage.setValue("userInfo",info);
          console.log(info);
        },
        handleErrorResponse(error){
          this.$Message.error(error.toString())
          console.log(error);
        }
      },
      created(){
        this.$storage.deleteItem("access_token");
        this.$storage.deleteItem("userMenus")
      },
      data () {
        return {
          modal2: false,
          modal_loading: false,
          loginInfo : {
            username:"",
            password:""
          },
          msg: 'Welcome to Your Vue.js App',
          ruleInline: {
            username: [
              { required: true, message: 'Please fill in the user name', trigger: 'blur' }
            ],
            password: [
              { required: true, message: 'Please fill in the password.', trigger: 'blur' },
              { type: 'string', min: 4, message: 'The password length cannot be less than 4 bits', trigger: 'blur' }
            ]
          }
        }
      }
    }
</script>

<style scoped>

</style>
