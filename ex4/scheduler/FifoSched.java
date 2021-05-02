package scheduler;

/**
 * @author Ayase
 * @date 2021/4/17-15:04
 */
public class FifoSched extends SingleScheduler {
    /**
     * @MethodName schedule
     * @Description TODO 用FIFO算法调度进程
     * @Param []
     * @Return void
     * @author Ayase
     * @date 20:54
     */
    @Override
    public void schedule() {
        //若当前没有进程在运行则从队头取出一个进程执行
        if(nowProcess == null) {
            //若当前队列不为空则进行调度
            if(!pList.isEmpty()) {
                nowProcess = pList.get(0);
                pList.remove(0);
            }
        }
    }
}
