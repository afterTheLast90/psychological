package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@TableName("students_class")
@Data
@Accessors(chain = true)
public class StudentsClass implements Serializable,Cloneable {
    /**
     * ID
     */
    @TableId
    private Long studentsClassId;
    /**
     * 学生ID
     */
    private Long studentId;
    /**
     * 班级ID
     */
    private Long classId;
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
