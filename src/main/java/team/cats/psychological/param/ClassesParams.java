package team.cats.psychological.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ClassesParams {
    @NotNull(message = "班级ID不能为空")
    private Long classId;

    @NotNull(message = "教师不能为空")
    private Long teacherId;

    @NotNull(message = "学校不能为空")
    private Long schoolId;

    @NotNull(message = "年级不能为空")
    private Long grade;

    @NotBlank(message = "班级名不能为空")
    private String className;
}
