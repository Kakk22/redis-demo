package com.cyf.redisdemo.service;

import com.cyf.redisdemo.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public User login(String username, String password) {
        return null;
    }

    /**
     * 登录不成功的操作
     * @param username
     * @return
     */
    @Override
    public String loginValDate(String username) {
        int count = 5;//登录次数
        String key = User.getLoginCountKey(username);
        //判断登录次数key是否存在
        if(!stringRedisTemplate.hasKey(key)){
            //不存在 写入并记录时间 ，redis中先赋值 再设置失效期
            stringRedisTemplate.opsForValue().set(key,"1");
            stringRedisTemplate.expire(key,2,TimeUnit.MINUTES);
            return "登录失败，在两分钟内还剩"+(count-1)+"次";
        }else {
            //存在，判断一下是否<4,比4小 直接加1
            Long longparam = Long.parseLong(stringRedisTemplate.opsForValue().get(key));
            if (longparam<(count-1)){
                stringRedisTemplate.opsForValue().increment(key,1);
                Long  time= stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);//返回还剩多少秒
                return "登录失败，在"+time +"秒内还允许输入"+(count-longparam-1)+"次";
            }else {
                //登录次数达到上限
                String loginTimeLockKey = User.getLoginTimeLockKey(username);
                stringRedisTemplate.expire(loginTimeLockKey,1,TimeUnit.HOURS);
                return "登录次数超过"+count+"次，已经限制登录1小时";
            }
        }
    }

    /**
     * 判断是否被限制了
     * 查询key是否存在 如果存在 返回限制的时间和信息
     * 如果不存在 则没被限制
     * @param username
     * @return
     */
    @Override
    public Map<String,Object> loginUserLook(String username) {
        Map<String,Object> map = new HashMap<>();
        String key = User.getLoginTimeLockKey(username);
        if(stringRedisTemplate.hasKey(User.getLoginTimeLockKey(username))){
            //被限制了
            Long lockTime = stringRedisTemplate.getExpire(key, TimeUnit.MINUTES);//查看还剩多少时间
            map.put("flag",true);
            map.put("lockTime",lockTime);
        }else {
            map.put("flag",false);
        }

        return map;
    }


}
