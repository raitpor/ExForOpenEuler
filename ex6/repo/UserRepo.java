package repo;

import entity.User;

import java.util.ArrayList;

/**
 * @author Ayase
 * @date 2021/4/20-11:33
 */
public class UserRepo {
    /**
     * instance 静态单例
     * userMap 用户Map，键为用户名，值为密码
     * users 静态常量用户列表，导入到userMap中,本例中用户名长限制为8位且仅限英文及数字
     */
    private static UserRepo instance;
    private static ArrayList<User> userList;
    private static final String[][] users = {
            {"A", "1"},
            {"B", "2"}
    };

    /**
     * @MethodName getInstance
     * @Description TODO 获取单例
     * @Param []
     * @Return repo.UserRepo
     * @author Ayase
     * @date 14:22
     */
    public static UserRepo getInstance() {
        //若为空则初始化
        if (instance == null) {
            instance = new UserRepo();
        }
        return instance;
    }

    /**
     * @MethodName UserRepo
     * @Description TODO 私有构造方法
     * @Param []
     * @Return
     * @author Ayase
     * @date 14:42
     */
    private UserRepo() {
        userList = new ArrayList<>();
        //导入用户
        for (String str[] : users) {
            userList.add(new User(str[0],str[1]));
        }
    }

    /**
     * @MethodName getUserById
     * @Description TODO 通过id获取用户
     * @Param [id]
     * @Return entity.User
     * @author Ayase
     * @date 10:30
     */
    public User getUserById(int id){
        return userList.get(id);
    }

    public User getUserByName(String name){
        for(int i = 0 ; i < userList.size() ; i++){
            if(name.equals(userList.get(i).getName())){
                return userList.get(i);
            }
        }
        return null;
    }
}
