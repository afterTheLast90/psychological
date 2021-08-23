package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.nh.micro.ext.ExtBeanWrapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@TableName(value = "questionnaire_details",autoResultMap = true)
@Data
@Accessors(chain = true)
public class QuestionnaireDetails implements Serializable,Cloneable {
    /**
     * 题目ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long questionId;
    /**
     * 问卷ID
     */
    private Long questionnaireId;
    /**
     * 题目
     */
    private String question;
    /**
     * 因子名称
     */
    private Integer factorGroupId;
    /**
     * 选项;选项名称，选项内容，选项分值
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Option> answerOptions;
    /**
     * 选择类型;0-单选，1多选
     */
    private Integer chooseType;
    /**
     * 填写对象;0-学生，1-家长，2-老师
     */
    private Integer chosePeople;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 删除标记;0否，1是
     */
    private Boolean deleteFlag;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
