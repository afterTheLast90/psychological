package team.cats.psychological.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Area;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class UsersAndArea{
    /**
     * 用户ID
     */
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
     * 删除标记;0否，1是
     */
    private Boolean deleteFlag;

    /**
     * 状态
     */
    private Integer state;

    private List<Area> areas;

}
