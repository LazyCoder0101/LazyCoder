package com.lazycoder.uicodegeneration.component.operation.container.sendparam;

import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import lombok.Data;

@Data
public class CommandContainerComponentParam extends GeneralContainerComponentParam {

	private OpratingContainerInterface thisOpratingContainer;

	public CommandContainerComponentParam() {
		// TODO Auto-generated constructor stub
		super();
	}

}
