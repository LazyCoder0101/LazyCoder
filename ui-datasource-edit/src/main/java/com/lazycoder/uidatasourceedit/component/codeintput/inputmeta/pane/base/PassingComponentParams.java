package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base;

import com.lazycoder.service.vo.BasePane;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PassingComponentParams {

	private BasePane thisPane = null;

	private AbstractEditContainerModel generalModel = null;

}
