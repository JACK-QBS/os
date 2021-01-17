package lesson4;

/**
 * 有三个线程，每个线程只能打印 A 或 B 或 C
 * 要求：同时执行三个线程，按 CBA 顺序打印
 */
public class SequencePrint {
    public static void main(String[] args) {
        Thread c = new Thread(new Print("C",null));
        Thread b = new Thread(new Print("B",c));
        Thread a = new Thread(new Print("A",b));
        a.start();
        b.start();
        c.start();
    }

    private static class Print implements Runnable {
        private String content;
        private Thread joinTask;
        public Print(String content, Thread joinTask) {
            this.content = content;
            this.joinTask = joinTask;
        }

        @Override
        public void run() {
            if (joinTask != null) {
                try {
                    joinTask.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(content);
        }
    }
}
