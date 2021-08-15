package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("area")
@Data
@Accessors(chain = true)
public class Area implements Serializable,Cloneable {
    /**
     * 地区ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long areaId;
    /**
     * 地区名
     */
    private String areaName;
    /**
     * 市ID
     */
    private String cityId;
    /**
     * 市名
     */
    private String cityName;
    /**
     * 省ID
     */
    private String provinceId;
    /**
     * 省名
     */
    private String provinceName;
    /**
     * 地区负责人
     */
    private Long areaPrincipal;
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
