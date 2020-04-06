package cn.mango.annotation;

import java.util.Date;

/**
 * @Author mango
 * @Since 2020/3/9 17:37
 **/
@TableName("tb_user")
public class User {

    @TableField(value = 11, type = TypeEnum.INTEGER)
    private Integer id;

    @TableField(value = 255)
    private String username;

    /**
     * 默认名称可以转化为驼峰形式--找api实现
     */
    @TableField(type = TypeEnum.DATATIME)
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
