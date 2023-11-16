package com.lazycoder.uicodegeneration.proj.stostr.codeshown.format;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TheCodeStatementModel extends BaseCodeGenerateStatementModel {

	private int codeSerialNumber;

	private int codeOrdinal;

	private int codeLength = 0;

}
