package team.cats.psychological.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.cats.psychological.base.R;

import javax.annotation.Resource;

@RestController
@Api(value = "测试操作",tags = "测试操作")
public class TestController {


    @GetMapping("/testLogin")
    @ApiOperation("登录测试")
    public R Login(@RequestParam("userId")Long userId){
        StpUtil.login(userId);
        System.out.println(StpUtil.getTokenValueByLoginId(userId));
        System.out.println(StpUtil.getTokenName());
        System.out.println(StpUtil.getTokenValue());
        StpUtil.checkLogin();
        return R.success(StpUtil.getTokenValue());
    }

}
