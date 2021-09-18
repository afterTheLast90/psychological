package team.cats.psychological.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ConditionParams {
    /**
     * 第n个变量
     */
    @NotNull(message = "条件变量不能为空")
    private Integer variable;
    /**
     * 类型  0 =
     *      1 >
     *      2 <
     *      3 >=
     *      4 <=
     */
    @NotNull(message = "条件必须有类型")
    private Integer type;
    /**
     * 值
     */
    @NotNull(message = "条件值不能为空")
    private Double value;
}
