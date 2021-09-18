package team.cats.psychological.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Condition {
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
