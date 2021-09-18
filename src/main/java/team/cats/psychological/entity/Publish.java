package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@TableName("publish")
@Data
@Accessors(chain = true)
public class Publish {
    @TableId(type = IdType.ASSIGN_ID)
    private Long publishId;
    private Integer publishType;
    private Long strangeId;
    private Integer state;
    private Long questionnaireId;
    private Integer choosePeople;
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
    private Integer submissionNumber;
    private Long publisherId;
}
