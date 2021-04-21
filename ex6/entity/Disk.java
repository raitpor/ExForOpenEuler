package entity;

import fileSystem.AbstractFileSystem;
import fileSystem.MyFileSystem;

import java.util.ArrayList;

/**
 * @author Ayase
 * @date 2021/4/20-11:33
 */
public class Disk {
    /**
     * blocks 块表
     * NUM_BLOCK 配置块数
     */
    private AbstractFileSystem fileSystem;
    private ArrayList<Block> blocks;
    public static final int NUM_BLOCK = 2048;

    /**
     * @MethodName Disk
     * @Description TODO 依照设定常量初始化磁盘
     * @Param []
     * @Return
     * @author Ayase
     * @date 14:59
     */
    public Disk() {
        blocks = new ArrayList<>(NUM_BLOCK);
        for (int i = 0; i < NUM_BLOCK; i++) {
            blocks.add(new Block());
        }

        //也可以实现其他自己的模拟文件系统
        this.fileSystem = new MyFileSystem(this.blocks);
    }

    /**
     * @MethodName getBlock
     * @Description TODO 返回指定块号的块
     * @Param [n]
     * @Return entity.Block
     * @author Ayase
     * @date 15:05
     */
    public Block getBlock(int n) {
        return blocks.get(n);
    }

    public AbstractFileSystem getFileSystem() {
        return fileSystem;
    }
}
