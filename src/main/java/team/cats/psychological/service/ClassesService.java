package team.cats.psychological.service;

import com.github.pagehelper.PageHelper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.entity.Classes;
import team.cats.psychological.mapper.ClassesMapper;
import team.cats.psychological.mapper.SchoolMapper;
import team.cats.psychological.mapper.UsersMapper;
import team.cats.psychological.param.ClassesParams;
import team.cats.psychological.vo.ClassesView;
import team.cats.psychological.vo.Teacher;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassesService {

    @Resource
    private ClassesMapper classesMapper;

    @Resource
    private SchoolMapper schoolMapper;

    @Resource
    private UsersMapper usersMapper;

    /**
     * 获取班级信息
     * @param basePageParam
     * @param teacherId
     * @param schoolId
     * @param value
     * @return
     */
    public PageResult<ClassesView> selectClasses(BasePageParam basePageParam, Long teacherId, Long schoolId, String value) {
        PageHelper.startPage(basePageParam.getPageNum(), basePageParam.getPageSize());
        List<ClassesView> classes = classesMapper.selectClasses(teacherId, schoolId, value);
        for (ClassesView aClass : classes) {
            aClass.setSchoolName(schoolMapper.selectById(aClass.getSchoolId()).getSchoolName());
            aClass.setTeacherName(usersMapper.selectById(aClass.getTeacherId()).getUserName());
        }
        return new PageResult<ClassesView>(classes);
    }

    public void insertClasses(ClassesParams classesParams){
        Classes classes=new Classes();
        classes.setClassId(YitIdHelper.nextId());
        classes.setTeacherId(classesParams.getTeacherId());
        classes.setSchoolId(classesParams.getSchoolId());
        classes.setClassName(classesParams.getClassName());
        classes.setGrade(classesParams.getGrade());
        int insert = classesMapper.insert(classes);
        if (insert == 0) {
            throw new BaseException(400, "插入失败");
        }
    }

    public void modifyClasses(ClassesParams classesParams){
        Classes classes = classesMapper.selectById(classesParams.getClassId());
        classes.setTeacherId(classesParams.getTeacherId());
        classes.setSchoolId(classesParams.getSchoolId());
        classes.setClassName(classesParams.getClassName());
        classes.setGrade(classesParams.getGrade());
        int i = classesMapper.updateById(classes);
        if(i==0){
            throw new BaseException(400,"更新失败");
        }
    }

    public void delClasses(Long classId){
        int i = classesMapper.deleteById(classId);
        if(i==0){
            throw new BaseException(400,"删除失败");
        }
    }
}
