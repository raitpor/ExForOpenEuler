package entity;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

/**
 * @author Ayase
 * @date 2021/4/20-11:33
 */
public class Inode {
    /*******************************************************************************
     * 以字节为单位的文件大小 size
     * 文件所有者          owner
     * 指向数据块          iBlock
     * 文件类型            mode
     * 简单起见一个块一个Inode,文件名存在第一个数据块的前8位  即iBlock.get(0)[0~7]
     ******************************************************************************/
    private int size;
    private int owner;
    private ArrayList<Integer> iBlock;
    private int mode;

//    降低复杂度，可自行完善
//    private int link;  连接数
//    private int editTime;  修改时间

    public Inode(Block block) {
        iBlock = new ArrayList<>();
        char[] data = block.read();
        this.mode = data[0];
        this.size = data[1];
        this.owner = data[2];
        for(int i = 3; i < Block.BLOCK_SIZE ; i++){
            if(data[i] == 0){
                break;
            }
            iBlock.add((int)data[i]);
        }
    }

/***********************************************************************************************************************
* getter和setter方法
***********************************************************************************************************************/
    public int getSize() {
        return size;
    }

    public int getOwner() {
        return owner;
    }

    public ArrayList<Integer> getiBlock() {
        return iBlock;
    }

    public int getMode() {
        return mode;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {

        return "Inode{" +
                "size=" + size +
                ", owner=" + owner +
                ", mode=" + mode +
                '}';
    }
}
