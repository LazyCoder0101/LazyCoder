package com.lazycoder.uicodegeneration.generalframe.palette.additional;

import com.lazycoder.database.model.featureSelectionModel.AdditionalFunctionFeatureSelectionModel;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.container.AdditionalFunctionOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class AdditionalFunctionFeatureMenuItem extends JMenuItem {

    /**
     *
     */
    private static final long serialVersionUID = -3990074396849586827L;

    private AdditionalFunctionFeatureSelectionModel model;

    public AdditionalFunctionFeatureMenuItem(AdditionalFunctionFeatureSelectionModel model) {
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
            if (CodeGenerationFrameHolder.currentAdditiveMethodCodePane == null) {
                LazyCoderOptionPane.showMessageDialog(null, "(￣ェ￣;) 亲，你还没告诉我这个功能要添加到哪里");
            } else {
                CodeGenerationFrameHolder.temporaryErrorList.clear();//先把临时错误列表清空·
                AdditionalFunctionOpratingContainer container = CodeGenerationFrameHolder.currentAdditiveMethodCodePane.addAdditionalFunction(model, true);
                if (container == null) {
                    LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o    亲，这个功能不能添加在这里的喔", "系统信息",
                            JOptionPane.PLAIN_MESSAGE);
                } else {
                    //该功能的组件自动选择
                    //container.setNoUserSelectionIsRequiredValue();

                    //所有功能自动选择
                    ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
                    if (opratingContainerList != null) {
                        for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                            opratingContainer.setNoUserSelectionIsRequiredValue();
                        }
                    }

                    container.requestFocus();
                    CodeGenerationFrameHolder.showErrorListIfNeed("这个功能有点异常喔   (=ω=；)");
                }
            }
        }
    };


}
