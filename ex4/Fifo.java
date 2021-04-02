public class Fifo{
    private static class P{
        int time;
        char pName;
        P(int time,char name){
            this.time = time;
            this.pName = name;
        }
    }

    public static void fifo(P[] ps){
        int time = 0;
        for(int i = 0 ; i < ps.length ; i++){
            System.out.println("Time:" + time + "  process:" + ps[i].pName);
            time += ps[i].time;
        }
        System.out.println("Time:" + time + "\nEnd");
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
    }
}