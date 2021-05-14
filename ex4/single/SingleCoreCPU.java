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
    /************************************************
     * 请在此初始化你的进程内容，0位为到达时间，1位为运行时间
     ***********************************************/
    public static final int TIME_LIMIT = 60;
    private SingleScheduler singleScheduler;

    public SingleCoreCPU(SingleScheduler scheduler){
        this.singleScheduler = scheduler;
    }

    @Override
    public void run() {
        int timecount = 0;
        long startTime = System.currentTimeMillis();
        while(true){
            long nowTime = System.currentTimeMillis();

            if(nowTime - startTime > timecount*1000){
                //从调度类获取当前进程并运行
                MyProcess now = singleScheduler.getNowProcess();
                timecount++;
                if(now != null){
                    System.out.println("Now timeCount:" + timecount + "  NowProcess:" + now.getId());
                    now.execute();
                }else {
                    System.out.println("Now timecount:" + timecount + " Idle");
                }
            }
            if(nowTime - startTime > TIME_LIMIT*1000){
                break;
            }
        }
    }

    private synchronized void add2Sched(MyProcess p){
        singleScheduler.addP(p);
    }

    public static void main(String[] args) {
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

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(()->{
            long startTime = System.currentTimeMillis();
            int timecount = 0;
            while (true) {
                long now = System.currentTimeMillis();

                if(now - startTime > timecount*1000){
                    timecount++;
                    cpu.add2Sched(pMap.get((int)(now-startTime)/1000));
                    pMap.remove((int)(now-startTime)/1000);
                }
                if(pMap.isEmpty()){
                    break;
                }
            }
        });
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        pool.execute(cpu);

        pool.shutdown();
    }
}

