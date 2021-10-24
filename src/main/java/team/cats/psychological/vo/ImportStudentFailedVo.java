package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author wmgx
 * @create 2021-10-24-1:25
 **/
@Data
@Accessors(chain = true)
public class ImportStudentFailedVo extends ImportFailedBaseVo {
    private String userName;
    private String account;
    private Integer userGender;
    private LocalDate userBirthday;
    private String parentName;
    private String parentPhone;
}
