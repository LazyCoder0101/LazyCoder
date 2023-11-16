package com.lazycoder.service.service.impl;

import com.lazycoder.database.dao.ImportCodeMapper;
import com.lazycoder.database.model.ImportCode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "ImportCodeServicelmpl")
public class ImportCodeServicelmpl {

	@Autowired
	private ImportCodeMapper dao;

	/**
	 * 保存
	 *
	 * @param list
	 * @param moduleId
	 */
	public void save(List<ImportCode> list, String moduleId) {
		dao.delDataOfModule(moduleId);
		if (list.size() > 0) {
			dao.addImportCode(list);
		}
	}

	/**
	 * 添加引入代码
	 */
	public void addImportCode(List<ImportCode> importCodeList) {
		dao.addImportCode(importCodeList);
	}

	/**
	 * 删除引入代码
	 * @param moduleId
	 */
	public void delImportCode(String moduleId) {
		dao.delDataOfModule(moduleId);
	}

	/**
	 * 获取初始化引入代码
	 * @param moduleId
	 * @return
	 */
	public List<ImportCode> getImportCodeList(String moduleId) {
		return dao.getImportCodeList(moduleId);
	}


	public void delDataOfModule(String moduleId) {
		dao.delDataOfModule(moduleId);
	}

	/**
	 * 查看是否有使用这个代码标签
	 * @param codeLabelId 代码标签的对应id
	 * @return
	 */
	public boolean selectExistCodeLabelIdUsed(String codeLabelId) {
		boolean flag = false;
		if (dao.selectExistCodeLabelIdUsed(codeLabelId) > 0) {
			flag = true;
		}
		return flag;
	}

}
