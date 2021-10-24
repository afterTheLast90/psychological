package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wmgx
 * @create 2021-10-24-1:24
 **/
@Data
@Accessors(chain = true)
public class ImportFailedVo {
    private List<ImportFailedBaseVo> data = new ArrayList<>();
    private Integer size =0;
    private Integer total=0;
}
