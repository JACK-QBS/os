package lesson3;

public class SynchronizedTest2 {
    /**
     * 有一个教室，座位有 50 个，同时有 三个老师安排同学的座位
     * 每个老师安排 100 个学生到这个教室：
     * 座位编号是 1-50，三个多线程同时启动来安排同学
     * 学生可以循环操作来安排，直到座位安排满
     */

    private static int COUNT = 50;

    public static void main(String[] args) {

        new Thread(new Task(10)).start();
        new Thread(new Task(20)).start();
        new Thread(new Task(20)).start();

    }

    private static class Task implements Runnable {
        //一个老师所能分配的数量
        private int num;
        public Task(int num) {
            this.num = num;
        }
        @Override
        public void run() {
            //安排学生：
            for (int i = 0; i < 100; i++) {
                synchronized (Task.class) {
                    if (COUNT > 0 && num > 0) {
                        COUNT--;
                        num--;
                        System.out.printf("%s: count=%s,num=%s \n",Thread.currentThread().getName(),COUNT,num);
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}


