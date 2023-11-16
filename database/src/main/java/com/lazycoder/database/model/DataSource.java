package com.lazycoder.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 创建DataSource.java实体类，数据源信息装配的时候用
 */
@NoArgsConstructor
@Data
//@Component
public class DataSource {

	private String datasourceId;
	private String url;
	private String userName;
	private String passWord;

	private String driverClassName;

}
