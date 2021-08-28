package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListItemVo {
    private Long value;
    private String label;
    private List<ListItemVo> children;
}
