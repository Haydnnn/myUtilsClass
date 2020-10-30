package 生产者消费者模型;

/**
 * @author phd
 * @version 1.0
 * @date 2020/8/31 22:36
 * 生产者消费者模型练习
 */

class AirConditioner{
    private int number = 0;
    public synchronized void increment() throws InterruptedException {
        while (number != 0){//1.判断 用循环防止虚假唤醒
            this.wait();
        }
        //2.干活
        number++;
        //3.通知
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        this.notifyAll();
    }

    public synchronized void decreament() throws InterruptedException {
        while(number == 0){
            this.wait();
        }
        number-- ;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        this.notifyAll();

    }
}



public class ThreadWaitNotifyDemo {
    
    public static void main(String args[]) {
        AirConditioner airConditioner = new AirConditioner();
        new Thread(()->{
            try {
                for (int i = 0; i < 10; i++) {
                    airConditioner.increment();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    airConditioner.decreament();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();

        new Thread(()->{
            try {
                for (int i = 0; i < 10; i++) {
                    airConditioner.increment();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"C").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    airConditioner.decreament();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"D").start();
    }

}
