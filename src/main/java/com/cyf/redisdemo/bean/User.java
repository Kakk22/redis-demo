package com.cyf.redisdemo.bean;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private Integer age;

    //返回当前对象key的名称
    public static String getKeyName(){
        return "user:";
    }
    // 登录限制时间key   user:loginTime:look:用户名
    public static String getLoginTimeLockKey(String username){
        return  "user:loginTime:look:"+username;
    }
    //登录错误次数key  user:loginCount:fail:用户名
    public static String getLoginCountKey(String username){
        return "user:loginCount:fail:"+username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
