package test;

import entity.Disk;

/**
 * @author Ayase
 * @date 2021/4/21-17:05
 */
public class Test {
    public static void main(String[] args) {
        Disk disk = new Disk();

        disk.getFileSystem().bash();
    }
}
