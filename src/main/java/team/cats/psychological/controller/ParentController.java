package team.cats.psychological.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.R;
import team.cats.psychological.param.UserParams;
import team.cats.psychological.service.StudentsParentService;
import team.cats.psychological.service.UsersService;
import team.cats.psychological.vo.AParentView;
import team.cats.psychological.vo.ParentView;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "家长操作相关",tags = "家长操作相关")
public class ParentController {

    @Resource
    private StudentsParentService studentsParentService;

    @Resource
    private UsersService usersService;

    @PostMapping("/addParent")
    @ApiOperation("添加家长")
    public R addTeacher(@Validated @RequestBody UserParams userParams){
        usersService.InsertUser(userParams, 3L);
//        studentsParentService.insertParent(studentId,userId);
        return R.success();
    }

    @PostMapping("/delParent")
    @ApiOperation("移除家长")
    public R delParent(@RequestParam("studentId") Long studentId,@RequestParam("parentId")Long parentId){
        studentsParentService.delStudentParent(studentId,parentId);
        return R.success();
    }

    @GetMapping("getParent")
    @ApiOperation("查找家长")
    public R<List<AParentView>> getParent(){
        return R.successNoShow(studentsParentService.getParent());
    }

    @PostMapping("/modifyParent")
    @ApiOperation("更新家长")
    public R modifyParent(@RequestParam("studentId") Long studentId,@RequestParam("parentId") Long parentId){
        studentsParentService.modifyParent(studentId,parentId);
        return R.success();
    }
}
