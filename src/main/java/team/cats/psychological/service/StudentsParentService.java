package team.cats.psychological.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.entity.StudentsParent;
import team.cats.psychological.entity.Users;
import team.cats.psychological.mapper.ClassesMapper;
import team.cats.psychological.mapper.StudentsClassMapper;
import team.cats.psychological.mapper.StudentsParentMapper;
import team.cats.psychological.mapper.UsersMapper;
import team.cats.psychological.vo.AParentView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentsParentService {

    @Resource
    StudentsParentMapper studentsParentMapper;

    @Resource
    UsersService usersService;
    @Resource
    ClassesMapper classesMapper;
    @Resource
    StudentsClassMapper studentsClassMapper;
    @Resource
    UsersMapper usersMapper;

    public void insertParent(Long studentId, Long parentId) {
        System.out.println("**********************************************************");
        StudentsParent studentsParent = new StudentsParent();
        studentsParent.setStudentsParentId(YitIdHelper.nextId());
        studentsParent.setParentId(parentId);
        studentsParent.setStudentId(studentId);
        studentsParentMapper.insert(studentsParent);
    }
    public void modifyParent(Long studentId,Long parentId){
        QueryWrapper<StudentsParent> studentsParentQueryWrapper=new QueryWrapper<>();
        studentsParentQueryWrapper.eq("student_id",studentId);
        StudentsParent studentsParent = studentsParentMapper.selectOne(studentsParentQueryWrapper);
        studentsParent.setParentId(parentId);
        studentsParentMapper.updateById(studentsParent);
    }

    public void delStudentParent(Long studentId, Long parent_id) {
        QueryWrapper<StudentsParent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId);
        queryWrapper.eq("parent_id", parent_id);
        StudentsParent studentsParent = studentsParentMapper.selectOne(queryWrapper);
        studentsParentMapper.deleteById(studentsParent);
        usersService.deleteUser(parent_id);
    }

    public List<AParentView> getParent() {
        List<AParentView> parentViews = new ArrayList<>();
        QueryWrapper<Users> classesQueryWrapper = new QueryWrapper<>();
        classesQueryWrapper.eq("user_role", 3);
        classesQueryWrapper.orderByDesc("create_time");
        List<Users> users = usersMapper.selectList(classesQueryWrapper);
        for (Users user : users) {
            AParentView aParentView = new AParentView();
            aParentView.setParentId(user.getUserId());
            aParentView.setParentName(user.getUserName());
            parentViews.add(aParentView);
        }
        return parentViews;
    }
}
