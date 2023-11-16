package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier;

import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.element.lable.control.functionadd.FunctionAddCodeModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.code.MethodAddCodePane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeUsePropetyMenu;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MethodAddFunctionCodeTier extends AbstractCodeTier {

    /**
     *
     */
    private static final long serialVersionUID = 8385033799704835832L;

    private JPanel panel;

    private JScrollPane scrollPane;

    private CodeLabelCombobox codeLabelCombobox;

    private CodeUsePropetyMenu codeUsePropetyMenu;

    public MethodAddFunctionCodeTier(int operatingOrdinalNumber, int codeOrdinal, ContainerModel model) {
        this.operatingOrdinalNumber = operatingOrdinalNumber;
        this.codeOrdinal = codeOrdinal;

        panel = new JPanel();
        setViewportView(panel);
        panel.setLayout(new BorderLayout(0, 0));

        codePane = new MethodAddCodePane(codeOrdinal, model);
        codePane.setUpdateScrollpane(this);
        model.getFunctionCodes().put(codeOrdinal, this);
        scrollPane = new JScrollPane(codePane);
        panel.add(scrollPane, BorderLayout.CENTER);

        JLabel clLabel = new JLabel("代码标签：");
        clLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        codeLabelCombobox = new CodeLabelCombobox();
//        codeLabelCombobox.setToolTipText("该代码会随着用户选择，添加到符合条件的功能拓展组件（代码中）的位置，\n（没有符合的将无法写入）");

        codeUsePropetyMenu = new CodeUsePropetyMenu();

        Box box = Box.createHorizontalBox();
        box.add(clLabel);
        box.add(codeLabelCombobox);
        box.add(Box.createHorizontalStrut(10));
        box.add(codeUsePropetyMenu);

        Box vbox = Box.createVerticalBox();
        vbox.add(box);
        vbox.add(Box.createVerticalStrut(10));

        panel.add(vbox, BorderLayout.SOUTH);
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (super.check() == false) {
            flag = false;
            LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   第" + this.operatingOrdinalNumber +
                            "个方法中，第" + codeOrdinal + "条代码没写内容", "系统信息",
                    JOptionPane.PLAIN_MESSAGE);
        }
        return flag;
    }

    /**
     * 还原内容
     *
     * @param functionAddCodeModel
     */
    public void restoreContent(FunctionAddCodeModel functionAddCodeModel) {
        ArrayList<BaseElementInterface> elementList = DeserializeElementMethods.getCodePaneElementList(functionAddCodeModel.getCodeFormatParam());
        codePane.reductionContent(elementList);
        codeLabelCombobox.setSelectedCodeLabel(functionAddCodeModel.getCodeLabelId());
        codeUsePropetyMenu.setCodeUsePropertyParam(functionAddCodeModel.getCodeUsePropertyParam());
//		code_pane.reductionContent(methodAddFunctionStorageFormat);
    }

    public void deleteComponent() {
        codePane.deleteAllComponents();
    }

    @Override
    public String getCodeParam() {
        String out = "";
        out = codePane.getCodeParam();
        return out;
    }

    @Override
    public FunctionAddCodeModel getCodeModel() {
        FunctionAddCodeModel codeModel = new FunctionAddCodeModel();
        codeModel.setCodeFormatParam(getCodeParam());
//		codeModel.setCodeOrdinal(codePane.getSerialNumber());
        codeModel.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        codeModel.setCodeUsePropertyParam(codeUsePropetyMenu.getCodeUsePropetyParam());
        return codeModel;
    }

    @Override
    public String getPathParam() {
        return "";
    }


}