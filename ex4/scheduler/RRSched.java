package scheduler;

/**
 * @author Ayase
 * @date 2021/4/17-16:51
 */
public class RRSched extends SingleScheduler {
    /**
     * TIME_SLICE时间片，一个进程可以运行的时长
     * count计时器，由于第一次进行调度时会先加一，因此初始化为-1
     */
    private final static int TIME_SLICE = 5;
    private static int count = -1;
    @Override
    void schedule() {
        //计时器加一
        count++;

        if(nowProcess != null){
            if(count == TIME_SLICE){
                System.out.println("Time slice end.");
                //若队列内没有进程则没有需要调度的进程，CPU空闲
                if(!pList.isEmpty()){
                    pList.add(nowProcess);
                    nowProcess = pList.get(0);
                    pList.remove(0);
                    count = 0;
                }
            }
        }else{
            //若队列内没有进程则没有需要调度的进程，CPU空闲
            if(!pList.isEmpty()){
                nowProcess = pList.get(0);
                pList.remove(0);
                count = 0;
            }
        }
    }
}
