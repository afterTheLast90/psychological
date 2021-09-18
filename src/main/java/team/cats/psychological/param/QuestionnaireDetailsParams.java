package team.cats.psychological.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Option;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class QuestionnaireDetailsParams {
    /**
     * 题目唯一ID
     */
    @NotNull(message = "题目唯一Id不能为空")
    private Long questionId;
    /**
     * 题目Id
     */
    @NotNull(message = "题目Id不能为空")
    private Integer titleId;
    /**
     * 问卷ID
     */
    @NotNull(message = "问卷Id不能为空")
    private Long questionnaireId;
    /**
     * 题目
     */
    @NotBlank(message = "题目不能为空")
    private String question;
    /**
     * 因子名称
     */
    @NotNull(message = "因子不能为空")
    private Integer factorGroupId;
    /**
     * 选项;选项名称，选项内容，选项分值
     */
    private List<Option> answerOptions;
    /**
     * 选择类型;0-单选，1多选
     */
    @NotNull(message = "选择类型不能为空")
    private Integer chooseType;
    /**
     * 填写对象;0-学生，1-家长，2-老师
     */
    @NotNull(message = "填写对象不能为空")
    private Integer chosePeople;
}
