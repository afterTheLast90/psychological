package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@TableName("distribution")
@Data
@Accessors(chain = true)
public class Distribution implements Serializable,Cloneable{
    /** 分发ID */
    @TableId(type = IdType.ASSIGN_ID)
    private Long distributionId ;
    /** 分发类型;0-地区，1-学校，2-老师 */
    private Long distributionType ;
    /** 负责人ID */
    private Long principalId ;
    /** 问卷ID */
    private Long questionnaireId ;
    /** 创建时间 */
    private LocalDateTime createTime ;
    /** 删除标记;0否，1是 */
    private Long deleteFlag ;
    /** 修改时间 */
    private LocalDateTime updateTime ;
    /** 创建人ID */
    private Long createPersonId ;
}