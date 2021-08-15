package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nh.micro.ext.ExtBeanWrapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@TableName("user_questionnaire")
@Data
@Accessors(chain = true)
public class UserQuestionnaire implements Serializable,Cloneable {
    /**
     * ID
     */
    @TableId
    private Long userQuestionnaireId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 问卷ID
     */
    private Long questionnaire;
    /**
     * 状态;0-未完成，1已完成
     */
    private Long state;
    /**
     * 答题详情;总分，题号，选项，变量及对应分数，结果
     */
    private ExtBeanWrapper answerDetails;
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
     * 学校ID
     */
    private Long schoolId;
    /**
     * 学校名
     */
    private String schoolName;
    /**
     * 年级
     */
    private Long grade;
    /**
     * 地区ID
     */
    private Long areaId;
    /**
     * 地区名
     */
    private String areaName;
    /**
     * 班级ID
     */
    private Long classId;
    /**
     * 班级名
     */
    private String className;
    /**
     * 市ID
     */
    private Long cityId;
    /**
     * 市名
     */
    private String cityName;
    /**
     * 省ID
     */
    private Long provinceId;
    /**
     * 省名
     */
    private String provinceName;
}
