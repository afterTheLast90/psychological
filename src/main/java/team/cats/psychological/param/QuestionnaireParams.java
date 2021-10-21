package team.cats.psychological.param;

import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Option;
import team.cats.psychological.entity.Result;
import team.cats.psychological.entity.Variable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Accessors(chain = true)
public class QuestionnaireParams {
    @NotNull(message = "问卷ID不能为空")
    private Long questionnaireId;

    @NotBlank(message = "问卷名不能为空")
    private String questionnaireName;

    @NotBlank(message = "问卷简介不能为空")
    private String questionnaireIntroduction;

    private List<Variable> variables;

    /**
     * 计算方式;0-因子，1-总分
     */
    @NotNull(message = "计算方式不能为空")
    private Integer calculation;
    /**
     * 结果;结果内容，结果介绍，判断表达式
     */
    private List<Result> results;
    /**
     * 问卷状态;0-未发布，1-已发布
     */
    @NotNull(message = "问卷状态不能为空")
    private Integer questionnaireState;
    /**
     * 发布人ID
     */
    @NotNull(message = "发布人不能为空")
    private Long publisherId;
    /**
     * 创建者
     */
    @NotNull(message = "创建者不能为空")
    private Long creator;
    /**
     * 题目模板
     */
    private List<Option> topicTemplate;

}
