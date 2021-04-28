package fileSystem;

import entity.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ayase
 * @date 2021/4/20-15:29
 */
public abstract class AbstractFileSystem {
    /*******************************************************************************************************************
     * NUM_INODE  inode数量，前NUM_INODE个块保留为inode
     * NAME_LENGTH 文件名长度，一般不超过一个块长度：32
     ******************************************************************************************************************/
    public static final int NUM_INODE = 256;
    public static final int NAME_LENGTH = 16;

    /*******************************************************************************************************************
     * nowUser      当前登录的用户
     * nowDir       当前工作目录
     * bitmap       数据位图，用于管理空闲块（简化，实际应存在块中）
     * blocks       块表
     * scanner      读取指令
     * code         输入的指令，可以用split方法分割
     ******************************************************************************************************************/
    User nowUser;
    Inode nowDir;
    int[] bitmap;
    ArrayList<Block> blocks;
    Scanner scanner;
    String[] code;

    /********************************************************************
     * bash()      命令方法，实现指令系统
     * ls()        列出文件
     * read()      读取文件
     * write()     写入文件,只提供覆盖写入
     * login()     登录方法
     * cd()        打开目录
     * mkdir()     创建目录
     * newFile()   新建文件
     * rm()        删除文件
     *********************************************************************/
    public abstract void bash();

    abstract void ls();

    abstract void read();

    abstract void write();

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

    /***********************************************************
     * @MethodName block2inode
     * @Description TODO 将磁盘块转成inode，若超过存储inode的存储位置则报错
     * @Param [n]
     * @Return entity.Inode
     * @author Ayase
     * @date 10:35
     *********************************************************/
    Inode block2inode(int n) {
        if(n > NUM_INODE){
            throw new IllegalArgumentException("超过inode存储地址："+ NUM_INODE);
        }
        return new Inode(blocks.get(n),n);
    }

    /***********************************************************
     * @MethodName inode2file
     * @Description TODO inode转成MyFile对象
     * @Param [inode]
     * @Return entity.MyFile
     * @author Ayase
     * @date 10:36
     *********************************************************/
    MyFile inode2file(Inode inode) {
        ArrayList<Character> data = new ArrayList<>();
        ArrayList<Character> name = new ArrayList<>();

        for(int n = 0 ; n < inode.getiBlock().size(); n++){
            int blockAddr = inode.getiBlock().get(n);
            char[] bData = blocks.get(blockAddr).read();
            //读取文件名
            if(n == 0){
                for(int i = 0 ; i < NAME_LENGTH; i++){
                    if(bData[i]!=0){
                        name.add(Character.valueOf(bData[i]));
                    }
                }
                //读取数据
                for(int i = NAME_LENGTH ; i < bData.length ; i++){
                    data.add(Character.valueOf(bData[i]));
                }
            }else {
                //读取数据
                for (int i = 0 ; i < bData.length ; i++){
                    data.add(Character.valueOf(bData[i]));
                }
            }
        }
        char[] result = new char[data.size()];
        for(int i = 0 ; i < data.size() ; i++){
            result[i] = data.get(i);
        }

        char[] nameresult = new char[name.size()];
        for(int i = 0 ; i < name.size() ; i++){
            nameresult[i] = name.get(i);
        }

        return new MyFile(inode.getMode(),new String(nameresult),result);
    }
}