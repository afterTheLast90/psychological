package team.cats.psychological.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.entity.StudentsParent;
import team.cats.psychological.entity.*;
import team.cats.psychological.mapper.*;
import team.cats.psychological.vo.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserQuestionnaireService {

    @Resource
    private UsersMapper usersMapper;

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

    @Resource
    private PublishMapper publishMapper;


    public void releaseQuestionnaire(Long questionnaireId, Long id, Integer publishType, LocalDateTime releaseTime, LocalDateTime deadLine) {
        long userId = StpUtil.getLoginIdAsLong();
        Questionnaire questionnaire1 = questionnaireMapper.selectById(questionnaireId);
        Publish publish = new Publish();
        publish.setPublishId(YitIdHelper.nextId());
        publish.setQuestionnaireId(questionnaireId);
        publish.setChoosePeople(questionnaire1.getChoosePeople());
        publish.setPublishType(publishType);
        publish.setStrangeId(id);
        publish.setState(0);
        publish.setReleaseTime(releaseTime);
        publish.setDeadline(deadLine);
        publish.setPublisherId(userId);
        publishMapper.insert(publish);
    }

    public List<QuestionnaireIdAndStudentIdView> selectByUserId() {
        long userId = StpUtil.getLoginIdAsLong();
        Users users = usersMapper.selectById(userId);
        List<QuestionnaireIdAndStudentIdView> questionnaireIds = new ArrayList<>();
        List<Long> classId = new ArrayList<>();
        List<Long> schoolId = new ArrayList<>();
        List<Long> areaId = new ArrayList<>();
        if (users.getUserRole() == 2) {
//拿到发给学生个人的问卷
            QueryWrapper<Publish> publishQueryWrapperStudent = new QueryWrapper<>();
            publishQueryWrapperStudent.eq("strange_id", userId);
            publishQueryWrapperStudent.eq("publish_type", 3);
            publishQueryWrapperStudent.eq("state", 0);
            publishQueryWrapperStudent.gt("deadLine", new Date());
            publishQueryWrapperStudent.in("choose_people", new Integer[]{0, 3, 4, 6});
            List<Publish> publishes = publishMapper.selectList(publishQueryWrapperStudent);
            for (Publish publish : publishes) {
                QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                questionnaireIdAndStudentIdView.setStudentId(userId);
                questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                questionnaireIdAndStudentIdView.setState(true);
                questionnaireIds.add(questionnaireIdAndStudentIdView);
            }
//发给班级的问卷

            QueryWrapper<StudentsClass> studentsQueryWrapper = new QueryWrapper<>();
            studentsQueryWrapper.eq("student_id", userId);
            List<StudentsClass> studentsClasses = studentsClassMapper.selectList(studentsQueryWrapper);
            for (StudentsClass studentsClass : studentsClasses) {
                QueryWrapper<Publish> publishQueryWrapperClass = new QueryWrapper<>();
                publishQueryWrapperClass.eq("strange_id", studentsClass.getClassId());
                publishQueryWrapperClass.eq("publish_type", 2);
                publishQueryWrapperClass.eq("state", 0);
                publishQueryWrapperClass.gt("deadLine", new Date());
                publishQueryWrapperClass.in("choose_people", new Integer[]{0, 3, 4, 6});
                List<Publish> publishes1 = publishMapper.selectList(publishQueryWrapperClass);
                for (Publish publish : publishes1) {
                    QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                    questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                    questionnaireIdAndStudentIdView.setStudentId(userId);
                    ;
                    questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                    questionnaireIdAndStudentIdView.setState(true);
                    questionnaireIds.add(questionnaireIdAndStudentIdView);
                }
                Classes classes = classesMapper.selectById(studentsClass.getClassId());
                schoolId.add(classes.getSchoolId());
                //发给学校的问卷
                QueryWrapper<Publish> publishQueryWrapperSchool = new QueryWrapper<>();
                publishQueryWrapperSchool.eq("strange_id", classes.getSchoolId());
                publishQueryWrapperSchool.eq("publish_type", 1);
                publishQueryWrapperSchool.eq("state", 0);
                publishQueryWrapperSchool.gt("deadLine", new Date());
                publishQueryWrapperSchool.in("choose_people", new Integer[]{0, 3, 4, 6});
                List<Publish> publishes2 = publishMapper.selectList(publishQueryWrapperSchool);
                for (Publish publish : publishes2) {
                    QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                    questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                    questionnaireIdAndStudentIdView.setStudentId(userId);
                    ;
                    questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                    questionnaireIdAndStudentIdView.setState(true);
                    questionnaireIds.add(questionnaireIdAndStudentIdView);
                }

                School school = schoolMapper.selectById(classes.getSchoolId());

                //发给地区的问卷
                QueryWrapper<Publish> publishQueryWrapperArea = new QueryWrapper<>();
                publishQueryWrapperArea.eq("strange_id", school.getAreaId());
                publishQueryWrapperArea.eq("publish_type", 0);
                publishQueryWrapperArea.eq("state", 0);
                publishQueryWrapperArea.gt("deadLine", new Date());
                publishQueryWrapperArea.in("choose_people", new Integer[]{0, 3, 4, 6});
                List<Publish> publishes3 = publishMapper.selectList(publishQueryWrapperArea);
                for (Publish publish : publishes3) {
                    QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                    questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                    questionnaireIdAndStudentIdView.setStudentId(userId);
                    ;
                    questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                    questionnaireIdAndStudentIdView.setState(true);
                    questionnaireIds.add(questionnaireIdAndStudentIdView);
                }
            }
        } else if (users.getUserRole() == 3) {
            QueryWrapper<StudentsParent> parentQueryWrapper = new QueryWrapper<>();
            parentQueryWrapper.eq("parent_id", userId);
            List<StudentsParent> studentsParents = studentsParentMapper.selectList(parentQueryWrapper);
            for (StudentsParent studentsParent : studentsParents) {
                //发给学生个人的问卷
                QueryWrapper<Publish> publishQueryWrapper = new QueryWrapper<>();
                publishQueryWrapper.eq("strange_id", studentsParent.getStudentId());
                publishQueryWrapper.eq("publish_type", 3);
                publishQueryWrapper.eq("state", 0);
                publishQueryWrapper.gt("deadLine", new Date());
                publishQueryWrapper.in("choose_people", new Integer[]{1, 5});
                List<Publish> publishes = publishMapper.selectList(publishQueryWrapper);
                for (Publish publish : publishes) {
                    QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                    questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                    questionnaireIdAndStudentIdView.setStudentId(studentsParent.getStudentId());
                    ;
                    questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                    questionnaireIdAndStudentIdView.setState(true);
                    questionnaireIds.add(questionnaireIdAndStudentIdView);
                }

                QueryWrapper<StudentsClass> studentsQueryWrapper = new QueryWrapper<>();
                studentsQueryWrapper.eq("student_id", studentsParent.getStudentId());
                List<StudentsClass> studentsClasses = studentsClassMapper.selectList(studentsQueryWrapper);
                for (StudentsClass studentsClass : studentsClasses) {
                    classId.add(studentsClass.getClassId());
                    //发给班级的问卷
                    QueryWrapper<Publish> publishQueryWrapperClass = new QueryWrapper<>();
                    publishQueryWrapperClass.eq("strange_id", studentsClass.getClassId());
                    publishQueryWrapperClass.eq("publish_type", 2);
                    publishQueryWrapperClass.eq("state", 0);
                    publishQueryWrapperClass.gt("deadLine", new Date());
                    publishQueryWrapperClass.in("choose_people", new Integer[]{1, 5});
                    List<Publish> publishes1 = publishMapper.selectList(publishQueryWrapperClass);
                    for (Publish publish : publishes1) {
                        QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                        questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                        questionnaireIdAndStudentIdView.setStudentId(studentsParent.getStudentId());
                        ;
                        questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                        questionnaireIdAndStudentIdView.setState(true);
                        questionnaireIds.add(questionnaireIdAndStudentIdView);
                    }

                    Classes classes = classesMapper.selectById(studentsClass.getClassId());
                    schoolId.add(classes.getSchoolId());

                    //发给学校的问卷
                    QueryWrapper<Publish> publishQueryWrapperSchool = new QueryWrapper<>();
                    publishQueryWrapperSchool.eq("strange_id", classes.getSchoolId());
                    publishQueryWrapperSchool.eq("publish_type", 1);
                    publishQueryWrapperSchool.eq("state", 0);
                    publishQueryWrapperSchool.gt("deadLine", new Date());
                    publishQueryWrapperSchool.in("choose_people", new Integer[]{1, 5});
                    List<Publish> publishes2 = publishMapper.selectList(publishQueryWrapperSchool);
                    for (Publish publish : publishes2) {
                        QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                        questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                        questionnaireIdAndStudentIdView.setStudentId(studentsParent.getStudentId());
                        ;
                        questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                        questionnaireIdAndStudentIdView.setState(true);
                        questionnaireIds.add(questionnaireIdAndStudentIdView);
                    }

                    School school = schoolMapper.selectById(classes.getSchoolId());
                    areaId.add(school.getAreaId());
                    //发给地区的问卷
                    QueryWrapper<Publish> publishQueryWrapperArea = new QueryWrapper<>();
                    publishQueryWrapperArea.eq("strange_id", school.getAreaId());
                    publishQueryWrapperArea.eq("publish_type", 0);
                    publishQueryWrapperArea.eq("state", 0);
                    publishQueryWrapperArea.gt("deadLine", new Date());
                    publishQueryWrapperArea.in("choose_people", new Integer[]{1, 5});
                    List<Publish> publishes3 = publishMapper.selectList(publishQueryWrapperArea);
                    for (Publish publish : publishes3) {
                        QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                        questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                        questionnaireIdAndStudentIdView.setStudentId(studentsParent.getStudentId());
                        ;
                        questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                        questionnaireIdAndStudentIdView.setState(true);
                        questionnaireIds.add(questionnaireIdAndStudentIdView);
                    }
                }

            }
        } else if (users.getUserRole() == 4) {
            QueryWrapper<Classes> classesQueryWrapper = new QueryWrapper<>();
            classesQueryWrapper.eq("teacher_id", userId);
            List<Classes> classes = classesMapper.selectList(classesQueryWrapper);
            for (Classes aClass : classes) {
                QueryWrapper<StudentsClass> classesQueryWrapperStudent = new QueryWrapper<>();
                classesQueryWrapperStudent.eq("class_id", aClass.getClassId());
                List<StudentsClass> studentsClasses = studentsClassMapper.selectList(classesQueryWrapperStudent);
                for (StudentsClass studentsClass : studentsClasses) {
                    //发给学生个人
                    QueryWrapper<Publish> publishQueryWrapper = new QueryWrapper<>();
                    publishQueryWrapper.eq("strange_id", studentsClass.getStudentId());
                    publishQueryWrapper.eq("publish_type", 3);
                    publishQueryWrapper.eq("choose_people", 2);
                    publishQueryWrapper.eq("state", 0);
                    publishQueryWrapper.gt("deadLine", new Date());
                    List<Publish> publishes = publishMapper.selectList(publishQueryWrapper);
                    for (Publish publish : publishes) {
                        QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                        questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                        questionnaireIdAndStudentIdView.setStudentId(studentsClass.getStudentId());
                        ;
                        questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                        questionnaireIdAndStudentIdView.setState(true);
                        questionnaireIds.add(questionnaireIdAndStudentIdView);
                    }
                }
                classId.add(aClass.getClassId());
                schoolId.add(aClass.getSchoolId());
            }
            for (Long aLong : classId) {
                QueryWrapper<Publish> publishQueryWrapperClass = new QueryWrapper<>();
                publishQueryWrapperClass.eq("strange_id", aLong);
                publishQueryWrapperClass.eq("publish_type", 2);
                publishQueryWrapperClass.eq("state", 0);
                publishQueryWrapperClass.gt("deadLine", new Date());
                publishQueryWrapperClass.eq("choose_people", 2);
                List<Publish> publishes1 = publishMapper.selectList(publishQueryWrapperClass);
                for (Publish publish : publishes1) {
                    QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                    questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                    questionnaireIdAndStudentIdView.setStudentId(userId);
                    questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                    questionnaireIdAndStudentIdView.setState(true);
                    questionnaireIds.add(questionnaireIdAndStudentIdView);
                }
            }
            for (Long aLong : schoolId) {
                //发给学校的问卷
                QueryWrapper<Publish> publishQueryWrapperSchool = new QueryWrapper<>();
                publishQueryWrapperSchool.eq("strange_id", aLong);
                publishQueryWrapperSchool.eq("publish_type", 1);
                publishQueryWrapperSchool.eq("choose_people", 2);
                publishQueryWrapperSchool.eq("state", 0);
                publishQueryWrapperSchool.gt("deadLine", new Date());
                List<Publish> publishes1 = publishMapper.selectList(publishQueryWrapperSchool);
                for (Publish publish : publishes1) {
                    QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                    questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                    questionnaireIdAndStudentIdView.setStudentId(userId);
                    ;
                    questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                    questionnaireIdAndStudentIdView.setState(true);
                    questionnaireIds.add(questionnaireIdAndStudentIdView);
                }

                School school = schoolMapper.selectById(aLong);
                areaId.add(school.getAreaId());
            }
            areaId = areaId.stream().distinct().collect(Collectors.toList());
            for (Long aLong : areaId) {
                //发给地区的问卷
                QueryWrapper<Publish> publishQueryWrapperArea = new QueryWrapper<>();
                publishQueryWrapperArea.eq("strange_id", aLong);
                publishQueryWrapperArea.eq("publish_type", 0);
                publishQueryWrapperArea.eq("choose_people", 2);
                publishQueryWrapperArea.eq("state", 0);
                publishQueryWrapperArea.gt("deadLine", new Date());
                List<Publish> publishes1 = publishMapper.selectList(publishQueryWrapperArea);
                for (Publish publish : publishes1) {
                    QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                    questionnaireIdAndStudentIdView.setQuestionnaireId(publish.getQuestionnaireId());
                    questionnaireIdAndStudentIdView.setStudentId(userId);
                    ;
                    questionnaireIdAndStudentIdView.setPublishId(publish.getPublishId());
                    questionnaireIdAndStudentIdView.setState(true);
                    questionnaireIds.add(questionnaireIdAndStudentIdView);
                }
            }
        }
//用户问卷表里面家长教师部分
        QueryWrapper<UserQuestionnaire> queryWrapper = new QueryWrapper<>();
        if (users.getUserRole() == 3) {
            queryWrapper.eq("parent_id", userId);
            queryWrapper.eq("state", 1);
            queryWrapper.in("choose_people", new Integer[]{1, 3, 5, 6});
        }
        if (users.getUserRole() == 4) {
            queryWrapper.eq("teacher_id", userId);
            queryWrapper.eq("state", 2);
            queryWrapper.in("choose_people", new Integer[]{2, 4, 5, 6});
        }
        if (users.getUserRole() != 2) {
            List<UserQuestionnaire> userQuestionnaires = userQuestionnaireMapper.selectList(queryWrapper);
            for (
                    UserQuestionnaire userQuestionnaire : userQuestionnaires) {
                QuestionnaireIdAndStudentIdView questionnaireIdAndStudentIdView = new QuestionnaireIdAndStudentIdView();
                questionnaireIdAndStudentIdView.setQuestionnaireId(userQuestionnaire.getQuestionnaire());
                questionnaireIdAndStudentIdView.setStudentId(userQuestionnaire.getUserId());
                questionnaireIdAndStudentIdView.setPublishId(userQuestionnaire.getPublishId());
                questionnaireIdAndStudentIdView.setState(false);
                questionnaireIds.add(questionnaireIdAndStudentIdView);
            }
        }

        return questionnaireIds;
    }

    public void answerQuestionnaire(AnswerQuestionnaireView answerQuestionnaireView) {
        QueryWrapper<UserQuestionnaire> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", answerQuestionnaireView.getUserId());
        queryWrapper.eq("questionnaire", answerQuestionnaireView.getQuestionnaireId());
        queryWrapper.eq("publish_id", answerQuestionnaireView.getPublishId());
        //查到用户问卷表
        UserQuestionnaire userQuestionnaire = userQuestionnaireMapper.selectOne(queryWrapper);
        userQuestionnaire.setAnswer(answerQuestionnaireView.getAnswers());
        if (answerQuestionnaireView.getRole() == 0) {
            if (userQuestionnaire.getChoosePeople() == 3 || userQuestionnaire.getChoosePeople() == 6) {
                userQuestionnaire.setState(1);
            } else if (userQuestionnaire.getChoosePeople() == 4) {
                userQuestionnaire.setState(2);
            } else {
                userQuestionnaire.setState(3);
            }
        } else if (answerQuestionnaireView.getRole() == 1) {
            if (userQuestionnaire.getChoosePeople() == 6 || userQuestionnaire.getChoosePeople() == 5) {
                userQuestionnaire.setState(2);
            } else {
                userQuestionnaire.setState(3);
            }
        } else {
            userQuestionnaire.setState(3);
        }
        if (userQuestionnaire.getState() != 3) {
            userQuestionnaireMapper.updateById(userQuestionnaire);
            return;
        }
        Questionnaire questionnaire1 = questionnaireMapper.selectById(answerQuestionnaireView.getQuestionnaireId());
        QueryWrapper<QuestionnaireDetails> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("questionnaire_id", answerQuestionnaireView.getQuestionnaireId());
        //问卷题目
        List<QuestionnaireDetails> questionnaireDetails = questionnaireDetailsMapper.selectList(queryWrapper4);
        List<Double> variablesAnswer = new ArrayList<>();
        List<Variable> variables = questionnaire1.getVariables();
        Double total = (double) 0;
        //求总分
        for (Answer answer : userQuestionnaire.getAnswer()) {
            total += answer.getScore();
        }
        System.out.println("*********************************************************");
        System.out.println(total);
        for (Variable variable : variables) {
            if (variable.getType() == 0) {
                Double aDouble = (double) 0;
                for (int i = 0; i < questionnaireDetails.size(); i++) {
                    if (questionnaireDetails.get(i).getFactorGroupId().equals(variable.getFactor())) {
                        aDouble += userQuestionnaire.getAnswer().get(i).getScore();
                    }
                }
                variablesAnswer.add(aDouble);
                System.out.println(aDouble);
            } else if (variable.getType() == 1) {
                Double aDouble = (double) 0;
                Double aDouble1 = (double) 0;
                for (int i = 0; i < questionnaireDetails.size(); i++) {
                    if (questionnaireDetails.get(i).getFactorGroupId().equals(variable.getFactor())) {
                        aDouble1++;
                        aDouble += userQuestionnaire.getAnswer().get(i).getScore();
                    }
                }
                if (aDouble1 == 0) {
                    variablesAnswer.add(0.0);
                } else {
                    variablesAnswer.add(aDouble / aDouble1);
                }
            } else if (variable.getType() == 2) {
                Double aDouble = new Double(0);
                for (int i = 0; i < questionnaireDetails.size(); i++) {
                    if (questionnaireDetails.get(i).getFactorGroupId() == variable.getFactor()) {
                        for (String s : userQuestionnaire.getAnswer().get(i).getAnswer()) {
                            if (s.equals(variable.getOption())) {
                                aDouble++;
                            }
                        }
                    }
                }
                variablesAnswer.add(aDouble);
            } else if (variable.getType() == 3) {
                variablesAnswer.add(Double.valueOf(variable.getConstant()));
            } else if (variable.getType() == 4) {
                Double aDouble = new Double(0);
                if (variable.getOperation().equals("+")) {
                    aDouble = variablesAnswer.get(variable.getOperation1()) + variablesAnswer.get(variable.getOperation2());
                } else if (variable.getOperation().equals("-")) {
                    aDouble = variablesAnswer.get(variable.getOperation1()) - variablesAnswer.get(variable.getOperation2());
                } else if (variable.getOperation().equals("*")) {
                    aDouble = variablesAnswer.get(variable.getOperation1()) * variablesAnswer.get(variable.getOperation2());
                } else if (variable.getOperation().equals("/")) {
                    aDouble = variablesAnswer.get(variable.getOperation1()) / variablesAnswer.get(variable.getOperation2());
                }
                variablesAnswer.add(aDouble);
            }
        }
        List<Result> results = questionnaire1.getResults();
        for (Result result : results) {
            if (result.check(variablesAnswer)) {
                userQuestionnaire.setResult(result);
                break;
            }
        }
        List<Number> numbers = new ArrayList<>();
        for (Double aDouble : variablesAnswer) {
            numbers.add(aDouble);
        }
        userQuestionnaire.setVariables(numbers);
        userQuestionnaire.setTotal(total);
        Publish publish = publishMapper.selectById(userQuestionnaire.getPublishId());
        publish.setSubmissionNumber(publish.getSubmissionNumber() + 1);
        publishMapper.updateById(publish);
        userQuestionnaireMapper.updateById(userQuestionnaire);

    }

    public void answerNewQuestionnaire(NewAnswerQuestionnaireView answerQuestionnaireView) {
        UserQuestionnaire userQuestionnaire = new UserQuestionnaire();
        userQuestionnaire.setUserQuestionnaireId(YitIdHelper.nextId());
        userQuestionnaire.setUserId(answerQuestionnaireView.getStudentId());
        userQuestionnaire.setQuestionnaire(answerQuestionnaireView.getQuestionnaireId());
        userQuestionnaire.setAnswer(answerQuestionnaireView.getAnswers());
        if (answerQuestionnaireView.getRole() == 2) {
            QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teacher_id", answerQuestionnaireView.getUserId());
            Classes classes = classesMapper.selectOne(queryWrapper);
            userQuestionnaire.setClassId(classes.getClassId());
            userQuestionnaire.setClassName(classes.getClassName());
            userQuestionnaire.setGrade(classes.getGrade());
            userQuestionnaire.setSchoolId(classes.getSchoolId());
            School school = schoolMapper.selectById(classes.getSchoolId());
            userQuestionnaire.setSchoolName(school.getSchoolName());
            userQuestionnaire.setAreaId(school.getAreaId());
            Area area = areaMapper.selectById(school.getAreaId());
            userQuestionnaire.setAreaName(area.getAreaName());
            userQuestionnaire.setCityName(area.getCityName());
            userQuestionnaire.setCityId(area.getCityId());
            userQuestionnaire.setProvinceName(area.getProvinceName());
            userQuestionnaire.setProvinceId(area.getProvinceId());
            userQuestionnaire.setTeacherId(answerQuestionnaireView.getUserId());
            userQuestionnaire.setPublishId(answerQuestionnaireView.getUserId());
            userQuestionnaire.setPublishId(answerQuestionnaireView.getPublishId());
        } else {
            QueryWrapper<StudentsClass> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id", answerQuestionnaireView.getStudentId());
            StudentsClass studentsClass = studentsClassMapper.selectOne(queryWrapper);
            userQuestionnaire.setClassId(studentsClass.getClassId());
            Classes classes = classesMapper.selectById(studentsClass.getClassId());
            userQuestionnaire.setClassName(classes.getClassName());
            userQuestionnaire.setGrade(classes.getGrade());
            userQuestionnaire.setSchoolId(classes.getSchoolId());
            School school = schoolMapper.selectById(classes.getSchoolId());
            userQuestionnaire.setSchoolName(school.getSchoolName());
            userQuestionnaire.setAreaId(school.getAreaId());
            Area area = areaMapper.selectById(school.getAreaId());
            userQuestionnaire.setAreaName(area.getAreaName());
            userQuestionnaire.setCityName(area.getCityName());
            userQuestionnaire.setCityId(area.getCityId());
            userQuestionnaire.setProvinceName(area.getProvinceName());
            userQuestionnaire.setProvinceId(area.getProvinceId());
            userQuestionnaire.setTeacherId(classes.getTeacherId());
            QueryWrapper<StudentsParent> parentQueryWrapper = new QueryWrapper<>();
            parentQueryWrapper.eq("student_id", answerQuestionnaireView.getStudentId());
            StudentsParent studentsParent = studentsParentMapper.selectOne(parentQueryWrapper);
            userQuestionnaire.setParentId(studentsParent.getParentId());
            userQuestionnaire.setPublishId(answerQuestionnaireView.getPublishId());
        }
        Questionnaire questionnaire = questionnaireMapper.selectById(answerQuestionnaireView.getQuestionnaireId());
        userQuestionnaire.setChoosePeople(questionnaire.getChoosePeople());
        if (answerQuestionnaireView.getRole() == 0) {
            if (questionnaire.getChoosePeople() == 3 || questionnaire.getChoosePeople() == 6) {
                userQuestionnaire.setState(1);
            } else if (questionnaire.getChoosePeople() == 4) {
                userQuestionnaire.setState(2);
            } else {
                userQuestionnaire.setState(3);
            }
        } else if (answerQuestionnaireView.getRole() == 1) {
            if (questionnaire.getChoosePeople() == 6 || questionnaire.getChoosePeople() == 5) {
                userQuestionnaire.setState(2);
            } else {
                userQuestionnaire.setState(3);
            }
        } else {
            userQuestionnaire.setState(3);
        }
        if (userQuestionnaire.getState() != 3) {
            userQuestionnaireMapper.insert(userQuestionnaire);
            return;
        }
        Questionnaire questionnaire1 = questionnaireMapper.selectById(answerQuestionnaireView.getQuestionnaireId());
        QueryWrapper<QuestionnaireDetails> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("questionnaire_id", answerQuestionnaireView.getQuestionnaireId());
        //问卷题目
        List<QuestionnaireDetails> questionnaireDetails = questionnaireDetailsMapper.selectList(queryWrapper4);
        List<Double> variablesAnswer = new ArrayList<>();
        List<Variable> variables = questionnaire1.getVariables();
        Double total = (double) 0;
        //求总分
        for (Answer answer : userQuestionnaire.getAnswer()) {
            total += answer.getScore();
        }
        for (Variable variable : variables) {
            if (variable.getType() == 0) {
                Double aDouble = (double) 0;
                for (int i = 0; i < questionnaireDetails.size(); i++) {
                    if (questionnaireDetails.get(i).getFactorGroupId().equals(variable.getFactor())) {
                        System.out.println(userQuestionnaire.getAnswer());
                        aDouble += userQuestionnaire.getAnswer().get(i).getScore();
                    }
                }
                variablesAnswer.add(aDouble);
            } else if (variable.getType() == 1) {
                Double aDouble = (double) 0;
                Double aDouble1 = (double) 0;
                for (int i = 0; i < questionnaireDetails.size(); i++) {
                    if (questionnaireDetails.get(i).getFactorGroupId().equals(variable.getFactor())) {
                        aDouble1++;
                        aDouble += userQuestionnaire.getAnswer().get(i).getScore();
                    }
                }
                if (aDouble1 == 0) {
                    variablesAnswer.add(0.0);
                } else {
                    variablesAnswer.add(aDouble / aDouble1);
                }
            } else if (variable.getType() == 2) {
                Double aDouble = new Double(0);
                for (int i = 0; i < questionnaireDetails.size(); i++) {
                    if (questionnaireDetails.get(i).getFactorGroupId() == variable.getFactor()) {
                        for (String s : userQuestionnaire.getAnswer().get(i).getAnswer()) {
                            if (s.equals(variable.getOption())) {
                                aDouble++;
                            }
                        }
                    }
                }
                variablesAnswer.add(aDouble);
            } else if (variable.getType() == 3) {
                variablesAnswer.add(Double.valueOf(variable.getConstant()));
            } else if (variable.getType() == 4) {
                Double aDouble = new Double(0);
                if (variable.getOperation().equals("+")) {
                    aDouble = variablesAnswer.get(variable.getOperation1()) + variablesAnswer.get(variable.getOperation2());
                } else if (variable.getOperation().equals("-")) {
                    aDouble = variablesAnswer.get(variable.getOperation1()) - variablesAnswer.get(variable.getOperation2());
                } else if (variable.getOperation().equals("*")) {
                    aDouble = variablesAnswer.get(variable.getOperation1()) * variablesAnswer.get(variable.getOperation2());
                } else if (variable.getOperation().equals("/")) {
                    aDouble = variablesAnswer.get(variable.getOperation1()) / variablesAnswer.get(variable.getOperation2());
                }
                variablesAnswer.add(aDouble);
            }
        }
        List<Result> results = questionnaire1.getResults();
        for (Result result : results) {
            if (result.check(variablesAnswer)) {
                userQuestionnaire.setResult(result);
                break;
            }
        }
        List<Number> numbers = new ArrayList<>();
        for (Double aDouble : variablesAnswer) {
            numbers.add(aDouble);
        }
        userQuestionnaire.setVariables(numbers);
        userQuestionnaire.setTotal(total);
        Publish publish = publishMapper.selectById(userQuestionnaire.getPublishId());
        publish.setSubmissionNumber(publish.getSubmissionNumber() + 1);
        publishMapper.updateById(publish);
        userQuestionnaireMapper.insert(userQuestionnaire);
    }

    public UserQuestionnaire getUserQuestionnaire(Long userId, Long questionnaireId, Long publishId) {
        QueryWrapper<UserQuestionnaire> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("questionnaire", questionnaireId);
        queryWrapper.eq("publish_id", publishId);
        return userQuestionnaireMapper.selectOne(queryWrapper);
    }

    public PageResult<QuestionnaireResultView> getQuestionnaireResult(BasePageParam basePageParam) {
        PageHelper.startPage(basePageParam.getPageNum(), basePageParam.getPageSize());
        long userId = StpUtil.getLoginIdAsLong();
        Users users = usersMapper.selectById(userId);
        Long userRole = users.getUserRole();
        List<UserQuestionnaire> userQuestionnaires = new ArrayList<>();
        if (userRole == 2) {
            QueryWrapper<UserQuestionnaire> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            queryWrapper.eq("state", 3);
            queryWrapper.orderByDesc("create_time");
            userQuestionnaires = userQuestionnaireMapper.selectList(queryWrapper);
        } else if (userRole == 3) {
            QueryWrapper<UserQuestionnaire> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", userId);
            queryWrapper.eq("state", 3);
            queryWrapper.orderByDesc("create_time");
            userQuestionnaires = userQuestionnaireMapper.selectList(queryWrapper);
        } else if (userRole == 4) {
            QueryWrapper<UserQuestionnaire> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teacher_id", userId);
            queryWrapper.eq("state", 3);
            queryWrapper.orderByDesc("create_time");
            userQuestionnaires = userQuestionnaireMapper.selectList(queryWrapper);
        }
        List<QuestionnaireResultView> questionnaireResultViews = new ArrayList<>();
        for (UserQuestionnaire userQuestionnaire : userQuestionnaires) {
            QuestionnaireResultView questionnaireResultView = new QuestionnaireResultView();
            questionnaireResultView.setUserQuestionnaire(userQuestionnaire);
            Publish publish = publishMapper.selectById(userQuestionnaire.getPublishId());
            questionnaireResultView.setPublisherName(usersMapper.selectById(publish.getPublisherId()).getUserName());
            questionnaireResultView.setQuestionnaireName(questionnaireMapper.selectById(userQuestionnaire.getQuestionnaire()).getQuestionnaireName());
            questionnaireResultViews.add(questionnaireResultView);
        }
        return new PageResult<QuestionnaireResultView>(questionnaireResultViews);
    }

    public UserQuestionnaire getLastResult(Long userQuestionnaireId) {
        UserQuestionnaire userQuestionnaire = userQuestionnaireMapper.selectById(userQuestionnaireId);
        return userQuestionnaire;
    }

    public PageResult<ResultView> getPublishResult(BasePageParam basePageParam, Long publishId) {
        PageHelper.startPage(basePageParam.getPageNum(), basePageParam.getPageSize());
        List<ResultView> resultViews = new ArrayList<>();
        QueryWrapper<UserQuestionnaire> userQuestionnaireQueryWrapper = new QueryWrapper<>();
        userQuestionnaireQueryWrapper.eq("publish_id", publishId);
        userQuestionnaireQueryWrapper.eq("state", 3);
        List<UserQuestionnaire> userQuestionnaires = userQuestionnaireMapper.selectList(userQuestionnaireQueryWrapper);
        for (UserQuestionnaire userQuestionnaire : userQuestionnaires) {
            ResultView resultView = new ResultView();
            resultView.setUserQuestionnaire(userQuestionnaire);
            resultView.setUserName(usersMapper.selectById(userQuestionnaire.getUserId()).getUserName());
            resultViews.add(resultView);
        }
        return new PageResult<ResultView>(resultViews);
    }
}
