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
public class AreaParams {

    @NotNull(message = "地区Id不能为空")
    private Long areaId;

    @NotBlank(message = "地区不能为空")
    private String areaName;

    @NotNull(message = "市Id不能为空")
    private Long cityId;

    @NotBlank(message = "市不能为空")
    private String cityName;

    @NotNull(message = "省Id不能为空")
    private Long provinceId;

    @NotBlank(message = "省不能为空")
    private String provinceName;

    @NotNull(message = "负责人不能为空")
    private Long principal;
}
