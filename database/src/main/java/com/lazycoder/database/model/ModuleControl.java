package com.lazycoder.database.model;


import com.lazycoder.database.model.general.format.GenerlFormatOperatingModel;
import lombok.Data;

@Data
public class ModuleControl extends GenerlFormatOperatingModel {

	private String moduleId;

	public ModuleControl() {
		// TODO Auto-generated constructor stub
		super();
		setAdditionalSerialNumber(0);
	}

}
