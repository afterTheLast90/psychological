package team.cats.psychological.service;

import com.github.yitter.idgen.YitIdHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cats.psychological.entity.Classes;
import team.cats.psychological.entity.School;
import team.cats.psychological.mapper.ClassesMapper;
import team.cats.psychological.mapper.SchoolMapper;
import team.cats.psychological.vo.ListItemVo;
import team.cats.psychological.vo.SchoolView;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    @Resource
    private SchoolMapper schoolMapper;
    @Resource
    private ClassesMapper classesMapper;

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

    public List<School> selectSchool(){
        List<School> schools = schoolMapper.selectList(null);
        return schools;
    }

    @Transactional
    public List<ListItemVo> getSchoolList(){
        List<SchoolView> schoolViews=schoolMapper.getSchoolList();
//        for (SchoolView schoolView : schoolViews) {
//            schoolView.setClasses(classesMapper.selectBySchoolId(schoolView.getSchoolId()));
//        }
        return schoolMapper.getSchoolList().stream().map(
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
