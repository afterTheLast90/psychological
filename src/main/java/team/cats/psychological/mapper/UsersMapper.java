package team.cats.psychological.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import team.cats.psychological.entity.Users;
import team.cats.psychological.vo.ParentView;
import team.cats.psychological.vo.StudentView;
import team.cats.psychological.vo.Teacher;
import team.cats.psychological.vo.UsersAndArea;

import java.util.List;

public interface UsersMapper extends BaseMapper<Users> {


    //userId,userName,userAccount,userPhoneNumber,areaId

    public List<UsersAndArea> selectUserArea(@Param("value") String value,@Param("areaId") Long areaId);

    public List<Teacher> selectTeacher(@Param("value") String value,@Param("schoolId") Long schoolId);

    public List<StudentView> selectStudent(@Param("value") String value,@Param("classId") Long classId);

    public List<ParentView> selectParent(@Param("value")String value);
}
