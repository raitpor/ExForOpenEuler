package scheduler;

import common.MyProcess;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Ayase
 * @date 2021/4/17-15:03
 */
public abstract class Scheduler{
    MyProcess nowProcess;

    Queue<MyProcess> pList;

    public Scheduler(){
        pList = new LinkedList<>();
    }

    public String execute(){
        if(nowProcess == null){
            schedule();
        }
        StringBuilder result = new StringBuilder();
        if(nowProcess == null){
            return "execute: 空闲";
        }
        else{
            result.append("execute: " + nowProcess.getId());
        }
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
