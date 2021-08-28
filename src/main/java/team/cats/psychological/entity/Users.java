package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@TableName("users")
@Data
@Accessors(chain = true)
@ApiModel("用户实体类")
public class Users implements Serializable,Cloneable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * 出生日期
     */
    private LocalDate userBirthday;
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
    private Boolean deleteFlag;

    /**
     * 状态
     */
    private Integer state;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
