package qspinlock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Ayase
 * @date 2021/4/29-14:41
 */
public class QSpinLock {
    /******************************************************************
     * JOIN_TIME 进程到达时间
     * pool 线程池
     *****************************************************************/
    private static final int[] JOIN_TIME = {1,6,9,20,15};
    private static ExecutorService pool = Executors.newFixedThreadPool(2);

    private static AtomLock lock = new AtomLock(6);

    static class MyProcess implements Runnable{

        /********************************************
         * EXE_TIME 线程执行时间
         * idcount  静态id计数器，用于给创建的线程一个id
         * id       线程id
         *******************************************/
        private static final int EXE_TIME = 10;
        private static int idcount = 0;
        private int id;

        /***********************************************************
         * @MethodName MyProcess
         * @Description TODO 构造方法，每次新建线程计数器加一
         * @Param []
         * @Return
         * @author Ayase
         * @date 20:21
         *********************************************************/
        public MyProcess() {
            this.id = idcount++;
        }

        /***********************************************************
         * @MethodName run
         * @Description TODO 线程执行内容，在运行中获取锁状态，实验中该方法需要学生实现
         * @Param []
         * @Return void
         * @author Ayase
         * @date 20:21
         *********************************************************/
        @Override
        public void run() {
            //若锁不为空闲则自旋
            if(lock.getLocked() != 0){
                System.out.println("自旋:" + id);
            }
            while (true){
                //当前正在争用锁
                if(lock.getLocked() == 0){
                    //退出自旋，没有线程争用锁
                    lock.setPending(0);
                    break;
                }
                lock.setPending(1);
            }
            //获取锁
            lock.setLocked(1);
            System.out.println("开始执行:" + id);
            long now = System.currentTimeMillis();
            while (true){
                if(System.currentTimeMillis() - now > EXE_TIME*1000){
                    System.out.println("ID:"+id+" 获取到临界资源:" + lock.critical++);
                    System.out.println("完成:" + id);
                    System.out.println();
                    break;
                }
            }
            //释放锁
            lock.setLocked(0);
        }
    }


    public static void main(String[] args) throws Exception {
        //初始化进程到达时间表
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0 ; i < JOIN_TIME.length ;i++){
            list.add(JOIN_TIME[i]);
        }
        list.sort(Integer::compareTo);

        Queue<MyProcess> wait = new LinkedList();

        /****************************
         * timecount 开始时间，用于计时
         ***************************/
        long timecount = System.currentTimeMillis();

        needToComplete:
        while (true){
            if(!list.isEmpty() && System.currentTimeMillis() - timecount > list.get(0)*1000){
                list.remove(0);
                if(lock.getPending() == 0){
                    pool.execute(new MyProcess());
                }
                else{
                    MyProcess newP = new MyProcess();
                    System.out.println("ID:" + newP.id + "进入等待队列");
                    wait.add(newP);
                    lock.setTail(wait.size());
                }
            }

            //若当前没有争用从等待队列中则执行一个线程
            if(lock.getPending() == 0 && lock.getTail() > 0){
                System.out.println("出队:" + wait.peek().id);
                pool.execute(wait.poll());
                lock.setTail(wait.size());
            }

            //结束判定,该代码段会放入实验给出代码
            if(list.size() == 0){
                if(((ThreadPoolExecutor)pool).getActiveCount() == 0){
                    pool.shutdown();
                    break needToComplete;
                }
            }
        }
    }
}

class AtomLock{
    /*********************************************************************************
     * 原子锁，以下均为原子变量
     * locked   锁状态，1表示不可获取，0表示可获取
     * pending  锁的未决位，为1时表示至少一个线程等待锁，为0时要么锁没有争用要么等待锁的队列已存在
     * tail     队尾标识，为0时等待锁的队列未创建，即最多2个线程争用锁，值不为0时表示等待锁队列已存在
     * critical 临界资源
     ********************************************************************************/
    private int locked = 0;
    private int pending = 0;
    private int tail = 0;
    public int critical;

    /***********************************************************
     * @MethodName AtomLock
     * @Description TODO 构造方法
     * @Param [critical]
     * @Return
     * @author Ayase
     * @date 16:27
     *********************************************************/
    public AtomLock(int critical) {
        this.critical = critical;
    }

    //以下为getter和setter，均为原子操作

    public synchronized int getLocked() {
        return locked;
    }

    public synchronized void setLocked(int locked) {
        if(locked != 1 && locked != 0){
            throw new IllegalArgumentException("非法参数，范围（0，1）：" + locked);
        }
        this.locked = locked;
    }

    public synchronized int getPending() {
        return pending;
    }

    public synchronized void setPending(int pending) {
        if(pending != 1 && pending != 0){
            throw new IllegalArgumentException("非法参数，范围（0，1）：" + pending);
        }
        this.pending = pending;
    }

    public synchronized int getTail() {
        return tail;
    }

    public synchronized void setTail(int tail) {
        this.tail = tail;
    }

    @Override
    public String toString() {
        return "AtomLock{" +
                "locked=" + locked +
                ", pending=" + pending +
                ", tail=" + tail +
                ", critical=" + critical +
                '}';
    }
}
