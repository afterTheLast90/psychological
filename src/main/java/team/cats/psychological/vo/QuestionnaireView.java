package team.cats.psychological.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Option;
import team.cats.psychological.entity.Result;
import team.cats.psychological.entity.Variable;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class QuestionnaireView {
    private Long publishId;
    //问卷新旧
    private Boolean state;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 学生Id
     */
    private Long studentId;
    /**
     * 问卷ID
     */
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
    private List<Variable> variables;
    /**
     * 计算方式;0-因子，1-总分
     */
    private Integer calculation;
    /**
     * 结果;结果内容，结果介绍，判断表达式
     */
    private List<Result> results;
    /**
     * 问卷状态;0-可编辑，1-已确认
     */
    private Long questionnaireState;
    /**
     * 发布人ID
     */
    private Long publisherId;
    /**
     * 发布人姓名
     */
    private String publisherName;
    /**
     * 创建者
     */
    private Long creator;
    /**
     * 创建者姓名
     */
    private String creatorName;
    /**
     * 发布时间
     */
    private LocalDateTime releaseTime;
    /**
     * 截至时间
     */
    private LocalDateTime deadLine;
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
    private List<Option> topicTemplate;
}
