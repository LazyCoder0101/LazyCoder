package com.lazycoder.uicodegeneration.component.operation.container.component;

import com.lazycoder.database.model.featureSelectionModel.InitFeatureSelectonModel;
import com.lazycoder.service.vo.meta.InitMetaModel;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.container.InitOpratingContainerOriV1;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class InitChoiceMenuOriV1 extends JMenuBar {

    private JMenu menu;

    private InitOpratingContainerOriV1 initOpratingContainer;


    private InitChoiceMenuOriV1() {
        this.menu = new JMenu();
        this.add(menu);
        menu.setBorder(BorderFactory.createLoweredSoftBevelBorder());
    }

    public InitChoiceMenuOriV1(List<InitFeatureSelectonModel> initList, InitOpratingContainerOriV1 initOpratingContainer) {
        this();
        this.initOpratingContainer = initOpratingContainer;
        if (initList != null) {
            InitChoiceMenuItem menuItem;
            for (InitFeatureSelectonModel model : initList) {
                menuItem = new InitChoiceMenuItem(model);
                menu.add(menuItem);
            }
        }
    }

    /**
     * 显示当前选中的初始化方法
     *
     * @param initMetaModel
     */
    public void showSelectInit(InitMetaModel initMetaModel) {
        this.menu.setText(initMetaModel.getOperatingModel().getShowText());
        this.menu.setToolTipText(initMetaModel.getOperatingModel().getShowText());
    }


    class InitChoiceMenuItem extends JMenuItem {

        private InitFeatureSelectonModel featureSelectonModel;

        public InitChoiceMenuItem(InitFeatureSelectonModel featureSelectonModel) {
            this.featureSelectonModel = featureSelectonModel;
            this.setText(featureSelectonModel.getShowText());
            this.addActionListener(menuListener);

            if (featureSelectonModel != null && ("[]".equals(featureSelectonModel.getNoteListParam()) == false)) {
                HTMLText noteTip = CodeGenerationFrameHolder.getNoteToolTip(featureSelectonModel.getNoteListParam());
                if (noteTip != null) {
                    setToolTipText(noteTip.getHtmlContent());
                }
            }

        }

        private ActionListener menuListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CodeGenerationFrameHolder.temporaryErrorList.clear();//先把临时错误列表清空
                initOpratingContainer.updateInitCode(featureSelectonModel);
                CodeGenerationFrameHolder.showErrorListIfNeed("这个功能有点异常喔   (=ω=；)");
            }

        };

    }

}


