package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nh.micro.ext.ExtBeanWrapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("questionnaire")
@Data
@Accessors(chain = true)
public class Questionnaire implements Serializable,Cloneable {
    /**
     * 问卷ID
     */
    @TableId
    private Long questionnaireId;
    /**
     * 问卷名称
     */
    private String questionnaireName;
    /**
     * 问卷简介
     */
    private String questionnaireIntroduction;
    /**
     * 因子;因子类型，名称，介绍，表达式
     */
    private ExtBeanWrapper factor;
    /**
     * 计算方式;0-因子，1-总分
     */
    private Long calculation;
    /**
     * 结果;结果内容，结果介绍，判断表达式
     */
    private ExtBeanWrapper result;
    /**
     * 问卷状态;0-未发布，1-已发布
     */
    private Long questionnaireState;
    /**
     * 发布人ID
     */
    private Long publisherId;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 提交人数
     */
    private Long submissionNumber;
    /**
     * 发布时间
     */
    private LocalDateTime releaseTime;
    /**
     * 截止时间
     */
    private LocalDateTime deadline;
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
    /**
     * 题目模板
     */
    private ExtBeanWrapper topicTemplate;
}
