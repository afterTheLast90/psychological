package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Province {
    /**
     * 省ID
     */
    private String provinceId;
    /**
     * 省名
     */
    private String provinceName;
}
