package lesson2;

public class ThreadTest1 {
    public static void main(String[] args) {
        //子线程休眠3秒，同时执行，系统调度无序执行
        for (int i = 0; i < 20; i++) {
            final int n = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        //在匿名内部类中使用外边变量必须是被 final 所修饰
                        System.out.println(n);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        //main线程和子线程同时进行
        System.out.println("OK");
    }
}
