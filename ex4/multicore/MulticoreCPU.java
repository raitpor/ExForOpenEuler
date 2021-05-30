package multicore;

import common.MyProcess;
import scheduler.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ayase
 * @date 2021/4/28-21:33
 */
public class MulticoreCPU {
    /**************************************************************************
     * NUM_CORE CPU核心数
     * TIME_LIMIT 时间限制
     * nowProcesses CPU当前运行进程数组
     * cpus 存放CPU的数组
     * pool 线程池，用于模拟实现中运行CPU线程
     *************************************************************************/
    public static final int NUM_CORE = 3;
    private static final int TIME_LIMIT = 30;
    private static MultiScheduler scheduler;
    private static CPU[] cpus;
    private static ExecutorService pool;

    private static class CPU extends Thread{
        /*********************************************
         * idcount id计数，用于在构造方法中提供递增id
         * id      CPU的id，CPU唯一标识
         * nowProcess 当前运行中的进程
         ********************************************/
        private static int idcount = 0;
        private int id;
        private MyProcess nowProcess;

        /***********************************************************
         * @MethodName CPU
         * @Description TODO 构造方法，分配id
         * @Param []
         * @Return
         * @author Ayase
         *********************************************************/
        CPU(){
            id = idcount++;
        }

        /***********************************************************
         * @MethodName run
         * @Description TODO 实现Runnable接口，
         * @Param []
         * @Return void
         * @author Ayase
         *********************************************************/
        @Override
        public void run() {
            //计时器
            int timecount = 0;
            //记录开始运行时刻
            long startTime = System.currentTimeMillis();
            while(true){
                //记录当次循环开始时刻
                long nowTime = System.currentTimeMillis();
                //计时
                if(nowTime - startTime > timecount*1000){
                    //计时
                    timecount++;
                    //打印调度器内列表状态，若不方便观察可将该语句注释
                    System.out.println(scheduler.printList(id));
                    //从调度类获取当前进程并运行
                    if(nowProcess != null){
                        MyProcess next = scheduler.getNextP(id);
                        //当前有进程的话，比较下一个进程是否原进程，是则继续运行该进程否则切换进程
                        if(!nowProcess.equals(next)){
                            nowProcess = next;
                        }
                        //如果当前进程不为空则运行
                        if(nowProcess != null){
                            System.out.println("CPU"+id+" Now timeCount:" + timecount + "  NowProcess:" + nowProcess.getId());
                            nowProcess.execute();
                        }
                        else {
                            //当前没有进程运行，CPU空闲
                            System.out.println("CPU"+id+" Now timeCount:" + timecount + "  Idle");
                        }
                    }
                    else {
                        //从调度器中获取下一个进程
                        nowProcess = scheduler.getNextP(id);
                        //如果当前进程不为空则运行
                        if(nowProcess != null){
                            System.out.println("CPU"+id+" Now timeCount:" + timecount + "  NowProcess:" + nowProcess.getId());
                            nowProcess.execute();
                        }
                        else {
                            //获取进程为空则CPU空闲
                            System.out.println("CPU"+id+" Now timeCount:" + timecount + "  Idle");
                        }
                    }
                }
                //超时退出循环，线程结束
                if(nowTime - startTime > TIME_LIMIT*1000){
                    break;
                }
            }
        }
    }

    /***********************************************************
     * @MethodName MulticoreCPU
     * @Description TODO 构造方法
     * @Param []
     * @Return
     * @author Ayase
     *********************************************************/
    public MulticoreCPU() {
        //初始化CPU数组
        cpus = new CPU[NUM_CORE];
        //初始化所有CPU
        for(int i = 0; i < cpus.length ; i++){
            cpus[i] = new CPU();
        }
        //初始化线程池
        pool = Executors.newFixedThreadPool(NUM_CORE+1);

        //按照输入确定调度算法
        System.out.println("Please input the schedule:");
        switch(new Scanner(System.in).next()) {
            case "ml":
                scheduler = new MultiListScheduler(NUM_CORE);break;
            case "sl":
                scheduler = new SingleListScheduler(NUM_CORE);break;
            default:throw new IllegalArgumentException("输入错误");
        }
    }

    /***********************************************************
     * @MethodName main
     * @Description TODO
     * @Param [args]
     * @Return void
     * @author Ayase
     *********************************************************/
    public static void main(String[] args) {
        //测试用例初始化
        Integer[][] testcase = {{1,2},{2,5},{3,7},{4,6},{5,9},{6,8},{7,2},{8,1},{9,13}};

        HashMap<Integer,MyProcess> map = new HashMap<>();
        for(int i = 0 ; i < testcase.length ; i++){
            map.put(testcase[i][0],new MyProcess(testcase[i][1]));
        }
        //运行用户线程，模拟用户创建进程
        pool.execute(()->{
            //记录开始运行时刻
            long startTime = System.currentTimeMillis();
            //计时器
            int timecount = 0;
            while (true) {
                //记录当次循环开始时刻
                long now = System.currentTimeMillis();
                //计时
                if(now - startTime > timecount*1000) {
                    timecount++;
                    System.out.println("timecount "+(timecount));
                    //若该时刻有进程被创建则加入调度器队列中
                    if (!(map.get(timecount) == null)) {
                        //该处-1为标识进程第一次进入调度器
                        scheduler.addP(map.get(timecount), -1);
                    }
                }
                //超时退出循环，线程结束
                if(now - startTime > TIME_LIMIT*1000){
                    break;
                }
            }
        });
        //主线程休眠，确保用户线程先于CPU线程运行
        try {
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }
        //CPU模拟开始
        for(int i = 0 ; i < cpus.length ;i++){
            pool.execute(cpus[i]);
        }
        //等待所有线程执行完毕，关闭线程池
        pool.shutdown();
    }
}
