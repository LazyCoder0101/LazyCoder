package com.lazycoder.service.service.impl;

public interface DBChangeService {

	boolean changeDb(String datasourceId) throws Exception;

	public boolean changeUserDB(String databaseName, String dbId);

}
