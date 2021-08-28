package team.cats.psychological.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.entity.StudentsClass;
import team.cats.psychological.entity.StudentsParent;
import team.cats.psychological.entity.TeacherSchool;
import team.cats.psychological.mapper.StudentsParentMapper;

import javax.annotation.Resource;

@Service
public class StudentsParentService {

    @Resource
    StudentsParentMapper studentsParentMapper;

    @Resource
    UsersService usersService;

    public void insertParent(Long studentId,Long parentId){
        StudentsParent studentsParent=new StudentsParent();
        studentsParent.setStudentsParentId(YitIdHelper.nextId());
        studentsParent.setParentId(parentId);
        studentsParent.setStudentId(studentId);
        studentsParentMapper.insert(studentsParent);
    }

    public void delStudentParent(Long studentId,Long parent_id){
        QueryWrapper<StudentsParent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",studentId);
        queryWrapper.eq("parent_id",parent_id);
        StudentsParent studentsParent = studentsParentMapper.selectOne(queryWrapper);
        studentsParentMapper.deleteById(studentsParent);
        usersService.deleteUser(parent_id);
    }
}
