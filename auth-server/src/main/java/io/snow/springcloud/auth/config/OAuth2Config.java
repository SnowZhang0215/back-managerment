package io.snow.springcloud.auth.config;

import io.snow.springcloud.auth.authentication.SmsCodeTokenGranter;
import io.snow.springcloud.auth.filter.CustomerAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通过@EnableAuthorizationServer注解来启动OAuth2授权服务机制
 * 通过继承AuthorizationServerConfigurerAdapter 并覆写三个configure来进行配置
 * OAuth2 认证流程大致如下：
 * 1.请求被监听/oauth/token的过滤器ClientCredentialsTokenEndpointFilter拦截。
 * 2.在这个拦截器中，attemptAuthentication方法获取请求参数中的client_id 和 client_secret
 *   并将两个参数封装成UsernamePasswordAuthenticationToken,交给AuthenticationManager
 *   的authenticate方法去认证。
 * 3.authenticationManager 根据 provideManager提供的DaoAuthenticationProvider加载
 *   client信息。根据client_id 去内存或者数据库中查询 clientDetailsService
 * 4.client信息验证通过之后，继续doFilter请求到达TokenEndpoint，认证失败直接就return了
 * 5.在TokenEndpoint中我们看到了熟悉的Controller "/oauth/token",在这个Endpoint中
 *   获取client_id 根据client_id 去加载 已经认证通过的那个clientDetails。进行一些必要的验证
 *   之后，将参数封装成一个TokenRequest对象。
 * 6.调用 TokenGranter 的 grant方法。创建OAuth2AccessToken。
 * 7.进入AuthorizationServerEndpointsConfigurer的tokenGranter()，加载五个默认的TokenGranter
 * 8.CompositeTokenGranter 遍历所有的 tokenGranter。找到对应的grant_type 然后调用getAccessToken
 *   方法，在方法中子类实现的getOAuth2Authentication方法，获取请求的用户名和密码，生成相应的
 *   AuthenticateToken交给authenticationManager去验证。验证成功，创建token，存储token，返回token
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerAuthenticationFilter customerAuthenticationFilter;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    @Qualifier("mobileUserDetailService")
    private UserDetailsService smsUserDetailService;


//    @Bean
//    RedisTokenStore redisTokenStore(){
//        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
//        redisTokenStore.setPrefix("oauth2Token");
//        return redisTokenStore;
//    }

    @Bean
    JdbcTokenStore jdbcTokenStore(){
        return new JdbcTokenStore(dataSource);
    }


    /**
     * 配置客户端信息，inMemory内存模式；jdbc数据库模式，
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }

    @Bean("clientDetails")
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * AuthorizationServerSecurity配置
     * 可以通过addTokenEndpointAuthenticationFilter添加认证过滤器
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                //在这里可以添加拦截器，对请求参数进行相关解密。
                .addTokenEndpointAuthenticationFilter(customerAuthenticationFilter);

        ;

    }

    /**
     * 用于配置授权authorization及令牌token的访问端点
     * 可以在此处添加Authentication的认证节点TokenGranter。
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jdbcTokenStore())
                 .tokenEnhancer(jwtAccessTokenConverter())
                 .tokenServices(createJdbcTokenService())
                 .tokenGranter(tokenGranter(endpoints))//OAuth2最红认证就是在tokenGranter中完成的。
                 .authenticationManager(authenticationManager);
    }

    /**
     * 添加TokenGranter
     * @param endpoints
     * @return
     */
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));
        SmsCodeTokenGranter smsCodeTokenGranter = new SmsCodeTokenGranter(authenticationManager,endpoints.getTokenServices(),endpoints.getClientDetailsService(),endpoints.getOAuth2RequestFactory(),"sms_code");
        granters.add(smsCodeTokenGranter);
        return new CompositeTokenGranter(granters);
    }
    /**
     * jdbc token service
     * @return
     */
    private DefaultTokenServices createJdbcTokenService(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(jdbcTokenStore());
        tokenServices.setClientDetailsService(clientDetails());
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds(60*60*12);
        tokenServices.setRefreshTokenValiditySeconds(60*60*24*7);
        return tokenServices;
    }

//    @Bean
//    public TokenStore tokenStore(){
//       return new JwtTokenStore(jwtAccessTokenConverter());
//    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("snow-jwt.jks"),"snow123".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("snow-jwt"));
        return converter;
    }
}
