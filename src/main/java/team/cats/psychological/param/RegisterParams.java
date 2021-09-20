package team.cats.psychological.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author wmgx
 * @create 2021-09-20-22:25
 **/
@Data
@Accessors(chain = true)
public class RegisterParams {
    @NotBlank(message = "姓名不能为空")
    private String userName;
    @NotBlank(message = "账号不能为空")
    private String account;
    @NotBlank(message = "性别不能为空")
    private Integer userGender;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotNull(message = "出生日期不能为空")
    private LocalDate userBirthday;
    @NotBlank(message = "重复密码不能为空")
    private String rePassword;
    @NotNull(message = "班级不能为空")
    private Long classId;
    @NotBlank(message = "家长名不能为空")
    private String parentName;
    @NotBlank(message = "家长手机号不能为空")
    private String parentPhone;
}
