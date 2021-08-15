package team.cats.psychological.base;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BasePageParam {
    private Integer pageSize=10;
    private Integer pageNum=1;
}
