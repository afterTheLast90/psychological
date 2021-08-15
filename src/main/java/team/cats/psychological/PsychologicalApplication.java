package team.cats.psychological;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("team.cats.psychological.mapper")
public class PsychologicalApplication {



    public static void main(String[] args) {
        SpringApplication.run(PsychologicalApplication.class, args);
    }

}
