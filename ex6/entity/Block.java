package entity;

/**
 * @author Ayase
 * @date 2021/4/20-11:02
 */
public class Block {
    /*******************************************************************************************************************
     * BLOCK_SIZE 块的大小，由于是模拟因此适当减小以降低复杂度
     * block 块，以byte数组存储数据
     *
     * 以下说明block规定
     * 一、inode
     *  1、Disk前NUM_INODE位保存inode
     *  2、inode第0位为文件类型 0为目录 1为文本文件
     *  3、inode第1位记录文件大小 最大为char型最大值（但实际受到磁盘块数影响）
     *  4、inode第2位为所有者id  为0时所有人可见
     *  5、inode第3位到第BLOCK_SIZE-1位是数据块地址
     *
     * 二、文件
     *  1、第一个数据块的前8位为文件名
     *  2、后面为数据
     ******************************************************************************************************************/
    public static final int BLOCK_SIZE = 32;
    private char[] block = new char[BLOCK_SIZE];

    /**
     * @MethodName write
     * @Description TODO 写入数据到数据块
     * @Param [data]
     * @Return void
     * @author Ayase
     * @date 15:26
     */
    public void write(char[] data) {
        if (data.length > BLOCK_SIZE) {
            throw new IllegalArgumentException("data长于一个块长度:"+BLOCK_SIZE);
        }
        for (int i = 0; i < data.length; i++) {
            block[i] = data[i];
        }
    }

    /**
     * @MethodName read
     * @Description TODO 从数据块中读取数据
     * @Param []
     * @Return byte[]
     * @author Ayase
     * @date 15:26
     */
    public char[] read() {
        return block;
    }
}
