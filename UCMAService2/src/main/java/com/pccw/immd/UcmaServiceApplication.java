package com.pccw.immd;

import com.pccw.immd.domain.User;
import com.pccw.immd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class UcmaServiceApplication {

    @Autowired
    UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(UcmaServiceApplication.class, args);
    }

    @PostConstruct
    public void generateUsers(){
        userService.deleteAll();
        User register=new User();
        register.setUsername("jeff");
        register.setPassword("jeff");
        register.setFirstname("Jeffrey");
        register.setLastname("Valdenor");
        register.setRoles("user,admin");
        userService.register(register);

        User admin=new User();
        admin.setUsername("pccw");
        admin.setPassword("1234");
        admin.setFirstname("PCCW");
        admin.setLastname("HKTD");
        admin.setRoles("admin");
        userService.register(admin);


        User user=new User();
        user.setUsername("user1");
        user.setPassword("1234");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setRoles("user");
        userService.register(user);

    }



}
