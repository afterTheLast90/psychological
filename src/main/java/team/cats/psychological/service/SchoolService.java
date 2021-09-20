package team.cats.psychological.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cats.psychological.entity.Area;
import team.cats.psychological.entity.School;
import team.cats.psychological.entity.TeacherSchool;
import team.cats.psychological.entity.Users;
import team.cats.psychological.mapper.*;
import team.cats.psychological.vo.ListItemVo;
import team.cats.psychological.vo.SchoolView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    @Resource
    private SchoolMapper schoolMapper;
    @Resource
    private ClassesMapper classesMapper;
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private AreaMapper areaMapper;
    @Resource
    private TeacherSchoolMapper teacherSchoolMapper;

    public void insertSchool(String name, Long areaId) {
        School school = new School();
        school.setSchoolId(YitIdHelper.nextId());
        school.setSchoolName(name);
        school.setAreaId(areaId);
        schoolMapper.insert(school);
    }

    public void delSchool(Long schoolId) {
        schoolMapper.deleteById(schoolId);
    }

    public List<School> selectSchool() {
        List<School> schoolList=new ArrayList<>();

        long userId = StpUtil.getLoginIdAsLong();
        Users users = usersMapper.selectById(userId);
        Long userRole = users.getUserRole();
        if (userRole == 0) {
            List<School> schools = schoolMapper.selectList(null);
            schoolList=schools;
        } else if (userRole == 1) {
            QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
            areaQueryWrapper.eq("area_principal", userId);
            List<Area> areas = areaMapper.selectList(areaQueryWrapper);
            for (Area area : areas) {
                QueryWrapper<School> schoolQueryWrapper = new QueryWrapper<>();
                schoolQueryWrapper.eq("area_id", area.getAreaId());
                List<School> schools = schoolMapper.selectList(schoolQueryWrapper);
                for (School school : schools) {
                    schoolList.add(school);
                }
            }
        } else if (userRole==4){
            QueryWrapper<TeacherSchool> teacherSchoolQueryWrapper = new QueryWrapper<>();
            teacherSchoolQueryWrapper.eq("teacher_id",userId);
            List<TeacherSchool> teacherSchools = teacherSchoolMapper.selectList(teacherSchoolQueryWrapper);
            for (TeacherSchool teacherSchool : teacherSchools) {
                schoolList.add(schoolMapper.selectById(teacherSchool.getSchoolId()));
            }
        }
        return schoolList;
    }

    @Transactional
    public List<ListItemVo> getSchoolList() {
        List<SchoolView> schoolViewList = new ArrayList<>();
        List<SchoolView> schoolList = schoolMapper.getSchoolList();
        long userId = StpUtil.getLoginIdAsLong();
        Users users = usersMapper.selectById(userId);
        Long userRole = users.getUserRole();
        if (userRole == 0) {
            schoolViewList=schoolList;
        } else if (userRole == 1) {
            QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
            areaQueryWrapper.eq("area_principal", userId);
            List<Area> areas = areaMapper.selectList(areaQueryWrapper);
            for (Area area : areas) {
                QueryWrapper<School> schoolQueryWrapper = new QueryWrapper<>();
                schoolQueryWrapper.eq("area_id", area.getAreaId());
                List<School> schools = schoolMapper.selectList(schoolQueryWrapper);
                for (SchoolView schoolView : schoolList) {
                    for (School school : schools) {
                        if (schoolView.getSchoolId().equals(school.getSchoolId())){
                            schoolViewList.add(schoolView);
                        }
                    }
                }

            }
        }else if (userRole==4){
            QueryWrapper<TeacherSchool> teacherSchoolQueryWrapper = new QueryWrapper<>();
            teacherSchoolQueryWrapper.eq("teacher_id",userId);
            List<TeacherSchool> teacherSchools = teacherSchoolMapper.selectList(teacherSchoolQueryWrapper);
            for (TeacherSchool teacherSchool : teacherSchools) {
                for (SchoolView schoolView : schoolList) {
                    if (teacherSchool.getSchoolId().equals(schoolView.getSchoolId())){
                        schoolViewList.add(schoolView);
                    }
                }
            }
        }else if (userRole==2){
            schoolViewList=schoolList;
        }
        return schoolViewList.stream().map(
                i -> {
                    final List<ListItemVo> children = classesMapper.selectBySchoolId(i.getSchoolId()).stream().map(t -> new ListItemVo()
                            .setLabel(t.getClassName())
                            .setValue(t.getClassId())).collect(Collectors.toList());
                    return new ListItemVo()
                            .setLabel(i.getSchoolName())
                            .setValue(i.getSchoolId())
                            .setChildren(children);
                }).collect(Collectors.toList());
    }
}
