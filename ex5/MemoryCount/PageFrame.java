package MemoryCount;

/**
 * @author Ayase
 * @date 2021/4/26-10:54
 */
public class PageFrame {
    /*****************************************************
     * FRAME_SIZE     内存页框大小
     * frame          页框用于存储数据
     ****************************************************/
    public static final int FRAME_SIZE = 10;
    private int[] frame;

    public PageFrame(){
        frame = new int[FRAME_SIZE];
        fillFrame();
    }

    /***********************************************************
     * @MethodName getFrame
     * @Description TODO 返回frame数据
     * @Param []
     * @Return int[]
     * @author Ayase
     * @date 11:00
     *********************************************************/
    public int[] getFrame() {
        //深拷贝防止frame被恶意修改
        int[] temp = new int[FRAME_SIZE];
        for(int i = 0 ; i < FRAME_SIZE ; i++){
            temp[i] = frame[i];
        }

        return temp;
    }

    /***********************************************************
     * @MethodName fillFrame
     * @Description TODO 随机数据填充页框
     * @Param []
     * @Return void
     * @author Ayase
     * @date 11:05
     *********************************************************/
    public void fillFrame(){
        for (int i = 0 ; i < FRAME_SIZE ; i++){
            frame[i] = (int)(Math.random()*100);
        }
    }
}
