package wgqproject.demo.service;

import org.beetl.sql.core.engine.PageQuery;
import wgqproject.demo.entity.User;

import java.util.List;

public interface UserService  {
    PageQuery<User> pageUser(int pageNumber, int pageSize );
    User getUserOne(String name);
    List<User> user();
    int saveUser(User user);
    int updateUser(User user);
    int deleteUser(User user);
}
