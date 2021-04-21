package entity;

/**
 * @author Ayase
 * @date 2021/4/20-21:24
 */
public class User {
    int id;
    String name;
    String pwd;
    static int idcount = 0;

    /**
     * @MethodName User
     * @Description TODO 构造方法
     * @Param [id, name, pwd]
     * @Return
     * @author Ayase
     * @date 21:56
     */
    public User(String name, String pwd) {
        this.id = idcount++;
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}