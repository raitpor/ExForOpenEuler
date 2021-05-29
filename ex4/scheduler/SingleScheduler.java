package scheduler;

import common.MyProcess;

import java.util.ArrayList;

/**
 * @author Ayase
 * @date 2021/4/17-15:03
 */
public abstract class SingleScheduler {
    /***************
     * 当前执行的线程
     **************/
    MyProcess nowProcess;

    /************
     * 进程表
     ***********/
    ArrayList<MyProcess> pList;

    public SingleScheduler(){
        pList = new ArrayList<>();
    }

    /***********************************************************
     * @MethodName getNowProcess
     * @Description TODO 进行调度后返回当前进程
     * @Param []
     * @Return common.MyProcess
     * @author Ayase
     * @date  10:51
     *********************************************************/
    public synchronized MyProcess getNextP(){
        //当前进程不为空时检查当前进程是否执行完，若结束则清除
        if(nowProcess != null){
            if(nowProcess.getRuntime() == 0){
                System.out.println("Process out!");
                nowProcess = null;
            }
        }
        //调度进程
        schedule();
        //若调度后无进程执行则CPU空闲
        if(nowProcess == null){
            return null;
        }
        else{
            return nowProcess;
        }
    };

    /***********************************************************
     * @MethodName addP
     * @Description TODO 将指定进程添加到进程列表中
     * @Param [p]
     * @Return void
     * @author Ayase
     * @date  10:52
     *********************************************************/
    public synchronized void addP(MyProcess p){
        if(p == null){
            return;
        }
        pList.add(p);
    }

    /***********************************************************
     * @MethodName schedule
     * @Description TODO 调度抽象方法，具体由子类实现
     * @Param []
     * @Return void
     * @author Ayase
     * @date 10:52
     *********************************************************/
    abstract void schedule();
}
