# general-spring-cloud
# 基于SpringCloud构建的一套服务框架
- Eureka注册中心:eureka-server
- Config配置中心:config-server
- Security + OAuth2 + Jwt的统一认证鉴权: auth-server
- Zuul 网关:core-gateway(提供服务路由，用户统一认证权限过滤等操作RBAC权限认证方式，适合复杂的企业系统的用户-角色-权限场景)
- 用户中心：user-service
- UI 端使用流行的Vue框架 + Element-ui 组件库 构建。根据用户的角色获取用户菜单，动态生成菜单和路由。
可以在此基础上进行简单配置即可用于企业级应用开发。

项目启动过程：

- 1 EurekaServer
- 2 ConfigServer
- 3 CoreGateway
- 4 AuthServer
- 5 UserService
- vue-ui-app 进入项目运行 `npm run dev`


- 项目支持Jenkins持续化集群部署，Jenkins部署问题，可以联系我

- 项目集成SpringCloud Sleuth + Zipkin 全链路分析。日志通过RabbitMQ对接logstash
到ELK平台
http://121.36.174.250:5601/

在使用中任何问题可以通过下面的方式与我联系：
snowzhang@aliyun.com

aa