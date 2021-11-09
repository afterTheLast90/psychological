package team.cats.psychological.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.param.UserParams;
import team.cats.psychological.service.StudentsClassService;
import team.cats.psychological.service.StudentsParentService;
import team.cats.psychological.service.UsersService;
import team.cats.psychological.vo.AllStudentsView;
import team.cats.psychological.vo.StudentView;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "学生操作相关",tags = "学生操作相关")
public class StudentsController {

    @Resource
    private UsersService usersService;

    @Resource
    private StudentsClassService studentsClassService;
    @Resource
    private StudentsParentService studentsParentService;

    @GetMapping("/getStudentList")
    @ApiOperation("获取学生信息")
    public  R<PageResult<StudentView>> selectClasses(BasePageParam basePageParam, @RequestParam(value = "classId", required = false, defaultValue = "") Long classId,
                                                     @RequestParam(value = "value", required = false, defaultValue = "") String value){
        return R.successNoShow(usersService.getClassesList(basePageParam,classId,value));
    }

    @PostMapping("/addStudent")
    @ApiOperation("添加学生")
    public R addTeacher(@Validated @RequestBody UserParams userParams, @RequestParam("classId") Long classId,@RequestParam("parentId")Long parentId){
        if(classId==null){
            throw new BaseException(400,"class不能为空");
        }
        if(parentId==null){
            throw new BaseException(400,"parent不能为空");
        }
        Long userId = usersService.InsertUser(userParams, 2L);
        studentsClassService.insertStudentClass(userId,classId);
        studentsParentService.insertParent(userId,parentId);
        return R.success();
    }

    @PostMapping("/addClass")
    @ApiOperation("加入班级")
    public R addClass(@RequestParam("classId") Long classId,@RequestParam("studentId")Long studentId){
        if (classId==null){
            throw new BaseException(400,"class不能为空");
        }
        if (studentId==null){
            throw new BaseException(400,"student不能为空");
        }
        studentsClassService.insertStudentClass(studentId,classId);
        return R.success();
    }

    @PostMapping("/deleteClass")
    @ApiOperation("退出班级")
    public R delClass(@RequestParam("classId") Long classId,@RequestParam("studentId")Long studentId){
        if (classId==null){
            throw new BaseException(400,"class不能为空");
        }
        if (studentId==null){
            throw new BaseException(400,"student不能为空");
        }
        studentsClassService.delStudentClass(studentId,classId);
        return R.success();
    }

    @PostMapping("/modifyStudent")
    @ApiOperation("修改学生信息")
    public R modifyTeacher(@Validated @RequestBody UserParams userParams){
        userParams.setUserPhoneNumber("");
        usersService.modifyAdministrator(userParams);
        return R.success();
    }

    @GetMapping("getStudents")
    @ApiOperation("获取所有学生")
    public R<List<AllStudentsView>> getStudents(@RequestParam("classId") Long  classId){
        List<AllStudentsView> students = studentsClassService.getStudents(classId);
        return R.success(students);
    }
}
