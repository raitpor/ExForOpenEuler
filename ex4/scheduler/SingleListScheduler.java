package scheduler;

import common.MyProcess;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Ayase
 * @date 2021/5/12-11:42
 */
public class SingleListScheduler implements MultiScheduler{
    public static final int TIME_SLICE = 2;
    private Queue<MyProcess> list = new LinkedList();
    private MyProcess[] nowProcesses;
    private int[] timecount;

    public SingleListScheduler(int n) {
        nowProcesses = new MyProcess[n];
        timecount = new int[n];
    }

    @Override
    public synchronized MyProcess getNextP(int n) {
        if(n > nowProcesses.length){
            throw new IndexOutOfBoundsException("查询index大于" + nowProcesses.length + ":"+ n);
        }
        //若进程结束则返回队头，使用算法为RR
        if(nowProcesses[n] != null){
            //更新当前进程
            if(nowProcesses[n].getRuntime() == 0){
                nowProcesses[n] = list.poll();
                System.out.println("CPU"+ n +" Process out!  ");
                timecount[n] = 1;
            }else{
                if(timecount[n] < TIME_SLICE){
                    timecount[n]++;
                }else {
                    System.out.println("CPU"+ n +" Time slice end!  ");
                    timecount[n] = 0;
                    addP(nowProcesses[n],n);
                    nowProcesses[n] = list.poll();
                }
            }
        }else {
            nowProcesses[n] = list.poll();
            timecount[n] = 0;
        }
        return nowProcesses[n];
    }

    @Override
    public synchronized void addP(MyProcess p,int n) {
        if(list.contains(p)){
            throw new RuntimeException("CPU"+n+"添加重复进程p:"+p.getId());
        }
        list.add(p);
    }

    @Override
    public synchronized String printList(int n) {
        StringBuffer str = new StringBuffer();
        str.append("List:");
        for(MyProcess p:list){
            str.append(" " + p.getId());
        }
        return str.toString();
    }
}

