package com.spring_security_jwt.controller;

import com.spring_security_jwt.entity.User;
import com.spring_security_jwt.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping({"register-new-user"})
    public User registerNewUser(@RequestBody User user){
        return userService.registerNewUser(user);
    }

    @PostConstruct
    public void initRoleandUser(){
        userService.initRoleAndUser();
    }

    @GetMapping({"for-admin"})
    public String forAdmin(){
        return "this url is only accessible to admin";
    }

    @GetMapping({"for-user"})
    public String forUser(){
        return "this url is only accessible to user";
    }
}
