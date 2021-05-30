package repo;

import entity.*;

/**
 * @author Ayase
 * @date 2021/5/23-10:34
 */
public class HardwareRepo  {
    public static final int SIZE_MEMORY = 64;

    private static Memory memory;

    private static Memory swapSpace;

    public static Memory getMemory() {
        if(memory == null){
            memory = new Memory(SIZE_MEMORY);
        }
        return memory;
    }

    public static Memory getSwapSpace() {
        if(swapSpace == null){
            swapSpace = new Memory(SIZE_MEMORY);
        }
        return swapSpace;
    }
}
