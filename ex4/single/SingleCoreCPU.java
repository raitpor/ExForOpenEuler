package single;

import common.MyProcess;

import scheduler.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ayase
 * @date 2021/4/16-15:49
 */
public class SingleCoreCPU implements Runnable{
    /****************************************************************************
     * TIME_LIMIT 时间限制，单位为秒，运行TIME_LIMIT秒后无论进程是否全部运行完都会终止程序
     * singleScheduler 单核调度器，具体细节由实现该接口的类实现
     ***************************************************************************/
    public static final int TIME_LIMIT = 60;
    private SingleScheduler singleScheduler;

    /***********************************************************
     * @MethodName SingleCoreCPU
     * @Description TODO 传入一个调度器，确定算法
     * @Param [scheduler]
     * @Return
     * @author Ayase
     * @date 10:26
     *********************************************************/
    public SingleCoreCPU(SingleScheduler scheduler){
        this.singleScheduler = scheduler;
    }

    /***********************************************************
     * @MethodName run
     * @Description TODO 通过线程模拟CPU运行
     * @Param []
     * @Return void
     * @author Ayase
     * @Date  10:44
     *********************************************************/
    @Override
    public void run() {
        //计时器
        int timecount = 0;
        //记录开始时刻
        long startTime = System.currentTimeMillis();
        while(true){
            //记录当次循环开始的时刻
            long nowTime = System.currentTimeMillis();
            //计时
            if(nowTime - startTime > timecount*1000){
                //从调度类获取当前进程并运行
                MyProcess now = singleScheduler.getNextP();
                //计时器加一
                timecount++;
                //若获取的进程不为空则运行
                if(now != null){
                    System.out.println("Now timeCount:" + timecount + "  NowProcess:" + now.getId());
                    now.execute();
                }else {
                    //获取为空则CPU空闲
                    System.out.println("Now timecount:" + timecount + " Idle");
                }
            }
            //超时退出循环，线程结束
            if(nowTime - startTime > TIME_LIMIT*1000){
                break;
            }
        }
    }

    /***********************************************************
     * @MethodName add2Sched
     * @Description TODO 将进程添加到调度器内
     * @Param [p]
     * @Return void
     * @author Ayase
     * @date 10:29
     *********************************************************/
    private synchronized void add2Sched(MyProcess p){
        singleScheduler.addP(p);
    }

    /***********************************************************
     * @MethodName main
     * @Description TODO 主方法
     * @Param [args]
     * @Return void
     * @author Ayase
     * @date 10:29
     *********************************************************/
    public static void main(String[] args) {
        //此处初始化测试用例
        //第0位为到达时间，第1位是进程运行时间
        HashMap<Integer,MyProcess> pMap = new HashMap<>();
        int[][] testCase = {{1,10},{6,8},{8,2},{9,7},{15,16}};
        //初始化进程
        for(int i = 0; i < testCase.length ; i++){
            pMap.put(testCase[i][0],new MyProcess(testCase[i][1]));
        }
        SingleCoreCPU cpu;

        //按照输入确定调度算法
        System.out.println("Please input the schedule:");
        switch(new Scanner(System.in).next()) {
            case "fifo":
                cpu = new SingleCoreCPU(new FifoSched());break;
            case "sjf":
                cpu = new SingleCoreCPU(new SjfSched());break;
            case "rr":
                cpu = new SingleCoreCPU(new RRSched());break;
            default:throw new IllegalArgumentException("输入错误");
        }
        //创建线程池管理执行线程
        ExecutorService pool = Executors.newFixedThreadPool(2);
        //运行用户线程，即模拟用户执行程序产生进程
        pool.execute(()->{
            //记录开始运行时刻
            long startTime = System.currentTimeMillis();
            //计时器
            int timecount = 0;
            while (true) {
                //记录当次循环开始时刻
                long now = System.currentTimeMillis();
                //计时
                if(now - startTime > timecount*1000){
                    timecount++;
                    //向调度器添加进程
                    cpu.add2Sched(pMap.get((int)(now-startTime)/1000));
                    //从即将到来进程列表中移除已加入的进程
                    pMap.remove((int)(now-startTime)/1000);
                }
                //如果全部进程都进入调度器则退出循环，线程结束
                if(pMap.isEmpty()){
                    break;
                }
            }
        });
        //主线程休眠0.1秒保证用户进程先于cpu线程执行
        try {
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }

        //执行cpu线程
        pool.execute(cpu);
        //等待全部线程结束，关闭线程池
        pool.shutdown();
    }
}

