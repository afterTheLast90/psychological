package team.cats.psychological.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Area;
import team.cats.psychological.mapper.AreaMapper;
import team.cats.psychological.mapper.UsersMapper;
import team.cats.psychological.service.UsersService;
import team.cats.psychological.vo.UsersAndArea;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@Api(value = "人员操作相关",tags = "人员操作相关")
public class UsersController {


    @Autowired
    UsersService usersService;

    /**
     * 支持模糊查询按userId,userName,userAccount,userPhoneNumber,areaId
     * @return
     */
    @GetMapping(value = {"/psychological"})
    @ApiOperation("获取管理员信息")
    public  R<PageResult<UsersAndArea>> getUsers(BasePageParam basePageParam, @RequestParam(value = "areaId",required = false,defaultValue = "")Long areaId,
                                                 @RequestParam(value = "value",required = false,defaultValue = "")String value){
        return R.success(usersService.getUserList(basePageParam,areaId,value));
    }

    /**
     * 禁用管理员
     * @param userId
     * @return
     */
    @ApiOperation("修改管理员状态")
    @PostMapping("/changeUserState/{userId}")
    public R disabled(@PathVariable("userId") Long userId, @RequestParam(value = "state") Boolean state){
       usersService.changeUserState(userId,state);
       return R.success();
    }
}
