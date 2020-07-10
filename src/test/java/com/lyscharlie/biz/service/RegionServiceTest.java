package com.lyscharlie.biz.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lyscharlie.biz.entity.RegionDO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionServiceTest {

	@Autowired
	private RegionService regionService;

	@Test
	public void testInitData() {
		String path = "/Users/liyishi/Work/2020行政区域.txt";

		try {
			List<String> list = FileUtils.readLines(new File(path), Charsets.toCharset("utf-8"));

			if (CollectionUtils.isNotEmpty(list)) {
				List<RegionDO> regionList = new ArrayList<>();

				Date now = new Date();

				for (String s : list) {
					String[] array = StringUtils.split(s);
					// System.out.println(array[0] + "-----" + array[1]);

					String code = array[0];

					RegionDO region = new RegionDO();
					region.setRegionId(Long.valueOf(code));
					region.setRegionName(array[1]);
					region.setStatus(1);
					region.setGmtCreate(now);
					region.setGmtModified(now);

					if (StringUtils.endsWith(code, "0000")) {
						region.setRegionLevel(1);
						region.setParentId(0L);
					} else if (StringUtils.endsWith(code, "00")) {
						region.setRegionLevel(2);
						long parentId = region.getRegionId() / 10000 * 10000;
						region.setParentId(parentId);
					} else {
						region.setRegionLevel(3);
						long parentId = region.getRegionId() / 100 * 100;
						region.setParentId(parentId);
					}

					// String province = StringUtils.substring(code, 0, 1);
					// String city = StringUtils.substring(code, 0, 3);

					regionList.add(region);
				}

				Set<Long> noList = new HashSet<>();

				for (RegionDO region : regionList) {
					// System.out.println(region.getRegionId() + "----" + region.getRegionName() + "----" + region.getRegionLevel() + "----" + region.getParentId());
					if (region.getParentId().equals(0)) {
						continue;
					}

					boolean noParent = true;
					for (RegionDO area : regionList) {
						if (region.getParentId().equals(area.getRegionId())) {
							noParent = false;
							break;
						}
					}

					if (noParent) {
						noList.add(region.getParentId());
					}
				}

				for (Long regionId : noList) {

					long parentId = regionId / 10000 * 10000;

					for (RegionDO region : regionList) {
						if (region.getRegionId().equals(parentId)) {
							// System.out.println(regionId + "---" + region.getRegionId() + "----" + region.getRegionName());

							RegionDO newRegion = new RegionDO();
							newRegion.setRegionId(regionId);
							newRegion.setParentId(parentId);
							newRegion.setRegionLevel(region.getRegionLevel() + 1);
							newRegion.setStatus(1);
							newRegion.setGmtCreate(now);
							newRegion.setGmtModified(now);

							if (StringUtils.endsWith(region.getRegionName(), "市")) {
								newRegion.setRegionName(region.getRegionName());
							} else if (StringUtils.endsWith(region.getRegionName(), "省")) {
								newRegion.setRegionName("省直辖县");
							} else if (StringUtils.endsWith(region.getRegionName(), "自治区")) {
								newRegion.setRegionName("自治区直辖县级地区");
							} else {
								newRegion.setRegionName("其他");
							}

							regionList.add(newRegion);
							break;
						}

					}

				}

				// for (RegionDO region : regionList) {
				// 	this.regionService.s
				// }
				this.regionService.saveBatch(regionList);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}