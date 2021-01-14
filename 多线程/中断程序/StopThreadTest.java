package lesson2;

public class StopThreadTest {
    private static volatile boolean STOP = false;
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //...执行任务，执行时间可能很长
                for (int i = 0; i < 10000 && !STOP; i++) {
                    System.out.println(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        System.out.println("t start");
        //模拟，t执行了3秒，还没由结束，要中断，停止t进程
        Thread.sleep(5000);
        STOP = true;
        System.out.println("t stop");
    }
}
