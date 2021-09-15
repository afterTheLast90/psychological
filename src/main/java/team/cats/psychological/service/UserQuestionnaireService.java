package team.cats.psychological.service;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.entity.*;
import team.cats.psychological.mapper.*;
import team.cats.psychological.vo.AnswerQuestionnaireView;
import team.cats.psychological.vo.ParentView;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserQuestionnaireService {

    @Resource
    private UserQuestionnaireMapper userQuestionnaireMapper;

    @Resource
    private QuestionnaireMapper questionnaireMapper;

    @Resource
    private QuestionnaireDetailsMapper questionnaireDetailsMapper;

    @Resource
    private StudentsClassMapper studentsClassMapper;

    @Resource
    private SchoolMapper schoolMapper;

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private ClassesMapper classesMapper;

    @Resource
    private TeacherSchoolMapper teacherSchoolMapper;

    @Resource
    private StudentsParentMapper studentsParentMapper;

    public void releaseQuestionnaire(Long questionnaireId, Long classId, Long schoolId, LocalDateTime releaseTime,LocalDateTime deadLine){
        boolean student=false;
        boolean teacher=false;
        boolean parent=false;
        List<QuestionnaireDetails> questionnaireDetails = questionnaireDetailsMapper.selectByQuestionnaireId(questionnaireId);
        for (QuestionnaireDetails questionnaireDetail : questionnaireDetails) {
             if (questionnaireDetail.getChosePeople()==0){
                 student=true;
             }else if (questionnaireDetail.getChosePeople()==1){
                 teacher=true;
             }else {
                parent=true;
             }
        }
        School school = schoolMapper.selectById(schoolId);
        Area area = areaMapper.selectById(school.getAreaId());
        Classes classes = classesMapper.selectById(classId);
        if (student){
            QueryWrapper<StudentsClass> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("class_id",classId);
            List<StudentsClass> studentsClasses = studentsClassMapper.selectList(queryWrapper);
            for (StudentsClass studentsClass : studentsClasses) {
                UserQuestionnaire userQuestionnaire=new UserQuestionnaire();
                userQuestionnaire.setUserQuestionnaireId(YitIdHelper.nextId());
                userQuestionnaire.setUserId(studentsClass.getStudentId());
                userQuestionnaire.setQuestionnaire(questionnaireId);
                userQuestionnaire.setState(0L);
                userQuestionnaire.setSchoolId(school.getSchoolId());
                userQuestionnaire.setSchoolName(school.getSchoolName());
                userQuestionnaire.setGrade(classes.getGrade());
                userQuestionnaire.setAreaId(area.getAreaId());
                userQuestionnaire.setClassId(classes.getClassId());
                userQuestionnaire.setClassName(classes.getClassName());
                userQuestionnaire.setCityId(area.getCityId());
                userQuestionnaire.setCityName(area.getCityName());
                userQuestionnaire.setProvinceId(area.getProvinceId());
                userQuestionnaire.setProvinceName(area.getProvinceName());
                userQuestionnaire.setAreaName(area.getAreaName());
                userQuestionnaireMapper.insert(userQuestionnaire);
            }
        }
        if (teacher){
            QueryWrapper<TeacherSchool> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("school_id",schoolId);
            List<TeacherSchool> teacherSchools = teacherSchoolMapper.selectList(queryWrapper);
            for (TeacherSchool teacherSchool : teacherSchools) {
                UserQuestionnaire userQuestionnaire=new UserQuestionnaire();
                userQuestionnaire.setUserQuestionnaireId(YitIdHelper.nextId());
                userQuestionnaire.setUserId(teacherSchool.getTeacherId());
                userQuestionnaire.setQuestionnaire(questionnaireId);
                userQuestionnaire.setState(0L);
                userQuestionnaire.setSchoolId(school.getSchoolId());
                userQuestionnaire.setSchoolName(school.getSchoolName());
                userQuestionnaire.setGrade(classes.getGrade());
                userQuestionnaire.setAreaId(area.getAreaId());
                userQuestionnaire.setClassId(classes.getClassId());
                userQuestionnaire.setClassName(classes.getClassName());
                userQuestionnaire.setCityId(area.getCityId());
                userQuestionnaire.setCityName(area.getCityName());
                userQuestionnaire.setProvinceId(area.getProvinceId());
                userQuestionnaire.setProvinceName(area.getProvinceName());
                userQuestionnaire.setAreaName(area.getAreaName());
                userQuestionnaireMapper.insert(userQuestionnaire);
            }
        }
        if (parent){
            QueryWrapper<StudentsClass> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("class_id",classId);
            List<StudentsClass> studentsClasses = studentsClassMapper.selectList(queryWrapper);
            for (StudentsClass studentsClass : studentsClasses) {
                List<ParentView> parentViews = studentsParentMapper.selectByStudentId(studentsClass.getStudentId());
                for (ParentView parentView : parentViews) {
                    UserQuestionnaire userQuestionnaire=new UserQuestionnaire();
                    userQuestionnaire.setUserQuestionnaireId(YitIdHelper.nextId());
                    userQuestionnaire.setUserId(parentView.getParentId());
                    userQuestionnaire.setQuestionnaire(questionnaireId);
                    userQuestionnaire.setState(0L);
                    userQuestionnaire.setSchoolId(school.getSchoolId());
                    userQuestionnaire.setSchoolName(school.getSchoolName());
                    userQuestionnaire.setGrade(classes.getGrade());
                    userQuestionnaire.setAreaId(area.getAreaId());
                    userQuestionnaire.setClassId(classes.getClassId());
                    userQuestionnaire.setClassName(classes.getClassName());
                    userQuestionnaire.setCityId(area.getCityId());
                    userQuestionnaire.setCityName(area.getCityName());
                    userQuestionnaire.setProvinceId(area.getProvinceId());
                    userQuestionnaire.setProvinceName(area.getProvinceName());
                    userQuestionnaire.setAreaName(area.getAreaName());
                    userQuestionnaireMapper.insert(userQuestionnaire);
                }
            }
        }
        Questionnaire questionnaire = questionnaireMapper.selectById(questionnaireId);
        questionnaire.setQuestionnaireState(1L);
        questionnaire.setReleaseTime(releaseTime);
        questionnaire.setDeadline(deadLine);
        questionnaireMapper.updateById(questionnaire);
    }

    public List<UserQuestionnaire>  selectByUserId(Long userId){
        QueryWrapper<UserQuestionnaire> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<UserQuestionnaire> userQuestionnaires = userQuestionnaireMapper.selectList(queryWrapper);
        return userQuestionnaires;
    }

    public void answerQuestionnaire(AnswerQuestionnaireView answerQuestionnaireView){
        QueryWrapper<UserQuestionnaire> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",answerQuestionnaireView.getUserId());
        queryWrapper.eq("questionnaire",answerQuestionnaireView.getQuestionnaireId());
        UserQuestionnaire userQuestionnaire = userQuestionnaireMapper.selectOne(queryWrapper);
        userQuestionnaire.setAnswer(answerQuestionnaireView.getAnswers());
        userQuestionnaire.setState(1L);
        userQuestionnaireMapper.updateById(userQuestionnaire);
        QueryWrapper<UserQuestionnaire> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("questionnaire",answerQuestionnaireView.getQuestionnaireId());
        queryWrapper2.eq("state",0);
        List<UserQuestionnaire> userQuestionnaires = userQuestionnaireMapper.selectList(queryWrapper2);
        //判断是否所有都填写完毕
        if (userQuestionnaires.size()!=0){
            return;
        }
        Questionnaire questionnaire1 = questionnaireMapper.selectById(answerQuestionnaireView.getQuestionnaireId());
        QueryWrapper<QuestionnaireDetails> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("questionnaire_id",answerQuestionnaireView.getQuestionnaireId());
        //问卷题目
        List<QuestionnaireDetails> questionnaireDetails = questionnaireDetailsMapper.selectList(queryWrapper4);
        QueryWrapper<UserQuestionnaire> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("questionnaire",answerQuestionnaireView.getQuestionnaireId());
        queryWrapper3.eq("state",1);
        //所有完成的用户问卷
        List<UserQuestionnaire> userQuestionnaires1 = userQuestionnaireMapper.selectList(queryWrapper3);
        for (QuestionnaireDetails questionnaireDetail : questionnaireDetails) {
            for (UserQuestionnaire questionnaire : userQuestionnaires1) {

            }
        }
    }
}
