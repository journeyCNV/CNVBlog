package com.cncodehub.service.impl;

import com.cncodehub.entity.Blog;
import com.cncodehub.mapper.BlogMapper;
import com.cncodehub.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cnCodeHub
 * @since 2021-05-09
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
