package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Answer;

import java.util.List;

@Data
@Accessors(chain = true)
public class NewAnswerQuestionnaireView {
    private List<Answer> answers;
    private Long studentId;
    private Long publishId;
    private Long userId;
    private Long questionnaireId;
    private Integer role;
}
