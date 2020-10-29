package 辅助类Demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author phd
 * @version 1.0
 * @date 2020/9/2 9:59
 */
public class CyclicBarrierDemo {
    public static void main(String args[]){

        //计数
        CyclicBarrier  cyclicBarrier = new CyclicBarrier(7,() -> {
            System.out.println("召唤神龙");
        });
        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                System.out.println("收集到第："+Thread.currentThread().getName()+"龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
