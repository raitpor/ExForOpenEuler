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
     * init your processes
     * first number is arrivetime
     * second number is runtime
     */
    private static int[][] processInput = {{1,2},{6,8},{8,2}};
    private static HashMap<Integer,MyProcess> pMap = new HashMap<>();
    private static Scheduler scheduler;

    public static void main(String[] args) {
        //确定算法
        System.out.println("Please input the schedule:");
        switch(new Scanner(System.in).next()) {
            case "fifo":scheduler = new FifoSched();break;
            default:throw new IllegalArgumentException("输入错误");
        }
        //初始化进程
        for(int i = 0 ; i < processInput.length ;i++){
            pMap.put(processInput[i][0],new MyProcess(processInput[i][1]));
        }
        int timeCount = 0;
        //模拟时钟，限时1200
        while(timeCount++ < 1200){
            MyProcess add = pMap.get(timeCount);
            if(add != null){
                scheduler.addP(add);
                pMap.remove(timeCount);
            }
            System.out.println("Now timeCount:" + timeCount +"  " + scheduler.execute());
            if(pMap.isEmpty() && scheduler.isEnd()){
                break;
            }
        }
    }
}

