package single;

import common.MyProcess;

import scheduler.*;

import java.util.*;

/**
 * @author Ayase
 * @date 2021/4/16-15:49
 */
public class SingleCoreCPU{
    /************************************************
     * 请在此初始化你的进程内容，0位为到达时间，1位为运行时间
     ***********************************************/
    private int[][] processInput = {{1,10},{6,8},{8,2},{9,7},{15,16}};
    private HashMap<Integer,MyProcess> pMap = new HashMap<>();
    private SingleScheduler singleScheduler;

    public void execute() {
        if(this.singleScheduler == null){
            //按照输入确定调度算法
            System.out.println("Please input the schedule:");
            switch(new Scanner(System.in).next()) {
                case "fifo":
                    this.singleScheduler = new FifoSched();break;
                case "sjf":
                    this.singleScheduler = new SjfSched();break;
                case "rr":
                    this.singleScheduler = new RRSched();break;
                default:throw new IllegalArgumentException("输入错误");
            }
        }
        //初始化进程
        for(int i = 0 ; i < this.processInput.length ;i++){
            this.pMap.put(this.processInput[i][0],new MyProcess(this.processInput[i][1]));
        }
        //初始化计时器
        int timeCount = 0;
        //模拟时钟，限时1200
        while(timeCount++ < 1200){
            MyProcess add = this.pMap.get(timeCount);
            //若该时间点有进程到达则添加到调度类内
            if(add != null){
                this.singleScheduler.addP(add);
                this.pMap.remove(timeCount);
            }
            //打印并调用调度类的execute方法
            System.out.println("Now timeCount:" + timeCount +"  " + this.singleScheduler.execute());
            if(this.pMap.isEmpty() && this.singleScheduler.isEnd()){
                break;
            }
        }
    }

    public static void main(String[] args) {
        SingleCoreCPU cpu = new SingleCoreCPU();
        cpu.execute();
    }
}

