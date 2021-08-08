package team.cats.psychological.entity;

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
    @TableId
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
    private Long deleteFlag;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
