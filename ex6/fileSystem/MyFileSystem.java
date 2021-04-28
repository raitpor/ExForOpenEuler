package fileSystem;

import entity.*;
import repo.UserRepo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ayase
 * @date 2021/4/21-10:45
 */
public class MyFileSystem extends AbstractFileSystem{

    public MyFileSystem(ArrayList<Block> blocks) {
        //导入磁盘块表
        this.blocks = blocks;
        //数据位图位数与磁盘块数一致
        bitmap = new int[Disk.NUM_BLOCK];
        //初始化第一个块，即根目录inode，数据位图标记块已使用
        bitmap[0] = 1;
        //初始化根目录Inode指向的数据块，数据位图标记为已用
        bitmap[NUM_INODE] = 1;
        //全0字符数组
        char[] data = new char[32];
        //inode.size == 一个数据块字节大小
        data[1] = Block.BLOCK_SIZE;
        //第3位开始记录数据块地址，NUM_INODE为第一个数据块
        data[3] = NUM_INODE;
        //写入到块中
        blocks.get(0).write(data);

        //写入根目录数据(目录名)，初始为空目录
        data = new char[1];
        data[0] = 47;
        blocks.get(NUM_INODE).write(data);
        //初始化scanner
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        code = new String[1];
    }

    @Override
    public void bash() {
        System.out.println("欢迎使用MyFileSystem!");
        while(getNowUser() == null){
            login();
        }
        //登录成功后进入根目录
        nowDir = block2inode(0);


        while(!"exit".equals(code[0])){
            System.out.print("[" + getNowUser().getName() + "@ " + inode2file(nowDir).getName() +"]#");
            code = scanner.next().split(" ");

            switch (code[0]){
                case "ls":{ls();break;}
                case "cd":{cd();break;}
                case "read":{read();break;}
                case "write":{write();break;}
                case "mkdir":{mkdir();break;}
                case "newfile":{newFile();break;}
                case "rm":{rm();break;}
                case "exit":{break;}
                default:{System.out.println("不存在的指令"); break;}
            }
        }
        //关闭scanner
        scanner.close();
    }

    /***********************************************************
     * @MethodName ls
     * @Description TODO 列出当前列表中的文件
     * @Param []
     * @Return void
     * @author Ayase
     * @date 9:26
     *********************************************************/
    @Override
    void ls() {
        ArrayList<Integer> inodes = new ArrayList<>();
        for(int i = 0 ; i < nowDir.getiBlock().size() ; i++){
            //获取数据块数据
            Block data = blocks.get(nowDir.getiBlock().get(i));
            if(i>0){
                for(int n = 0 ; data.read()[n] != 0 ; n++){
                    inodes.add((int)data.read()[n]);
                }
            }else{
                for(int n = NAME_LENGTH ; data.read()[n] != 0 ; n++){
                    inodes.add((int)data.read()[n]);
                }
            }
        }
        //整理目录
        StringBuilder list = new StringBuilder();
        for(int i:inodes){
            list.append(inode2file(block2inode(i)).getName() + "  ");
        }

        System.out.println(list);
    }

    /***********************************************************
     * @MethodName read
     * @Description TODO 读取当前目录下文件内容
     * @Param []
     * @Return void
     * @author Ayase
     * @date 11:34
     *********************************************************/
    @Override
    void read() {
        if(!code[1].endsWith(".txt")){
            System.out.println("仅支持读取.txt文件");
            return;
        }

        MyFile file = null;
        for(int i : inode2file(nowDir).getData()){
            if(inode2file(block2inode(i)).getName().equals(code[1])){
                file = inode2file(block2inode(i));
            }
        }
        if(file == null){
            System.out.println("不存在该文件");
        }else {
            StringBuilder str = new StringBuilder();
            for(char c:file.getData()){
                if(c != 0){
                    str.append(c);
                }
            }
            if("".equals(str.toString())){
                System.out.println("warning：空文件!");
            }else {
                System.out.println(str);
            }
        }
    }

    @Override
    void write() {
        if(!code[1].endsWith(".txt")){
            System.out.println("仅支持读取.txt文件");
            return;
        }

        MyFile file = null;
        int i = -1;
        String text;
        for(int j:inode2file(nowDir).getData()){
            if(inode2file(block2inode(j)).getName().equals(code[1])){
                file = inode2file(block2inode(j));
                i = j;
            }
        }
        if(file == null){
            System.out.println("不存在该文件");
            return;
        }else {
            System.out.println("请输入文件内容：");
            text = scanner.next();
        }
        if(text.length() > (Block.BLOCK_SIZE-3)*Block.BLOCK_SIZE){
            System.out.println("写入内容过大！");
            return;
        }

        //清空文件内容
        Inode wait2write = block2inode(i);
        for(int j:wait2write.getiBlock()){
            //清空该文件数据位图
            bitmap[j] = 0;
        }
        wait2write.getiBlock().clear();

        //重新写入内容
        //查找空闲块
        int l = text.length();
        double need = (double)(text.length()+NAME_LENGTH)/(double)Block.BLOCK_SIZE;
        int needBlock = (text.length()+NAME_LENGTH)/Block.BLOCK_SIZE;
        if(need>(double)needBlock){
            needBlock++;
        }
        ArrayList<Integer> freeBlock = new ArrayList<>();
        for(int j = NUM_INODE;j < bitmap.length ; j++){
            if(bitmap[j] == 0){
                freeBlock.add(j);
                bitmap[j] = 1;
            }
            if(freeBlock.size() == needBlock){
                break;
            }
        }
        //写入内容
        int count = 0;
        int writePos = 0;
        for(int j : freeBlock){
            Block block = blocks.get(j);
            if(count == 0){
                char[] data = new char[Block.BLOCK_SIZE];
                //重写入文件名
                for(int k = 0; k < code[1].length() ; k++){
                    data[k] = code[1].charAt(k);
                }
                for (int k = NAME_LENGTH ; k < Block.BLOCK_SIZE && writePos < text.length(); k++){
                    data[k] = text.charAt(writePos++);
                }
                block.write(data);
                count++;
            }
            else {
                char[] data = new char[Block.BLOCK_SIZE];
                for(int k = 0 ; k < Block.BLOCK_SIZE && writePos < text.length();  k++){
                    data[k] = text.charAt(writePos++);
                }
                block.write(data);
                count++;
            }
        }

        Block block = blocks.get(i);
        char[] data = block.read();
        int j = 3;
        for(int bit:freeBlock){
            data[j++] = (char)bit;
        }
        block.write(data);
    }

    /***********************************************************
     * @MethodName login
     * @Description TODO 登录
     * @Param []
     * @Return void
     * @author Ayase
     * @date 11:34
     *********************************************************/
    @Override
    void login() {
        User user = null;
        //查找用户
        while(nowUser == null){
            System.out.print("请输入用户名：");
            user = UserRepo.getInstance().getUserByName(scanner.next());
            if(user == null){
                System.out.println("用户不存在！");
                System.out.println();
            }
            //核对密码
            if(user != null){
                System.out.print("请输入密码：");
                if(user.getPwd().equals(scanner.next())){
                    nowUser = user;
                    System.out.println("登录成功！");
                    System.out.println();
                }else {
                    System.out.println("密码错误！");
                    System.out.println();
                }
            }
        }

    }

    /***********************************************************
     * @MethodName cd
     * @Description TODO 目录跳转
     * @Param []
     * @Return void
     * @author Ayase
     * @date 11:34
     *********************************************************/
    @Override
    void cd() {
        if(code.length != 2){
            System.out.println("无效指令，请查看指令格式！");
            return;
        }
        //进入上一层目录
        if("..".equals(code[1])){
            if(nowDir.getId() == 0){
                System.out.println("没有上一层目录！");
                return;
            }
            String target = inode2file(nowDir).getFullName();
            int i = target.length();
            int j = inode2file(nowDir).getName().length();
            target = target.substring(0,i-j);
            code[1] = target;
            cd();
            return;
        }

        //从父目录开始查找
        if(code[1].contains("/")){
            if("/".equals(code[1])){
                nowDir = block2inode(0);
                return;
            }

            String[] dirs = code[1].split("/");
            //从根目录开始进入
            Inode now = new Inode(blocks.get(0),0);

            for(int i = 0 ; i < dirs.length ; i++){
                MyFile file = inode2file(now);
                //寻找该文件名目录
                for(int blockId:file.getData()){
                    MyFile temp = inode2file(block2inode(blockId));
                    //判断该文件名目录
                    if(dirs[i].equals(temp.getName())){
                        //判断是否目录
                        if(temp.getMode() == 1){
                            System.out.println("这个不是目录!");
                            return;
                        }
                        now = block2inode(blockId);
                        break;
                    }
                }
            }
            //找到则更改当前目录
            if(inode2file(now).getName().equals(dirs[dirs.length-1])) {
                nowDir = now;
            }else {
                System.out.println("没有这个目录！");
            }
        }
        else{
            //查找指定目录
            for(int i = 0 ; i < inode2file(nowDir).getData().length ; i++){
                if(code[1].equals(inode2file(block2inode(inode2file(nowDir).getData()[i])).getName())){
                    if(inode2file(block2inode(inode2file(nowDir).getData()[i])).getMode() == 1){
                        System.out.println("这个不是目录!");
                        return;
                    }
                    //切换当前目录
                    nowDir = block2inode(inode2file(nowDir).getData()[i]);
                    return;
                }
            }
            //找不到报错
            System.out.println("没有这个目录！");
        }
    }

    /***********************************************************
     * @MethodName mkdir
     * @Description TODO 在当前目录创建目录
     * @Param []
     * @Return void
     * @author Ayase
     * @date 9:28
     *********************************************************/
    @Override
    void mkdir() {
        if(code.length < 2){
            System.out.println("无效指令，请查看指令格式！");
            return;
        }

        if(code[1].contains("/")){
            System.out.println("文件名中包含非法字符\"/\"！");
            return;
        }

        //检查是否已含有该目录
        MyFile file = inode2file(nowDir);
        for(int i = 0 ;  i < file.getData().length ; i++){
            if(inode2file(block2inode(file.getData()[i])).getName().equals(code[1])){
                System.out.println("当前目录已有该文件/目录！");
                return;
            }
        }

        if(nowDir.getId() != 0){
            code[1] = "/"+code[1];
        }
        String fullName = inode2file(nowDir).getFullName() + code[1];
        if(fullName.length()>NAME_LENGTH){
            System.out.println("文件名过长！length:" + NAME_LENGTH);
            return;
        }
        //寻找空闲可存放inode的块
        int availableInode = 0;
        while(bitmap[availableInode] == 1 && availableInode<NUM_INODE){
            availableInode++;
        }
        if(availableInode == NUM_INODE){
            System.out.println("可存储文件满！文件数：" + NUM_INODE);
            return;
        }
        //寻找空闲数据块
        int availableBlock = NUM_INODE;
        while(bitmap[availableBlock] == 1 && availableBlock>=NUM_INODE){
            availableBlock++;
            if(availableBlock == blocks.size()){
                System.out.println("磁盘无空间！");
                return;
            }
        }

        //写入数据到块中
        char[] inode = new char[32];
        char[] data = new char[32];

        inode[0] = 0;
        inode[1] = 1;
        inode[2] = (char)nowUser.getId();
        inode[3] = (char)availableBlock;

        for(int i = 0 ; i < fullName.length() ; i++){
            data[i] = fullName.charAt(i);
        }
        //写入数据
        blocks.get(availableInode).write(inode);
        blocks.get(availableBlock).write(data);
        //更新数据位图
        bitmap[availableInode] = 1;
        bitmap[availableBlock] = 1;

        //修改nowDir的数据块
        //获取最后一个数据块
        int last = nowDir.getiBlock().size()-1;
        if(last == 0){
            Block block = blocks.get(nowDir.getiBlock().get(last));
            //查找块中空闲位
            int i = NAME_LENGTH;
            for(; i < Block.BLOCK_SIZE ; i++){
                if(block.read()[i] == 0){
                    break;
                }
            }
            //若块满则添加新的数据块
            if(i > Block.BLOCK_SIZE){
                //找到空闲块
                int addBlock = availableBlock+1;
                while(bitmap[addBlock] == 1 && addBlock>=NUM_INODE){
                    addBlock++;
                    if(addBlock == blocks.size()){
                        System.out.println("磁盘无空间！");
                        return;
                    }
                }
                char[] addBlockData = {(char)availableBlock};
                blocks.get(addBlock).write(addBlockData);
                bitmap[addBlock] = 1;
                getNowDir().getiBlock().add(addBlock);
            }
            else {
                char[] temp = block.read();
                temp[i] = (char)availableInode;
                block.write(temp);
            }
        }
        else{
            Block block = blocks.get(nowDir.getiBlock().get(last));
            //查找块中空闲位
            int i = 0;
            for(; i < Block.BLOCK_SIZE ; i++){
                if(block.read()[i] == 0){
                    break;
                }
            }
            //若块满则添加新的数据块
            if(i > Block.BLOCK_SIZE){
                //找到空闲块
                int addBlock = availableBlock+1;
                while(bitmap[addBlock] == 1 && addBlock>=NUM_INODE){
                    addBlock++;
                    if(addBlock == blocks.size()){
                        System.out.println("磁盘无空间！");
                        return;
                    }
                }
                //写入数据块
                char[] addBlockData = {(char)availableBlock};
                blocks.get(addBlock).write(addBlockData);
                //修改数据位图
                bitmap[addBlock] = 1;
                //添加引用到父目录inode
                getNowDir().getiBlock().add(addBlock);
            }
            else {
                char[] temp = block.read();
                temp[i] = (char)availableInode;
                block.write(temp);
            }
        }
    }

    @Override
    void newFile() {
        if(!code[1].endsWith(".txt")){
            System.out.println("当前仅支持创建txt文件");
            return;
        }

        mkdir();

        MyFile file = inode2file(nowDir);
        for(int i:file.getData()){
            if(inode2file(block2inode(i)).getName().equals(code[1])){
                //更改文件属性
                char[] temp = blocks.get(i).read();
                temp[0] = 1;
                blocks.get(i).write(temp);
            }
        }
    }

    /***********************************************************
     * @MethodName rm
     * @Description TODO 简单起见不能删除目录（若有空闲时间可以尝试实现）
     * @Param []
     * @Return void
     * @author Ayase
     * @date 11:56
     *********************************************************/
    @Override
    void rm() {
        MyFile now = inode2file(nowDir);
        int rmId = -1;

        for(int i = 0 ; i < now.getData().length; i++){
            //查找要删除的文件
            if(code[1].equals(inode2file(block2inode(now.getData()[i])).getName())){
                rmId = now.getData()[i];
            }
        }

        if(rmId == -1){
            System.out.println("没有这个文件");
            return;
        }

        Inode rmInode = block2inode(rmId);

        if(inode2file(rmInode).getMode() == 0){
            System.out.println("不能删除目录！");
            return;
        }

        //更新数据位图信息
        for(int i:rmInode.getiBlock()){
            bitmap[i] = 0;
        }
        bitmap[rmId] = 0;

        //修改当前目录inode
        for(int i = 0 ; i < nowDir.getiBlock().size();i++){
            Block block = blocks.get(nowDir.getiBlock().get(i));
            char[] temp = block.read();
            if(i ==  0){
                for(int j = NAME_LENGTH ; j < Block.BLOCK_SIZE ; j++){
                    if(temp[j] == rmId){
                        if(j == 0){
                            bitmap[nowDir.getiBlock().get(i)] = 0;
                            nowDir.getiBlock().remove(i);
                        }
                        else {
                            temp[j] = 0;
                            block.write(temp);
                        }
                    }
                }
            }
            else{
                for(int j = 0 ; j < Block.BLOCK_SIZE ; j++){
                    if(temp[j] == rmId){
                        if(j == 0){
                            bitmap[nowDir.getiBlock().get(i)] = 0;
                            nowDir.getiBlock().remove(i);
                        }
                        else {
                            temp[j] = 0;
                        }
                    }
                }
            }

        }
    }

}
