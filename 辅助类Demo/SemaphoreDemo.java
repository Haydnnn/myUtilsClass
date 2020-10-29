package 辅助类Demo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author phd
 * @version 1.0
 * @date 2020/9/2 10:08
 */
public class SemaphoreDemo {
    public static void main(String args[]){
        //主要用于多线程资源的控制，信号的互斥控制
        Semaphore semaphore = new Semaphore(3); //模拟资源类，有3个空车位

        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t"+"抢占到了车位");
                    //暂停一会儿线程
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+"\t"+"离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },String.valueOf(i+1)).start();
        }
    }
}
