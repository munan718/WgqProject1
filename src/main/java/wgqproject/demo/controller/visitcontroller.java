package wgqproject.demo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wgqproject.demo.entity.User;
import wgqproject.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wgq")
@Api(value = "测试接口")
public class visitcontroller {
    @Autowired
    private UserService userService;
    @Autowired
    private SQLManager sqlManager;
    @GetMapping(value = "/select")
    /*分页接口*/
    @ApiOperation(value = "分页接口",notes = "返回分页数据")
    public PageQuery<User> getUser(@RequestParam int pageNumber, @RequestParam int pageSize) {
        List<User> users = new ArrayList<>();
        PageQuery<User> pageQuery = userService.pageUser(pageNumber,pageSize);
        return pageQuery;
    }

    /*查询接口*/
    @GetMapping("selectone")
    @ApiOperation(value = "查询接口",notes = "返回查询数据")
    public User getUserOne(@RequestParam String name){
        User user = new User();
        user = userService.getUserOne(name);
        return user;
    }

   /* 保存*/
    @PostMapping("/save")
    @ApiOperation(value = "保存接口",notes = "返回true false")
    public boolean saveUser(@RequestBody User user){
        int saveuse= userService.saveUser(user);
        if (saveuse > 0){
            return true;
        }
       return false;
    }
    /*更新*/
    @PostMapping("/update")
    @ApiOperation(value = "更新接口",notes = "返回true false")
    public boolean update(@RequestBody User user){
        int update = userService.updateUser(user);
        if (update > 0){
            return true;
        }
        return false;
    }
    /*删除*/
    @PostMapping("/delete")
    @ApiOperation(value = "删除接口",notes = "返回true false")
    public boolean delete(@RequestBody User user){
        int delete = userService.deleteUser(user);
        if (delete > 0){
            return true;
        }
        return false;
    }
}
