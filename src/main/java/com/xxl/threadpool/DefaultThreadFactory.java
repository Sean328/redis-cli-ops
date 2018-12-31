package com.xxl.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程池工厂
 *
 * @author lixin
 * @date 2018-12-25 17:18
 **/
public class DefaultThreadFactory implements ThreadFactory {


    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public DefaultThreadFactory() {
        namePrefix = "custom-";
        // 获取当前线程的Top Level线程组
        ThreadGroup root = Thread.currentThread().getThreadGroup();
        while (null != root.getParent()) {
            root = root.getParent();
        }
        group = new ThreadGroup(root, namePrefix + "-pool");
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon()){
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY){
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
