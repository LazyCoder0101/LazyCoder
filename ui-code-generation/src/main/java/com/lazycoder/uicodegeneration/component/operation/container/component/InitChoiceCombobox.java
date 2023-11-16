package com.lazycoder.uicodegeneration.component.operation.container.component;


import com.lazycoder.database.model.featureSelectionModel.InitFeatureSelectonModel;
import com.lazycoder.uicodegeneration.component.operation.container.InitOpratingContainer;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class InitChoiceCombobox extends JComboBox<InitFeatureSelectonModel> {

	/**
	 *
	 */
	private static final long serialVersionUID = -2901089403672648896L;

	private InitOpratingContainer initOpratingContainer;
	private ItemListener listener = new ItemListener() {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			InitFeatureSelectonModel option = (InitFeatureSelectonModel) getModel().getSelectedItem();
			initOpratingContainer.updateInitCode(option);
			/**
			 * 切换
			 */
		}
	};

	@SuppressWarnings("unchecked")
	public InitChoiceCombobox(List<InitFeatureSelectonModel> initList, InitOpratingContainer initOpratingContainer) {
		// TODO Auto-generated constructor stub
		super();
		this.initOpratingContainer = initOpratingContainer;
		DefaultComboBoxModel<InitFeatureSelectonModel> model = new DefaultComboBoxModel<>();
		if (initList != null) {
			for (int i = 0; i < initList.size(); i++) {
				model.addElement(initList.get(i));
			}
		}
		setModel(model);
		setRenderer(new InitChoiceComboboxRenderer());
		addItemListener(listener);
	}
}

class InitChoiceComboboxRenderer implements ListCellRenderer {

	private DefaultListCellRenderer defaultCellRenderer = new DefaultListCellRenderer();

	public InitChoiceComboboxRenderer() {
		super();
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
												  boolean cellHasFocus) {
		JLabel renderer = (JLabel) defaultCellRenderer.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);

		if (value instanceof InitFeatureSelectonModel) {
			InitFeatureSelectonModel initSelectionOption = (InitFeatureSelectonModel) value; // 把值转换成数组

			renderer.setText(initSelectionOption.getShowText());
		}
		return renderer;
	}

}

