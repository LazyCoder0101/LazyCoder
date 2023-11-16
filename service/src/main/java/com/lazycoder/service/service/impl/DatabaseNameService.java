package com.lazycoder.service.service.impl;

import com.lazycoder.database.model.DataSource;
import java.util.List;

public interface DatabaseNameService {

	public List<String> getDatabaseList();

	public void addDatabase(DataSource databasesource);

	public boolean creatDatabase(String databaseName);
}
