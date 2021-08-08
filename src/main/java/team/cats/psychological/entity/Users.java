package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@TableName("users")
@Data
@Accessors(chain = true)
public class Users implements Serializable,Cloneable {
    /**
     * 用户ID
     */
    @TableId
    private Long userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 性别;0女，1男，
     */
    private Long userGender;
    /**
     * 手机号
     */
    private String userPhoneNumber;
    /**
     * 手机号验证
     */
    private Long phoneNumberChecked;
    /**
     * 角色;0-超级管理，1-管理员，2-学生，3-家长，4-教师
     */
    private Long userRole;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 删除标记;0否，1是
     */
    private Long deleteFlag;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}