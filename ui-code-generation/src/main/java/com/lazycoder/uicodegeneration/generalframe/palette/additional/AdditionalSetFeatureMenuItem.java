package com.lazycoder.uicodegeneration.generalframe.palette.additional;

import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;


public class AdditionalSetFeatureMenuItem extends JMenuItem {

	/**
	 *
	 */
	private static final long serialVersionUID = -3990074396849586827L;

	private FormatTypeFeatureSelectionModel model;

	public AdditionalSetFeatureMenuItem(FormatTypeFeatureSelectionModel model) {
		// TODO Auto-generated constructor stub
		super(model.getShowText());
		this.model = model;
		this.addActionListener(listener);

		if (model != null && ("[]".equals(model.getNoteListParam()) == false)) {
			HTMLText noteTip = CodeGenerationFrameHolder.getNoteToolTip(model.getNoteListParam());
			if (noteTip != null) {
				setToolTipText(noteTip.getHtmlContent());
			}
		}
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
//			CodeGenerationFrameHolder.currentAdditiveMethodCodePane.addAdditionalSetFunction(model);
		}
	};

}
