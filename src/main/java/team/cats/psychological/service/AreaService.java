package team.cats.psychological.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.Area;
import team.cats.psychological.mapper.AreaMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AreaService {

    @Resource
    private AreaMapper areaMapper;

    public List<Area> getAreaList(){
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("delete_flag",0);
        List<Area> areas = areaMapper.selectList(queryWrapper);
        return areas;
    }
}
