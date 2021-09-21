package team.cats.psychological.vo;


import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.UserQuestionnaire;

@Data
@Accessors(chain = true)
public class ResultView {
    private UserQuestionnaire userQuestionnaire;
    private String userName;
}
