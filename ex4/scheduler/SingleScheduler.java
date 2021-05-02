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

    //进行调度后执行进程
    public String execute(){
        //调度进程
        schedule();
        StringBuilder result = new StringBuilder();
        //若调度后无进程执行则CPU空闲
        if(nowProcess == null){
            return "execute: 空闲";
        }
        else{
            result.append("execute: " + nowProcess.getId());
        }
        //CPU内有进程则执行一个时间单位，若执行完则销毁进程
        if(nowProcess.getRuntime()>0){
            nowProcess.execute();
            if(nowProcess.getRuntime() == 0){
                result.append("  process out");
                nowProcess = null;
            }
        }
        return result.toString();
    };

    /**
     * @MethodName addP
     * @Description TODO 将指定进程添加到进程列表中
     * @Param [p]
     * @Return void
     * @author Ayase
     * @date 20:28
     */
    public void addP(MyProcess p){
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

    /**
     * @MethodName isEnd
     * @Description TODO 是否已经没有进程可以调度
     * @Param []
     * @Return boolean
     * @author Ayase
     * @date 20:27
     */
    public boolean isEnd() {
        if(pList.isEmpty() && nowProcess == null) {
            return true;
        }
        return false;
    }
}
