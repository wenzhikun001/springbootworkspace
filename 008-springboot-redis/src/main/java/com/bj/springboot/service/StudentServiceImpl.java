package com.bj.springboot.service;

import com.bj.springboot.mapper.StudentMapper;
import com.bj.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired  //注入redis模板
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public Integer findAllStudentCount() {
        //过呢更新redisTemlate模板中的序列化 否则redis中的名称是这样的\xAC\xED\x00\x05t\x00\x0FallStudentCount,设置之后是这样的 allStudentCount
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //先去redis中查询一下是否有数据
        Integer allStudentCount= (Integer) redisTemplate.opsForValue().get("allStudentCount");
        if(null==allStudentCount){
            //从数据库中获取
            allStudentCount= studentMapper.selectAllStudentCount();
            //往redis中放入 存活时间为15秒
            redisTemplate.opsForValue().set("allStudentCount",allStudentCount,15, TimeUnit.SECONDS);
            System.out.println("从数据库中获取");
        }else {
            System.out.println("从redis中获取");
        }
        return allStudentCount;
    }
}
