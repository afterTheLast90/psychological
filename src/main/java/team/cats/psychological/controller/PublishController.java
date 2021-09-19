package team.cats.psychological.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Publish;
import team.cats.psychological.service.PublishService;
import team.cats.psychological.vo.PublishView;
import team.cats.psychological.vo.UsersAndArea;

import javax.annotation.Resource;

@RestController
@Api(value = "发布记录操作相关",tags = "发布记录操作相关")
public class PublishController {

    @Resource
    private PublishService publishService;


    @GetMapping(value = {"/publish"})
    @ApiOperation("获取所有发布记录")
    public R<PageResult<PublishView>> getUsers(BasePageParam basePageParam, @RequestParam(value = "userId") Long userId) {
        return R.successNoShow(publishService.getPublish(basePageParam,userId));
    }

    @PostMapping("delPublish")
    @ApiOperation("删除发布")
    public R delPublish(@RequestParam("publishId")Long publishId){
        publishService.delPublish(publishId);
        return R.success();
    }
}
