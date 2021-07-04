package com.cncodehub.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cncodehub.common.dto.LoginDto;
import com.cncodehub.common.lang.Result;
import com.cncodehub.entity.User;
import com.cncodehub.service.UserService;
import com.cncodehub.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AopController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/hello")
    public String sayHello(){
        System.out.println("hello");
        return "hello";
    }

    @PostMapping("/login3")
    public Result login3(@Validated  @RequestBody LoginDto loginDto, HttpServletResponse response){
        /**
         * queryWrapper是mybatis plus中实现查询的对象封装操作类
         */
        User user = userService.getOne(new QueryWrapper<User>().eq("username",loginDto.getUsername()));

        //这里使用断言的处理
        //Assert.notNull(user,"用户不存在"); //我感觉这样处理对返回给前端不够方便,主要会影响前端后置拦截的编写
        if(user==null){
            return Result.succ(400,"用户不存在",null);
        }

        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.succ(400,"密码不正确",null);
        }
        //生成Token
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");
        return Result.succ(200,"登录成功",
                MapUtil.builder()
                        .put("id",user.getId())
                        .put("username",user.getUsername())
                        .put("avatar",user.getAvatar())
                        .put("email",user.getEmail())
                        .map()
        );
    }

}
