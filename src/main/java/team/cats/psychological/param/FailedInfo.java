package team.cats.psychological.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;

/**
 * @author wmgx
 * @create 2021-10-24-21:27
 **/
@Data
@Accessors(chain = true)
public class FailedInfo {
    List<HashMap<String, Object>> list;

}
