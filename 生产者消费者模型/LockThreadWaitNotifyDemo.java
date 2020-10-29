package 生产者消费者模型;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author phd
 * @version 1.0
 * @date 2020/8/31 23:13
 * 用lock实现
 */

class AirContainer{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void increment() throws InterruptedException {
        lock.lock();
        try{
            while (number != 0){
                condition.await();
            }
            //2.干活
            number++;
            //3.通知
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public void decreament() throws InterruptedException {
        lock.lock();
        try{
            while (number == 0){
                condition.await();
            }
            //2.干活
            number--;
            //3.通知
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }
}


public class LockThreadWaitNotifyDemo {

    public static void main(String args[]){
        AirContainer airContainer = new AirContainer();
        new Thread(()->{
            try {
                for (int i = 0; i < 10; i++) {
                    airContainer.increment();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    airContainer.decreament();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();

        new Thread(()->{
            try {
                for (int i = 0; i < 10; i++) {
                    airContainer.increment();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"C").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    airContainer.decreament();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"D").start();
    }

}
