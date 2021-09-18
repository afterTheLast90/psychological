package team.cats.psychological.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class TopicTemplateParams {
    /**
     * 选项(ABCDE)
     */
    @NotBlank(message = "选项不能为空")
    private String optionName;
    /**
     * 选项内容
     */
    @NotBlank(message = "选项内容不能为空")
    private String optionContent;
    /**
     * 选项分值
     */
    @NotNull(message = "选项分值不能为空")
    private Integer optionPoints;
}
