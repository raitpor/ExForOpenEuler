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

class MyProcess{
    private static int idcounters = 0;
    private int id;
    private int runtime;

    MyProcess(int runtime){
        if(runtime < 1){
            throw new IllegalArgumentException("runtime < 0 !");
        }
        this.id = idcounters++;
        this. runtime = runtime;
    }

    public int getId() {
        return id;
    }

    public int getRuntime() {
        return runtime;
    }

    @Override
    public String toString(){
        return "Pid" + getId();
    }

    /**
     * @MethodName execute
     * @Description TODO 进程执行
     * @Param []
     * @Return void
     * @author Ayase
     * @date 13:44
     */
    public void execute(){
        this.runtime--;
    }
}

abstract class Scheduler{
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



class FifoSched extends Scheduler{
    /**
     * 调度
     * @return
     */
    @Override
    public void schedule() {
        nowProcess = pList.poll();
    }
}