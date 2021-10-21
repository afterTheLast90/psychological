package team.cats.psychological.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 选项
 * @author lihaotian
 * @since 2021/8/23 15:18
 */
@Data
@Accessors(chain = true)
public class Option {
    /**
     * 选项(ABCDE)
     */
    private String optionName;
    /**
     * 选项分值
     */
    private Integer optionPoints;
    /**
     * 选项内容
     */
    private String optionContent;
    /**
     * 选项类型
     */
    private Boolean optionType;
}
