package io.snow.springcloud.auth.oauth;

import io.snow.rest.common.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/oauthClient")
@Component
public class OAuthClientController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthClientController.class);

    @Autowired
    @Qualifier("clientDetails")
    private ClientDetailsService clientDetailsService;

    @PostMapping("/add")
    public ResponseData addOauthClient(@RequestBody ClientDetails clientDetails){
        ResponseData responseData = null;
        clientDetailsService = clientDetailsService instanceof JdbcClientDetailsService ? ((JdbcClientDetailsService) clientDetailsService) : null;
        if (clientDetailsService!=null){
            try {
                ((JdbcClientDetailsService) clientDetailsService).addClientDetails(clientDetails);
            } catch (ClientAlreadyExistsException e) {
                logger.error("create oauth client detail error: {}",e);
                return ResponseData.error(e.getMessage());
            }
            responseData = ResponseData.ok(null);
        }else {
           responseData = ResponseData.error("not support action");
        }
        return responseData;
    }

    @PostMapping("/test")
    public ResponseData test(){
        ClientDetails clientDetails = new BaseClientDetails();
        ((BaseClientDetails) clientDetails).setClientId("web");
        Set<String> scopes = new HashSet<>();
        scopes.add("web");
        ((BaseClientDetails) clientDetails).setScope(scopes);
        ((BaseClientDetails) clientDetails).setClientSecret(new BCryptPasswordEncoder().encode("1234"));
        Set<String> grantTypes = new HashSet<>();
        grantTypes.add("authorization_code");
        grantTypes.add("password");
        grantTypes.add("refresh_token");
        grantTypes.add("client_credentials");
        ((BaseClientDetails) clientDetails).setAuthorizedGrantTypes(grantTypes);
        return addOauthClient(clientDetails);
    }
}
