package simulateOs;

import entity.*;
import repo.HardwareRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Ayase
 * @date 2021/5/23-10:37
 */
public class MyOS {
    private Memory memory;
    private Memory swapSpace;
    private MMU mmu;
    private ArrayList<MyPCB> pcbs;
    private Scanner reader;
    private ArrayList<Integer> freeList;
    private HashMap<Integer,Integer> pte;
    private int ptecount;

    public MyOS(){
        this.memory = HardwareRepo.getMemory();
        this.swapSpace = HardwareRepo.getSwapSpace();
        this.pcbs = new ArrayList<>();
        this.reader = new Scanner(System.in);
        reader.useDelimiter("\n");
        this.pte = new HashMap<>();
        //初始化空闲页框表
        this.freeList = new ArrayList<>();
        for(int i = 0 ; i < memory.getPageFrames().length ; i++){
            this.freeList.add(i);
        }
        this.ptecount = 0;

        mmu = new MMU(pte);
    }

    public void bash(){
        exit:
        while(true){
            System.out.print("请输入指令：");
            String[] str = reader.next().split(" ");
            switch(str[0]){
                case "np":{
                    newP();
                    break;
                }
                case "free":{
                    freeP(Integer.parseInt(str[1]));
                    break;
                }
                case "pp":{
                    printP(Integer.parseInt(str[1]));
                    break;
                }
                case "cp":{
                    checkP(Integer.parseInt(str[1]));
                    break;
                }
                case "exit":break exit;
                default:
                    System.out.println("没有这个指令！");break;
            }
            System.out.println();
            System.out.println("-----------------------------------------");
        }
    }

    public void newP(){
        int dataSize = (int)(Math.random()*HardwareRepo.SIZE_MEMORY/2)+1;
//        int dataSize = 5;
        if(dataSize > freeList.size()){
            System.out.println("内存不足！还需空间:" + (dataSize - freeList.size()));
            return;
        }
        MyPCB pcb = new MyPCB(dataSize);
        System.out.println("新建进程:" + pcb.getId() + "  长度:" + dataSize);
        for(int i = 0 ; i < dataSize ; i++){
            //出队
            int index = freeList.get(0);
            freeList.remove(0);

            //将进程数据写入内存
            char[] data = new char[PageFrame.PAGE_SIZE];
            for(int j = 0 ; j < PageFrame.PAGE_SIZE ; j++){
                data[j] = pcb.getExecuteData()[i*PageFrame.PAGE_SIZE+j];
            }
            memory.setPageFrame(index,data);

            //向pcb添加虚拟地址
            pte.put(ptecount,index);
            for(int j = 0;j < PageFrame.PAGE_SIZE ; j++){
                pcb.getAddrs().add(ptecount*PageFrame.PAGE_SIZE+j);
            }
            ptecount++;
        }
        pcbs.add(pcb);
    }

    public void freeP(int n){
        for(int i = 0 ; i < pcbs.size();i++){
            if(pcbs.get(i).getId() == n){
                MyPCB p = pcbs.get(i);
                for(int j = 0 ; j < p.getAddrs().size()/PageFrame.PAGE_SIZE;j++){
                    int addr = mmu.toRealAddr(p.getAddrs().get(j*PageFrame.PAGE_SIZE));
                    freeList.add(addr/PageFrame.PAGE_SIZE);
                    pte.remove(addr/PageFrame.PAGE_SIZE);
                    memory.clear(addr/PageFrame.PAGE_SIZE);
                }

                pcbs.remove(i);
                mmu.printHitRate();
                System.out.println("已释放进程" + n);
                return;
            }
        }
        System.out.println("找不到指定的进程！");
    }

    public void printP(int n){
        if(n == 2){
            System.out.println();
        }
        for(int i = 0 ; i < pcbs.size();i++){
            if(pcbs.get(i).getId() == n){
                MyPCB p = pcbs.get(i);
                for(int j = 0 ; j < p.getAddrs().size()/PageFrame.PAGE_SIZE;j++){
                    int addr = mmu.toRealAddr(p.getAddrs().get(j*PageFrame.PAGE_SIZE));
                    memory.printData(addr/PageFrame.PAGE_SIZE);
                }
                mmu.printHitRate();
                System.out.println();
                return;
            }
        }
        System.out.println("找不到指定的进程！");
    }

    /***********************************************************
     * @MethodName checkP
     * @Description TODO 检查地址转换是否正确
     * @Param [n]
     * @Return void
     * @author Ayase
     *********************************************************/
    public void checkP(int n){
        for(int i = 0 ; i < pcbs.size();i++){
            if(pcbs.get(i).getId() == n){
                MyPCB p = pcbs.get(i);
                for(int j = 0 ; j < p.getAddrs().size();j++){
                    //从pcb中获取虚拟地址经过mmu转换获得物理地址，然后通过物理地址获取数据
                    if(p.getExecuteData()[j] != memory.getData(mmu.toRealAddr(p.getAddrs().get(j)))){
                        System.out.println("数据不一致！");
                        return;
                    }
                }
                System.out.println("数据一致！");
                mmu.printHitRate();
                System.out.println();
                return;
            }
        }
        System.out.println("找不到指定的进程！");
    }
}
