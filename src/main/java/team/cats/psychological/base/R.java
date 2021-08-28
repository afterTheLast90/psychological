package team.cats.psychological.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors
@NoArgsConstructor
public class R<T> {
    private Integer code;
    private String msg;
    private T data;
    public R(Integer code, String msg){
        this.code = code;
        this.msg=msg;
    }
    public R(Integer code, String msg,T data){
        this.code = code;
        this.msg=msg;
        this.data=data;
    }
    public static  <E> R<E> success(E data){
        return new R<E>(200,"成功",data);
    }
    public static  <E> R<E> successNoShow(E data){
        return new R<E>(201,"成功",data);
    }
    public static  R success(){
        return new R(200,"成功");
    }
    public static  R fail(){
        return new R(200,"成功");
    }
}
