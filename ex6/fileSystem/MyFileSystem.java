package fileSystem;

import entity.Block;
import entity.Inode;

import java.util.ArrayList;

/**
 * @author Ayase
 * @date 2021/4/21-10:45
 */
public class MyFileSystem extends AbstractFileSystem{

    public MyFileSystem(ArrayList<Block> blocks) {
        super(blocks);
    }

    @Override
    void bash() {
        System.out.println("欢迎使用MyFileSystem!");
        while(getNowUser() == null){
            login();
        }

        System.out.print("[" + getNowUser().getName() + "@  " + readInode(0));
    }

    @Override
    void ls() {

    }

    @Override
    void read() {

    }

    @Override
    void write(String str,int flag) {

    }

    @Override
    void pwd() {

    }

    @Override
    void login() {

    }

    @Override
    void cd() {

    }

    @Override
    void mkdir() {

    }

    @Override
    void newFile() {

    }

    @Override
    void rm() {

    }

    @Override
    public Inode readInode(int n) {
        if(n > NUM_INODE){
            throw new IllegalArgumentException("超过inode存储地址："+ NUM_INODE);
        }
        return new Inode(blocks.get(n));
    }

    @Override
    public String readFile(Inode inode) {
        if(inode.getMode() == 0){

        }else if(inode.getMode() == 1){

        }
        throw new IllegalStateException("inode.mode数值异常：" + inode.getMode());
    }

}
