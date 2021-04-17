package scheduler;

/**
 * @author Ayase
 * @date 2021/4/17-15:04
 */
public class FifoSched extends Scheduler{
    /**
     * 调度
     * @return
     */
    @Override
    public void schedule() {
        nowProcess = pList.poll();
    }
}
