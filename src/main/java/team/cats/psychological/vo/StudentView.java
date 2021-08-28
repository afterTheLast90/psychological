package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Classes;
import team.cats.psychological.entity.School;
import team.cats.psychological.entity.Users;

import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
public class StudentView{
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
     * 删除标记;0否，1是
     */
    private Boolean deleteFlag;

    /**
     * 状态
     */
    private Integer state;

    private Integer age;

    private List<ClassesView> classes;

    private List<ParentView> parents;

}
