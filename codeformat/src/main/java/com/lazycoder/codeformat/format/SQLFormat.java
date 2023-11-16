package com.lazycoder.codeformat.format;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.lazycoder.codeformat.FormatInterface;

public class SQLFormat implements FormatInterface {

	public SQLFormat() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String formatter(String fromSource) {
		// TODO Auto-generated method stub
		return SqlFormatter.format(fromSource, "\t");
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
