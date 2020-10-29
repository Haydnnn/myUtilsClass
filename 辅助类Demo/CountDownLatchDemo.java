package 辅助类Demo;

import java.util.concurrent.CountDownLatch;

/**
 * @author phd
 * @version 1.0
 * @date 2020/9/2 9:51
 */
public class CountDownLatchDemo {

    public static void main(String args[]) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6); //倒计数
        for (int i = 1; i <= 6 ; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t"+"灭国");
                countDownLatch.countDown();
            },ConutryEnum.forEach_ConutryEnum(i).getRetMessage()).start();

        }
        countDownLatch.await();
        System.out.println(ConutryEnum.forEach_ConutryEnum(7).getRetMessage()+"\t 一统天下");
    }
}
