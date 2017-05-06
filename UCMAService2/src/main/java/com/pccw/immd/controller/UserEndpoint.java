package com.pccw.immd.controller;

import com.pccw.immd.generated.user.*;
import com.pccw.immd.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;

/**
 * Created by vagrant on 5/3/17.
 */

@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://immd.pccw.com/users";

    @Autowired
    UserService userService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest loginRequest) {
        Login request=loginRequest.getLogin();
        com.pccw.immd.domain.User login = userService.login(request.getUsername(), request.getPassword());
        ModelMapper modelMapper=new ModelMapper();
        User user = modelMapper.map(login, User.class);

        LoginResponse response=new LoginResponse();
        UUID sessionId = UUID.randomUUID();
        response.setUser(user);
        Session session=new Session();
        session.setXAuthToken(sessionId.toString());
        response.setSession(session);
        return response;
    }
}
