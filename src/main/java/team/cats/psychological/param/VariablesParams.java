package team.cats.psychological.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class VariablesParams {
    /**
     * id
     */
    @NotNull(message = "变量Id不能为空")
    private Integer id;
    /**
     * 名称
     */
    @NotBlank(message = "变量名称不能为空")
    private String name;
    /**
     * 介绍
     */
    @NotBlank(message = "变量简介不能为空")
    private String introduction;
    /**
     * 类型 0 求和 1求平均 2 求个数 3常量 4 表达式
     */
    @NotNull(message = "变量类型不能为空")
    private Integer type;
    /**
     * 因子
     */
    private Integer factor;
    /**
     * 选项
     */
    private String option;
    /**
     * 常量
     */
    private Integer constant;
    /**
     * 表达式1
     */
    private Integer operation1;
    /**
     * 运算符
     */
    private String operation;
    /**
     * 表达式2
     */
    private String operation2;
}
