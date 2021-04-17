/**
 * @author Ayase
 * @date 2021/4/16-15:49
 */
public class MyCPU{
    /**
     * init your processes
     * first number is arrivetime
     * second number is runtime
     */
    private static int[][] processInput;
    private static HashMap<Integer,MyProcess> pMap;

    public static void main(String[] args) {
        for(int i = 0 ; i < processInput.length ;i++){
            pMap.put(processInput[i][0],new MyProcess(processInput[i][1]));
        }
        int timeCount = 0;
        while(!pMap.isEmpty()){
            
        }
    }
}

interface Scheduled{
    ArrayList<MyProcess> pList = new ArrayList<>();
    MyProcess nowProcess = null;

    void addP(MyProcess p);

    MyProcess getNowProcess();
}

class MyProcess{
    private static int idcounters = 0;
    private int id;
    private int runtime;

    MyProcess(int runtime){
        this.id = idcounters++;
        this. runtime = runtime;
    }
}

class FifoSched implements Scheduled{

}