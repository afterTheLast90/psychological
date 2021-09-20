package team.cats.psychological.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Users;
import team.cats.psychological.param.UserParams;
import team.cats.psychological.service.UsersService;
import team.cats.psychological.vo.UserInformationView;
import team.cats.psychological.vo.UsersAndArea;

import javax.annotation.Resource;

@RestController//失败回滚
@Api(value = "人员操作相关", tags = "人员操作相关")
public class UsersController {


    @Resource
    UsersService usersService;

    /**
     * 支持模糊查询按userId,userName,userAccount,userPhoneNumber,areaId
     *
     * @return
     */
    @GetMapping(value = {"/psychological"})
    @ApiOperation("获取管理员信息")
    public R<PageResult<UsersAndArea>> getUsers(BasePageParam basePageParam, @RequestParam(value = "areaId", required = false, defaultValue = "") Long areaId,
                                                @RequestParam(value = "value", required = false, defaultValue = "") String value) {
        return R.successNoShow(usersService.getUserList(basePageParam, areaId, value));
    }

    @PostMapping("/addUser")
    @ApiOperation("添加管理员信息")
    public R addUser(@Validated @RequestBody UserParams userParams) {
        usersService.InsertUser(userParams,1L);
        return R.success();
    }


    @PostMapping("/modifyUser")
    @ApiOperation("修改管理员信息")
    public R modifyUser(@Validated @RequestBody UserParams userParams) {
        usersService.modifyAdministrator(userParams);
        return R.success();
    }

    /**
     * 禁用管理员
     *
     * @param userId
     * @return
     */
    @ApiOperation("修改管理状态")
    @PostMapping("/changeUserState/{userId}")
    public R disabled(@PathVariable("userId") Long userId, @RequestParam(value = "state") Boolean state) {
        if (userId==null){
            throw new BaseException(400,"用户不能为空");
        }
        if (state==null){
            throw new BaseException(400,"状态不能为空");
        }
        usersService.changeUserState(userId, state);
        return R.success();
    }
    /**
     * 删除管理员
     *
     * @param userId
     * @return
     */
    @ApiOperation("删除管理员")
    @PostMapping("/deleteUser/{userId}")
    public R disabled(@PathVariable("userId") Long userId) {
        usersService.deleteUser(userId);
        return R.success();
    }

    @GetMapping("/getUser")
    @ApiOperation("获取用户信息")
    public R<UserInformationView> getUser(){
        UserInformationView user = usersService.getUser();
        return R.successNoShow(user);
    }
}
