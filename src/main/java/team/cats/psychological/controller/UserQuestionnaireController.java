package team.cats.psychological.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Questionnaire;
import team.cats.psychological.entity.UserQuestionnaire;
import team.cats.psychological.param.ReleaseParams;
import team.cats.psychological.service.UserQuestionnaireService;
import team.cats.psychological.vo.AnswerQuestionnaireView;
import team.cats.psychological.vo.NewAnswerQuestionnaireView;
import team.cats.psychological.vo.QuestionnaireResultView;
import team.cats.psychological.vo.UsersAndArea;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController//失败回滚
@Api(value = "人员问卷操作相关", tags = "人员问卷操作相关")
public class UserQuestionnaireController {

    @Resource
    private UserQuestionnaireService userQuestionnaireService;

    @PostMapping(value = {"/release"})
    @ApiOperation("发布问卷")
    public R releaseQuestionnaire(@Valid @RequestBody ReleaseParams releaseParams) {
        System.out.println(releaseParams);
        userQuestionnaireService.releaseQuestionnaire(releaseParams.getUserId(), releaseParams.getQuestionnaireId(),
                releaseParams.getId(), releaseParams.getPublishType(), releaseParams.getReleaseTime(), releaseParams.getDeadLine());
        return R.success();
    }

    @PostMapping("/answerQuestionnaire")
    @ApiOperation("回答问卷")
    public R answerQuestionnaire(@Valid @RequestBody AnswerQuestionnaireView answerList) {
        userQuestionnaireService.answerQuestionnaire(answerList);
        return R.success();
    }

    @PostMapping("/answerNewQuestionnaire")
    @ApiOperation("回答新问卷")
    public R answerNewQuestionnaire(@Valid @RequestBody NewAnswerQuestionnaireView answerQuestionnaireView){
        userQuestionnaireService.answerNewQuestionnaire(answerQuestionnaireView);
        return R.success();
    }

    @GetMapping("/getUserQuestionnaires")
    @ApiOperation("获取用户问卷关系")
    public R<UserQuestionnaire> getUserQuestionnaires(@RequestParam("userId") Long userId,
                                                      @RequestParam("questionnaireId") Long questionnaireId) {
        UserQuestionnaire userQuestionnaire = userQuestionnaireService.getUserQuestionnaire(userId, questionnaireId);
        return R.successNoShow(userQuestionnaire);
    }

    @GetMapping("/getResult")
    @ApiOperation("获取问卷结果")
    public R<PageResult<QuestionnaireResultView>> getQuestionnaireResult(BasePageParam basePageParam, @RequestParam("userId") Long userId){
        return R.successNoShow(userQuestionnaireService.getQuestionnaireResult(basePageParam,userId));
    }

    @GetMapping("getLastResult")
    @ApiOperation("获取单个结果")
    public R<UserQuestionnaire> getLastResult(@RequestParam("userQuestionnaireId")Long userQuestionnaireId){

        return R.success(userQuestionnaireService.getLastResult(userQuestionnaireId));
    }
}
