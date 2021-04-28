package MemoryCount;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author Ayase
 * @date 2021/4/26-11:06
 */
public class Memory {
    /********************************************
     * NUM_PAGE 页框数
     * memory   页框数组，用于存储页框.
     ********************************************/
    public static final int NUM_PAGE = 10;
    private PageFrame[] memory;



    public Memory() {
        memory = new PageFrame[NUM_PAGE];
        for (int i = 0; i < NUM_PAGE; i++) {
            memory[i] = new PageFrame();
        }
    }

    /***********************************************************
     * @MethodName getData
     * @Description TODO 给出指定物理地址返回数据
     * @Param [addr]
     * @Return int
     * @author Ayase
     * @date 16:18
     *********************************************************/
    public int getData(int addr){
        if(addr > NUM_PAGE*PageFrame.FRAME_SIZE || addr < 0 ){
            throw new IllegalArgumentException("地址超出内存长度：" + NUM_PAGE*PageFrame.FRAME_SIZE + " Give:" + addr);
        }
        int pid = addr/PageFrame.FRAME_SIZE;
        int innerId = addr%PageFrame.FRAME_SIZE;

        return memory[pid].getFrame()[innerId];
    }
}
