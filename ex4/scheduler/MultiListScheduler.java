package scheduler;

import common.MyProcess;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Ayase
 * @date 2021/5/12-12:17
 */
public class MultiListScheduler implements MultiScheduler{
    public static final int LEAST_IN = -1;
    Queue<MyProcess>[] lists;
    MyProcess[] nowProcesses;

    /***********************************************************
     * @MethodName MultiListScheduler
     * @Description TODO 初始化调度器，n为CPU核数
     * @Param [n]
     * @Return
     * @author Ayase
     * @date 12:20
     *********************************************************/
    public MultiListScheduler(int n){
        lists = new Queue[n];
        for(int i = 0 ; i < n ; i++){
            lists[i] = new LinkedList();
        }
        nowProcesses = new MyProcess[n];
    }

    @Override
    public synchronized MyProcess getNextP(int n) {
        if(n > nowProcesses.length){
            throw new IndexOutOfBoundsException("查询index大于" + nowProcesses.length + ":"+ n);
        }
        //若进程结束则返回队头，使用算法为FIFO
        if(nowProcesses[n] != null){
            if(nowProcesses[n].getRuntime() == 0){
                lists[n].poll();
                nowProcesses[n] = lists[n].peek();
            }
        }else {
            nowProcesses[n] = lists[n].peek();
        }
        return nowProcesses[n];
    }

    /***********************************************************
     * @MethodName addP
     * @Description TODO 往指定索引列表添加进程
     * @Param [p]
     * @Return void
     * @author Ayase
     * @date 12:23
     *********************************************************/
    @Override
    public synchronized void addP(MyProcess p, int n) {
        if(n > lists.length){
            throw new IndexOutOfBoundsException("指定放入的列表索引超过当前核数:" + n);
        }

        if(n == LEAST_IN){
            int index = 0;
            int min = lists[0].size();
            //找到最少进程的列表添加进程
            for(int i = 1 ; i < lists.length ;i++){
                if(i == 2){
                    System.out.println("min"+min);
                    System.out.println("size"+lists[2].size());
                }
                if(min > lists[i].size()){
                    min = lists[i].size();
                    index = i;
                }
            }
            lists[index].add(p);
            System.out.println(index + "add");
        }else {
            System.out.println(n + "add");
            lists[n].add(p);
        }
    }
}
