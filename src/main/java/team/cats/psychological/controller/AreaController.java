package team.cats.psychological.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Area;
import team.cats.psychological.service.AreaService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "地区操作相关",tags = "地区操作相关")
public class AreaController {

    @Resource
    private AreaService areaService;


    @ApiOperation("获取地区信息")
    @GetMapping("/area")
    public R<List<Area>> gerArea(){
        return R.success(areaService.getAreaList());
    }
}
