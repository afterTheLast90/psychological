package team.cats.psychological.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import team.cats.psychological.entity.Classes;
import team.cats.psychological.vo.ClassesView;
import team.cats.psychological.vo.UsersAndArea;

import java.util.List;

public interface ClassesMapper extends BaseMapper<Classes> {

    public List<ClassesView> selectClasses(@Param("teacherId") Long teacherId, @Param("schoolId") Long schoolId, @Param("value") String value);

    public List<ClassesView> selectByStudentId(@Param("studentId") Long studentId);

    public List<Classes> selectBySchoolId(Long schoolId);
}
