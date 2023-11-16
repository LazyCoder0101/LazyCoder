package com.lazycoder.codeformat.format;

import com.lazycoder.codeformat.FormatInterface;
import com.lazycoder.codeformat.format.exejs.ExeTabifierFormat;

public class PHPFormat extends ExeTabifierFormat implements FormatInterface {

	public PHPFormat() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String formatter(String fromSource) {
		// TODO Auto-generated method stub
		return cpformatter(fromSource);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

}
