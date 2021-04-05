import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class MyCPU<pubic> {
    private static class P{
        int time;
        char pName;
        P(int time,char name){
            this.time = time;
            this.pName = name;
        }

        @Override
        public String toString(){
            return "Time:" + time + "  Process:" + pName;
        }
    }

    /**
     * @MethodName fifo
     * @Description TODO 先进先出算法
     * @Param [ps]
     * @Return void
     * @author Ayase
     * @date 19:55
     */
    public static void fifo(P[] ps){
        int time = 0;
        int waitSum = 0;
        System.out.println("fifo:");
        for(int i = 0 ; i < ps.length ; i++){
            waitSum += time;
            System.out.println("Time:" + time + "  process:" + ps[i].pName);
            time += ps[i].time;
        }
        System.out.println("Time:" + time + "   End");
        System.out.println("WaitSum = " + waitSum);
    }

    /**
     * @MethodName sjf
     * @Description TODO 短进程优先算法
     * @Param [ps]
     * @Return void
     * @author Ayase
     * @date 19:55
     */
    public static void sjf(P[] ps){
        int time = 0;
        int waitSum = 0;
        System.out.println("sjf:");
        ArrayList<P> list = new ArrayList(Arrays.asList(ps));
        list.sort(new Comparator<P>() {
            @Override
            public int compare(P p, P t1) {
                if(p.time>t1.time){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        fifo(list.toArray(new P[list.size()]));
    }

    /**
     * @MethodName rr
     * @Description TODO 轮转调度
     * @Param [ps]
     * @Return void
     * @author Ayase
     * @date 19:56
     */
    public static void rr(P[] ps){
        int time = 0;
        int timeP = 5;
        ArrayList<P> list = new ArrayList(Arrays.asList(ps));
        while(!list.isEmpty()){
            for(int i = 0; i < list.size() ; i++){
                System.out.println("Time:" + time + ". Process:" + list.get(i).pName);
                if(timeP < list.get(i).time){
                    list.get(i).time -= timeP;
                    time += timeP;
                }
                else{
                    time += list.get(i).time;
                }
            }
        }
        System.out.println("Time:" + time + "  End");
    }


    public static void main(String[] args){
        P[] ps = {
            new P(1,'a'),
            new P(20,'b'),
            new P(3,'c'),
            new P(96,'d'),
            new P(2,'e')
        };
        fifo(ps);
        System.out.println();
        sjf(ps);
        System.out.println();
        rr(ps);
    }
}