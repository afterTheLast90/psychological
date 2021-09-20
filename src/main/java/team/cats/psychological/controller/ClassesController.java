package team.cats.psychological.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.param.ClassesParams;
import team.cats.psychological.service.ClassesService;
import team.cats.psychological.vo.ClassesView;
import team.cats.psychological.vo.UsersAndArea;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "班级操作相关",tags = "班级操作相关")
public class ClassesController {

    @Resource
    private ClassesService classesService;

    @GetMapping("/selectClasses")
    @ApiOperation("获取班级信息")
    public R<PageResult<ClassesView>> selectClasses(BasePageParam basePageParam, @RequestParam(value = "teacherId", required = false, defaultValue = "") Long teacherId,
                                                    @RequestParam(value = "schoolId", required = false, defaultValue = "") Long schoolId,
                                                    @RequestParam(value = "value", required = false, defaultValue = "") String value){
        return R.successNoShow(classesService.selectClasses(basePageParam,teacherId,schoolId,value));
    }

    @PostMapping("/insertClasses")
    @ApiOperation("添加班级")
    public R InsertClasses(@Validated @RequestBody ClassesParams classesParams){
        classesService.insertClasses(classesParams);
        return R.success();
    }

    @PostMapping("/modifyClasses")
    @ApiOperation("修改班级")
    public R ModifyClasses(@Validated @RequestBody ClassesParams classesParams){
        classesService.modifyClasses(classesParams);
        return R.success();
    }

    @PostMapping("/delClasses")
    @ApiOperation("删除班级")
    public R delClasses(@RequestParam("classId") Long classId){
        if (classId==null){
            throw new BaseException(400,"classId不能为空");
        }
        classesService.delClasses(classId);
        return R.success();
    }

}
