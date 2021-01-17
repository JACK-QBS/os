package lesson5;

import java.security.Policy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        //创建线程池：
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                5,//核心线程数 ---> 正式员工
                10,//最大线程数 ---> 正式员工+临时工
                60,
                TimeUnit.SECONDS,//idle线程的空闲时间：临时工最大的存活时间
                new LinkedBlockingQueue<>(),//阻塞队列：任务存放的地方（快递仓库）
                new ThreadFactory() {//创建线程的工厂类：线程池创建线程时，调用该工厂的方法创建线程--->招聘员工的标准
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                },
                /**
                 * 拒绝策略：达到最大线程数且阻塞队列已满，采取的拒绝策略
                 * 1、AbortPolicy：直接抛 RejectedExecutionException（不提供 handler 时的默认策略）
                 * 2、CallerRunsPolicy：谁（某个线程）交给我（线程池）任务，我拒绝执行，由谁自己执行
                 * 3、DiscardPolicy：交给我的任务，直接丢弃掉
                 * 4、DiscardOldestPolicy：丢弃阻塞队列中最旧的任务
                 */
                new ThreadPoolExecutor.AbortPolicy()
        );

        //线程池创建以后，只要有任务就自动执行
        for (int i = 0; i < 200; i++) {
            //线程池执行任务：execute\submit --->提交执行一个任务
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
