package entity;

import java.util.ArrayList;

/**
 * @author Ayase
 * @date 2021/5/23-10:23
 */
public class MyPCB {
    /******************************************************************
     * executeData 为程序数据，本实验中用于验证答案，在现实情况中PCB不保存这些数据
     *****************************************************************/
    private static int idcount = 0;
    private int id;
    private ArrayList<Integer> addrs;
    private char[] executeData;

    public MyPCB(int n){
        this.id = idcount++;
        executeData = new char[n*PageFrame.PAGE_SIZE];
        addrs = new ArrayList<>();
        //随机生成数据
        for (int i = 0 ; i < n*PageFrame.PAGE_SIZE; i++){
            char c = (char)(Math.random()*26+97);
            executeData[i] = c;
        }
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getAddrs(){
        return addrs;
    }

    public char[] getExecuteData() {
        return executeData;
    }

    /***********************************************************
     * @MethodName getDataStr
     * @Description TODO
     * @Param []
     * @Return java.lang.String
     * @author Ayase
     *********************************************************/
    public String getDataStr(){
        StringBuffer str = new StringBuffer();
        for(char ch:executeData){
            str.append(ch);
        }

        return str.toString();
    }
}
