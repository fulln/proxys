package com.fulln.demo.model;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysPermission implements Serializable {

	@TableId
	private Long permissionId;
	private String permissionResourceName;
	private Long permissionSort;
	private String permissionState;
	private String permissionRemarks;
	private Long updateDate;
	private String updateBy;
	private Long createDate;
	private String createBy;
	private Integer resourceType;
	private String resourceUrl;
	private Long permissionParentId;
	private String permissionNo;


}
