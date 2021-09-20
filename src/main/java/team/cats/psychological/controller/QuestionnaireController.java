package team.cats.psychological.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Questionnaire;
import team.cats.psychological.entity.UserQuestionnaire;
import team.cats.psychological.mapper.QuestionnaireMapper;
import team.cats.psychological.param.QuestionnaireParams;
import team.cats.psychological.service.QuestionnaireService;
import team.cats.psychological.service.UserQuestionnaireService;
import team.cats.psychological.vo.QuestionnaireIdAndStudentIdView;
import team.cats.psychological.vo.QuestionnaireView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "问卷操作相关",tags = "问卷操作相关")
public class QuestionnaireController {

    @Resource
    private QuestionnaireService questionnaireService;

    @Resource
    private UserQuestionnaireService userQuestionnaireService;

    @GetMapping("/selectQuestionnaire")
    @ApiOperation("获取问卷信息")
    public R<PageResult<QuestionnaireView>> selectQuestionnaire(BasePageParam basePageParam, @RequestParam(value = "value",required = false,defaultValue = "")String value){

        return R.successNoShow(questionnaireService.selectQuestionnaire(basePageParam,value));
    }

    @GetMapping("/question")
    @ApiOperation("获取问卷详情")
    public R<Questionnaire> getById(@RequestParam("id") String id){
        return R.successNoShow(questionnaireService.selectById(id));
    }

    @PostMapping("/insertQuestionnaire")
    @ApiOperation("新增问卷")
    public R insertQuestionnaire(@RequestParam("name") String name,@RequestParam("introduction") String introduction,@RequestParam("creator") Long creator){
        questionnaireService.InsertQuestionnaire(name,introduction,creator);
        return R.success();
    }

    @PostMapping("/delQuestionnaire")
    @ApiOperation("删除问卷")
    public R delQuestionnaire(@RequestParam("id") Long id){
        questionnaireService.DelQuestionnaire(id);
        return R.success();
    }
    @PostMapping("modifyQuestionnaire")
    @ApiOperation("编辑问卷")
    public R modifyQuestionnaire(@Valid @RequestBody QuestionnaireParams questionnaireParams){
        System.out.println(questionnaireParams);
        questionnaireService.ModifyQuestionnaire(questionnaireParams);
        return R.success();
    }

    @GetMapping("getUserQuestionnaire")
    @ApiOperation("获取用户问卷")
    public R<List<QuestionnaireView>> getUserQuestionnaire(){
        List<QuestionnaireIdAndStudentIdView> questionnaireIds = userQuestionnaireService.selectByUserId();
        List<QuestionnaireView> userQuestionnaire = questionnaireService.getUserQuestionnaire(questionnaireIds);
        return R.successNoShow(userQuestionnaire);
    }

    @PostMapping("/confirm")
    @ApiOperation("确认问卷")
    public R confirm(@RequestParam("questionnaireId")Long questionnaireId){
        questionnaireService.confirm(questionnaireId);
        return R.success();
    }
}
