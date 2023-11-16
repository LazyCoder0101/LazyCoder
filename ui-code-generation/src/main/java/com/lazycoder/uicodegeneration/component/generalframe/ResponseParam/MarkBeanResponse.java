package com.lazycoder.uicodegeneration.component.generalframe.ResponseParam;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更改、删除标记里面某句代码的内容的响应结果
 */
@NoArgsConstructor
@Data
public class MarkBeanResponse {

	private boolean haveOrNot = false;    //是否需要更改

	private int cursorIncrement = 0;        //在这过程中，游标的位移增量

}
