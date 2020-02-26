package wgqproject.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "用户信息")
public class User  implements Serializable {
    @ApiModelProperty(value = "序号")
    private Integer id;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "时间")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public User() {

    }
}
