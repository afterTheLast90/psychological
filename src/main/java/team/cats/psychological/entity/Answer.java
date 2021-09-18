package team.cats.psychological.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Answer {
    private String titleId;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> answer;
    private Integer score;
}
