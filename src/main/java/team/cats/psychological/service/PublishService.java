package team.cats.psychological.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.entity.*;
import team.cats.psychological.mapper.*;
import team.cats.psychological.vo.PublishView;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
            if (publish.getDeadline().compareTo(LocalDateTime.now())<0){
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
        userQuestionnaireQueryWrapper.eq("publish_id",publishId);
        userQuestionnaireMapper.delete(userQuestionnaireQueryWrapper);
        publishMapper.deleteById(publishId);
    }
}
