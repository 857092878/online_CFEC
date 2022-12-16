package com.rui.online.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @Author 蒲锐
 * @CreateTme 2022/11/2 17:08
 * Version1.0.0
 */
@Component
public class RedisJsonUtil<T> {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setBean(String key, Object obj){
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(obj));
    }
//    public List<T> getBean(String key ,Class<T> t){
//        String s = stringRedisTemplate.opsForValue().get(key);
//        List<T> bean = JSONObject.parseArray(s, t);
//        System.out.println(bean);
//        return bean;
//    }

    public List<T> getBean(String key ,Class<T> t){
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s == null){
            return null;
        }
        JSONObject obj =JSONObject.parseObject(s);
        System.out.println(obj);
        JSONArray arr = obj.getJSONArray("data");
        String js= JSON.toJSONString(arr, SerializerFeature.WriteClassName);

        List<T> bean = JSONObject.parseArray(js, t);
        System.out.println(bean);
        return bean;
    }


}
