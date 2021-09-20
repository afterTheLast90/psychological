package team.cats.psychological.controller;


import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.TeacherSchool;
import team.cats.psychological.entity.Users;
import team.cats.psychological.param.ClassesParams;
import team.cats.psychological.param.UserParams;
import team.cats.psychological.service.TeacherSchoolService;
import team.cats.psychological.service.UsersService;
import team.cats.psychological.vo.StudentView;
import team.cats.psychological.vo.Teacher;

import javax.annotation.Resource;
import java.util.List;

@RestController//失败回滚
@Api(value = "教师操作相关", tags = "教师操作相关")
public class TeacherSchoolController {

    @Resource
    private UsersService usersService;
    @Resource
    private TeacherSchoolService teacherSchoolService;

    @PostMapping("/addTeacher")
    @ApiOperation("添加教师")
    public R addTeacher(@Validated @RequestBody UserParams userParams,@RequestParam("schoolId") Long schoolId){
        if (schoolId==null){
            throw new BaseException(400,"学校不能为空");
        }
        Long userId = usersService.InsertUser(userParams, 4L);
        teacherSchoolService.insertTeacher(userId,schoolId);
        return R.success();
    }

    @GetMapping("/getTeacherArray")
    @ApiOperation("获取所有教师")
    public R<List<Users>> getTeacherArray(){
        return R.successNoShow(usersService.getTeacherArray());
    }



    @GetMapping("/getTeacherList")
    @ApiOperation("获取教师信息")
    public  R<PageResult<Teacher>> selectTeacher(BasePageParam basePageParam, @RequestParam(value = "schoolId", required = false, defaultValue = "") Long schoolId,
                                                 @RequestParam(value = "value", required = false, defaultValue = "") String value){
        return R.successNoShow(usersService.getTeacherList(basePageParam,schoolId,value));
    }

    @PostMapping("/modifyTeacher")
    @ApiOperation("修改教师信息")
    public R modifyTeacher(@Validated @RequestBody UserParams userParams,@RequestParam("schoolId") Long schoolId){
        if(schoolId==null){
            throw new BaseException(400,"学校不能为空");
        }
        usersService.modifyAdministrator(userParams);
        teacherSchoolService.modifyTeacher(userParams.getUserId(),schoolId);
        return R.success();
    }
}
