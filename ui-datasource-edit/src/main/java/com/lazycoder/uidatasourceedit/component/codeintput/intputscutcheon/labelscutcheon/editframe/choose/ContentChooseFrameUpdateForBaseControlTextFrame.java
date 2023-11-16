package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.OptionDataModelTempHolder;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;;
import com.lazycoder.uidatasourceedit.GeneralSettingPaneHolder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ContentChooseFrameUpdateForBaseControlTextFrame extends AbstractOptionDataModelEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = -8908582051517176457L;

    private String optionId;

    public ContentChooseFrameUpdateForBaseControlTextFrame(String optionId) {
        super();
        this.optionId = optionId;

        showOriginalData();
        ok.addActionListener(listener);
        cancel.addActionListener(listener);
        setVisible(true);

    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == ok) {
                if (check() == true) {
                    ok();
                }
            } else if (e.getSource() == cancel) {
                ContentChooseFrameUpdateForBaseControlTextFrame.this.dispose();
            }
        }
    };

    private void showOriginalData() {
        OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(this.optionId);
        setTitle("更改选项\"" + optionDataModel.getOptionName() + "\"属性");
        this.usingRange = optionDataModel.getUsingRange();
        this.moduleId = optionDataModel.getModuleId();

        this.additionalSerialNumber = optionDataModel.getAdditionalSerialNumber();
        if (optionDataModel != null) {
            optionEditPane.recoverModel(optionDataModel);

            optionNameTextField.setText(optionDataModel.getOptionName());
            optionNameTextField.setEditable(false);

            if (optionDataModel.getUsingRange() == OptionDataModel.GENERAL_TYPE) {
                generalCheckBox.setSelected(true);

            } else {
                generalCheckBox.setSelected(false);
            }
            generalCheckBox.setEnabled(false);

            if (optionDataModel.getOptionType() == OptionDataModel.EXECLUSIVE) {
                nOr1Combobx.setSelectedItem("单选");

            } else if (optionDataModel.getOptionType() == OptionDataModel.MULTIPLE) {
                nOr1Combobx.setSelectedItem("多选");
                leftTextField.setText(optionDataModel.getLeftStr());
                rightTextField.setText(optionDataModel.getRightStr());
                separatorTextField.setText(optionDataModel.getSeparatorStr());
            }
            nOr1Combobx.setEnabled(false);
        }
    }

    @Override
    public void ok() {
        // 保存到数据库
        OptionDataModel optionDataModel = getBaseOptionDatamodel();
        optionDataModel.setOptionId(this.optionId);
        SysService.OPTION_SERVICE.updateOption(optionDataModel);

        dispose();//关闭本窗口

        if (GeneralSettingPaneHolder.generalOptionPane != null) {//更新类表
            GeneralSettingPaneHolder.generalOptionPane.updateList();
        }
        DataSourceEditHolder.temporaryErrorList.clear();
        OptionDataModelTempHolder.updateOption(optionDataModel);
        DataSourceEditHolder.updateCorrespondingContentChooseModel(optionDataModel);//把对应可以添加这个选项的面板，里面的选项组件，还有方法组件和不常用组件里面这个选项的数据都进行更改
        DataSourceEditHolder.showErrorListIfNeed("注意一下  (*^▽^*)");
    }

}
