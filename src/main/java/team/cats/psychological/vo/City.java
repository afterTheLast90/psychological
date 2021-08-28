package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class City {
    /**
     * 市ID
     */
    private String cityId;
    /**
     * 市名
     */
    private String cityName;
}
