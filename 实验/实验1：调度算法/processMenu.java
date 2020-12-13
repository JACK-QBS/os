package Test1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class processMenu {
    ArrayList<JCB> jcb;// 存放所有进程
    LinkedList<JCB> link;// 存放已经进入队列的进程
    ArrayList<JCB> new_jcb;// 存放按指定调度算法排序后的进程
    JCB nowProess;// 当前应执行进程

    public void init() {//初始化
        
        jcb = new ArrayList<JCB>();
        link = new LinkedList<JCB>();
        new_jcb = new ArrayList<JCB>();
        // 给定5个进程：
        JCB p1 = new JCB("1", 0, 4,3);
        JCB p2 = new JCB("2", 1, 3,2);
        JCB p3 = new JCB("3", 2, 5,3);
        JCB p4 = new JCB("4", 3, 2,1);
        JCB p5 = new JCB("5", 4, 4,5);
        jcb.add(p1);jcb.add(p2);jcb.add(p3);jcb.add(p4);jcb.add(p5);
        //先将jcb排序，便于下面的算法实现，就不需要再定义一个标识进程是否已到达的boolean,即无需每次都从头开始扫描jcb容器，
        //而是用一个K记录下当前已经扫描到的位置，一次遍历即可，提高了算法效率。
        Collections.sort(jcb, new compareAt_St());
    }

    public void FCFS(){//先来先服务算法
        ProcessQueue pq=new ProcessQueue();//调用内部类
        pq.EnqueueLast();//让最先到达的进程先入队1
        System.out.println("*************************************************************************************************************");
        System.out.println("先来先服务（FCFS）：");
        while(!link.isEmpty()) {//while(new_jcb.size()!=jcb.size())
            pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue();//出队，一次一个
            pq.EnqueueLast();//已到达的进程入队
        }
    }
    public void SJF() {// 短作业优先算法
        ProcessQueue pq=new ProcessQueue();
        pq.EnqueueLast();
        System.out.println("************************************************************************************************************");
        System.out.println("短作业优先（SJF）：");
        while(!link.isEmpty()) {
            pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue();//出队，一次一个
            pq.EnqueueLast();//已到达的进程入队
            Collections.sort(link, new compareSt());//队列中的进程还需按服务时间长度进行排序
        }
    }
    public void RR() {//时间片轮转调度算法
        ProcessQueue pq=new ProcessQueue();
        pq.EnqueueLast();
        System.out.println("***********************************************************************************************************");
        System.out.println("时间片轮转调度（RR）：");
        while(!link.isEmpty()) {
            pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue(1);//出队，一次一个，因为上一轮出的得让刚到达的进程先进队列，所以没办法，进队操作只能也放在这个函数里了
        }
    }
    public void HRRN() {//最高响应比优先调度算法
        ProcessQueue pq=new ProcessQueue();
        pq.EnqueueLast();
        System.out.println("***********************************************************************************************************");
        System.out.println("最高响应比优先算法（HRRN）：");
        while(!link.isEmpty()) {
            pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue();//出队，一次一个
            pq.EnqueueLast();//已到达的进程入队
            Collections.sort(link, new comparePriority());//队列中的进程还需按响应比进行排序
        }
    }
    class ProcessQueue{
        int k = 0;// jcb中的进程遍历时的下标
        int nowTime = 0;// 当前时间
        double sliceTime;//轮转调度时间片
        int i=0;//记录当前出入队列的次数
        public void EnqueueLast() {//进程首次入队，可一次进多个,从队尾进入
            while (k < jcb.size()) {//当遍历完jcb中的所有进程时结束
                if (jcb.get(k).arriveTime <= nowTime) {//已经到达的进程按到达时间先后进入队列
                    link.addLast(jcb.get(k));
                    k++;
                } else {
                    break;//如果该进程还未入队，即先结束遍历，保留当前下标k值，注意：此处不要k--；
                }
            }
        }
        public void EnqueueFirst() {//进程首次入队，可一次进多个,从队首进入
            while (k < jcb.size()) {//当遍历完jcb中的所有进程时结束
                if (jcb.get(k).arriveTime <= nowTime) {//已经到达的进程按到达时间先后进入队列
                    link.addFirst(jcb.get(k));
                    k++;
                } else {
                    break;//如果该进程还未入队，即先结束遍历，保留当前下标k值，注意：此处不要k--；
                }
            }
        }

        //进程出队，一次只出一个
        public void Dequeue() {
            nowProess = link.removeFirst();//移除队列的队首元素并且返回该对象元素
            nowProess.beginTime = nowTime;//计算开始时间，即为上一个进程的结束时间
            nowProess.finshTime = nowProess.beginTime + nowProess.serveTime;//计算结束时间，该进程开始时间+服务时间
            nowProess.roundTime = nowProess.finshTime - nowProess.arriveTime;//计算周转时间
            nowProess.aveRoundTime = (double) nowProess.roundTime / nowProess.serveTime;//计算平均周转时间
            nowTime = nowProess.finshTime;//获得结束时间，即当前时间，方便判断剩下的进程是否已到达
            new_jcb.add(nowProess);//经处理过数据后加入new_jcb容器
            for(int i=0;i<link.size();++i) {
                link.get(i).waitTime++;//所有进入等待队列的进程等待时间+1,此处只为最高响应比算法所用
            }
        }

        //重载Dequeue方法，实现轮转调度算法的出队
        public void Dequeue(double sliceTime) {
            nowProess = link.removeFirst();//移除队列的队首元素并且返回该对象元素
            if(nowProess.firstTimeTag==false) {
                /*轮转调度进程可能会多次反复进出队列，不像FCFS和SJF的进程只会进出一次，所以计算开始时间可以设个标志位，让每个进程在
                 * 第一次执行时记录一遍即可*/
                nowProess.beginTime=nowTime;//进程开始执行的时间
                nowProess.firstTimeTag=true;//计算第一次即可，下次无需更新计算
            }
            nowTime+=sliceTime;//每次出队，用时一个时间片，更新当前时间
            nowProess.clock+=sliceTime;//更新当前出队列的进程已服务时间
            if(nowProess.clock>=nowProess.serveTime) {
                nowProess.finshTime=nowTime;//计算该进程完成时间
                nowProess.roundTime = nowProess.finshTime - nowProess.arriveTime;//计算周转时间
                nowProess.aveRoundTime = (double) nowProess.roundTime / nowProess.serveTime;//计算平均周转时间
                new_jcb.add(nowProess);//经处理过数据后加入new_jcb容器
                EnqueueFirst();//已到达的进程先入队
            } else {
                EnqueueFirst(); //已到达的进程先入队
                link.addLast(nowProess); //上一轮出的再紧接着进入队尾
            }
        }

        //队列中等候的进程
        public void DisplayQueue(){
            i++;
            System.out.println("第"+i+"次队列中排队的进程："+link);
        }
    }

    // 打印结果：
    public void printProcess() {
        System.out.println("进程名 到达时间  服务时间   开始时间  完成时间  周转时间  带权周转时间");
        for (int i = 0; i < new_jcb.size(); ++i) {
            System.out.println(" "+"P"+new_jcb.get(i).name + "     " + new_jcb.get(i).arriveTime + "       " +
                    new_jcb.get(i).serveTime+ "        " + new_jcb.get(i).beginTime + "        " + new_jcb.get(i).finshTime +
                    "        "+ new_jcb.get(i).roundTime + "         " + new_jcb.get(i).aveRoundTime);
        }
        new_jcb.clear();//清空new_jcb容器内的内容，方便存储各种算法的结果并展示
    }
}

// 按服务时间升序
class compareSt implements Comparator<JCB> {
    public int compare(JCB arg0, JCB arg1) {
        return arg0.serveTime - arg1.serveTime;
    }
}

// 按到达时间升序，若到达时间相同，按服务时间升序
class compareAt_St implements Comparator<JCB> {
    public int compare(JCB o1, JCB o2) {
        int a = o1.arriveTime - o2.arriveTime;
        if (a > 0)
            return 1;
        else if (a == 0) {
            return o1.serveTime > o2.serveTime ? 1 : -1;
        } else
            return -1;
    }
}

//按响应比升序排序
class comparePriority implements Comparator<JCB>{
    public int compare(JCB o1, JCB o2) {
        double r1=(double)o1.waitTime/o1.serveTime;
        double r2=(double)o2.waitTime/o2.serveTime;
        return r1>r2?1:-1;
    }
}
