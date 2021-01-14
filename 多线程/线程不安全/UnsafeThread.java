package lesson3;

//不安全的线程
public class UnsafeThread {
    //有一个变量 COUNT=0；同时启动20个线程，每个线程循环 1000 次，每个循环 COUNT++
    //等着20个子线程执行完毕之后，在main线程打印COUNT
    private static int COUNT = 0;
    public static void main(String[] args) throws InterruptedException {
        //尽量同时启动，不让 new Thread 耗时影响
        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        COUNT++;
                    }
                }
            });
        }
        for (Thread t : threads) {
            t.start();
        }
        //让 main 线程阻塞等待所有的20个子线程执行完毕
        for (Thread t : threads) {
            t.join();
        }
        System.out.println(COUNT);
    }
}
