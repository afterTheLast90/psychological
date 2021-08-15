package team.cats.psychological.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.Nullable;
import team.cats.psychological.entity.Users;
import team.cats.psychological.vo.UsersAndArea;

import java.util.List;

public interface UsersMapper extends BaseMapper<Users> {


    //userId,userName,userAccount,userPhoneNumber,areaId

    public List<UsersAndArea> selectUserArea(@Param("value") String value,@Param("areaId") Long areaId);

    @Update("update users set delete_flag=1 where user_id=#{userId}")
    public int updateById(@Param("userId") String userId);
}
