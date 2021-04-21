package test;

import entity.Disk;
import entity.Inode;
import fileSystem.MyFileSystem;

/**
 * @author Ayase
 * @date 2021/4/21-17:05
 */
public class Test {
    public static void main(String[] args) {

        Disk disk = new Disk();

        Inode inode = disk.getFileSystem().readInode(0);
        System.out.println();
    }
}
