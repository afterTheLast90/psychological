package team.cats.psychological.param;

import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Result;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Accessors(chain = true)
public class ResultParams {
    /**
     * id
     */
    @NotNull(message = "结果Id不能为空")
    private Integer id;
    /**
     * 名称
     */
    @NotBlank(message = "结果名称不能为空")
    private String name;
    /**
     * 介绍
     */
    @NotBlank(message = "结果简介不能为空")
    private String introduction;
    /**
     * 条件
     */
    @Valid
    private List<ConditionParams> conditions;
}
