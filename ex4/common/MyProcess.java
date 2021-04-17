package common;

/**
 * @author Ayase
 * @date 2021/4/17-15:02
 */
public class MyProcess{
    private static int idcounters = 0;
    private int id;
    private int runtime;

    public MyProcess(int runtime){
        if(runtime < 1){
            throw new IllegalArgumentException("runtime < 0 !");
        }
        this.id = idcounters++;
        this. runtime = runtime;
    }

    public int getId() {
        return id;
    }

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
}
