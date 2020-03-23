<template>
    <div>{{name}}
      <div class="Box-body">
        <article class="markdown-body entry-content p-5" itemprop="text"><h1><a id="user-content-general-spring-cloud" class="anchor" aria-hidden="true" href="#general-spring-cloud"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>general-spring-cloud</h1>
<h1><a id="user-content-基于springcloud构建的一套服务框架" class="anchor" aria-hidden="true" href="#基于springcloud构建的一套服务框架"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>基于SpringCloud构建的一套服务框架</h1>
<ul>
<li>Eureka注册中心:eureka-server</li>
<li>Config配置中心:config-server</li>
<li>Security + OAuth2 + Jwt的统一认证鉴权: auth-server</li>
<li>Zuul 网关:core-gateway(提供服务路由，用户统一认证权限过滤等操作RBAC权限认证方式，适合复杂的企业系统的用户-角色-权限场景)</li>
<li>用户中心：user-service</li>
<li>UI 端使用流行的Vue框架 + IView 组件库 构建。根据用户的角色获取用户菜单，动态生成菜单和路由。
可以在此基础上进行简单配置即可用于企业级应用开发。</li>
</ul>
<p>项目启动过程：</p>
<ul>
<li>
<p>1 EurekaServer</p>
</li>
<li>
<p>2 ConfigServer</p>
</li>
<li>
<p>3 CoreGateway</p>
</li>
<li>
<p>4 AuthServer</p>
</li>
<li>
<p>5 UserService</p>
</li>
<li>
<p>vue-ui-app 进入项目运行 <code>npm run dev</code></p>
</li>
<li>
<p>项目支持Jenkins持续化集群部署，Jenkins部署问题，可以联系我</p>
</li>
</ul>
<p>在使用中任何问题可以通过下面的方式与我联系：
<a href="mailto:snowzhang@aliyun.com">snowzhang@aliyun.com</a></p>
</article>
      </div>
    </div>
</template>

<script>
    export default {
        name: "Index",
        data(){
          return{
            name:'Index'
          }
        },
        methods:{

        },
        created(){
          if (this.$storage.getValue("access_token")) {

          } else {

          }
        }
    }
</script>

<style scoped>

</style>
