package com.lyscharlie.web.controller.area;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyscharlie.biz.entity.AreaDO;
import com.lyscharlie.biz.service.AreaService;
import com.lyscharlie.web.vo.AreaVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "area")
@RestController
@RequestMapping(value = "area")
@Slf4j
public class AreaController {

	@Autowired
	private AreaService areaService;

	@ApiOperation(value = "中国省份列表")
	@RequestMapping(value = "chinaList.php", method = RequestMethod.GET)
	public List<AreaVO> queryListForChina() {
		List<AreaDO> list = this.areaService.list(Wrappers.<AreaDO>lambdaQuery().eq(AreaDO::getParentId, 1));
		return convertToVO(list);
	}

	@ApiOperation(value = "查询省份下所有城市列表")
	@ApiImplicitParam(name = "provinceId", value = "省份id", required = true, dataType = "Long", paramType = "form")
	@PostMapping(value = "queryCityListByProvinceId.php")
	public List<AreaVO> queryCityListByProvinceId(@RequestParam(name = "provinceId") Long provinceId) {
		List<AreaVO> resultList = null;
		if (null == provinceId || provinceId < 1) {
			resultList = new ArrayList<>();
			return resultList;
		}

		List<AreaDO> list = this.areaService.list(Wrappers.<AreaDO>lambdaQuery().eq(AreaDO::getParentId, provinceId).eq(AreaDO::getAreaLevel, 3));
		resultList = convertToVO(list);
		return resultList;
	}

	/**
	 * 类型转换
	 *
	 * @param list
	 * @return
	 */
	private List<AreaVO> convertToVO(List<AreaDO> list) {
		List<AreaVO> resultList = new ArrayList<>();
		for (AreaDO areaDO : list) {
			AreaVO vo = new AreaVO();
			BeanUtils.copyProperties(areaDO, vo);
			resultList.add(vo);
		}
		return resultList;
	}
}
