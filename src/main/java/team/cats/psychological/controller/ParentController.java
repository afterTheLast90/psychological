package team.cats.psychological.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.cats.psychological.base.R;
import team.cats.psychological.param.UserParams;
import team.cats.psychological.service.StudentsParentService;
import team.cats.psychological.service.UsersService;

import javax.annotation.Resource;

@RestController
@Api(value = "家长操作相关",tags = "家长操作相关")
public class ParentController {

    @Resource
    private StudentsParentService studentsParentService;

    @Resource
    private UsersService usersService;

    @PostMapping("/addParent")
    @ApiOperation("添加家长")
    public R addTeacher(@Validated @RequestBody UserParams userParams, @RequestParam("studentId") Long studentId){
        Long userId = usersService.InsertUser(userParams, 3L);
        studentsParentService.insertParent(studentId,userId);
        return R.success();
    }

    @PostMapping("/delParent")
    @ApiOperation("移除家长")
    public R delParent(@RequestParam("studentId") Long studentId,@RequestParam("parentId")Long parentId){
        studentsParentService.delStudentParent(studentId,parentId);
        return R.success();
    }
}
