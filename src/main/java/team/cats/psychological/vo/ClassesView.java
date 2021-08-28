package team.cats.psychological.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.School;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ClassesView {
    /** 班级ID */
    private Long classId ;
    /** 教师ID */
    private Long teacherId ;
    /** 学校ID */
    private Long schoolId ;
    /** 班级名称 */
    private String className ;
    /** 删除标记;0否，1是 */
    private Long deleteFlag ;
    /** 年级 */
    private Long grade;

    private String teacherName;

    private String SchoolName;
}
