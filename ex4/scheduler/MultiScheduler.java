package scheduler;

import common.MyProcess;

/**
 * @author Ayase
 * @date 2021/5/12-11:40
 */
public interface MultiScheduler {
    /**************************************************************
     * @MethodName getNextP
     * @Description TODO 获取下一个进程
     * @Param [n] n指识从哪个队列获取进程，单队列调度器可以他用（如作为flag）
     * @Return common.MyProcess
     * @author Ayase
     *************************************************************/
    MyProcess getNextP(int n);

    /***********************************************************
     * @MethodName addP
     * @Description TODO 往调度器添加指定进程
     * @Param [p, n] n标识添加到哪个列表，若为-1则该进程为首次进入列表
     * @Return void
     * @author Ayase
     *********************************************************/
    void addP(MyProcess p,int n);

    /***********************************************************
     * @MethodName printList
     * @Description TODO 打印列表，用于观察调度器状态
     * @Param [n]
     * @Return java.lang.String
     * @author Ayase
     *********************************************************/
    String printList(int n);
}
