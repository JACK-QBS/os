package lesson3;

public class SynchronizedTest {

    private static int COUNT;

    public static void increment() {
        COUNT++;
    }

    public static void main(String[] args) {

        //线程加锁
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (SynchronizedTest.class) {
                    increment();
                }
            }
        }).start();

        //线程不加锁
        new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        }).start();
    }
}
