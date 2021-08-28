package team.cats.psychological.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Area;
import team.cats.psychological.entity.School;

import java.util.List;

@Data
@Accessors(chain = true)
public class AreaAndUser {
    /**
     * 地区ID
     */
    private Long areaId;
    /**
     * 地区名
     */
    private String areaName;
    /**
     * 市ID
     */
    private Long cityId;
    /**
     * 市名
     */
    private String cityName;
    /**
     * 省ID
     */
    private Long provinceId;
    /**
     * 省名
     */
    private String provinceName;
    /**
     * 地区负责人
     */
    private Long areaPrincipal;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 学校
     */
    private List<School> school;
}
