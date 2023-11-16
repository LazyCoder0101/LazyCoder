package com.lazycoder.uidatasourceedit.modulemanagement.editframe;

import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.service.SysService;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.util.List;


/**
 * 分类显示下拉框
 *
 * @author admin
 */
public class ModuleEditFrameClassCombobox extends JComboBox<String> {

	/**
	 *
	 */
	private static final long serialVersionUID = 8242490600511402800L;

	public ModuleEditFrameClassCombobox() {
		// TODO Auto-generated constructor stub

	}

	public void updateItems() {
		removeAllItems();
		List<TheClassification> list = SysService.CLASSIFICATION_SERVICE.getAllClassificationList();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (int i = 0; i < list.size(); i++) {
			model.addElement(list.get(i).getClassName());
		}
		setModel(model);
		if (list.size() > 0) {
			setSelectedIndex(0);
		}
	}

}
