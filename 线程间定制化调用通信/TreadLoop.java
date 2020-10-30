package 线程间定制化调用通信;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author phd
 * @version 1.0
 * @date 2020/9/1 15:06
 *
 *  * @Description:
 *  * 多线程之间按顺序调用，实现A->B->C
 *  * 三个线程启动，要求如下：
 *  *
 *  * AA打印5次，BB打印10次，CC打印15次
 *  * 接着
 *  * AA打印5次，BB打印10次，CC打印15次
 *  * ......来10轮
 */

/**
 * 解决思路
 * 1、有顺序通知，需要有标识位
 *
 * 2、有一个锁Lock，3把钥匙Condition
 *  
 * 3、判断标志位
 * 
 * 4、修改标志位，通知下一个
**/

class PrintResource{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void printA(){
        lock.lock();
        try{
            while (number != 0){
                condition1.await();
            }
            for (int i = 0; i < 5 ; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            number = 1;
            condition2.signal(); //用下一把唤醒
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void printB(){
        lock.lock();
        try{
            while(number != 1){
                condition2.await();
            }
            for (int i = 0; i < 10 ; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            number = 2;
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void printC(){
        lock.lock();
        try{
            while(number != 2){
                condition3.await();
            }
            for (int i = 0; i < 15 ; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            number = 0;
            condition1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}



public class TreadLoop {
    public static void main(String args[]){
        PrintResource printResource = new PrintResource();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                printResource.printA();
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                printResource.printB();
            }
        },"B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                printResource.printC();
            }
        },"C").start();
    }

}
