package entity;

import java.util.HashMap;

/**
 * @author Ayase
 * @date 2021/5/23-10:24
 */
public class MMU {
    public static final int RANDOM_REMOVE = 0;
    public static final int LRU_REMOVE = 1;

    public static final int NUM_TLB = 10;
    private TLB[] TLBList;
    private HashMap<Integer,Integer> pte;
    private int[] bitmap;
    private int[] hitcount;
    private static class TLB{
        private int pageId;
        private int pageFrameId;

        TLB(int pageId,int pageFrameId){
            this.pageId = pageId;
            this.pageFrameId = pageFrameId;
        }

        @Override
        public String toString() {
            return "TLB{" +
                    pageId +
                    ", " + pageFrameId +
                    '}';
        }
    }

    public MMU(HashMap<Integer,Integer> pte){
        this.TLBList = new TLB[NUM_TLB];
        this.pte = pte;
        this.bitmap = new int[NUM_TLB];
        this.hitcount = new int[2];
    }

    public int toRealAddr(int virtualAddr){
        //在实际情况中直接查询pte需要一次访存，而使用TLB则不需要，因此会默认先使用TLB以节省开销。
        int pageFrameId;
        TLB tlb = findTLB(virtualAddr/PageFrame.PAGE_SIZE);
        if(tlb == null){
            pageFrameId = pte.get(virtualAddr/PageFrame.PAGE_SIZE);
            newTLB(virtualAddr/PageFrame.PAGE_SIZE,pageFrameId,RANDOM_REMOVE);
            System.out.println(pageFrameId);
            hitcount[1]++;
        }else {
            pageFrameId = tlb.pageFrameId;
            hitcount[0]++;
        }

        int bias = virtualAddr%PageFrame.PAGE_SIZE;
        return pageFrameId*PageFrame.PAGE_SIZE + bias;
    }

    private TLB findTLB(int pageId){
        for(int i = 0 ; i < TLBList.length ; i++){
            if(TLBList[i] == null){
                break;
            }
            if(TLBList[i].pageId == pageId){
                bitmap[i]++;
                return TLBList[i];
            }
        }
        return null;
    }

    /***********************************************************
     * @MethodName newTLB
     * @Description TODO
     * @Param [pageId, pageFrameId,flag]
     * @Return void
     * @author Ayase
     *********************************************************/
    private void newTLB(int pageId,int pageFrameId,int flag){
        if(isTLBFull()){
            switch (flag){
                case RANDOM_REMOVE:{
                    int index = randomRemove();
                    TLBList[index] = new TLB(pageId,pageFrameId);
                    bitmap[index] = 0;
                    break;
                }
                case LRU_REMOVE:{
                    int index = lruRemove();
                    TLBList[index] = new TLB(pageId,pageFrameId);
                    bitmap[index] = 0;
                    break;
                }
                default:throw new IllegalArgumentException("非法参数!");
            }
        }
        else {
            //未满则添加到空闲位
            for(int i = 0 ; i < TLBList.length ; i++){
                if(TLBList[i] == null){
                    TLBList[i] = new TLB(pageId,pageFrameId);
                    return;
                }
            }
        }
    }

    private int lruRemove(){
        int min = Integer.MAX_VALUE;
        int index = 0;
        for(int i = 0 ; i < bitmap.length; i++){
            if(min > bitmap[i]){
                min = bitmap[i];
                index = i;
            }
        }
        TLBList[index] = null;
        return index;
    }

    private int randomRemove(){
        int index = (int)(Math.random()*TLBList.length);
        TLBList[index] = null;
        return index;
    }

    private boolean isTLBEmpty(){
        for(int i = 0; i < TLBList.length; i++){
            if(TLBList[i] != null){
                return false;
            }
        }
        return true;
    }

    private boolean isTLBFull(){
        for(int i = 0 ; i < TLBList.length ; i++){
            if(TLBList[i] == null){
                return false;
            }
        }
        return true;
    }

    public void printHitRate(){
        Double hitRate = (double)hitcount[0]/(double)(hitcount[0]+hitcount[1]);
        System.out.println("HitRate:" + hitRate*100 + "%");
        printTLBList();
        hitcount[0] = 0;
        hitcount[1] = 0;
    }

    public void printTLBList(){
        StringBuffer str = new StringBuffer();
        int i= 0;
        for(TLB tlb:TLBList){
            if(tlb == null){
                break;
            }
            str.append(i++ +"|" + tlb.toString() +" ");
        }
        System.out.println(str);
    }
}
