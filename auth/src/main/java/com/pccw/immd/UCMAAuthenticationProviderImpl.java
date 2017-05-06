package com.pccw.immd;

import com.pccw.immd.client.UCMAClient;
import com.pccw.immd.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Created by aiam on 5/6/2017.
 */
@Component
public class UCMAAuthenticationProviderImpl {
//    public class UCMAAuthenticationProviderImpl implements AuthenticationProvider {

    public static final Logger logger = LoggerFactory.getLogger(UCMAAuthenticationProviderImpl.class);

    @Autowired
    UCMAClient ucmaClient;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getPrincipal() + "";
//        String password = authentication.getCredentials() + "";
//        User user;
//        try{
//            user = ucmaClient.login(username,password);
//        } catch (Exception e) { //TODO catch soap exception
//            e.printStackTrace();
//            throw new BadCredentialsException("Invalid Credentials");
//        }
//        return new UsernamePasswordAuthenticationToken(username, password,user.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
}
