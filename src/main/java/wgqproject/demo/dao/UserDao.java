package wgqproject.demo.dao;

import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import wgqproject.demo.entity.User;

import java.util.List;

@Component
@SqlResource("test")
public interface UserDao extends BaseMapper<User> {

}
