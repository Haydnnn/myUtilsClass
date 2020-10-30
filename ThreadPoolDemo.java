package 线程池;

import java.util.concurrent.*;

/**
 * @author phd
 * @version 1.0
 * @date 2020/9/3 14:22
 */
public class ThreadPoolDemo {
    public static void main(String args[]){

//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
//        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        System.out.println(Runtime.getRuntime().availableProcessors()); //获取系统核数

        ExecutorService myThreadPool = new ThreadPoolExecutor(2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()); //拒绝策略 有四种策略
        try{
            for (int i = 1; i <=  9; i++) {  //5+3=8 最大处理最大线程数+阻塞队列数
                myThreadPool.execute(() ->{
                    System.out.println(Thread.currentThread().getName()+"\t"+"办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            myThreadPool.shutdown();
        }

    }
}

/*
    public ThreadPoolExecutor(int corePoolSize, //线程池中的常驻核心线程数
                              int maximumPoolSize, //线程池中能够容纳同时执行的最大线程数，此值必须大于等于1
                              long keepAliveTime, // 多余的空闲线程的存活时间
                              TimeUnit unit, //keepAliveTime的单位
                              BlockingQueue<Runnable> workQueue, //任务队列，被提交但尚未执行的任务 阻塞队列
                              ThreadFactory threadFactory, //表示生产线程池中工作线程的线程工厂，用于创建线程，一般默认的即可
                              RejectedExecutionHandler handler) { 拒绝策略，表示当队列满了，并且工作线程大于等于线程池的最大线程数时如何请求执行的runnable的策略
        if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.acc = System.getSecurityManager() == null ?
                null :
                AccessController.getContext();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
*/
