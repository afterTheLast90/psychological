package team.cats.psychological.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import team.cats.psychological.entity.QuestionnaireDetails;
import team.cats.psychological.vo.QuestionnaireView;

import java.util.List;

public interface QuestionnaireDetailsMapper extends BaseMapper<QuestionnaireDetails> {


    @Select("select * from questionnaire_details where delete_flag=false and questionnaire_id=#{Id}")
    public List<QuestionnaireDetails> selectByQuestionnaireId(Long Id);
}
