package team.cats.psychological.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Users;
import team.cats.psychological.param.*;
import team.cats.psychological.service.UsersService;
import team.cats.psychological.utils.ExcelUtils;
import team.cats.psychological.vo.LoginVo;
import team.cats.psychological.vo.UserInformationView;
import team.cats.psychological.vo.UsersAndArea;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController//失败回滚
@Api(value = "人员操作相关", tags = "人员操作相关")
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class UsersController {


    @Resource
    UsersService usersService;

    @PostMapping("/loginForAdmin")
    public R loginForAdmin(@RequestBody @Validated LoginParams loginParams) {
        Users userByPhone = usersService.getUserByPhoneOrAccount(loginParams.getAccount());
        if (userByPhone == null) {
            throw new BaseException("用户不存在");
        }
        if (userByPhone.getUserRole().equals(2L) || userByPhone.getUserRole().equals(3L)) {
            throw new BaseException("用户身份错误");
        }
        if (userByPhone.getUserPassword().equals(loginParams.getPassword())) {
            StpUtil.login(userByPhone.getUserId());
            return R.success().setMsg("登录成功").setData(new LoginVo().setToken(StpUtil.getTokenInfo().tokenValue).setRole(userByPhone.getUserRole()));
        }
        throw new BaseException("账号与密码不匹配");
    }

    @PostMapping("/login")
    public R login(@RequestBody @Validated LoginParams loginParams) {
        Users userByPhone = usersService.getUserByPhoneOrAccount(loginParams.getAccount());
        if (userByPhone == null) {
            throw new BaseException("用户不存在");
        }
        if (userByPhone.getUserRole().equals(1L) || userByPhone.getUserRole().equals(0L)) {
            throw new BaseException("用户身份错误");
        }
        if (userByPhone.getUserPassword().equals(loginParams.getPassword())) {
            StpUtil.login(userByPhone.getUserId());
            return R.success().setMsg("登录成功").setData(new LoginVo().setToken(StpUtil.getTokenInfo().tokenValue).setRole(userByPhone.getUserRole()));
        }
        throw new BaseException("账号与密码不匹配");
    }

    @PostMapping("/register")
    public R register(@RequestBody @Validated RegisterParams registerParams) {
        usersService.register(registerParams);
        return R.success().setMsg("注册成功");
    }

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
        usersService.InsertUser(userParams, 1L);
        return R.success();
    }


    @PostMapping("/modifyUser")
    @ApiOperation("修改管理员信息")
    public R modifyUser(@Validated @RequestBody UserParams userParams) {
        usersService.modifyAdministrator(userParams);
        return R.success();
    }

    @PostMapping("/importStudent")
    @ApiModelProperty("导入学生")
    @Transactional
    public R importStudent(@RequestBody ImportStudent importStudent) throws IOException {
//        ExcelReader reader = ExcelUtil.getReader(importStudent.getFile().getInputStream(), 0);
        return R.successNoShow(usersService.importStudent(importStudent.getClassId(),
                importStudent.getData()));

//        return R.success( usersService.importStudent(importStudent.getClassId(),reader.read()));
    }

    @PostMapping("/downloadImportFailedStudentInfo")
    public void downloadImportFailedStudentInfo(@RequestBody FailedInfo failedInfo,
                                                HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Expose-Headers", "filename");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=failedInfo.xlsx");
        response.setHeader("filename","failedInfo.xlsx");
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.writeRow(Arrays.asList("姓名", "性别", "账号", "出生日期", "家长姓名", "家长手机号", "错误信息"));
        for (HashMap<String, Object> stringObjectHashMap : failedInfo.getList()) {
            String[] s = new String[7];
            s[0] = stringObjectHashMap.getOrDefault("userName", "").toString();
            s[1] = stringObjectHashMap.getOrDefault("userGender", "").toString();
            s[2] = stringObjectHashMap.getOrDefault("account", "").toString();
            s[3] = stringObjectHashMap.getOrDefault("userBirthday", "").toString();
            s[4] = stringObjectHashMap.getOrDefault("parentName", "").toString();
            s[5] = stringObjectHashMap.getOrDefault("parentPhone", "").toString();
            s[6] = stringObjectHashMap.getOrDefault("msg", "").toString();
            writer.writeRow(Arrays.asList(s));
        }

        writer.flush(response.getOutputStream());

        writer.close();


    }

    /**
     * 禁用管理员
     *
     * @param userId
     *
     * @return
     */
    @ApiOperation("修改管理状态")
    @PostMapping("/changeUserState/{userId}")
    public R disabled(@PathVariable("userId") Long userId, @RequestParam(value = "state") Boolean state) {
        if (userId == null) {
            throw new BaseException(400, "用户不能为空");
        }
        if (state == null) {
            throw new BaseException(400, "状态不能为空");
        }
        usersService.changeUserState(userId, state);
        return R.success();
    }

    /**
     * 删除管理员
     *
     * @param userId
     *
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
    public R<UserInformationView> getUser() {
        UserInformationView user = usersService.getUser();
        return R.successNoShow(user);
    }
}
