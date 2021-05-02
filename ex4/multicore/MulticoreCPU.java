package multicore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Ayase
 * @date 2021/4/28-21:33
 */
public class MulticoreCPU {
    public static final int NUM_CORE = 3;

    public void run(){

//        ExecutorService pool = new ThreadPoolExecutor();
//                Executors.newFixedThreadPool(NUM_CORE);

//        pool.shutdown();
    }

    public static void main(String[] args) {
        MulticoreCPU cpu = new MulticoreCPU();

        cpu.run();
    }
}
