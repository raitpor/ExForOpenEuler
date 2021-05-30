package entity;

/**
 * @author Ayase
 * @date 2021/5/24-10:21
 */
public class PageFrame {
    /************************************************************
     * PAGE_SIZE 页框大小
     * data      页框内数据
     ***********************************************************/
    public static final int PAGE_SIZE = 8;
    private char[] data;

    PageFrame(){
        data = new char[PAGE_SIZE];
    }

    PageFrame(PageFrame pf){
        this();
        setData(pf.getData());
    }

    /***********************************************************
     * @MethodName getData
     * @Description TODO 返回data，深拷贝防恶意修改
     * @Param []
     * @Return char[]
     * @author Ayase
     *********************************************************/
    public char[] getData(){
        char[] temp = new char[PAGE_SIZE];
        for(int i = 0 ; i < PAGE_SIZE ; i++){
            temp[i] = data[i];
        }

        return temp;
    }

    /***********************************************************
     * @MethodName setData
     * @Description TODO
     * @Param [data]
     * @Return void
     * @author Ayase
     *********************************************************/
    public void setData(char[] data) {
        this.data = data;
    }

    /***********************************************************
     * @MethodName clear
     * @Description TODO
     * @Param []
     * @Return void
     * @author Ayase
     *********************************************************/
    public void clear(){
        this.data = new char[PAGE_SIZE];
    }

    public void printData(){
        System.out.println("|" + new String(data));
    }

    @Override
    public String toString(){
        return new String(data);
    }
}
