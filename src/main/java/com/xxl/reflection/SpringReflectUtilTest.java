package com.xxl.reflection;

import com.xxl.threadpool.ThreadPoolUtils;

import javax.management.ReflectionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author lixin
 * @date 2018-12-24 16:38
 **/
public class SpringReflectUtilTest {


    public static void main(String[] args) throws ReflectionException, ExecutionException, InterruptedException {
        InstanceClass instance = new InstanceClass();
        Object[] fieldTypes = new Object[2];
        fieldTypes[0] = "我是谁";
        fieldTypes[1] = new Object();
//        Method method = ReflectionUtils.findMethod(InstanceClass.class,"sout",fieldTypes);

//        RunnableTask runnableTask = new RunnableTask(instance,"sout","我是谁",null);
//        Thread threadTest = new Thread(runnableTask);
//        threadTest.start();

//        String ab = ReflectionUtils.invokMethod(instance,"sout",fieldTypes);
//        String string = ReflectionUtils.invokMethod(instance,"getSout",fieldTypes);
//        System.out.println("反射调用获取结果："+string);

        ThreadPoolUtils.submit(instance,"sout",fieldTypes);

        Future future = ThreadPoolUtils.submitWithReturn(instance,"getSout",fieldTypes);
        System.out.println(future.get());



//        System.out.println(method.getName());
    }

}
