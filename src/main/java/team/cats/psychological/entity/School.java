package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@TableName("school")
@Data
@Accessors(chain = true)
public class School implements Serializable,Cloneable {
    /**
     * 学校ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long schoolId;
    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 地区ID
     */
    private Long areaId;
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
