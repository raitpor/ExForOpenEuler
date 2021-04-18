package scheduler;

import common.MyProcess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Ayase
 * @date 2021/4/17-15:03
 */
public abstract class Scheduler{
    MyProcess nowProcess;

    ArrayList<MyProcess> pList;

    public Scheduler(){
        pList = new ArrayList<>();
    }

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

    public void addP(MyProcess p){
        pList.add(p);
    }

    abstract void schedule();

    public boolean isEnd() {
        if(pList.isEmpty() && nowProcess == null) {
            return true;
        }
        return false;
    }
}
