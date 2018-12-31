package com.xxl.threadpool;

import java.util.concurrent.Future;

/**
 * @author lixin
 * @date 2018-12-25 15:01
 **/
public class ThreadPoolUtils {

    private static ThreadPoolService threadPoolService = ThreadPoolService.getInstance();

    public static void submit(Object clazz, String methodName, Object... fileds) {
        threadPoolService.submit(new RunnableTask(clazz, methodName, fileds));
    }


    public static Future submitWithReturn(Object clazz, String methodName, Object... fileds) {
        return threadPoolService.submitWithReturn(new CallableTask(clazz, methodName, fileds));
    }

}
