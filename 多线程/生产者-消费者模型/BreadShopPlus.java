package lesson4;

/**
 * 生产者-消费者模型(面包店)
 * 10个生产者，每个每次生产3个
 * 20个消费者，每次消费1个
 * 升级版需求：面包师傅每个最多生产30次，
 *           消费者不再一直消费，把生产者生产完的面包消费完就结束
 * 隐藏信息：面包店每天生产面包的最大数量：面包店每天生产 10*30*3 = 900个面包
 *         消费者，把900个面包消费完结束
 */
public class BreadShopPlus {

    private static int COUNT;//面包库存数
    private static int PRODUCE_NUMBER;//面包店生产面包的总数，不会消费
    private static int MAX_COUNT = 100;//最大库存数
    private static int CONSUMER_NUM = 10;//消费者数量
    private static int CONSUMER_COUNT = 5;//每次消费的面包数
    private static int PRODUCE_NUM = 5;//生产者数量
    private static int PRODUCE_TIMES = 10;//生产者生产次数
    private static int PRODUCE_COUNT = 3;//每次生产的面包数

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
                    synchronized (BreadShopPlus.class) {
                        if (PRODUCE_NUMBER == PRODUCE_NUM*PRODUCE_COUNT*PRODUCE_TIMES) {
                            break;
                        }
                        //库存到达下线，不能继续消费，需要阻塞等待
                        if (COUNT - CONSUMER_COUNT < 0) {
                            BreadShopPlus.class.wait();//释放对象锁
                        } else {
                            //库存 > 0，允许消费
                            COUNT -= CONSUMER_COUNT;
                            System.out.printf("消费者 %s 消费了 %s 面包,库存 %s\n", name, CONSUMER_COUNT, COUNT);
                            //通知代码进入阻塞的进程
                            BreadShopPlus.class.notifyAll();
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
                //达到生产次数
                for (int i = 0; i < PRODUCE_TIMES; i++) {
                    synchronized (BreadShopPlus.class) {
                        //库存达到上限，不能继续生产，需要阻塞队列
                        while (COUNT + PRODUCE_COUNT > MAX_COUNT) {
                            BreadShopPlus.class.wait();
                        }
                            COUNT += PRODUCE_COUNT;
                            PRODUCE_NUMBER += PRODUCE_COUNT;
                            System.out.printf("生产者 %s 生产了%s面包，库存 %s，生产的数量 %s\n",name,PRODUCE_COUNT,COUNT,PRODUCE_NUM*PRODUCE_COUNT*PRODUCE_TIMES);
                            BreadShopPlus.class.notifyAll();
                            Thread.sleep(200);

                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //同时启动个消费者线程
        Thread[] consumers = new Thread[CONSUMER_NUM];
        for (int i = 0; i < CONSUMER_NUM; i++) {
            consumers[i] = new Thread(new Consumer(String.valueOf(i)));
        }
        //同时启动10个产生着线程
        Thread[] produces = new Thread[PRODUCE_TIMES];
        for (int i = 0; i < PRODUCE_TIMES; i++) {
            produces[i] = new Thread(new Producer(String.valueOf(i)));
        }
        //同时启动
        for (Thread t : consumers) {
            t.start();
        }
        for (Thread t : produces) {
            t.start();
        }
    }
}
