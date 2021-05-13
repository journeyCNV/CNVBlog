package com.cncodehub.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * AuthenticationToken 用于收集用户提交的身份（如用户名）及凭据（如密码）
 */
public class JwtToken implements AuthenticationToken {

    private String tocken;

    public JwtToken(String jwt){
        this.tocken = jwt;
    }

    @Override
    public Object getPrincipal() {
        return tocken;
    } //身份

    @Override
    public Object getCredentials() {
        return tocken;
    } //凭据
}
