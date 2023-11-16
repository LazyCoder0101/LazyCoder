package com.lazycoder.uicodegeneration.dbselectframe;

import com.lazycoder.lazycodercommon.vo.DataSourceInfo;
import com.lazycoder.service.service.SysService;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import lombok.Getter;
import lombok.Setter;


public class DataSourceSelectPane extends JList<DataSourceInfo> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4362422896048589498L;

	@Getter
	@Setter
	private DataSourceInfo selectedDataSourceInfo = null;
//	private String selectedDataSourceName = null;

	/**
	 * Create the panel.
	 */
	public DataSourceSelectPane() {
		DefaultListModel<DataSourceInfo> model = new DefaultListModel<DataSourceInfo>();
		setModel(model);

		ArrayList<DataSourceInfo> list = SysService.DB_CHANGE_SERVICE.getAllDataSourceInfos();
		for (DataSourceInfo dataSourceInfo : list) {
			model.addElement(dataSourceInfo);
		}
	}


}
