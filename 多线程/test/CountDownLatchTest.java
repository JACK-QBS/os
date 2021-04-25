package test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(20);//初始化n
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                latch.countDown();
            }).start();
        }
        latch.await();//阻塞等待，直到 n = 0
        System.out.println("main");
    }
}
