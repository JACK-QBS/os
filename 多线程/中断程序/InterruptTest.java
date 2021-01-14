package lesson2;

public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {

            //中断以后停止程序：
            /*@Override
            public void run() {
                try {
                    for (int i = 0; i < 10000 && !Thread.currentThread().isInterrupted(); i++) {
                        System.out.println(i);
                       //模拟中断过程
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/

            //中断以后继续执行：
            @Override
            public void run() {
                for (int i = 0; i < 10000 && !Thread.currentThread().isInterrupted(); i++) {
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
        //告诉t进程，要中断（设置t进程的中断标志位为true），由t的代码自行决定是否中断
        //如果t线程处于阻塞状态，会抛出InterruptedException，并且重置t线程的中断标志位
        t.interrupt();
        System.out.println("t stop");
    }
}
