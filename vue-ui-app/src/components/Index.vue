<template>
  <el-container>
    <el-main>
      <h1>基于springcloud微服务构建的一套服务框架</h1>
      <template>
        <el-tabs :tab-position="tabPosition">
          <el-tab-pane label="Eureka注册中心"><Eureka/></el-tab-pane>
          <el-tab-pane label="Config配置中心"><Config/></el-tab-pane>
          <el-tab-pane label="统一认证中心"><AuthCenter/></el-tab-pane>
          <el-tab-pane label="Zuul网关"><Zuul/></el-tab-pane>
          <el-tab-pane label="用户中心"><UserCenter/></el-tab-pane>
          <el-tab-pane label="前端UI"><UICenter/></el-tab-pane>
          <el-tab-pane label="分布式服务追踪"><Sleuth/></el-tab-pane>
          <el-tab-pane label="统一日志平台"><ELKLog/></el-tab-pane>
          <el-tab-pane label="联系作者"><AuthorInfo/></el-tab-pane>
        </el-tabs>
      </template>
    </el-main>
  </el-container>
</template>
    
<script>
import Eureka from '../components/static/EurekaComponent'
import Config from '../components/static/ConfigComponent'
import AuthCenter from '../components/static/AuthCenterComponent'
import Zuul from '../components/static/ZuulComponent'
import UserCenter from '../components/static/UserCenterComponent'
import UICenter from '../components/static/UICenterComponent'
import Sleuth from '../components/static/SleuthComponent'
import ELKLog from '../components/static/ELKLogComponent'
import AuthorInfo from '../components/static/AuthorInfoComponent'
// import Config from ''
export default {
  name: "Index",
  data() {
    return {
      name: "Index",
      tabPosition: 'left',
    };
  },
  components:{
      Eureka,Config,AuthCenter,Zuul,UserCenter,UICenter,Sleuth,ELKLog,AuthorInfo
  },
  methods: {},
  created() {
    
  }
};
</script>

<style scoped>
</style>
