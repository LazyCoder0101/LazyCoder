package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOpratingPaneElement;
import lombok.Data;

@Data
public class StringMeta extends AbstractOpratingPaneElement {

	private String text = "";

	public StringMeta() {
		// TODO Auto-generated constructor stub
		setPaneElementType(STRING_ELEMENT);
	}

	public StringMeta(String text) {
		this();
		this.text = text;
	}

}
