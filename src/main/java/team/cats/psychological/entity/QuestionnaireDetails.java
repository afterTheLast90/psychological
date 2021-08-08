package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nh.micro.ext.ExtBeanWrapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("questionnaire_details")
@Data
@Accessors(chain = true)
public class QuestionnaireDetails implements Serializable,Cloneable {
    /**
     * 题目ID
     */
    @TableId
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
    private String factorGroupId;
    /**
     * 选项;选项名称，选项内容，选项分值
     */
    private ExtBeanWrapper answerOptions;
    /**
     * 选择类型;0-单选，1多选
     */
    private Long chooseType;
    /**
     * 填写对象;0-学生，1-家长，2-老师
     */
    private Long chosePeople;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 删除标记;0否，1是
     */
    private Long deleteFlag;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}