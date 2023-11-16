package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.GeneralSettingPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.BaseControlTextPane;
import com.lazycoder.utils.UUIDUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ContentChooseFrameAddForBaseControlTextFrame extends AbstractOptionDataModelEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = -7569566642549010241L;

    private BaseControlTextPane controlTextPane;

    /**
     * 模块选项编辑窗口
     *
     * @param moduleId
     * @param controlTextPane
     */
    public ContentChooseFrameAddForBaseControlTextFrame(String moduleId,
                                                        BaseControlTextPane controlTextPane) {
        super(MODULE_TYPE);
        this.controlTextPane = controlTextPane;
        this.moduleId = moduleId;
        addModel();
        setTitle("添加本模块的选择组件");

        ok.addActionListener(listener);
        cancel.addActionListener(listener);

        setVisible(true);
    }

    /**
     * 可选模板选项编辑窗口
     *
     * @param additionalSerialNumber
     * @param baseControlTextPane
     */
    public ContentChooseFrameAddForBaseControlTextFrame(int additionalSerialNumber,
                                                        BaseControlTextPane baseControlTextPane) {
        super(ADDITIONAL_TYPE);
        this.controlTextPane = baseControlTextPane;
        this.additionalSerialNumber = additionalSerialNumber;
        addModel();
        setTitle("添加本可选模板的选择组件");

        ok.addActionListener(listener);
        cancel.addActionListener(listener);

        setVisible(true);
    }

    /**
     * 通用函数添加
     */
    public ContentChooseFrameAddForBaseControlTextFrame() {
        super(GENERAL_TYPE);
        addModel();
        setTitle("添加通用的选择组件");

        generalCheckBox.setSelected(true);
        generalCheckBox.setEnabled(false);

        ok.addActionListener(listener);
        cancel.addActionListener(listener);

        setVisible(true);
    }

    /**
     * 必填模板选项编辑窗口
     *
     * @param controlTextPane
     */
    public ContentChooseFrameAddForBaseControlTextFrame(BaseControlTextPane controlTextPane) {
        super(MAIN_TYPE);
        this.controlTextPane = controlTextPane;
        addModel();
        setTitle("添加必填模板的选择组件");

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
                ContentChooseFrameAddForBaseControlTextFrame.this.dispose();

            }
        }
    };

    private void addModel() {
        optionEditPane.addOption();
        optionEditPane.addAline();
    }

    @Override
    public void ok() {
        // 保存到数据库
        OptionDataModel optionDataModel = getBaseOptionDatamodel();
        String optionId = UUIDUtil.getUUID();
        if (optionId != null) {
            optionDataModel.setOptionId(optionId);
            try {
                SysService.OPTION_SERVICE.addOption(optionDataModel);
                // 在面板添加
                if (usingRange != GENERAL_TYPE) {
                    controlTextPane.addContentChoose(optionDataModel.getOptionId());
                    if (generalCheckBox.isSelected() == true) {
                        if (GeneralSettingPaneHolder.generalOptionPane != null) {
                            GeneralSettingPaneHolder.generalOptionPane.updateList();
                        }
                    }
                } else {
                    if (GeneralSettingPaneHolder.generalOptionPane != null) {
                        GeneralSettingPaneHolder.generalOptionPane.updateList();
                    }
                }
            } catch (Exception e) {
                SysService.SYS_SERVICE_SERVICE.log_error(
                        ContentChooseFrameAddForBaseControlTextFrame.class+
                                "添加录入选项数据出错：\n" + e.getMessage() + "\n\n\n");
            }
        } else {
            SysService.SYS_SERVICE_SERVICE.log_error(
                    ContentChooseFrameAddForBaseControlTextFrame.class+
                    "添加录入选项数据出错，无法生成uuid！\n");
            LazyCoderOptionPane.showMessageDialog(null, "(￣ェ￣;)  重启应用试试，好像出问题了");
        }
        dispose();
    }

    @Override
    protected boolean check() {
        boolean existFlag = SysService.OPTION_SERVICE.selectExist(optionNameTextField.getText().trim()), flag = true;
        if (existFlag == false) {//没有添加过这个名字的选项，检查一次看看是否正常
            flag = super.check();
        } else {
            LazyCoderOptionPane.showMessageDialog(null, "亲，已经添加过这个名字的选项了，换个名字吧。  (*^▽^*)");
            flag = false;
        }
        return flag;
    }

}
