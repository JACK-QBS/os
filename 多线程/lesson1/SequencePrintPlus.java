package lesson5;

public class SequencePrintPlus {
    public static void main(String[] args) {
        Thread a = new Thread(new Task("A"));
        Thread b = new Thread(new Task("B"));
        Thread c = new Thread(new Task("C"));
        c.start();
        b.start();
        a.start();
    }

    private static class Task implements  Runnable {

        private String content;
        private static String[] ARR = {"A","B","C"};
        private static int INDEX;//从数组哪个索引打印

        public Task(String content) {
            this.content = content;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    synchronized (ARR) {//三个线程使用同一把锁
                        //从数组索引位置打印，如果当前线程要打印的内容不一致，释放对象锁等待
                        while (!content.equals(ARR[INDEX])) {
                            ARR.wait();
                        }
                        System.out.print(content);
                        if (INDEX == ARR.length - 1) {
                            System.out.println();
                        }
                        INDEX = (INDEX + 1) % ARR.length;
                        ARR.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
