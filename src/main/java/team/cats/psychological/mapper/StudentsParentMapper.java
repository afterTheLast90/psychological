package team.cats.psychological.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.cats.psychological.entity.StudentsParent;
import team.cats.psychological.entity.TeacherSchool;
import team.cats.psychological.vo.ParentView;

import java.util.List;

public interface StudentsParentMapper extends BaseMapper<StudentsParent> {


    public List<ParentView> selectByStudentId(@Param("studentId") Long studentId);
}
