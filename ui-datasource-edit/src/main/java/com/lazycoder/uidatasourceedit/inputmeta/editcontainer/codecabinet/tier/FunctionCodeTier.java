package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier;

import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.database.model.general.GeneralCodeModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.element.mark.FunctionMarkElement;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.code.FunctionCodePane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CommandContainerCodesImportCodeShowButton;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.ModuleCodeUsePropetyMenu;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FunctionCodeTier extends AbstractCodeTier {

    /**
     *
     */
    private static final long serialVersionUID = -6801599532639031043L;

    private CodeLabelCombobox codeLabelCombobox;

    private ModuleCodeUsePropetyMenu codeUsePropetyMenu;

    private JPanel panel;

    private JScrollPane scrollPane;

    private int typeSerialNumber;

    private CommandContainerCodesImportCodeShowButton commandContainerCodesImportCodeShowButton = null;

    public FunctionCodeTier(int typeSerialNumber, int operatingOrdinalNumber, int codeOrdinal, ContainerModel model) {
        this.typeSerialNumber = typeSerialNumber;
        this.operatingOrdinalNumber = operatingOrdinalNumber;
        this.codeOrdinal = codeOrdinal;

        panel = new JPanel();
        setViewportView(panel);
        panel.setLayout(new BorderLayout(0, 0));

        codePane = new FunctionCodePane(codeOrdinal, model);
        model.getFunctionCodes().put(codeOrdinal, this);
        scrollPane = new JScrollPane(codePane);
        codePane.setUpdateScrollpane(scrollPane);
        panel.add(scrollPane, BorderLayout.CENTER);

        commandContainerCodesImportCodeShowButton = new CommandContainerCodesImportCodeShowButton();

        JLabel clLabel = new JLabel("代码标签：");
        clLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        codeLabelCombobox = new CodeLabelCombobox();
        codeLabelCombobox.addMouseListener(mouseAdapter);
//        codeLabelCombobox.setToolTipText("该代码会随着用户选择，添加到对应模板符合条件的\"功能\"标签位置或者功能组件中符合条件的位置，\n（没有符合的将无法写入）");

        codeUsePropetyMenu = new ModuleCodeUsePropetyMenu();

        Box box = Box.createHorizontalBox();
        box.add(commandContainerCodesImportCodeShowButton);
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

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            FunctionMarkElement thanMarkElement = new FunctionMarkElement();
            thanMarkElement.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
            ModuleEditPaneHolder.needUseCodeFileEditPane.navigateToTheCorrespondingImportMark(thanMarkElement, true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            FunctionMarkElement thanMarkElement = new FunctionMarkElement();
            thanMarkElement.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
            ModuleEditPaneHolder.needUseCodeFileEditPane.navigateToTheCorrespondingImportMark(thanMarkElement, false);
        }
    };

    @Override
    public void reductionContent(GeneralCodeModel codeModel) {
        FunctionCodeModel theCodeModel = (FunctionCodeModel) codeModel;
        super.reductionContent(codeModel);
        codeLabelCombobox.setSelectedCodeLabel(theCodeModel.getCodeLabelId());
        codeUsePropetyMenu.setCodeUsePropertyParam(theCodeModel.getCodeUsePropertyParam());
        commandContainerCodesImportCodeShowButton.setThisImport(theCodeModel.getImportCodeParam());
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (super.check() == false) {
            flag = false;
            LazyCoderOptionPane.showMessageDialog(null,
                    "o(´^｀)o   分类\"" + ModuleEditPaneHolder.moduleFunctionEditPane.getTypeName(typeSerialNumber) + "\"中第"
                            + operatingOrdinalNumber + "个功能，第" + codeOrdinal + "条代码没写内容",
                    "系统信息", JOptionPane.PLAIN_MESSAGE);
        }
        return flag;
    }

    @Override
    public FunctionCodeModel getCodeModel() {
        // TODO Auto-generated method stub
        FunctionCodeModel codeModel = new FunctionCodeModel();
        codeModel.setCodeOrdinal(codeOrdinal);
        codeModel.setCodeFormatParam(getCodeParam());
        codeModel.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        codeModel.setCodeUsePropertyParam(codeUsePropetyMenu.getCodeUsePropetyParam());
        codeModel.setImportCodeParam(commandContainerCodesImportCodeShowButton.getImportCodeParam());
        return codeModel;
    }

    @Override
    public String getPathParam() {
        return "";
    }

    @Override
    public void packUpCorrespondingImportCodePane() {
        if (commandContainerCodesImportCodeShowButton != null) {
            commandContainerCodesImportCodeShowButton.packUpPanel();
        }
    }

}