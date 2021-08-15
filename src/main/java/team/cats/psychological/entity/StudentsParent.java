package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@TableName("students_parent")
@Data
@Accessors(chain = true)
public class StudentsParent implements Serializable,Cloneable {
    /**
     * ID
     */
    @TableId
    private Long studentsParentId;
    /**
     * 学生ID
     */
    private Long studentId;
    /**
     * 家长ID
     */
    private Long parentId;
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
