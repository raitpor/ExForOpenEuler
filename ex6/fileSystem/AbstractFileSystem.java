package fileSystem;

import entity.Block;
import entity.Disk;
import entity.Inode;
import entity.User;

import java.util.ArrayList;

/**
 * @author Ayase
 * @date 2021/4/20-15:29
 */
public abstract class AbstractFileSystem {
    /*******************************************************************************************************************
     * NUM_INODE  inode数量，前NUM_INODE个块保留为inode
     * APPEND 续写
     * OVERWRITE 覆盖写入
     ******************************************************************************************************************/
    public static final int NUM_INODE = 32;
    public static final int MODE_APPEND = 0;
    public static final int MODE_OVERWRITE = 1;

    /*******************************************************************************************************************
     * nowUser      当前登录的用户
     * nowDir       当前工作目录
     * bitmap       数据位图，用于管理空闲块（简化，实际应存在块中）
     * blocks       块表
     ******************************************************************************************************************/
    User nowUser;
    Inode nowDir;
    int[] bitmap;
    ArrayList<Block> blocks;

    public AbstractFileSystem(ArrayList<Block> blocks) {
        //导入磁盘块表
        this.blocks = blocks;
        //数据位图位数与磁盘块数一致
        bitmap = new int[Disk.NUM_BLOCK];
        //初始化第一个块，即根目录inode，数据位图标记块已使用
        bitmap[0] = 1;
        bitmap[NUM_INODE] = 1;
        //全0字符数组
        char[] data = new char[32];

        data[1] = 1;
        //第3位开始记录数据块地址，BLOCK_SIZE为第一个数据块
        data[3] = Block.BLOCK_SIZE * 10;
        //写入到块中
        blocks.get(0).write(data);
    }

    /********************************************************************
     * bash()      命令方法，实现指令系统
     * ls()        列出文件
     * read()      读取文件
     * write()     写入文件
     * pwd()       显示当前工作目录
     * login()     登录方法
     * cd()        打开目录
     * mkdir()     创建目录
     * newFile()   新建文件
     * rm()        删除文件
     *********************************************************************/
    abstract void bash();

    abstract void ls();

    abstract void read();

    abstract void write(String str, int flag);

    abstract void pwd();

    abstract void login();

    abstract void cd();

    abstract void mkdir();

    abstract void newFile();

    abstract void rm();

    public User getNowUser() {
        return nowUser;
    }

    public Inode getNowDir() {
        return nowDir;
    }

    abstract public Inode readInode(int n);

    abstract public String readFile(Inode inode);
}
