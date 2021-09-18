package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QuestionnaireIdAndStudentIdView {
    private Long questionnaireId;
    private Long studentId;
    private Long publishId;
    private Boolean state;//新旧问卷 新true
}
