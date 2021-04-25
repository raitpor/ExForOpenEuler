package entity;

/**
 * @author Ayase
 * @date 2021/4/22-15:20
 */
public class MyFile {
    private int mode;
    private String name;
    private char[] data;

    public MyFile(int mode,String name,char[] data){
        this.name = name;
        this.mode = mode;
        this.data = data;
    }

    public int getMode() {
        return mode;
    }

    public char[] getData() {
        return data;
    }

    /***********************************************************
     * @MethodName getFullName
     * @Description TODO 获取全路径名
     * @Param []
     * @Return java.lang.String
     * @author Ayase
     * @date 9:49
     *********************************************************/
    public String getFullName() {
        return name;
    }

    /***********************************************************
     * @MethodName getName
     * @Description TODO 获取文件名
     * @Param []
     * @Return java.lang.String
     * @author Ayase
     * @date 9:49
     *********************************************************/
    public String getName(){
        if ("/".equals(name)) {
            return "/";
        }
        String[]  strs = name.split("/");
        return strs[strs.length-1];
    }
}
