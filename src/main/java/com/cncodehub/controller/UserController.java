package com.cncodehub.controller;


import com.cncodehub.common.lang.Result;
import com.cncodehub.entity.User;
import com.cncodehub.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cnCodeHub
 * @since 2021-05-09
 */
@RestController
@RequestMapping("/user") //@RequestMapping 访问的时候如果 method没有指定方式，则post和get方法都可以使用
public class UserController {
    @Autowired
    UserService userService;

    @RequiresAuthentication //需要进行认证之后才能访问
    @GetMapping("/index")
    public Result index(){
        User user = userService.getById(1L);
        return Result.succ200(user);
    }

    // @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)
    // @PostMapping的注解 访问的时候只能使用post方式
    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        //这里加Validated注解如果校验不通过可以直接抛出异常,异常处理在全局异常处理里
        return Result.succ200(user);
    }
}
