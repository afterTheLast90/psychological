package team.cats.psychological.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import team.cats.psychological.base.BaseException;
import team.cats.psychological.base.BasePageParam;
import team.cats.psychological.base.PageResult;
import team.cats.psychological.base.R;
import team.cats.psychological.entity.*;
import team.cats.psychological.mapper.AreaMapper;
import team.cats.psychological.mapper.SchoolMapper;
import team.cats.psychological.mapper.UsersMapper;
import team.cats.psychological.param.AreaParams;
import team.cats.psychological.vo.AreaAndUser;
import team.cats.psychological.vo.City;
import team.cats.psychological.vo.Province;
import team.cats.psychological.vo.StudentView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaService {

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private SchoolMapper schoolMapper;

    public void insertArea(AreaParams areaParams){
        Area area=new Area();
        area.setAreaId(areaParams.getAreaId());
        area.setAreaName(areaParams.getAreaName());
        area.setCityId(areaParams.getCityId());
        area.setCityName(areaParams.getCityName());
        area.setProvinceId(areaParams.getProvinceId());
        area.setProvinceName(areaParams.getProvinceName());
        area.setAreaPrincipal(areaParams.getPrincipal());
        int insert = areaMapper.insert(area);
        if (insert==0){
            throw new BaseException(400,"插入失败");
        }
    }

    public void deleteArea(Long areaId) {
        Area area =getAreaNotNull(areaId);
        areaMapper.deleteById(areaId);
    }

    public Area getAreaNotNull(Long areaId){
        Area area = areaMapper.selectById(areaId);
        if(area==null){
            throw new BaseException(400, "地区不存在");
        }
        return area;
    }

    public List<Area> getArea(){
        List<Area> areaList = new ArrayList<>();

        long userId = StpUtil.getLoginIdAsLong();
        Users users = usersMapper.selectById(userId);
        Long userRole = users.getUserRole();
        if (userRole == 0) {
            QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("delete_flag",0);
            List<Area> areas = areaMapper.selectList(queryWrapper);
            areaList=areas;
        } else if (userRole == 1) {
            QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
            areaQueryWrapper.eq("area_principal", userId);
            List<Area> areas = areaMapper.selectList(areaQueryWrapper);
            areaList=areas;
        }
        return areaList;
    }

    public List<City> getCity(){
        List<City> cities = areaMapper.selectCity();
        return cities;
    }

    public List<Province> getProvince(){
        List<Province> provinces = areaMapper.selectProvince();
        return provinces;
    }

    public PageResult<AreaAndUser> getAreaUser(BasePageParam basePageParam, String value, Long cityId, Long provinceId){


        PageHelper.startPage(basePageParam.getPageNum(),basePageParam.getPageSize());
        List<AreaAndUser> areaAndUsers =areaMapper.selectAreaUsers(value,cityId,provinceId);
        for (AreaAndUser areaAndUser : areaAndUsers) {
            areaAndUser.setSchool(schoolMapper.selectByAreaId(areaAndUser.getAreaId()));
        }
        return new PageResult<AreaAndUser>(areaAndUsers);
    }

    public void editPrincipal(Long areaId,Long areaPrincipal){
        Area area = getAreaNotNull(areaId);
        System.out.println(area);
        area.setAreaPrincipal(areaPrincipal);
        int i = areaMapper.updateById(area);
        if(i==0){
            throw new BaseException(400,"更新失败");
        }
    }
}
