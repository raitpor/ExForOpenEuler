package tlb;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Ayase
 * @date 2021/4/27-17:27
 */
public class MMU {

    public static final int NUM_TLB = 5;
    public static int[][] testCase = {{5,3},{7,9},{4,2},{1,6},{0,1},{5,3},{3,7},{9,5},{2,4},{6,8},{0,1},{3,7},{9,5},{2,4},{6,8}};
//    public static final int NUM_PAGE = 10;

    private TLBList tlbs = new TLBList();

    public static void main(String[] args) {
        MMU mmu = new MMU();
        int hit = 0;
        int miss = 0;
        for(int i = 0 ; i < testCase.length ; i++){
            TLB tlb = new TLB(testCase[i][0],testCase[i][1]);
            System.out.println("Input:" + tlb);
            //在此处修改算法
            int code = mmu.tlbs.random(tlb);
            //统计
            if(code == 0){
                hit++;
            }else if(code == 1){
                miss++;
            }
        }
        System.out.println();
        System.out.println("Hit:" + hit);
        System.out.println("Miss" + miss);
    }

}
