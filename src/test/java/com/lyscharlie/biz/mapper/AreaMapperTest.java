package com.lyscharlie.biz.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyscharlie.SpringTestApplication;
import com.lyscharlie.biz.entity.AreaDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringTestApplication.class)
public class AreaMapperTest {

    @Autowired
    private AreaMapper areaMapper;

    @Test
    public void testSelectById() {
        Long id = 12L;
        AreaDO area = this.areaMapper.selectById(id);
        System.out.println(JSONObject.toJSONString(area));
        Assert.assertTrue(null != area);
    }

    @Test
    public void testSelectOne() {
        QueryWrapper<AreaDO> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).eq("area_level", 2).eq("parent_id", 1).orderByAsc("area_id").last("limit 1");
        AreaDO area = this.areaMapper.selectOne(wrapper);
        System.out.println(JSONObject.toJSONString(area));
        Assert.assertTrue(null != area);
    }

    @Test
    public void testSelectByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("parent_id", 1);
        List<AreaDO> list = this.areaMapper.selectByMap(map);
        System.out.println(JSONObject.toJSONString(list));
        Assert.assertTrue(!CollectionUtils.isEmpty(list));
    }

    @Test
    public void testSelectList() {
        // LambdaQueryWrapper<Area> lambdaQuery = Wrappers.<Area>lambdaQuery();
        // lambdaQuery.eq(Area::getAreaLevel, 2).eq(Area::getParentId, 1).eq(Area::getStatus, 1);
        List<AreaDO> list = this.areaMapper.selectList(Wrappers.<AreaDO>lambdaQuery().eq(AreaDO::getAreaLevel, 2).eq(AreaDO::getParentId, 1).eq(AreaDO::getStatus, 1));
        System.out.println(JSONObject.toJSONString(list));
        Assert.assertTrue(!CollectionUtils.isEmpty(list));
    }

    @Test
    public void testSelectPage() {
        long pageNo = 1;
        long pageSize = 20;

        IPage<AreaDO> page = this.areaMapper.selectPage(new Page<>(pageNo, pageSize), Wrappers.<AreaDO>lambdaQuery().eq(AreaDO::getAreaLevel, 2).eq(AreaDO::getParentId, 1)
                .eq(AreaDO::getStatus, 1));
        System.out.println(JSONObject.toJSONString(page));
        Assert.assertTrue(null != page);
    }

    @Test
    public void testSelectObjs() {
        List<Object> objectList = this.areaMapper.selectObjs(new QueryWrapper<AreaDO>().select("area_id").eq("area_level", 2).eq("parent_id", 1).orderByAsc("area_id"));
        System.out.println(JSONObject.toJSONString(objectList));
        Assert.assertTrue(!CollectionUtils.isEmpty(objectList));
    }

    @Test
    public void testSelectMaps() {
        List<Map<String, Object>> mapList = this.areaMapper.selectMaps(new QueryWrapper<AreaDO>().select("area_id", "area_name").eq("area_level", 2).eq("parent_id", 1).orderByAsc(
                "area_id"));
        System.out.println(JSONObject.toJSONString(mapList));
        Assert.assertTrue(!CollectionUtils.isEmpty(mapList));
    }

}
