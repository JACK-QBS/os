package lesson5;

public class SequencePrint {
    public static void main(String[] args) {

        Thread c = new Thread(()->{
            System.out.println(Thread.currentThread().getName());
        },"C");

        Thread b = new Thread(()->{
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        },"B");

        Thread a = new Thread(()->{
            try {
                b.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        },"A");

        a.start();
        b.start();
        c.start();

    }
}
