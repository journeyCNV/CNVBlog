package com.cncodehub.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 开启mapper接口扫描，添加分页插件
/**
 * 新建一个包：通过@MapperScan指定要变成实现类的接口所在的包
 * 包下面所有接口在编译之后都会生成相应的实现类
 */

@Configuration
@EnableTransactionManagement
@MapperScan("com.cncodehub.mapper")
public class MybatisPlusConfig {

    //PaginationInterceptor是一个分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
