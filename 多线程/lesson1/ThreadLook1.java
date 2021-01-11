package lesson1;

public class ThreadLook1 {
    public static void main(String[] args) {
        //第一种创建线程的方式：使用 Thread 类，重写 run()
        Thread t = new Thread("main中的子线程") {
            @Override
            public void run() {
                while (true) {

                }
            }
        };
        //设置守护线程(进程会终止)
        //t.setDaemon(true);
        // 线程启动，必须使用start()-->告诉系统调度本线程
        t.start();
        System.out.println(t.getId());
        System.out.println(t.getName());
        System.out.println(t.getPriority());//优先级
        System.out.println(t.getState());//状态
        System.out.println(t.isAlive());//是否存活
        System.out.println(t.isDaemon());//是否后台进程
        System.out.println(t.isInterrupted());//是否被中断
    }
}
