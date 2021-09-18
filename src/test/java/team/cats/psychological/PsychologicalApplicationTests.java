package team.cats.psychological;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.cats.psychological.entity.Questionnaire;
import team.cats.psychological.entity.Users;
import team.cats.psychological.mapper.QuestionnaireMapper;
import team.cats.psychological.mapper.UsersMapper;

import java.util.List;

@SpringBootTest
class PsychologicalApplicationTests {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    @Test
    void contextLoads() {
        QueryWrapper<Questionnaire> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Questionnaire::getQuestionnaireId,201925336137797L);
        System.out.println(questionnaireMapper.selectList(queryWrapper).get(0).getVariables().get(0).getClass());;
//        List<Users> usersMappers = usersMapper.selectList(null);
//        for (Users user : usersMappers) {
//            System.out.println(user);
//        }
    }

}
