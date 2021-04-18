package scheduler;

/**
 * @author Ayase
 * @date 2021/4/17-16:22
 */
public class SjfSched extends Scheduler{
    @Override
    public void schedule() {
        if(nowProcess == null){
            if(!pList.isEmpty()) {
                pList.sort((p1, p2) -> {
                    return p1.getRuntime()<=p2.getRuntime()?-1:1;
                });
                nowProcess = pList.get(0);
                pList.remove(0);
            }
        }
    }
}
