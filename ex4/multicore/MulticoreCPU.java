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
     * pool 线程池，用于模拟实现
     *************************************************************************/
    public static final int NUM_CORE = 3;
    private static final int TIME_LIMIT = 30;
    private static MultiScheduler scheduler;
    private static CPU[] cpus;
    private static ExecutorService pool;

    private static class CPU extends Thread{
        private static int idcount = 0;
        private int id;
        private MyProcess nowProcess;

        /***********************************************************
         * @MethodName CPU
         * @Description TODO
         * @Param []
         * @Return
         * @author Ayase
         * @date 21:59
         *********************************************************/
        CPU(){
            id = idcount++;
        }

        @Override
        public void run() {
            int timecount = 0;
            long startTime = System.currentTimeMillis();
            while(true){
                long nowTime = System.currentTimeMillis();

                if(nowTime - startTime > timecount*1000){
                    timecount++;
                    //从调度类获取当前进程并运行
                    if(nowProcess != null){
                        MyProcess next = MulticoreCPU.scheduler.getNextP(id);
                        //当前有进程的话，比较下一个进程是否原进程，是则继续运行该进程否则切换进程
                        if(!nowProcess.equals(next)){
                            if(nowProcess.getRuntime()>0){
                                scheduler.addP(nowProcess,id);
                            }
                            nowProcess = next;
                        }
                        //如果当前进程不为空则运行
                        if(nowProcess != null){
                            System.out.println("CPU"+id+" Now timeCount:" + timecount + "  NowProcess:" + nowProcess.getId());
                            nowProcess.execute();
                        }
                        else {
                            System.out.println("CPU"+id+" Now timeCount:" + timecount + "  Idle");
                        }
                    }
                    else {
                        nowProcess = scheduler.getNextP(id);
                        //如果当前进程不为空则运行
                        if(nowProcess != null){
                            System.out.println("CPU"+id+" Now timeCount:" + timecount + "  NowProcess:" + nowProcess.getId());
                            nowProcess.execute();
                        }
                        else {
                            System.out.println("CPU"+id+" Now timeCount:" + timecount + "  Idle");
                        }
                    }
                }
                if(nowTime - startTime > TIME_LIMIT*1000){
                    break;
                }
            }
        }
    }

    public MulticoreCPU() {
        cpus = new CPU[NUM_CORE];
        for(int i = 0; i < cpus.length ; i++){
            cpus[i] = new CPU();
        }

        pool = Executors.newFixedThreadPool(NUM_CORE+1);

        //按照输入确定调度算法
        System.out.println("Please input the schedule:");
        switch(new Scanner(System.in).next()) {
            case "ml":
                scheduler = new MultiListScheduler(NUM_CORE);break;
            case "sl":
                break;
            default:throw new IllegalArgumentException("输入错误");
        }
    }

    public static void main(String[] args) {
        MulticoreCPU cpu = new MulticoreCPU();

        //测试用例初始化
        Integer[][] testcase = {{1,12},{2,13},{3,14}};

        HashMap<Integer,MyProcess> map = new HashMap<>();
        for(int i = 0 ; i < testcase.length ; i++){
            map.put(testcase[i][0],new MyProcess(testcase[i][1]));
        }

        MulticoreCPU.pool.execute(()->{
            long startTime = System.currentTimeMillis();
            int timecount = 0;
            while (true) {
                long now = System.currentTimeMillis();

                if(now - startTime > timecount*1000) {
                    timecount++;
                    System.out.println(timecount-1);
                    if (!(map.get(timecount) == null)) {
                        scheduler.addP(map.get(timecount), -1);
                    }
                }
                if(now - startTime > TIME_LIMIT*1000){
                    break;
                }
            }
        });

        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i = 0 ; i < cpus.length ;i++){
            pool.execute(cpus[i]);
        }

        pool.shutdown();
    }
}
