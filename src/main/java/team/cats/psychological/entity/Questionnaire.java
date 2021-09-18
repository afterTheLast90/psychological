package team.cats.psychological.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@TableName(value = "questionnaire",autoResultMap = true)
public class Questionnaire implements Serializable,Cloneable {
    /**
     * 问卷ID
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Variable> variables;
    /**
     * 计算方式;0-因子，1-总分
     */
    private Integer calculation;
    /**
     * 结果;结果内容，结果介绍，判断表达式
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Result> results;
    /**
     * 问卷状态;0-未发布，1-已发布
     */
    private Integer questionnaireState;
    /**
     * 创建者
     */
    private Long creator;
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
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Option> topicTemplate;
    /**
     * 答题人
     */
    private Integer choosePeople;


    public void setVariables(List variables) {
        if (variables.size()==0){
            this.variables = variables;
            return;
        }
        if (variables.get(0) instanceof Map){
            this.variables = ((List<Map>)variables).stream().map(i-> BeanUtil.fillBeanWithMap(i,new Variable(),true)).collect(Collectors.toList());
            return;
        }
        if (variables.get(0) instanceof Variable){
            this.variables = variables;
        }

    }
    public void setResults(List results) {
        if (results.size()==0){
            this.results = results;
            return;
        }
        if (results.get(0) instanceof Map){
            this.results = ((List<Map>)results).stream().map(i-> BeanUtil.fillBeanWithMap(i,new Result(),true)).collect(Collectors.toList());
            return;
        }
        if (results.get(0) instanceof Result){
            this.results = results;
        }

    }
}
