package scheduler;

/**
 * @author Ayase
 * @date 2021/4/17-16:22
 */
public class SjfSched extends SingleScheduler {
    /**
     * @MethodName schedule
     * @Description TODO 用sjf算法进行调度
     * @Param []
     * @Return void
     * @author Ayase
     * @date 21:00
     */
    @Override
    public void schedule() {
        //当前没有进程运行则进行调度
        if(nowProcess == null){
            //若当前队列不为空则进行调度
            if(!pList.isEmpty()) {
                //对队列进行从小到大排序
                pList.sort((p1, p2) -> p1.getRuntime()<=p2.getRuntime()?-1:1);
                //进程出队
                nowProcess = pList.get(0);
                pList.remove(0);
            }
        }
    }
}
