package com.xxl.threadpool;

import com.alibaba.fastjson.JSON;
import com.xxl.reflection.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 重写Runnable的实现类
 * @author lixin
 * @date 2018-12-24 14:57
 **/
public class RunnableTask implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(RunnableTask.class);

    private Object clazz;
    private String methodName;
    private Object[] fields;

    private Map<String,Object> taskContext = new HashMap<>();

    public RunnableTask(){
        this.clazz = null;
        this.methodName = "";
        logger.debug("runnable 任务初始化，当前上下文：{}",taskContext);
    }

    public RunnableTask(Object clazz,String methodName){
        this.clazz = clazz;
        this.methodName = methodName;
        logger.debug("runnable 任务初始化，当前上下文：{}",taskContext);
    }

    public RunnableTask(Object clazz,String methodName,Object ... args){
        this.clazz = clazz;
        this.methodName = methodName;
        this.fields = args;
        logger.debug("runnable 任务初始化，当前上下文：{}",taskContext);
    }

    @Override
    public void run() {
        try {
            logger.debug("开始执行runnable任务：[class:{},methodName:{},fields:{}]",this.clazz,this.methodName,this.fields);
            ReflectionUtils.invokMethod(this.clazz,this.methodName,this.fields);
            logger.debug("执行任务结束:[{}#{}]",clazz,methodName);
            System.out.println(this.toString());
        }catch (Exception e){
            logger.error("执行任务失败，当前任务：[{}#{}]",clazz,methodName,e);
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
