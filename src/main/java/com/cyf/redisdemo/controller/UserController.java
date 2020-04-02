package com.cyf.redisdemo.controller;

import com.cyf.redisdemo.bean.User;
import com.cyf.redisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam(value = "password",required = false) String password){

        //登录校验，判断是否被限制
        Map<String, Object> map = userService.loginUserLook(username);
        if ((Boolean)map.get("flag")) {
            //如果是true说明被限制
            return "登录失败，当前用户被限制还剩"+map.get("lockTime") +"分钟";
        }else {
            //没被限制 执行登录功能
            User user = userService.login(username,password);
            if(user!=null){
                //1.登录成功
                //清空所有缓存的key

                return "susses";
            }else {
                //2.登录不成功
                String s = userService.loginValDate(username);
                return  s;
            }
        }
    }
}
