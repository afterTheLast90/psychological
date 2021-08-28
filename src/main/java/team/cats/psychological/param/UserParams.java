package team.cats.psychological.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UserParams {

    @NotNull(message = "userId不能为空")
    private Long userId;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String userName;

    @NotBlank(message = "账户不能为空")
    private String userAccount;

    @NotBlank(message = "密码不能为空")
    private String userPassword;

    @NotNull(message = "出生日期不能为空")
    private LocalDate userBirthday;

    @NotNull(message = "性别不能为空")
    private Long userGender;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "(?:0|86|\\+86)?1[3-9]\\d{9}", message = "手机号格式错误")
    private String userPhoneNumber;
}
