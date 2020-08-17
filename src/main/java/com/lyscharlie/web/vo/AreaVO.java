package com.lyscharlie.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "地区信息")
public class AreaVO {

	/**
	 * 地区id
	 */
	@ApiModelProperty(value = "地区id", example = "311000")
	private Long areaId;
	/**
	 * 地区名称
	 */
	@ApiModelProperty(value = "地区名称", example = "杭州市")
	private String areaName;
	/**
	 * 地区等级：1-国家，2-省，3-市，4-区/县/镇
	 */
	@ApiModelProperty(value = "地区等级：1-国家，2-省，3-市，4-区/县/镇", example = "3")
	private Integer areaLevel;
	/**
	 * 地区信息
	 */
	@ApiModelProperty(value = "地区信息", example = "杭州市")
	private String areaInfo;
	/**
	 * 上级地区id
	 */
	@ApiModelProperty(value = "上级地区id", example = "310000")
	private Long parentId;
	/**
	 * 状态：1-启用，2-停用
	 */
	@ApiModelProperty(value = "状态：1-启用，2-停用", example = "1")
	private Integer status;

}
