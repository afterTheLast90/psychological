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
     * 名称
     */
    private String name;
    /**
     * 介绍
     */
    private Integer introduction;
    /**
     * 类型 0 求和 1求平均 2 求个数 3常量 4 表达式
     */
    private Integer type;
    /**
     * 表达式
     *  type = 0  experssion 值为 0-因子的个数 0为全部求和 非0为指定因子求和
     *  type = 1  experssion 值为 0-因子的个数 0为全部求平均 非0为指定因子求平均
     *  type = 2  experssion 值为 数值/选项（ABCDE） 0-因子的个数 0为全部求个数 非0为指定因子求个数(A的个数，B的个数)
     *  type = 3  experssion 值为 数值 表示常量
     *  type = 4  experssion 值为 1+2 1-2 1*2 1/2 分别标识第一个变量加减乘除第二个变量
     */
    private String expression;
}
