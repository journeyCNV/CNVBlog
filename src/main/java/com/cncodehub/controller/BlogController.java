package com.cncodehub.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cncodehub.common.dto.LoginDto;
import com.cncodehub.common.lang.Result;
import com.cncodehub.entity.Blog;
import com.cncodehub.service.BlogService;
import com.cncodehub.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cnCodeHub
 * @since 2021-05-09
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blog") //分页处理
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page,new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ200(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id){//@PathVariable是spring3.0的一个新功能：接收请求路径中占位符的值
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客不存在");
        return Result.succ200(blog);
    }

    @RequiresAuthentication //需要认证之后才能访问
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {

//        Assert.isTrue(false, "公开版不能任意编辑！");

        Blog temp = null;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            System.out.println(ShiroUtil.getProfile().getId());
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");

        } else {

            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);

        return Result.succ200(null);
    }

    @RequiresAuthentication
    @GetMapping("/blog/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id){
        boolean result = blogService.removeById(id);
        if(result==true) {
            return Result.succ(200, "文章删除成功", null);
        }else{
            return Result.succ(400,"文章删除失败",null);
        }
    }

}
