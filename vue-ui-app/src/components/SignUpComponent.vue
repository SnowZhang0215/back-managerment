<template>
    <div class="login-card">
      <Card :bordered="false">
        <p slot="title">
          <Icon type="ios-log-in"/>
          注册
        </p>
      <Form ref="signUpModel" label-position="top" :model="signUpModel" :rules="ruleValidate">
        <FormItem label="用户名" prop="userName">
          <Input icon="ios-contact-outline" v-model="signUpModel.userName" placeholder="请输入用户名"></Input>
        </FormItem>
        <FormItem label="密码" prop="password">
          <Input icon="ios-lock-outline" type="password" v-model="signUpModel.password" placeholder="请输入密码"></Input>
        </FormItem>
        <FormItem label="邮箱" prop="mail">
          <Input icon="ios-mail-outline" v-model="signUpModel.mail" placeholder="请输入邮箱"></Input>
        </FormItem>
        <FormItem label="性别" prop="gender">
          <RadioGroup v-model="signUpModel.gender">
            <Radio label="男" value="1">男</Radio>
            <Radio label="女" value="0">女</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem style="text-align: center">
          <Button type="primary" @click="handleSubmit('signUpModel')">注册</Button>
          <Button @click="handleReset('signUpModel')" style="margin-left: 8px">重置</Button>
        </FormItem>
      </Form>
      </Card>
    </div>
</template>

<script>
    export default {
        name: "SignUpComponent",
      data(){
          return{
            signUpModel:{},
            ruleValidate: {
              userName: [
                { required: true, message: '请输入用户名', trigger: 'blur' }
              ],
              password: [
                { required: true, message: '请输入密码', trigger: 'blur' },
                { type: 'string', min: 4, message: '长度不能小于4', trigger: 'blur' }
              ],
              mail: [
                { required: true, message: '请输入邮箱', trigger: 'blur' },
                { type: 'email', message: '非法邮箱格式', trigger: 'blur' }
              ],
              gender: [
                { required: true, message: '请选择性别', trigger: 'change' }
              ]
            }
          }

      },
      methods:{
        handleSubmit (name) {
          this.$refs[name].validate((valid) => {
            if (valid) {
              this.doRegister(this.signUpModel);
            } else {
              this.$Message.error('Fail!');
            }
          })
        },
        doRegister(data){
          this.$axios.post(this.$api.signUpUrl,data)
            .then(response => this.onSignUpSuccess(response))
            .catch(error => this.onSignUpError(error))
        },
        handleReset (name) {
          this.$refs[name].resetFields();
        },
        onSignUpSuccess(data){
          if (data.errorCode === 200){
            this.$Modal.success({
              title: '成功',
              content: '注册成功，快去登录吧',
              onOk: ()=> this.$router.push("/login")
            })
          } else {
            this.onSignUpError(data.errorMsg)
          }
        },
        onSignUpError(error){
          this.$Modal.error({
            title: "错误",
            content: error.toString()
          });
        }
      }
    }
</script>

<style scoped>
  .login-card {
    width: 30% ;
    margin-left: 35% ;
    margin-top: 100px;
    text-align: left;
    background:#eee;padding: 2px;
  }
</style>
