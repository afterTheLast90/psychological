package team.cats.psychological.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import team.cats.psychological.entity.Area;
import team.cats.psychological.entity.TeacherSchool;

import java.util.List;

public interface TeacherSchoolMapper extends BaseMapper<TeacherSchool> {


    @Select("select school_id from teacher_school where teacher_id = #{teacherId} and delete_flag=0 ")
    public Long selectByTeacherId(@Param("teacherId") Long teacherId);

    @Select("select * from teacher_school where teacher_id = #{teacherId} and delete_flag=0 ")
    public TeacherSchool selectByTeacherId2(@Param("teacherId") Long teacherId);
}
