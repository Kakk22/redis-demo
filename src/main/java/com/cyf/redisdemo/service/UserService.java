package com.cyf.redisdemo.service;

import com.cyf.redisdemo.bean.User;

import java.util.Map;

public interface UserService {
    //登录账号密码校验
    public User login(String username, String password);


    /**
     * 登录前的限制检查
     * 返回用户详细信息的提示
     */
    public String loginValDate(String username);

    /**
     * 判断当前用户是否被限制了
     * @param username
     * @return
     */
    public Map<String,Object> loginUserLook(String username);
}
