package team.cats.psychological.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.entity.*;
import team.cats.psychological.mapper.*;
import team.cats.psychological.vo.PublishView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublishService {
    @Resource
    private PublishMapper publishMapper;
    @Resource
    private AreaMapper areaMapper;
    @Resource
    private SchoolMapper schoolMapper;
    @Resource
    private ClassesMapper classesMapper;
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private QuestionnaireMapper questionnaireMapper;
    @Resource
    private TeacherSchoolMapper teacherSchoolMapper;
    @Resource
    private UserQuestionnaireMapper userQuestionnaireMapper;

    public PageResult<PublishView> getPublish(BasePageParam basePageParam) {
        PageHelper.startPage(basePageParam.getPageNum(), basePageParam.getPageSize());
        List<PublishView> publishViews = new ArrayList<>();
        List<Publish> publishes = new ArrayList<>();
        long userId = StpUtil.getLoginIdAsLong();
        Users users = usersMapper.selectById(userId);
        Long userRole = users.getUserRole();
        QueryWrapper<Publish> queryWrapper = new QueryWrapper<>();
        if (userRole == 0) {
            queryWrapper.eq("delete_flag", 0);
            publishes = publishMapper.selectList(queryWrapper);
        } else if (userRole == 1) {
            QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
            areaQueryWrapper.eq("area_principal", userId);
            List<Area> areas = areaMapper.selectList(areaQueryWrapper);
            for (Area area : areas) {
                QueryWrapper<School> schoolQueryWrapper = new QueryWrapper<>();
                schoolQueryWrapper.eq("area_id", area.getAreaId());
                List<School> schools = schoolMapper.selectList(schoolQueryWrapper);
                for (School school : schools) {
                    QueryWrapper<TeacherSchool> teacherSchoolQueryWrapper = new QueryWrapper<>();
                    teacherSchoolQueryWrapper.eq("school_id", school.getSchoolId());
                    List<TeacherSchool> teacherSchools = teacherSchoolMapper.selectList(teacherSchoolQueryWrapper);
                    for (TeacherSchool teacherSchool : teacherSchools) {
                        queryWrapper.eq("publisher_id", userId);
                        queryWrapper.orderByDesc("release_time");
                        List<Publish> publishes1 = publishMapper.selectList(queryWrapper);
                        for (Publish publish : publishes1) {
                            publishes.add(publish);
                        }
                    }
                }
            }
        } else {
            queryWrapper.eq("publisher_id", userId);
            queryWrapper.orderByDesc("release_time");
            publishes = publishMapper.selectList(queryWrapper);
        }

        publishes = publishes.stream().distinct().collect(Collectors.toList());
        for (Publish publish : publishes) {
            if (publish.getDeadline().compareTo(LocalDateTime.now()) < 0) {
                publish.setState(1);
                publishMapper.updateById(publish);
            }
            PublishView publishView = new PublishView();
            publishView.setPublish(publish);
            Long strangeId = publish.getStrangeId();
            Integer publishType = publish.getPublishType();
            if (publishType == 0) {
                publishView.setStrangeName(areaMapper.selectById(strangeId).getAreaName());
            } else if (publishType == 1) {
                publishView.setStrangeName(schoolMapper.selectById(strangeId).getSchoolName());
            } else if (publishType == 2) {
                publishView.setStrangeName(classesMapper.selectById(strangeId).getClassName());
            } else {
                publishView.setStrangeName(usersMapper.selectById(strangeId).getUserName());
            }
            publishView.setQuestionnaireName(questionnaireMapper.selectById(publish.getQuestionnaireId()).getQuestionnaireName());
            publishView.setPublisherName(usersMapper.selectById(publish.getPublisherId()).getUserName());
            publishViews.add(publishView);
        }
        return new PageResult<PublishView>(publishViews);
    }

    public void delPublish(Long publishId) {
        QueryWrapper<UserQuestionnaire> userQuestionnaireQueryWrapper = new QueryWrapper<>();
        userQuestionnaireQueryWrapper.eq("publish_id", publishId);
        userQuestionnaireMapper.delete(userQuestionnaireQueryWrapper);
        publishMapper.deleteById(publishId);
    }

    public void export(Long publishId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int column = 0;
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        String fileName = "Results_" + System.currentTimeMillis() + ".xls";
        HSSFSheet sheet = wb.createSheet(fileName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //声明列对象
        HSSFCell cell = null;
        //excel标题
        String[] title = {"用户名", "省", "市", "地区", "学校", "班级", "年级", "结果", "总分"};
        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(column);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
            column += 1;
        }
        QueryWrapper<UserQuestionnaire> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publish_id", publishId);
        queryWrapper.eq("state", 3);
        List<UserQuestionnaire> userQuestionnaires = userQuestionnaireMapper.selectList(queryWrapper);
        if (userQuestionnaires.size() == 0) {
            throw new BaseException(400, "当前没有已答完的问卷");
        }
        Questionnaire questionnaire = questionnaireMapper.selectById(userQuestionnaires.get(0).getQuestionnaire());
        for (int i = 0; i < userQuestionnaires.get(0).getAnswer().size(); i++) {
            cell = row.createCell(column);
            cell.setCellValue("第" + (i + 1) + "题");
            cell.setCellStyle(style);
            column += 1;
            cell = row.createCell(column);
            cell.setCellValue("第" + (i + 1) + "题得分");
            cell.setCellStyle(style);
            column += 1;
        }
        if (questionnaire.getCalculation().equals(0)) {
            for (int i = 0; i < userQuestionnaires.get(0).getVariables().size(); i++) {
                cell = row.createCell(column);
                cell.setCellValue("第" + (i + 1) + "个变量");
                cell.setCellStyle(style);
                column += 1;
            }
        }
        //创建内容

        for (int i = 0; i < userQuestionnaires.size(); i++) {
            row = sheet.createRow((i + 1));
            int col = 0;
            row.createCell(col++).setCellValue(usersMapper.selectById(userQuestionnaires.get(i).getUserId()).getUserName());
            row.createCell(col++).setCellValue(userQuestionnaires.get(i).getProvinceName());
            row.createCell(col++).setCellValue(userQuestionnaires.get(i).getCityName());
            row.createCell(col++).setCellValue(userQuestionnaires.get(i).getAreaName());
            row.createCell(col++).setCellValue(userQuestionnaires.get(i).getSchoolName());
            row.createCell(col++).setCellValue(userQuestionnaires.get(i).getClassName());
            row.createCell(col++).setCellValue(userQuestionnaires.get(i).getGrade());
            row.createCell(col++).setCellValue(userQuestionnaires.get(i).getResult().getName());
            row.createCell(col++).setCellValue(userQuestionnaires.get(i).getTotal());
            for (Answer answer : userQuestionnaires.get(i).getAnswer()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : answer.getAnswer()) {
                    stringBuilder.append(s);
                }
                row.createCell(col++).setCellValue(stringBuilder.toString());
                row.createCell(col++).setCellValue(answer.getScore());
            }
            if (questionnaire.getCalculation().equals(0)){
                for (Number variable : userQuestionnaires.get(i).getVariables()) {
                    row.createCell(col++).setCellValue(variable.toString());
                }
            }
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("filename", fileName);
        response.setHeader("Access-Control-Expose-Headers", "filename");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.close();
        System.out.println("创建Excel完毕");
    }
}
