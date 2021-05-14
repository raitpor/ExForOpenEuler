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

    //进行调度后返回当前进程
    public synchronized MyProcess getNowProcess(){
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

    /**
     * @MethodName addP
     * @Description TODO 将指定进程添加到进程列表中
     * @Param [p]
     * @Return void
     * @author Ayase
     * @date 20:28
     */
    public synchronized void addP(MyProcess p){
        if(p == null){
            return;
        }
        pList.add(p);
    }

    /**
     * @MethodName schedule
     * @Description TODO 调度抽象方法，具体由子类实现
     * @Param []
     * @Return void
     * @author Ayase
     * @date 20:26
     */
    abstract void schedule();

//    /**
//     * @MethodName isEnd
//     * @Description TODO 是否已经没有进程可以调度
//     * @Param []
//     * @Return boolean
//     * @author Ayase
//     * @date 20:27
//     */
//    public boolean isEnd() {
//        if(pList.isEmpty() && nowProcess == null) {
//            return true;
//        }
//        return false;
//    }
}
