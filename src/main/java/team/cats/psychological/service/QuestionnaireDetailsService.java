package team.cats.psychological.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.entity.QuestionnaireDetails;
import team.cats.psychological.entity.Users;
import team.cats.psychological.mapper.QuestionnaireDetailsMapper;
import team.cats.psychological.mapper.UsersMapper;
import team.cats.psychological.param.QuestionnaireDetailsParams;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionnaireDetailsService {
    @Resource
    private QuestionnaireDetailsMapper questionnaireDetailsMapper;
    @Resource
    private UsersMapper usersMapper;

    public List<QuestionnaireDetails> getDetails(Long questionnaireId){
        QueryWrapper<QuestionnaireDetails> queryWrapper = new QueryWrapper();
        queryWrapper.eq("questionnaire_id", questionnaireId);
        List<QuestionnaireDetails> questionnaireDetails = questionnaireDetailsMapper.selectList(queryWrapper);
        return questionnaireDetails;
    }

    public List<QuestionnaireDetails> getUserDetails(Long questionnaireId,Long userId){
        QueryWrapper<QuestionnaireDetails> queryWrapper = new QueryWrapper();
        queryWrapper.eq("questionnaire_id", questionnaireId);
        List<QuestionnaireDetails> questionnaireDetails = questionnaireDetailsMapper.selectList(queryWrapper);
        return questionnaireDetails;
    }

    public void modifyDetails(List<QuestionnaireDetailsParams> questionnaireDetailsList){
        QueryWrapper<QuestionnaireDetails> queryWrapper = new QueryWrapper();
        queryWrapper.eq("questionnaire_id", questionnaireDetailsList.get(0).getQuestionnaireId());
        questionnaireDetailsMapper.delete(queryWrapper);
        for (QuestionnaireDetailsParams questionnaireDetails : questionnaireDetailsList) {
            QuestionnaireDetails questionnaireDetails1 = questionnaireDetailsMapper.selectById(questionnaireDetails.getQuestionId());
            if(questionnaireDetails1==null){
                questionnaireDetails1=new QuestionnaireDetails();
                questionnaireDetails1.setQuestionId(YitIdHelper.nextId());
                questionnaireDetails1.setTitleId(questionnaireDetails.getTitleId());
                questionnaireDetails1.setQuestion(questionnaireDetails.getQuestion());
                questionnaireDetails1.setQuestionnaireId(questionnaireDetails.getQuestionnaireId());
                questionnaireDetails1.setFactorGroupId(questionnaireDetails.getFactorGroupId());
                questionnaireDetails1.setAnswerOptions(questionnaireDetails.getAnswerOptions());
                questionnaireDetails1.setChooseType(questionnaireDetails.getChooseType());
                questionnaireDetails1.setChosePeople(questionnaireDetails.getChosePeople());
                questionnaireDetails1.setQuestionType(questionnaireDetails.getQuestionType());
                questionnaireDetailsMapper.insert(questionnaireDetails1);
            }
        }
    }
}
