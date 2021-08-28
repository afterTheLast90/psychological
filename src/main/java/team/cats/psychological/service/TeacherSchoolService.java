package team.cats.psychological.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.entity.TeacherSchool;
import team.cats.psychological.entity.Users;
import team.cats.psychological.mapper.TeacherSchoolMapper;
import team.cats.psychological.mapper.UsersMapper;
import team.cats.psychological.vo.Teacher;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherSchoolService {

    @Resource
    private TeacherSchoolMapper teacherSchoolMapper;
    @Resource
    private UsersMapper usersMapper;

    public void insertTeacher(Long teacherId,Long schoolId){
        TeacherSchool teacherSchool=new TeacherSchool();
        teacherSchool.setTeacherSchoolId(YitIdHelper.nextId());
        teacherSchool.setTeacherId(teacherId);
        teacherSchool.setSchoolId(schoolId);
        teacherSchoolMapper.insert(teacherSchool);
    }

    public void modifyTeacher(Long teacherId, Long schoolId){
        TeacherSchool teacherSchool = teacherSchoolMapper.selectByTeacherId2(teacherId);
        teacherSchool.setSchoolId(schoolId);
        teacherSchoolMapper.updateById(teacherSchool);
    }
}
