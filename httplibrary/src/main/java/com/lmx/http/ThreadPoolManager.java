package com.lmx.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018\4\8 0008.
 * 单例模式管理线程池
 */

public class ThreadPoolManager {

    private static ThreadPoolManager instance;

    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) {
                    instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }

    /**
     * 阻塞队列的runnable
     */
    private LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();

    /**
     * 阻塞队列的runnable
     *
     * @param runnable 将线程放进去
     */
    public void execute(Runnable runnable) {
        try {
            queue.put(runnable);
        } catch (InterruptedException e) {

        }
    }

    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPoolExecutor;


    private ThreadPoolManager() {
        /**
         * 4 核心线程数
         * 20允许的最大线程数
         * 15 线程保留的时间
         * TimeUnit.SECONDS 为秒
         */
        threadPoolExecutor = new ThreadPoolExecutor(4, 20, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), rejectedExecutionHandler);
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 线程池满后或者线程保留时间超过会执行rejectedExecution方法,重新将此线程放入到队列末端
     */
    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                queue.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    /*
    * 执行该线程
    * **/
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            while (true) {
                Runnable runnable = null;
                try {
                    runnable = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (null != runnable) {
                    threadPoolExecutor.execute(runnable);
                }
            }
        }
    };
}
