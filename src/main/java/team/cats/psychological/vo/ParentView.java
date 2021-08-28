package team.cats.psychological.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ParentView {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long studentsParentId;
    /**
     * 学生ID
     */
    private Long studentId;
    /**
     * 家长ID
     */
    private Long parentId;

    private String parentName;
}
