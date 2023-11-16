package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.model.command.InitCodeModel;
import com.lazycoder.database.model.general.GeneralCodeModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.code.InitCodePane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.ModuleCodeUsePropetyMenu;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu.InitPathChooseMenu;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu.PathChooseMenuItem;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class InitCodeTier extends AbstractCodeTier {

    /**
     *
     */
    private static final long serialVersionUID = -3705402774944639305L;

    private InitPathChooseMenu pathChooseMenu;
    private JMenuBar menuBar;
    private JPanel panel;

    private CodeLabelCombobox codeLabelCombobox;
    private ModuleCodeUsePropetyMenu codeUsePropetyMenu;
    private JScrollPane scrollPane;

    public InitCodeTier(int initSerialNumber, int codeNumber, ContainerModel model) {
        // model.getFunction_codes().put(serialNumber, this);

        this.codeOrdinal = codeNumber;
        this.operatingOrdinalNumber = initSerialNumber;

        panel = new JPanel();
        setViewportView(panel);
        panel.setLayout(new BorderLayout(0, 0));

        codePane = new InitCodePane(codeNumber, model);

        model.getFunctionCodes().put(codeNumber, this);
        scrollPane = new JScrollPane(codePane);
        codePane.setUpdateScrollpane(scrollPane);
        panel.add(scrollPane, BorderLayout.CENTER);

        JLabel mLabel = new JLabel("*");
        mLabel.setForeground(Color.RED);
        JLabel wriLoclabel = new JLabel("填写位置：");
        wriLoclabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        menuBar = new JMenuBar();

        JLabel clLabel = new JLabel("代码标签：");
        clLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        codeLabelCombobox = new CodeLabelCombobox();
        codeLabelCombobox.addPopupMenuListener(popupMenuListener);

        codeUsePropetyMenu = new ModuleCodeUsePropetyMenu();

        pathChooseMenu = new InitPathChooseMenu(initSerialNumber, codeNumber) {
            @Override
            protected void afterSelectedMenuItem(PathChooseMenuItem pathChooseMenuItem) {
                CodeFormatFlagParam codeFormatFlagParam = pathChooseMenuItem.getCodeFormatFlagParam();
                if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MODULE_TYPE &&
                        codeFormatFlagParam.getModuleId().equals(DataSourceEditHolder.currentModule.getModuleId())) {//如果选中模块代码，取消\"无需插入引入代码\"，并且失能该按钮（因为模块代码本来就不用插入引入代码，每没必要让用户选择）
                    codeUsePropetyMenu.setSelectedCodeUsePropety(AbstractCodeUseProperty.NoNeedToInsertImportCodeType, false);
                    codeUsePropetyMenu.setEnabled(AbstractCodeUseProperty.NoNeedToInsertImportCodeType, false);
                    codeUsePropetyMenu.setEnabled(AbstractCodeUseProperty.NeedUseModuleImportCodeType, false);
                } else {//其他的话，先使能\"无需插入引入代码\"菜单项（其他的代码文件是允许设置不插入引入代码的）
                    codeUsePropetyMenu.setEnabled(AbstractCodeUseProperty.NoNeedToInsertImportCodeType, true);//实际这句应该用不上，下面取消选择那句早起作用了
                    codeUsePropetyMenu.setEnabled(AbstractCodeUseProperty.NeedUseModuleImportCodeType, true);
                }
            }

            @Override
            protected void afterDeselectMenuItem(PathChooseMenuItem pathChooseMenuItem) {//取消选择某个代码文件以后，把原来失能的菜单项重新使能
                codeUsePropetyMenu.setEnabled(AbstractCodeUseProperty.NoNeedToInsertImportCodeType, true);
                codeUsePropetyMenu.setEnabled(AbstractCodeUseProperty.NeedUseModuleImportCodeType, true);
            }

            @Override
            public boolean check() {
                boolean flag = true;
                ArrayList<CodeFormatFlagParam> list = getSelectedCodeFormatFlagParams();
                if (list.size() == 0) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(null,
                            "o(´^｀)o   第" + initSerialNumber + "个初始化方法里面的第" + codeOrdinal + "条代码还没有在\"填写位置\"现在要写在哪里", "系统信息",
                            JOptionPane.PLAIN_MESSAGE);
                } else {
                    if (ModuleEditPaneHolder.usingRange != null &&
                            ModuleEditPaneHolder.usingRange.getUsingRange().size() != list.size()) {
                        for (CodeFormatFlagParam codeFormatFlagParamTemp : list) {
                            if (codeFormatFlagParamTemp.getFormatType() == CodeFormatFlagParam.MAIN_TYPE ||
                                    codeFormatFlagParamTemp.getFormatType() == CodeFormatFlagParam.ADDITIONAL_TYPE) {
                                flag = false;
                                LazyCoderOptionPane.showMessageDialog(null,
                                        "o(´^｀)o   第" + initSerialNumber + "个初始化方法里面的第" + codeOrdinal + "条代码的\"填写位置\"，对应设置的\"可使用范围\"中，有些还没有选择要写在哪里", "系统信息",
                                        JOptionPane.PLAIN_MESSAGE);
                                break;
                            }
                        }
                    }
                }
                return flag;
            }
        };
        menuBar.add(pathChooseMenu);

        Box box = Box.createHorizontalBox();
        box.add(mLabel);
        box.add(wriLoclabel);
        box.add(menuBar);
        box.add(Box.createHorizontalStrut(10));
        box.add(clLabel);
        box.add(codeLabelCombobox);
        box.add(Box.createHorizontalStrut(10));
        box.add(codeUsePropetyMenu);

        Box vbox = Box.createVerticalBox();
        vbox.add(box);
        vbox.add(Box.createVerticalStrut(10));

        panel.add(vbox, BorderLayout.SOUTH);
    }

    private PopupMenuListener popupMenuListener = new PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            pathChooseMenu.getMarkElement().setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
        }
    };

    public InitPathChooseMenu getPathChooseMenu() {
        return pathChooseMenu;
    }

    @Override
    public void reductionContent(GeneralCodeModel codeModel) {
        // TODO Auto-generated method stub
        InitCodeModel theCodeModel = (InitCodeModel) codeModel;
        super.reductionContent(codeModel);
        pathChooseMenu.setSelectedPath(theCodeModel.getPathParam());
        codeLabelCombobox.setSelectedCodeLabel(theCodeModel.getCodeLabelId());
        pathChooseMenu.getMarkElement().setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        codeUsePropetyMenu.setCodeUsePropertyParam(theCodeModel.getCodeUsePropertyParam());
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (super.check() == false) {
            flag = false;
            LazyCoderOptionPane.showMessageDialog(null,
                    "o(´^｀)o   第" + operatingOrdinalNumber + "个初始化方法里面的第" + codeOrdinal + "条代码没写内容，\n如果不需要请先删掉", "系统信息",
                    JOptionPane.PLAIN_MESSAGE);
        }
        if (flag == true) {
            if (pathChooseMenu.check() == true) {
                if (pathChooseMenu.checkHaveAddTheMarkScutcheonOrNot() == false) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(null,
                            "o(´^｀)o   看看第" + operatingOrdinalNumber + "个初始化方法里面的第" + codeOrdinal + "条代码，\n你想设置写在对应代码文件哪里，请在对应地方添加标记", "系统信息",
                            JOptionPane.PLAIN_MESSAGE);
                }
//            } else {
            }else {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public String getPathParam() {
        // TODO Auto-generated method stub
        return pathChooseMenu.getPathParam();
    }

    @Override
    public InitCodeModel getCodeModel() {
        // TODO Auto-generated method stub
        InitCodeModel codeModel = new InitCodeModel();
        codeModel.setCodeFormatParam(getCodeParam());
        codeModel.setPathParam(getPathParam());
        codeModel.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
        codeModel.setCodeOrdinal(codeOrdinal);
        codeModel.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        codeModel.setCodeUsePropertyParam(codeUsePropetyMenu.getCodeUsePropetyParam());
        return codeModel;
    }

}
