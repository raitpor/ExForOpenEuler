package common;

/**
 * @author Ayase
 * @date 2021/4/17-15:02
 */
public class MyProcess{
    /**
     * idcount静态进程号，用于分配进程号
     * id进程号
     * runtime运行时间
     */
    private static int idcounters = 0;
    private int id;
    private int runtime;

    /**
     * @MethodName MyProcess
     * @Description TODO 构造方法，分配进程号并检查运行时间是否合法
     * @Param [runtime]
     * @Return
     * @author Ayase
     * @date 20:47
     */
    public MyProcess(int runtime){
        if(runtime < 1){
            throw new IllegalArgumentException("runtime < 0 !");
        }
        this.id = idcounters++;
        this. runtime = runtime;
    }

    /**
     * @MethodName getId
     * @Description TODO 获取id
     * @Param []
     * @Return int
     * @author Ayase
     * @date 20:49
     */
    public int getId() {
        return id;
    }

    /**
     * @MethodName getRuntime
     * @Description TODO 获取 运行时间
     * @Param []
     * @Return int
     * @author Ayase
     * @date 20:49
     */
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

    /***********************************************************
     * @MethodName equals
     * @Description TODO 比较两个进程是否相同
     * @Param [obj]
     * @Return boolean
     * @author Ayase
     * @date 10:16
     *********************************************************/
    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(obj == null){
            return false;
        }

        if(obj instanceof MyProcess){
            if(this.getId() == ((MyProcess) obj).getId()){
                return true;
            }
        }
        return false;
    }
}
