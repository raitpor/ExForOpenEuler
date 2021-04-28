package test;

import MemoryCount.Memory;
import MemoryCount.PageFrame;
import MemoryCount.PageTable;

/**
 * @author Ayase
 * @date 2021/4/27-15:01
 */
public class Test5 {
    public static void main(String[] args) {
        PageTable pageTable = PageTable.getInstance();
        Memory memory = new Memory();

        int addr = pageTable.getRealAddr();
        System.out.println(memory.getData(addr));
    }
}
