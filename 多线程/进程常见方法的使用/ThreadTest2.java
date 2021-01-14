package lesson2;

public class ThreadTest2 {
    public static void main(String[] args) throws InterruptedException {
        //main线程和子线程同时进行，打印都是无序的
        /*for (int i = 0; i < 20; i++) {
            final int n = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(n);
                }
            });
            t.start();
        }
        System.out.println("OK");*/

        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            final int n = i;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(n);
                }
            });
        }
        for (Thread t : threads) {
            t.start();
        }
        //子线程执行完，在执行主线程代码
        /*while (Thread.activeCount()>1) {
            Thread.yield();//让当前线程让步：从运行态转变为就绪态
        }*/
        for (Thread t : threads) {
            t.join();
        }
        System.out.println("OK");
    }

}
