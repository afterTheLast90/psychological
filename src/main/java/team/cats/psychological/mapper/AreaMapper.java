package team.cats.psychological.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.cats.psychological.entity.Area;
import team.cats.psychological.vo.AreaAndUser;
import team.cats.psychological.vo.City;
import team.cats.psychological.vo.Province;
import team.cats.psychological.vo.UsersAndArea;

import java.util.List;

public interface AreaMapper extends BaseMapper<Area> {

    @Select("select * from area where area_principal = #{userId} ")
    public List<Area> selectByUserId(@Param("userId") Long userId);

    @Select("select distinct city_id,city_name from area")
    public List<City> selectCity();

    @Select("select distinct province_id,province_name from area")
    public List<Province> selectProvince();

    public List<AreaAndUser> selectAreaUsers(@Param("value") String value,@Param("cityId") Long cityId,@Param("provinceId") Long provinceId);
}
