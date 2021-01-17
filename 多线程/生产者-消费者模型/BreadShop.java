package lesson4;

/**
 * 生产者-消费者模型(面包店)
 * 10个生产者，每个每次生产3个
 * 20个消费者，每次消费1个
 */
public class BreadShop {
    //定义面包库存数
    private static int COUNT;

    //消费者：
    public static class Consumer implements Runnable {
        private String name;
        public Consumer(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            try {
                //一直消费：
                while (true) {
                    synchronized (BreadShop.class) {
                        //库存到达下线，不能继续消费，需要阻塞等待
                        if (COUNT == 0) {
                            BreadShop.class.wait();//释放对象锁
                        } else {
                            //库存 > 0，允许消费
                            COUNT--;
                            System.out.printf("消费者 %s 消费了一个面包，库存 %s\n",name,COUNT);
                            //通知代码进入阻塞的进程
                            BreadShop.class.notifyAll();
                            //模拟消费的耗时
                            Thread.sleep(200);
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //生产者：
    public static class Producer implements Runnable {
        private String name;
        public Producer(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            try {
                //一直生产
                while (true) {
                    synchronized (BreadShop.class) {
                        //库存达到上限，不能继续生产，需要阻塞队列
                        if (COUNT + 3 > 100) {
                            BreadShop.class.wait();
                        } else {
                            COUNT+=3;
                            System.out.printf("生产者 %s 生产了三个面包，库存%s\n",name,COUNT);
                            BreadShop.class.notifyAll();
                            Thread.sleep(200);
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //同时启动20个消费者线程
        Thread[] consumers = new Thread[20];
        for (int i = 0; i < 20; i++) {
            consumers[i] = new Thread(new Consumer(String.valueOf(i)));
        }
        //同时启动10个产生着线程
        Thread[] produces = new Thread[20];
        for (int i = 0; i < 10; i++) {
            produces[i] = new Thread(new Producer(String.valueOf(i)));
        }
        //同时启动
        for (int i = 0; i < 20; i++) {
            consumers[i].start();
        }
        for (int i = 0; i < 10; i++) {
            produces[i].start();
        }
    }
}
