package team.cats.psychological.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 因子
 * @author lihaotian
 * @since 2021/8/23 15:51
 */
@Data
@Accessors(chain = true)
public class Variable {
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * 类型 0 求和 1求平均 2 求个数 3常量 4 表达式
     */
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
