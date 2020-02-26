package wgqproject.demo.service.impl;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wgqproject.demo.dao.UserDao;
import wgqproject.demo.entity.User;
import wgqproject.demo.service.UserService;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public PageQuery<User> pageUser(int pageNumber, int pageSize) {
        LambdaQuery<User> query = userDao.createLambdaQuery();
        if (pageNumber > 0 && pageSize > 0) {
            return query.desc(User::getCreateDate).page(pageNumber, pageSize);
        }
        return null;
    }

    @Override
    public User getUserOne(String name) {
        return userDao.createLambdaQuery().andEq(User::getName,name).single();
    }

    @Override
    public List<User> user() {
        return userDao.createLambdaQuery().groupBy("create_date").select();
    }

    @Override
    public int saveUser(User user) {
        Date now = new Date();
        user.setCreateDate(now);
        int save = userDao.createLambdaQuery().insertSelective(user);
        if (save >0){
            return 1;
        }
        return 0;
    }

    @Override
    public int updateUser(User user) {
         return userDao.updateById(user);
    }

    @Override
    public int deleteUser(User user) {
        return userDao.deleteById(user.getId());
    }


}
