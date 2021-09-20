package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wmgx
 * @create 2021-09-20-21:22
 **/
@Data
@Accessors(chain = true)
public class LoginVo {
    private String token;
    private Long role;
}
