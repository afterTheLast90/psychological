package team.cats.psychological.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.github.yitter.idgen.YitIdHelper;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author wmgx
 * @create 2021-08-15-20:25
 **/
@Configuration
public class MybatisPlusConfig {


    /**
     * 自定义id生成器
     * 短的雪花id，避免前端long超范围
     * @return id生成器
     */
    @Bean
    public IdentifierGenerator myIdentifierGenerator(){

//        return new IdentifierGenerator(){
//            @Override
//            public Number nextId(Object entity) {
//                return YitIdHelper.nextId();
//            }
//
//        };
        return entity -> YitIdHelper.nextId();
    }
}
