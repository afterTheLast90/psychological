package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.UserQuestionnaire;

@Data
@Accessors(chain = true)
public class QuestionnaireResultView {
    private UserQuestionnaire userQuestionnaire;
    private String questionnaireName;
    private String publisherName;
}
