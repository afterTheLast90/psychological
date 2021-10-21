package team.cats.psychological.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Publish;
import team.cats.psychological.service.PublishService;
import team.cats.psychological.vo.PublishView;
import team.cats.psychological.vo.UsersAndArea;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Api(value = "发布记录操作相关",tags = "发布记录操作相关")
public class PublishController {

    @Resource
    private PublishService publishService;


    @GetMapping(value = {"/publish"})
    @ApiOperation("获取所有发布记录")
    @ResponseBody
    public R<PageResult<PublishView>> getUsers(BasePageParam basePageParam) {
        return R.successNoShow(publishService.getPublish(basePageParam));
    }

    @PostMapping("delPublish")
    @ApiOperation("删除发布")
    @ResponseBody
    public R delPublish(@RequestParam("publishId")Long publishId){
        publishService.delPublish(publishId);
        return R.success();
    }

    @PostMapping("/export")
    @ApiOperation("导出记录")
    public void export(@RequestParam("publishId")Long publishId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(publishId);
        publishService.export(publishId,request,response);
//        return R.success().setMsg("导出成功");
    }
}
