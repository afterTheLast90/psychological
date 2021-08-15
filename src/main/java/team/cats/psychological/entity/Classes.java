package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
@TableName("classes")
@Data
@Accessors(chain = true)
public class Classes implements Serializable,Cloneable{
    /** 班级ID */
    @TableId(type = IdType.ASSIGN_ID)
    private Long classId ;
    /** 教师ID */
    private Long teacherId ;
    /** 学校ID */
    private Long schoolId ;
    /** 班级名称 */
    private String className ;
    /** 创建时间 */
    private LocalDateTime createTime ;
    /** 删除标记;0否，1是 */
    private Long deleteFlag ;
    /** 修改时间 */
    private LocalDateTime updateTime ;
    /** 年级 */
    private Long grade;
}
