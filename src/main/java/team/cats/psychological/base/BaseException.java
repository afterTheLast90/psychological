package team.cats.psychological.base;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseException extends RuntimeException{
    public BaseException( Integer code,String msg) {
        this.msg = msg;
        this.code = code;
    }

    public BaseException() {
    }

    private String msg;
    private Integer code;

    public BaseException(String msg) {
        this.msg=msg;
        this.code = 451;
    }
}
