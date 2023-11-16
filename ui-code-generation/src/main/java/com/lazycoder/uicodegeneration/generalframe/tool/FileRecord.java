package com.lazycoder.uicodegeneration.generalframe.tool;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileRecord {

	private String fileName = "";

	private String path = "";

	private int addTimes = 0;

	public FileRecord(String fileName, String path) {
		this.fileName = fileName;
		this.path = path;
	}

	public void addTimes() {
		addTimes = addTimes + 1;
	}

	public void reduceTimes() {
		addTimes = addTimes - 1;
	}



}
