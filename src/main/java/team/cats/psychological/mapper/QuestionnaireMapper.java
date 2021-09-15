package team.cats.psychological.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import team.cats.psychological.entity.Option;
import team.cats.psychological.entity.Questionnaire;
import team.cats.psychological.vo.QuestionnaireView;

import java.util.List;
import java.util.Objects;

public interface QuestionnaireMapper extends BaseMapper<Questionnaire> {

    public List<QuestionnaireView> selectQuestionnaire(String value);
    public QuestionnaireView selectUserQuestionnaire(Long questionnaireId);
}
