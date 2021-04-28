package tlb;

/**
 * @author Ayase
 * @date 2021/4/28-15:02
 */
public class TLBList {
    /**************************************************
     * tlbs tlb数组
     * bitmap 用于统计使用次数（辅助LRU算法实现）
     *************************************************/
    TLB[] tlbs;
    int[] bitmap;

    public TLBList(){
        tlbs = new TLB[MMU.NUM_TLB];
        bitmap = new int[MMU.NUM_TLB];
        for(int i = 0 ; i < MMU.NUM_TLB ; i++){
            tlbs[i] = null;
        }
    }

    /***********************************************************
     * @MethodName size
     * @Description TODO 返回tlbs中项的数目
     * @Param []
     * @Return int
     * @author Ayase
     * @date 16:37
     *********************************************************/
    public int size(){
        int count = 0;
        for(int i = 0 ; i < tlbs.length ; i++){
            if(tlbs[i] != null){
                count++;
            }
        }
        return count;
    }

    /***********************************************************
     * @MethodName get
     * @Description TODO 给出索引返回指定索引的tlb项
     * @Param [index]
     * @Return tlb.TLB
     * @author Ayase
     * @date 15:17
     *********************************************************/
    public TLB get(int index){
        TLB tlb = new TLB(tlbs[index].getPageId(),tlbs[index].getPageFrameId());
        return tlb;
    }

    /***********************************************************
     * @MethodName contains
     * @Description TODO 查找是否含有指定id的tlb项
     * @Param [pid]
     * @Return boolean
     * @author Ayase
     * @date 15:16
     **********************************************************/
    public boolean contains(int pid){
        for(int i = 0 ; i < tlbs.length ; i++){
            if(tlbs[i] == null){
                continue;
            } else{
                if(tlbs[i].getPageId() == pid){
                    return true;
                }
            }
        }
        return false;
    }

    /***********************************************************
     * @MethodName lru
     * @Description TODO LRU算法更新LRU，实验中不提供
     * @Param [tlb]
     * @Return int 返回状态码，0为hit，1为miss
     * @author Ayase
     * @date 16:37
     *********************************************************/
    public int lru(TLB tlb){
        if(contains(tlb.getPageId())){
            System.out.println("Hit!");
            return 0;
        }

        if(size() < tlbs.length){
            bitmap[size()]++;
            tlbs[size()] = tlb;
            System.out.println("new");
            return -1;
        }

        System.out.println("miss!");
        int min = Integer.MAX_VALUE;
        int index = 0;
        for(int i = 0 ; i < bitmap.length ;i++){
            if(bitmap[i] < min){
                min = bitmap[i];
                index = i;
            }
        }

        bitmap[index] =  1;
        tlbs[index] = tlb;
        return 1;
    }

    /***********************************************************
     * @MethodName random
     * @Description TODO 随机算法，实验中不提供
     * @Param [tlb]
     * @Return int
     * @author Ayase
     * @date 17:04
     *********************************************************/
    public int random(TLB tlb){
        if(contains(tlb.getPageId())){
            System.out.println("Hit!");
            return 0;
        }

        if(size() < tlbs.length){
            bitmap[size()]++;
            tlbs[size()] = tlb;
            System.out.println("new");
            return -1;
        }

        System.out.println("Miss!");
        int ran = (int)(Math.random()*MMU.NUM_TLB);
        tlbs[ran] = tlb;
        return 1;
    }
}
