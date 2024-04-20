package com.edu.xmu.rag.controller;

import com.edu.xmu.rag.controller.vo.MessageVo;
import com.edu.xmu.rag.controller.vo.UserVo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.bo.User;
import com.edu.xmu.rag.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public ReturnObject createUser(@Validated @RequestBody UserVo userVo){
        return userService.register(userVo);
    }

    @PostMapping("/user/login")
    public ReturnObject login(@Validated @RequestBody UserVo userVo){
        return userService.login(userVo);
    }

    @GetMapping("/user/{id}")
    public ReturnObject getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user/new")
    public ReturnObject createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/user")
    public ReturnObject updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/user")
    public ReturnObject deleteUserById(@RequestBody User user) {
        return userService.deleteUserById(user);
    }

    @GetMapping("/userList")
    public ReturnObject getAllUsers() {
        return userService.getAllUsers();
    }
}
