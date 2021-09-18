package team.cats.psychological.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Questionnaire;
import team.cats.psychological.entity.QuestionnaireDetails;
import team.cats.psychological.param.QuestionnaireDetailsParams;
import team.cats.psychological.param.QuestionnaireParams;
import team.cats.psychological.service.QuestionnaireDetailsService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "问卷详情相关",tags = "问卷详情相关")
public class QuestionnaireDetailsController {

    @Resource
    private QuestionnaireDetailsService questionnaireDetailsService;

    @GetMapping("/questions")
    @ApiOperation("获取问卷详情")
    public R<List<QuestionnaireDetails>> getById(@RequestParam("id") Long id){
        return R.successNoShow(questionnaireDetailsService.getDetails(id));
    }

    @PostMapping("/modifyDetails")
    @ApiOperation("问卷详情编辑")
    public R modifyDetails(@Valid @RequestBody List<QuestionnaireDetailsParams> questionnaireParamsList){
        questionnaireDetailsService.modifyDetails(questionnaireParamsList);
        return R.success();
    }

    @GetMapping("/getUserQuestions")
    @ApiOperation("获取用户问卷详情")
    public R<List<QuestionnaireDetails>> getUserQuestionnaireDetails(@RequestParam("questionnaireId") Long questionnaireId
                                                                       , @RequestParam("userId") Long userId){
        List<QuestionnaireDetails> userDetails = questionnaireDetailsService.getUserDetails(questionnaireId, userId);
        return R.successNoShow(userDetails);
    }
}
