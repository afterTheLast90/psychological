package team.cats.psychological.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.yitter.idgen.YitIdHelper;
import lombok.val;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.entity.*;
import team.cats.psychological.entity.StudentsParent;
import team.cats.psychological.mapper.*;
import team.cats.psychological.param.UserParams;
import team.cats.psychological.vo.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class UsersService {
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private AreaMapper areaMapper;
    @Resource
    private SchoolMapper schoolMapper;
    @Resource
    private ClassesMapper classesMapper;
    @Resource
    private StudentsParentMapper studentsParentMapper;
    @Resource
    private TeacherSchoolMapper teacherSchoolMapper;
    @Resource
    private StudentsClassMapper studentsClassMapper;


    public List<Users> getTeacherArray() {
        QueryWrapper<Users> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_role", 4);
        List<Users> users = usersMapper.selectList(queryWrapper);
        return users;
    }


    /**
     * 获取userList带地区
     *
     * @param basePageParam
     * @param areaId
     * @param value
     * @return
     */
    public PageResult<UsersAndArea> getUserList(BasePageParam basePageParam, Long areaId, String value) {
        PageHelper.startPage(basePageParam.getPageNum(), basePageParam.getPageSize());
        List<UsersAndArea> usersAndAreas = usersMapper.selectUserArea(value, areaId);

        for (UsersAndArea usersAndArea : usersAndAreas) {
            usersAndArea.setAreas(areaMapper.selectByUserId(usersAndArea.getUserId()));
            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);  //当前年份
            int monthNow = cal.get(Calendar.MONTH);  //当前月份
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
            int yearBirth = usersAndArea.getUserBirthday().getYear();
            int monthBirth = usersAndArea.getUserBirthday().getMonthValue();
            int dayOfMonthBirth = usersAndArea.getUserBirthday().getDayOfMonth();
            int age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
                } else {
                    age--;//当前月份在生日之前，年龄减一
                }
            }
            usersAndArea.setAge(age);
        }

        return new PageResult<UsersAndArea>(usersAndAreas);
    }


    /**
     * 获取userList带学校
     *
     * @param basePageParam
     * @param schoolId
     * @param value
     * @return
     */
    public PageResult<Teacher> getTeacherList(BasePageParam basePageParam, Long schoolId, String value) {
        PageHelper.startPage(basePageParam.getPageNum(), basePageParam.getPageSize());

        long userId = StpUtil.getLoginIdAsLong();
        List<Teacher> teachers = usersMapper.selectTeacher(value, schoolId);
        for (Teacher teacher : teachers) {
            Long schoolId2 = teacherSchoolMapper.selectByTeacherId(teacher.getUserId());
            teacher.setSchool(schoolMapper.selectById(schoolId2));
            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);  //当前年份
            int monthNow = cal.get(Calendar.MONTH);  //当前月份
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
            int yearBirth = teacher.getUserBirthday().getYear();
            int monthBirth = teacher.getUserBirthday().getMonthValue();
            int dayOfMonthBirth = teacher.getUserBirthday().getDayOfMonth();
            int age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
                } else {
                    age--;//当前月份在生日之前，年龄减一
                }
            }
            teacher.setAge(age);
        }


        List<Teacher> aTeachers = new ArrayList<>();
        Users users = usersMapper.selectById(userId);
        Long userRole = users.getUserRole();
        if (userRole==0){
            aTeachers=teachers;
        }else if (userRole==1){
            QueryWrapper<Area> areaQueryWrapper=new QueryWrapper<>();
            areaQueryWrapper.eq("area_principal",userId);
            List<Area> areas = areaMapper.selectList(areaQueryWrapper);
            for (Area area : areas) {
                QueryWrapper<School> schoolQueryWrapper = new QueryWrapper<>();
                schoolQueryWrapper.eq("area_id",area.getAreaId());
                List<School> schools = schoolMapper.selectList(schoolQueryWrapper);
                for (School school : schools) {
                    for (Teacher teacher : teachers) {
                        if (teacher.getSchool().getSchoolId().equals(school.getSchoolId())){
                            aTeachers.add(teacher);
                        }
                    }
                }
            }
        }


        return new PageResult<Teacher>(aTeachers);
    }


    /**
     * 获取UserList带班级
     *
     * @param basePageParam
     * @param classId
     * @param value
     * @return
     */
    public PageResult<StudentView> getClassesList(BasePageParam basePageParam, Long classId, String value) {
        PageHelper.startPage(basePageParam.getPageNum(), basePageParam.getPageSize());
        List<StudentView> studentViewList = new ArrayList<>();
        List<StudentView> studentViews = usersMapper.selectStudent(value, classId);


        long userId = StpUtil.getLoginIdAsLong();
        Users users = usersMapper.selectById(userId);
        Long userRole = users.getUserRole();
        if (userRole == 0) {
            studentViewList=studentViews;
        } else if (userRole == 1) {
            QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
            areaQueryWrapper.eq("area_principal", userId);
            List<Area> areas = areaMapper.selectList(areaQueryWrapper);
            for (Area area : areas) {
                QueryWrapper<School> schoolQueryWrapper = new QueryWrapper<>();
                schoolQueryWrapper.eq("area_id", area.getAreaId());
                List<School> schools = schoolMapper.selectList(schoolQueryWrapper);
                for (School school : schools) {
                    QueryWrapper<Classes> classesQueryWrapper = new QueryWrapper<>();
                    classesQueryWrapper.eq("school_id", school.getSchoolId());
                    List<Classes> classes = classesMapper.selectList(classesQueryWrapper);
                    for (Classes aClass : classes) {
                        QueryWrapper<StudentsClass> studentsClassQueryWrapper = new QueryWrapper<>();
                        studentsClassQueryWrapper.eq("class_id",aClass.getClassId());
                        List<StudentsClass> studentsClasses = studentsClassMapper.selectList(studentsClassQueryWrapper);
                        for (StudentView studentView : studentViews) {
                            for (StudentsClass studentsClass : studentsClasses) {
                                if (studentsClass.getStudentId().equals(studentView.getUserId())){
                                    studentViewList.add(studentView);
                                }
                            }
                        }
                    }
                }
            }
        } else if (userRole == 4) {
            QueryWrapper<Classes> classesQueryWrapper = new QueryWrapper<>();
            classesQueryWrapper.eq("teacher_id", userId);
            List<Classes> classes = classesMapper.selectList(classesQueryWrapper);
            for (Classes aClass : classes) {
                QueryWrapper<StudentsClass> studentsClassQueryWrapper = new QueryWrapper<>();
                studentsClassQueryWrapper.eq("class_id",aClass.getClassId());
                List<StudentsClass> studentsClasses = studentsClassMapper.selectList(studentsClassQueryWrapper);
                for (StudentView studentView : studentViews) {
                    for (StudentsClass studentsClass : studentsClasses) {
                        if (studentsClass.getStudentId().equals(studentView.getUserId())){
                            studentViewList.add(studentView);
                        }
                    }
                }
            }
        }
        for (StudentView studentView : studentViewList) {
            List<ClassesView> classes = classesMapper.selectByStudentId(studentView.getUserId());
            for (ClassesView aClass : classes) {
                aClass.setSchoolName(schoolMapper.selectById(aClass.getSchoolId()).getSchoolName());
                aClass.setTeacherName(usersMapper.selectById(aClass.getTeacherId()).getUserName());
            }
            studentView.setClasses(classes);

            List<ParentView> parentViews = studentsParentMapper.selectByStudentId(studentView.getUserId());
            System.out.println(parentViews);
            if(parentViews.size()!=0){
                for (ParentView parentView : parentViews) {
                    parentView.setParentName(usersMapper.selectById(parentView.getParentId()).getUserName());
                }
            }
            studentView.setParents(parentViews);

            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);  //当前年份
            int monthNow = cal.get(Calendar.MONTH);  //当前月份
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
            int yearBirth = studentView.getUserBirthday().getYear();
            int monthBirth = studentView.getUserBirthday().getMonthValue();
            int dayOfMonthBirth = studentView.getUserBirthday().getDayOfMonth();
            int age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
                } else {
                    age--;//当前月份在生日之前，年龄减一
                }
            }
            studentView.setAge(age);
        }

        return new PageResult<StudentView>(studentViewList);
    }

    /**
     * 禁用
     *
     * @param userId
     * @param state
     */
    public void changeUserState(Long userId, Boolean state) {
        Users users = getUserNotNull(userId);
        users.setState(state ? 0 : 1);
        usersMapper.updateById(users);
    }

    /**
     * 删除用户
     *
     * @param userId
     */
    public void deleteUser(Long userId) {
        Users users = getUserNotNull(userId);
        users.setDeleteFlag(true);
        usersMapper.deleteById(users);
    }

    //    判断用户是否存在
    public Users getUserNotNull(Long userId) {
        Users users = usersMapper.selectById(userId);
        if (users == null) {
            throw new BaseException(400, "用户不存在");
        }
        return users;
    }

    /**
     * 添加用户
     *
     * @param addUserParams
     * @param userRole
     */
    public Long InsertUser(UserParams addUserParams, Long userRole) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_phone_number", addUserParams.getUserPhoneNumber());
        Integer integer = usersMapper.selectCount(queryWrapper);
        if (integer != 0) {
            throw new BaseException(400, "该电话已被注册");
        }
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", addUserParams.getUserAccount());
        Integer integer1 = usersMapper.selectCount(queryWrapper);
        if (integer1 != 0) {
            throw new BaseException(400, "该账号已被注册");
        }
        Users users = new Users();
        users.setUserId(YitIdHelper.nextId());
        users.setUserName(addUserParams.getUserName());
        users.setUserAccount(addUserParams.getUserAccount());
        users.setUserPassword(addUserParams.getUserPassword());
        users.setUserBirthday(addUserParams.getUserBirthday());
        users.setUserGender(addUserParams.getUserGender());
        users.setUserPhoneNumber(addUserParams.getUserPhoneNumber());
        users.setUserRole(userRole);
        int insert = usersMapper.insert(users);
        if (insert == 0) {
            throw new BaseException(400, "插入失败");
        }
        return users.getUserId();
    }

    /**
     * 修改管理员信息
     *
     * @param addUserParams
     */
    public void modifyAdministrator(UserParams addUserParams) {
        Users users = usersMapper.selectById(addUserParams.getUserId());
        users.setUserName(addUserParams.getUserName());
        users.setUserAccount(addUserParams.getUserAccount());
        users.setUserPassword(addUserParams.getUserPassword());
        users.setUserBirthday(addUserParams.getUserBirthday());
        users.setUserGender(addUserParams.getUserGender());
        users.setUserPhoneNumber(addUserParams.getUserPhoneNumber());
        int insert = usersMapper.updateById(users);
        if (insert == 0) {
            throw new BaseException(400, "修改失败");
        }
    }

    /**
     * 获取用户信息
     */
    public UserInformationView getUser(){
        UserInformationView userInformationView = new UserInformationView();
        List<String> classNames = new ArrayList<>();
        long userId = StpUtil.getLoginIdAsLong();
        Users user = usersMapper.selectById(userId);
        if (user.getUserRole()==2){
            QueryWrapper<StudentsClass> studentsClassQueryWrapper = new QueryWrapper<>();
            studentsClassQueryWrapper.eq("student_id",user.getUserId());
            List<StudentsClass> studentsClasses = studentsClassMapper.selectList(studentsClassQueryWrapper);
            for (StudentsClass studentsClass : studentsClasses) {
                Classes classes = classesMapper.selectById(studentsClass.getClassId());
                classNames.add(classes.getClassName());
            }
        }
        userInformationView.setUsers(user);
        userInformationView.setClassName(classNames);
        return userInformationView;
    }
}
