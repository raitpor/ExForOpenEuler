package scheduler;

import common.MyProcess;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Ayase
 * @date 2021/5/12-11:42
 */
public class SingleListScheduler implements MultiScheduler{
    private Queue<MyProcess> list = new LinkedList();

    @Override
    public MyProcess getNextP(int n) {
        return list.poll();
    }

    @Override
    public void addP(MyProcess p,int n) {
        list.add(p);
    }
}
