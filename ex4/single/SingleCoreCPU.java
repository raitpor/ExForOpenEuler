package single;

import common.MyProcess;

import scheduler.*;

import java.util.*;

/**
 * @author Ayase
 * @date 2021/4/16-15:49
 */
public class SingleCoreCPU{
    /**
     * 请在此初始化你的进程情况
     */
    private static int[][] processInput = {{1,10},{6,8},{8,2},{9,7},{15,16}};
    private static HashMap<Integer,MyProcess> pMap = new HashMap<>();
    private static Scheduler scheduler;

    public static void main(String[] args) {
        //按照输入确定调度算法
        System.out.println("Please input the schedule:");
        switch(new Scanner(System.in).next()) {
            case "fifo":scheduler = new FifoSched();break;
            case "sjf":scheduler = new SjfSched();break;
            case "rr":scheduler = new RRSched();break;
            default:throw new IllegalArgumentException("输入错误");
        }
        //初始化进程
        for(int i = 0 ; i < processInput.length ;i++){
            pMap.put(processInput[i][0],new MyProcess(processInput[i][1]));
        }
        //初始化计时器
        int timeCount = 0;
        //模拟时钟，限时1200
        while(timeCount++ < 1200){
            MyProcess add = pMap.get(timeCount);
            //若该时间点有进程到达则添加到调度类内
            if(add != null){
                scheduler.addP(add);
                pMap.remove(timeCount);
            }
            //打印并调用调度类的execute方法
            System.out.println("Now timeCount:" + timeCount +"  " + scheduler.execute());
            if(pMap.isEmpty() && scheduler.isEnd()){
                break;
            }
        }
    }
}

