package com.pccw.immd.client;

import com.pccw.immd.domain.User;
import com.pccw.immd.generated.user.wsdl.Login;
import com.pccw.immd.generated.user.wsdl.LoginRequest;
import com.pccw.immd.generated.user.wsdl.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.FaultMessageResolver;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by vagrant on 5/3/17.
 */
@Component
public class UCMAClient extends WebServiceGatewaySupport {

    public static final Logger logger = LoggerFactory.getLogger(UCMAClient.class);

    @Value("${immd.ucma-service-endpoint}")
    private String userEndpoint;

    public User login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest();
        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);
        loginRequest.setLogin(login);
        User user = new User();

        WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
        LoginResponse response = (LoginResponse) webServiceTemplate.marshalSendAndReceive(userEndpoint, loginRequest);

        user.setUsername(response.getUser().getUsername());
        user.setPassword(response.getUser().getPassword());
        Arrays.stream(response.getUser().getRoles().split(",")).forEach(role -> {
            user.getAuthorities().add(new SimpleGrantedAuthority(role));
        });

        return user;
    }
}
