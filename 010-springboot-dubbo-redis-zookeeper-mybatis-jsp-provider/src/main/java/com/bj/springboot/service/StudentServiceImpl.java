package com.bj.springboot.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bj.springboot.mapper.StudentMapper;
import com.bj.springboot.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = StudentService.class,version = "1.0.0",timeout = 35000)
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Override
    public Integer queryStudentAllCount() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());    //序列化
        Integer allStudentCount= (Integer) redisTemplate.opsForValue().get("allStudentCount");
        if (null==allStudentCount){
            //设置同步代码块
            synchronized (this){
                allStudentCount= (Integer) redisTemplate.opsForValue().get("allStudentCount");
            }
            if (null==allStudentCount){
                System.out.println("从数据库中拿数据");
                allStudentCount=studentMapper.queryAllStudentCount();
                redisTemplate.opsForValue().set("allStudentCount",allStudentCount,15, TimeUnit.SECONDS);
            }else {
                System.out.println("从redis中拿数据");
            }
        }else {
            System.out.println("从redis中拿数据");
        }
        return allStudentCount;
    }

    @Override
    public Student queryStudentDetailById(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }
}
