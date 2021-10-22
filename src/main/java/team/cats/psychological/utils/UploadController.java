package team.cats.psychological.utils;

import cn.dev33.satoken.stp.StpUtil;
import java.net.URLEncoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.R;
import java.io.*;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/22 9:26
 * @description 上传文件
 */

@RestController
@CrossOrigin
@RequestMapping("/upload")
@Api(tags = "上传信息接口")
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 上传用户头像
     * @param file 文件
     */
    @PostMapping("/avatar")
    @ApiOperation("上传用户头像")
    public R avatarUpload(@ApiParam("上传的文件") @RequestParam("file") MultipartFile file) {
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String realPathDir = System.getProperty("user.dir");
        String path ="/upload/"+fileName;
        try {
            File destFile = new File(realPathDir+path);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
                return R.success(path).setMsg("上传成功");
        } catch (IOException e) {
            logger.error("上传图片失败->message:{}",e.getMessage());
            throw new BaseException("上传失败");
        }
    }
}
