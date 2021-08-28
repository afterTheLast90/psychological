package team.cats.psychological.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Area;
import team.cats.psychological.param.AreaParams;
import team.cats.psychological.param.UserParams;
import team.cats.psychological.service.AreaService;
import team.cats.psychological.vo.AreaAndUser;
import team.cats.psychological.vo.City;
import team.cats.psychological.vo.Province;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "地区操作相关",tags = "地区操作相关")
public class AreaController {

    @Resource
    private AreaService areaService;


    @ApiOperation("获取地区信息列表")
    @GetMapping("/area")
    public R<List<Area>> gerAreaByPrincipal(){
        return R.successNoShow (areaService.getArea());
    }

    @ApiOperation("获取地区信息")
    @GetMapping("/areas")
    public R<PageResult<AreaAndUser>> gerAreaList(BasePageParam basePageParam, @RequestParam(value = "value",required = false)String value
            , @RequestParam(value = "cityId",required = false) Long cityId, @RequestParam(value = "provinceId",required = false)Long provinceId){

        return R.successNoShow (areaService.getAreaUser(basePageParam,value,cityId,provinceId));
    }

    @ApiOperation("获取市信息列表")
    @GetMapping("/city")
    public R<List<City>> gerCity(){
        return R.successNoShow (areaService.getCity());
    }

    @ApiOperation("获取省信息列表")
    @GetMapping("/province")
    public R<List<Province>> gerProvince(){
        return R.successNoShow (areaService.getProvince());
    }

    @ApiOperation("删除地区")
    @PostMapping("/deleteArea/{areaId}")
    public R disabled(@PathVariable("areaId") Long areaId) {
        areaService.deleteArea(areaId);
        return R.success();
    }

    @PostMapping("/addArea")
    @ApiOperation("添加地区")
    public R addUser(@Validated @RequestBody AreaParams areaParams) {
        areaService.insertArea(areaParams);
        return R.success();
    }

    @PostMapping("/editPrincipal")
    @ApiOperation(("修改负责人"))
    public R editPrincipal(@RequestParam("areaId") Long areaId,@RequestParam("areaPrincipal") Long areaPrincipal){
        areaService.editPrincipal(areaId,areaPrincipal);
        return R.success();
    }
}
