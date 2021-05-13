package com.cncodehub.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.cncodehub.entity.User;
import com.cncodehub.service.UserService;
import com.cncodehub.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token){
        //token是否是jwt的token
        return token instanceof JwtToken;
    }

    /**
     * 授权方法，用于获取用户的菜单权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证方法，用于校验用户信息，比如密码
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken; //已经获取jwt
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userService.getById(Long.valueOf(userId));
        if(user==null){
            throw new UnknownAccountException("账户不存在");
        }

        if(user.getStatus()==-1){
            throw new LockedAccountException("账户已被锁定");
        }

        AccountProfile profile = new AccountProfile();
        //BeanUtil.copyProperties()进行对象之间属性的赋值，避免通过get、set方法一个一个属性的赋值
        BeanUtil.copyProperties(user,profile);

        //把用户信息返回给Shiro,Shiro通过Subject巴拉巴拉去处理
        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}
