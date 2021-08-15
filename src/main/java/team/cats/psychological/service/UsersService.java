package team.cats.psychological.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.entity.Users;
import team.cats.psychological.mapper.AreaMapper;
import team.cats.psychological.mapper.UsersMapper;
import team.cats.psychological.vo.UsersAndArea;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UsersService {
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private AreaMapper areaMapper;

    public PageResult<UsersAndArea> getUserList(BasePageParam basePageParam, Long areaId, String value){
        PageHelper.startPage(basePageParam.getPageNum(),basePageParam.getPageSize());
        List<UsersAndArea> usersAndAreas = usersMapper.selectUserArea(value, areaId);

        for (UsersAndArea usersAndArea : usersAndAreas) {
            usersAndArea.setAreas(areaMapper.selectByUserId(usersAndArea.getUserId()));
        }

        return new PageResult<UsersAndArea>(usersAndAreas);
    }

    public void changeUserState(Long userId,Boolean state){
        Users users = getUserNotNull(userId);
        users.setState(state?0:1);
        usersMapper.updateById(users);
    }

    public Users getUserNotNull(Long userId){
        Users users = usersMapper.selectById(userId);
        if (users == null) {
            throw  new BaseException(451,"用户不存在");
        }
        return users;
    }
}
