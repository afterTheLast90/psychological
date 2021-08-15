package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@TableName("teacher_school")
@Data
@Accessors(chain = true)
public class TeacherSchool implements Serializable,Cloneable {
    /**
     * ID
     */
    @TableId
    private Long teacherSchoolId;
    /**
     * 教师ID
     */
    private Long teacherId;
    /**
     * 学校ID
     */
    private Long schoolId;
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
