package com.xxl.threadpool;
import com.xxl.reflection.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 重写Callable的实现类
 * @author lixin
 * @date 2018-12-25 10:16
 **/
public class CallableTask<T> implements Callable {
    private static final Logger logger = LoggerFactory.getLogger(CallableTask.class);

    private Object clazz;
    private String methodName;
    private Object[] fields;
    private Map<String,Object> taskContext;

    public CallableTask(){
      this.clazz = null;
      this.methodName = "";
      logger.debug("");
    }

    public CallableTask(Object clazz,String methodName){
        this.clazz = clazz;
        this.methodName = methodName;
        logger.debug("");
    }

    public CallableTask(Object clazz,String methodName,Object ...fields){
        this.clazz = clazz;
        this.methodName = methodName;
        this.fields = fields;
        logger.debug("");
    }

    @Override
    public T call() throws Exception {
        try {
            logger.debug("开始执行callable任务：[class:{},methodName:{},fields:{}]",this.clazz,this.methodName,this.fields);
            T result = ReflectionUtils.invokMethod(this.clazz,this.methodName,this.fields);
            logger.debug("执行任务结束:[{}#{}]",clazz,methodName);
            return result;
        }catch (Exception e){
            logger.error("执行任务失败，当前任务：[{}#{}]",clazz,methodName,e);
            throw e;
        }
    }
}
