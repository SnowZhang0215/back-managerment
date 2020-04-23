package io.snow.springcloud.apigateway.config;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();
        http.authorizeExchange()
                .pathMatchers("/auth-server/**","/user-service/menu/getUserMenus/**")
                .permitAll().and()
                .authorizeExchange().anyExchange().authenticated();
        return http.build();
    }
}
