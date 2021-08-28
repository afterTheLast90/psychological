package team.cats.psychological.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.entity.Area;
import team.cats.psychological.entity.StudentsClass;
import team.cats.psychological.entity.TeacherSchool;
import team.cats.psychological.mapper.StudentsClassMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentsClassService {

    @Resource
    private StudentsClassMapper studentsClassMapper;

    public void insertStudentClass(Long studentId,Long classId){
        QueryWrapper<StudentsClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",studentId);
        queryWrapper.eq("class_id",classId);
        StudentsClass studentsClass = new StudentsClass();
        List<StudentsClass> studentsClasses = studentsClassMapper.selectList(queryWrapper);
        for (StudentsClass aClass : studentsClasses) {
            System.out.println(aClass);
        }
        System.out.println(studentsClasses.size());
        if(studentsClasses.size()!=0){
            throw new BaseException(400,"已经加入该班级");
        }
        studentsClass.setStudentsClassId(YitIdHelper.nextId());
        studentsClass.setStudentId(studentId);
        studentsClass.setClassId(classId);
        studentsClassMapper.insert(studentsClass);
    }

    public void delStudentClass(Long studentId,Long classId){
        QueryWrapper<StudentsClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",studentId);
        queryWrapper.eq("class_id",classId);
        StudentsClass studentsClass = studentsClassMapper.selectOne(queryWrapper);
        studentsClassMapper.deleteById(studentsClass);
    }

}
