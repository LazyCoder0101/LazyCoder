package com.lazycoder.uicodegeneration.proj.stostr.codeshown;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.general.GeneralFileFormatDeserializer;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.TheFormatStatement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CodeShowPaneModel {

	@JSONField(deserializeUsing = TheFormatStatementDeserializer.class)
	private TheFormatStatement formatStatement;

	@JSONField(deserializeUsing = GeneralFileFormatDeserializer.class)
	private GeneralFileFormat codeModel;

}
