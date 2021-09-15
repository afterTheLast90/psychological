package team.cats.psychological.service;

import com.github.pagehelper.PageHelper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Option;
import team.cats.psychological.entity.Questionnaire;
import team.cats.psychological.entity.UserQuestionnaire;
import team.cats.psychological.mapper.QuestionnaireMapper;
import team.cats.psychological.mapper.UsersMapper;
import team.cats.psychological.param.QuestionnaireParams;
import team.cats.psychological.vo.QuestionnaireView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionnaireService {

    @Resource
    private QuestionnaireMapper questionnaireMapper;
    @Resource
    private UsersMapper usersMapper;

    public PageResult<QuestionnaireView> selectQuestionnaire(BasePageParam basePageParam,String value){
        PageHelper.startPage(basePageParam.getPageNum(), basePageParam.getPageSize());
        List<QuestionnaireView> questionnaires = questionnaireMapper.selectQuestionnaire(value);
        for (QuestionnaireView questionnaire : questionnaires) {
            questionnaire.setCreatorName(usersMapper.selectById(questionnaire.getCreator()).getUserName());
        }
        return new PageResult<QuestionnaireView>(questionnaires);
    }
    public Questionnaire selectById(String id){
        return questionnaireMapper.selectById(id);
    }

    public void InsertQuestionnaire(String name,String introduction,Long creator){
        Questionnaire questionnaire= new Questionnaire();
        questionnaire.setQuestionnaireId(YitIdHelper.nextId());
        questionnaire.setQuestionnaireName(name);
        questionnaire.setQuestionnaireIntroduction(introduction);
        questionnaire.setCreator(creator);
        questionnaire.setPublisherId(creator);
        questionnaireMapper.insert(questionnaire);
    }

    public void DelQuestionnaire(Long id){
        questionnaireMapper.deleteById(id);
    }

    public void ModifyQuestionnaire(QuestionnaireParams questionnaireParams){
        System.out.println(questionnaireParams);
        Questionnaire questionnaire = questionnaireMapper.selectById(questionnaireParams.getQuestionnaireId());
        questionnaire.setQuestionnaireName(questionnaireParams.getQuestionnaireName());
        questionnaire.setQuestionnaireIntroduction(questionnaireParams.getQuestionnaireIntroduction());
        questionnaire.setVariables(questionnaireParams.getVariables());
        questionnaire.setCalculation(questionnaireParams.getCalculation());
        questionnaire.setResults(questionnaireParams.getResults());
        questionnaire.setQuestionnaireState(questionnaireParams.getQuestionnaireState());
        questionnaire.setTopicTemplate(questionnaireParams.getTopicTemplate());
        questionnaireMapper.updateById(questionnaire);
    }

    public List<QuestionnaireView> getUserQuestionnaire(List<UserQuestionnaire> userQuestionnaires){
        List<QuestionnaireView> questionnaireViews= new ArrayList<>();
        for (UserQuestionnaire userQuestionnaire : userQuestionnaires) {
            QuestionnaireView questionnaireView = questionnaireMapper.selectUserQuestionnaire(userQuestionnaire.getQuestionnaire());
            questionnaireView.setCreatorName(usersMapper.selectById(questionnaireView.getCreator()).getUserName());
            questionnaireViews.add(questionnaireView);
        }
        return questionnaireViews;
    }
}
