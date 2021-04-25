package test;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) throws InterruptedException {
        Semaphore s = new Semaphore(0);//初始化n
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                s.release();
            }).start();
        }
        s.acquire(20);
        System.out.println("main");
    }
}
