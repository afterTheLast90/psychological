package team.cats.psychological.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.cats.psychological.entity.Area;
import team.cats.psychological.entity.School;
import team.cats.psychological.vo.SchoolView;

import java.util.List;

public interface SchoolMapper extends BaseMapper<School> {

    @Select("select * from school where area_id = #{areaId} and delete_flag = 0")
    public List<School> selectByAreaId(@Param("areaId") Long areaId);

    @Select("select * from school where delete_flag = false")
    public List<SchoolView> getSchoolList();

}
