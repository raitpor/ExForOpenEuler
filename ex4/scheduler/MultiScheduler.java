package scheduler;

import common.MyProcess;

/**
 * @author Ayase
 * @date 2021/5/12-11:40
 */
public interface MultiScheduler {
    MyProcess getNextP(int n);

    void addP(MyProcess p,int n);
}
