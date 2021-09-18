package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author lihaotian
 * @since 2021/8/23 16:01
 */
@Data
@Accessors(chain = true)
public class Result {
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
     * 条件
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Condition> conditions;

    public Boolean check(List<Double> values){
        return check(values.toArray(new Double[values.size()]));
    }
    public Boolean check(Double[] values){
        for (Condition condition : conditions) {
            boolean res = true;
            if (condition.getVariable()-1>=values.length)
                throw new ArrayIndexOutOfBoundsException();

            switch (condition.getType()){
                case 0: //=
                    res = values[condition.getVariable()-1] ==condition.getValue();
                    break;
                case 1: // >
                    res = values[condition.getVariable()-1]>condition.getValue();
                    break;
                case 2: //<
                    res = values[condition.getVariable()-1]<condition.getValue();
                    break;
                case 3: //>=
                    res = values[condition.getVariable()-1]>=condition.getValue();
                    break;
                case 4: //<=
                    res = values[condition.getVariable()-1]<=condition.getValue();
                    break;
            }
            if (!res)
                return false;
        }
        return true;
    }
}
