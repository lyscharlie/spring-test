package com.lyscharlie.biz.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author CodeGenTool
 * @ClassName: Region
 * @Description: by CodeGenerate
 * @date 2020-7-10
 */
@Data
@TableName("lys_region")
public class RegionDO {

	/**
	 * 地区id ( 主键 )
	 */
	@TableId(value = "region_id")
	private Long regionId;
	/**
	 * 地区名称
	 */
	@TableField(value = "region_name")
	private String regionName;
	/**
	 * 上级地区id
	 */
	@TableField(value = "parent_id")
	private Long parentId;
	/**
	 * 级别：1-省，2-市，3-区
	 */
	@TableField(value = "region_level")
	private Integer regionLevel;
	/**
	 * 状态：1-启用，2-停用
	 */
	@TableField(value = "status")
	private Integer status;
	/**
	 * 创建时间
	 */
	@TableField(value = "gmt_create")
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	@TableField(value = "gmt_modified")
	private Date gmtModified;

	/* -------------------- 非DB字段 -------------------- */

}