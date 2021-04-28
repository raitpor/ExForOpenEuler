package MemoryCount;

import java.util.HashMap;

/**
 * @author Ayase
 * @date 2021/4/27-14:23
 * @Description 实际上pte表是存在内存中的，该内存地址可以通过基址寄存器获取
 */
public class PageTable{
    /**********************************************************************
     *     pteList  pte表<addr,pteId>
     *     NUM_GENERATE_PTE  pte数量
     *     instance    静态单例
     *********************************************************************/
    private HashMap<Integer,Integer> pteList;
    private static final int NUM_GENERATE_PTE  = 5;
    private static PageTable instance = null;

    /***********************************************************
     * @MethodName PageTable
     * @Description TODO 构造方法随机添加pte
     * @Param []
     * @Return
     * @author Ayase
     * @date 17:00
     *********************************************************/
    private PageTable(){
        pteList = new HashMap<>();
        myLabel:
        for(int i = 0; i < NUM_GENERATE_PTE ; i++){
            while(true){
                int addr = (int)(Math.random()*Memory.NUM_PAGE);
                if(pteList.get(addr) == null){
                    pteList.put(addr,i);
                    continue myLabel;
                }
            }
        }
    }

    /***********************************************************
     * @MethodName getById
     * @Description TODO 通过id获取pte表中地址
     * @Param [id]
     * @Return int
     * @author Ayase
     * @date 17:00
     *********************************************************/
    private int getById(int id){
        for(int i:pteList.keySet()){
            if(pteList.get(i) == id){
                return i;
            }
        }
        return -1;
    }

    /***********************************************************
     * @MethodName getInstance
     * @Description TODO 获取静态单例
     * @Param []
     * @Return MemoryCount.PageTable
     * @author Ayase
     * @date 17:00
     *********************************************************/
    public static PageTable getInstance(){
        if(instance == null){
            instance = new PageTable();
        }
        return instance;
    }

    /***********************************************************
     * @MethodName getVirtualAddr
     * @Description TODO 获取虚拟地址，需要学生实现
     * @Param []
     * @Return int
     * @author Ayase
     * @date 17:01
     *********************************************************/
    public int getVirtualAddr() {
        int pteId = (int)(Math.random()*pteList.size());
        int offset = (int)(Math.random()*PageFrame.FRAME_SIZE);

        //实验指导中去除
        int addr = pteId*PageFrame.FRAME_SIZE  + offset;

        return addr;
    }

    /***********************************************************
     * @MethodName getRealAddr
     * @Description TODO
     * @Param []
     * @Return int
     * @author Ayase
     * @date 17:01
     *********************************************************/
    public int getRealAddr() {
        int va = getVirtualAddr();
        //实验指导中去除
        int pteId = va/PageFrame.FRAME_SIZE;
        int addr = getById(pteId)*PageFrame.FRAME_SIZE + va%PageFrame.FRAME_SIZE;

        return addr;
    }
}
