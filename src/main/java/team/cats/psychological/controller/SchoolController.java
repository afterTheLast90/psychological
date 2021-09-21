package team.cats.psychological.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.School;
import team.cats.psychological.service.SchoolService;
import team.cats.psychological.vo.ListItemVo;
import team.cats.psychological.vo.SchoolView;

import javax.annotation.Resource;
import java.util.List;

@RestController//失败回滚
@Api(value = "学校操作相关", tags = "学校操作相关")
public class SchoolController {

    @Resource
    private SchoolService schoolService;

    @PostMapping("/addSchool")
    @ApiOperation("添加学校")
    public R addSchool(@RequestParam("schoolName") String schoolName, @RequestParam("areaId") Long areaId){
        System.out.println(schoolName+areaId);
        schoolService.insertSchool(schoolName,areaId);
        return R.success();
    }

    @PostMapping("/delSchool")
    @ApiOperation("删除学校")
    public R delSchool(@RequestParam("schoolId") Long schoolId){
        schoolService.delSchool(schoolId);
        return R.success();
    }

    @GetMapping("/selectSchool")
    @ApiOperation("查询学校")
    public R<List<School>> selectSchool(){
        return R.successNoShow(schoolService.selectSchool()) ;
    }

    @GetMapping("/getSchoolList")
    @ApiOperation("获取学校列表")
    public R<List<ListItemVo>> getSchoolList(){
        return R.successNoShow(schoolService.getSchoolList());
    }
    @GetMapping("/getSchoolListNoLogin")
    @ApiOperation("不登陆获取学校列表")
    public R<List<ListItemVo>> getSchoolListNoLogin(){
        return R.successNoShow(schoolService.getSchoolListNoLogin());
    }
}
