package com.lazycoder.service.service.impl;

import com.lazycoder.database.dao.ModuleControlMapper;
import com.lazycoder.database.model.ModuleControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "ModuleControlServiceImpl")
public class ModuleControlServiceImpl {

	@Autowired
	private ModuleControlMapper dao;

	public void addModuleControl(ModuleControl moduleControl) {
		dao.addModuleControl(moduleControl);
	}

	/**
	 * 模块控制
	 *
	 * @param moduleControl
	 */
	public void updateModuleControl(ModuleControl moduleControl) {
		dao.updateModuleControl(moduleControl);
	}

	/**
	 * 获取模块
	 *
	 * @param moduleId
	 * @return
	 */
	public ModuleControl getModuleControl(String moduleId) {
		return dao.getModuleControl(moduleId);
	}

	public void delDataOfModule(String moduleId) {
		// TODO Auto-generated method stub
		dao.delModuleControl(moduleId);
	}

}
