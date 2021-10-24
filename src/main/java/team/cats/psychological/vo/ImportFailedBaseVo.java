package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wmgx
 * @create 2021-10-24-1:23
 **/
@Data
@Accessors(chain = true)
public class ImportFailedBaseVo {
    private String msg;
}
