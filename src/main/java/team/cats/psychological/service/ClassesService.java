package team.cats.psychological.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.entity.*;
import team.cats.psychological.mapper.*;
import team.cats.psychological.param.ClassesParams;
import team.cats.psychological.vo.ClassesView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassesService {

    @Resource
    private ClassesMapper classesMapper;

    @Resource
    private SchoolMapper schoolMapper;

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private AreaMapper areaMapper;
    @Resource
    private TeacherSchoolMapper teacherSchoolMapper;

    /**
     * 获取班级信息
     *
     * @param basePageParam
     * @param teacherId
     * @param schoolId
     * @param value
     * @return
     */
    public PageResult<ClassesView> selectClasses(BasePageParam basePageParam, Long teacherId, Long schoolId, String value) {
        PageHelper.startPage(basePageParam.getPageNum(), basePageParam.getPageSize());
        List<ClassesView> classesViewList = new ArrayList<>();

        List<ClassesView> classes = classesMapper.selectClasses(teacherId, schoolId, value);
        for (ClassesView aClass : classes) {
            aClass.setSchoolName(schoolMapper.selectById(aClass.getSchoolId()).getSchoolName());
            aClass.setTeacherName(usersMapper.selectById(aClass.getTeacherId()).getUserName());
        }


        long userId = StpUtil.getLoginIdAsLong();
        Users users = usersMapper.selectById(userId);
        Long userRole = users.getUserRole();
        if (userRole == 0) {
            classesViewList = classes;
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
                    List<Classes> classes1 = classesMapper.selectList(classesQueryWrapper);
                    for (ClassesView aClass : classes) {
                        for (Classes classes2 : classes1) {
                            if (aClass.getClassId().equals(classes2.getClassId())) {
                                classesViewList.add(aClass);
                            }
                        }
                    }
                }
            }
        } else if (userRole == 4) {

            QueryWrapper<Classes> classesQueryWrapper = new QueryWrapper<>();
            classesQueryWrapper.eq("teacher_id", userId);
            List<Classes> classes1 = classesMapper.selectList(classesQueryWrapper);
            for (ClassesView aClass : classes) {
                for (Classes classes2 : classes1) {
                    if (aClass.getClassId().equals(classes2.getClassId())) {
                        classesViewList.add(aClass);
                    }
                }
            }
        }

        return new PageResult<ClassesView>(classesViewList);
    }

    public void insertClasses(ClassesParams classesParams) {
        Classes classes = new Classes();
        classes.setClassId(YitIdHelper.nextId());
        classes.setTeacherId(classesParams.getTeacherId());
        QueryWrapper<TeacherSchool> teacherSchoolQueryWrapper   =new QueryWrapper<>();
        teacherSchoolQueryWrapper.eq("teacher_id",classesParams.getTeacherId());
        TeacherSchool teacherSchool = teacherSchoolMapper.selectOne(teacherSchoolQueryWrapper);
        classes.setSchoolId(teacherSchool.getSchoolId());
        classes.setClassName(classesParams.getClassName());
        classes.setGrade(classesParams.getGrade());
        int insert = classesMapper.insert(classes);
        if (insert == 0) {
            throw new BaseException(400, "插入失败");
        }
    }

    public void modifyClasses(ClassesParams classesParams) {
        Classes classes = classesMapper.selectById(classesParams.getClassId());
        classes.setTeacherId(classesParams.getTeacherId());
        classes.setSchoolId(classesParams.getSchoolId());
        classes.setClassName(classesParams.getClassName());
        classes.setGrade(classesParams.getGrade());
        int i = classesMapper.updateById(classes);
        if (i == 0) {
            throw new BaseException(400, "更新失败");
        }
    }

    public void delClasses(Long classId) {
        int i = classesMapper.deleteById(classId);
        if (i == 0) {
            throw new BaseException(400, "删除失败");
        }
    }
}
