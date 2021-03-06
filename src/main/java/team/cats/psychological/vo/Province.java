package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Province {
    /**
     * çID
     */
    private String provinceId;
    /**
     * çå
     */
    private String provinceName;
}
