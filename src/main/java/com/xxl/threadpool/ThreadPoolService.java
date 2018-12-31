package com.xxl.threadpool;

import java.util.concurrent.*;

/**
 * @author lixin
 * @date 2018-12-25 15:46
 **/
public class ThreadPoolService {

    private ThreadPoolService threadPoolService;

    //阻塞队列
    private BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(1000);
    //核心线程size
    private Integer corePoolSize = 10;
    //最大可执行线程size
    private Integer maximumPoolSize = 30;
    //闲置线程存活时间
    private Long keepAliveTime = 60L;
    //拒绝策略
    private RejectedExecutionHandler rejectHandler = new ThreadPoolExecutor.CallerRunsPolicy();
    //默认线程池工厂
    private ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return null;
        }
    };

    ThreadPoolExecutor defaultThreadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
            workQueue,new DefaultThreadFactory(),rejectHandler);

    private ThreadPoolService() {
    }

    public static ThreadPoolService getInstance() {
        return InstanceHolder.threadPoolService;
    }

    /**
     * 静态内部类初始化对象
     */
    private static class InstanceHolder {
        public static ThreadPoolService threadPoolService = new ThreadPoolService();
    }




    public void submit(RunnableTask runnableTask) {
        if (null == runnableTask) {
            throw new IllegalArgumentException("任务不能为空");
        }

        defaultThreadPool.submit(runnableTask);
    }


    public Future submitWithReturn(CallableTask callableTask) {
        if (null == callableTask) {
            throw new IllegalArgumentException("任务不能为空");
        }

        return defaultThreadPool.submit(callableTask);
    }

}
