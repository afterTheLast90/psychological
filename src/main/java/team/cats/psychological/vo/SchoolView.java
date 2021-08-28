package team.cats.psychological.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Classes;

import java.util.List;

@Data
@Accessors(chain = true)
public class SchoolView {
    /**
     * 学校ID
     */
    private Long schoolId;
    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 地区ID
     */
    private Long areaId;

    private List<Classes> classes;
}
