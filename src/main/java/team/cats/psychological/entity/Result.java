package team.cats.psychological.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

/**
 * @author lihaotian
 * @since 2021/8/23 16:01
 */
@Data
@Accessors(chain = true)
public class Result {
    @Data
    @Accessors(chain = true)
    class Condition{
        /**
         * 第n个变量
         */
        private Integer variable;
        /**
         * 类型  0 =
         *      1 >
         *      2 <
         *      3 >=
         *      4 <=
         */
        private Integer type;
        /**
         * 值
         */
        private Double value;
    }

    /**
     * 名称
     */
    private String name;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * 条件
     */
    private List<Condition> conditions;

    public Boolean check(List<Double> values){
        return check(values.toArray(new Double[values.size()]));
    }
    public Boolean check(Double[] values){
        for (Condition condition : conditions) {
            boolean res = true;
            if (condition.variable>=values.length)
                throw new ArrayIndexOutOfBoundsException();

            switch (condition.type){
                case 0: //=
                    res = values[condition.variable] ==condition.value;
                    break;
                case 1: // >
                    res = values[condition.variable]>condition.value;
                    break;
                case 2: //<
                    res = values[condition.variable]<condition.value;
                    break;
                case 3: //>=
                    res = values[condition.variable]>=condition.value;
                    break;
                case 4: //<=
                    res = values[condition.variable]<=condition.value;
                    break;
            }
            if (!res)
                return false;
        }
        return true;
    }
}
