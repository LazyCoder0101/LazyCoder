package com.lazycoder.uidatasourceedit.inputmeta.editcontainer;


import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.MethodAddFunctionCodeCabinet;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.OperatingPropertyCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet.MethodAddFunctionControlCabinet;
import com.lazycoder.uiutils.folder.MyContainerPane;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;

public class MethodAddFunctionEditContainer extends AbstractEditContainer {

    /**
     *
     */
    private static final long serialVersionUID = -6075122457314700185L;

    private static final double PROPORTION = 0.268;

    /**
     * 组件放置面板
     */
    private MyContainerPane componentPane;

    private MyButton addBt, delBt;

    private OperatingPropertyCombobox setPropertyCombobox;

    private FunctionAddControl controlElement;

    private FunctionAddParam functionAddParam;

    public MethodAddFunctionEditContainer() {
        super(15);
        // TODO Auto-generated constructor stub
        // blankPanelInit();
    }

    /**
     * 新建
     *
     * @param ordinal
     */
    public MethodAddFunctionEditContainer(int ordinal, FunctionAddControl controlElement,
                                          FunctionAddParam functionAddParam) {
        this();
        this.controlElement = controlElement;
        this.functionAddParam = functionAddParam;
        this.operatingOrdinalNumber = ordinal;
        controlPaneInit();
        controlCabinet.paneStyteInit(true);
        codePaneInit();
        codeCabinet.addCodePane(theModel);
    }

    /**
     * 还原内容
     *
     * @param methodAddStorageFormat
     */
    public MethodAddFunctionEditContainer(MethodAddStorageFormat methodAddStorageFormat,
                                          FunctionAddControl controlElement, FunctionAddParam functionAddParam) {
        this();
        this.controlElement = controlElement;
        this.functionAddParam = functionAddParam;
        this.operatingOrdinalNumber = methodAddStorageFormat.getOrdinal();
        controlPaneInit();
        controlCabinet.paneStyteInit(false);
        codePaneInit();
        theModel.reductionContent(methodAddStorageFormat);
        restoreContent(methodAddStorageFormat);
        CommandCodeControl.updateCodePaneMenu(theModel);
    }

    public void controlPaneInit() {
        controlCabinet = new MethodAddFunctionControlCabinet(theModel, this.operatingOrdinalNumber, true, PROPORTION, controlElement,
                this.functionAddParam) {
            @Override
            public void clearContainer() {
                MethodAddFunctionEditContainer.this.clearAndBlankCodePane();
            }

            @Override
            public void restoreContainer() {
                ArrayList<MethodAddStorageFormat> functionList = controlElement.getFunctionList();
                if (functionList != null) {
                    for (MethodAddStorageFormat methodAddStorageFormatTemp : functionList) {
                        if (operatingOrdinalNumber == methodAddStorageFormatTemp.getOrdinal()) {
                            MethodAddFunctionEditContainer.this.clear();
                            theModel.reductionContent(methodAddStorageFormatTemp);
                            MethodAddFunctionEditContainer.this.restoreContent(methodAddStorageFormatTemp);
                            CommandCodeControl.updateCodePaneMenu(theModel);
                            Container parent = MethodAddFunctionEditContainer.this.getParent();
                            if (parent!=null){
                                parent.validate();
                            }
                            break;
                        }
                    }
                }
            }
        };
        addContainer(controlCabinet);
    }

    public void codePaneInit() {
        componentPane = new MyContainerPane();

        addBt = new MyButton("添加");
        addBt.addActionListener(listener);
        addBt.setBounds(5, 0, 60, 30);
        componentPane.add(addBt);
        delBt = new MyButton("删除");
        delBt.addActionListener(listener);
        delBt.setBounds(80, 0, 60, 30);
        componentPane.add(delBt);

        JLabel label = new JLabel("使用限制：");
        label.setBounds(150, 0, 80, 30);
        componentPane.add(label);
        setPropertyCombobox = OperatingPropertyCombobox.creatSetPropertyCombobox();
        setPropertyCombobox.setBounds(210, 0, 200, 30);
        componentPane.add(setPropertyCombobox);

        tabs.add(componentPane);
        this.add(componentPane);

        codeCabinet = new MethodAddFunctionCodeCabinet(this.operatingOrdinalNumber, theModel, true, PROPORTION);
        codeCabinet.setCodeJspHeight(PROPORTION, 120);
        addContainer(codeCabinet);
    }

    @Override
    public void clearAndBlankCodePane() {
        super.clearAndBlankCodePane();
        setPropertyCombobox.clearInit();
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addBt) {
                addCodePane();
            } else if (e.getSource() == delBt) {
                delCodePane();
            }
        }
    };

    /**
     * 删除组件
     */
    public void deleteComponent() {
        ((MethodAddFunctionControlCabinet) controlCabinet).deleteComponent();
        ((MethodAddFunctionCodeCabinet) codeCabinet).deleteComponent();
    }

    /**
     * 还原内容
     *
     * @param methodAddStorageFormat
     */
    private void restoreContent(MethodAddStorageFormat methodAddStorageFormat) {
        this.operatingOrdinalNumber = methodAddStorageFormat.getOrdinal();
        theModel.reductionContent(methodAddStorageFormat);
        MethodAddFunctionCodeCabinet methodAddFunctionCodeCabinet = (MethodAddFunctionCodeCabinet) codeCabinet;
        methodAddFunctionCodeCabinet.restoreContent(theModel, methodAddStorageFormat);
        MethodAddFunctionControlCabinet methodAddFunctionControlCabinet = (MethodAddFunctionControlCabinet) controlCabinet;
        methodAddFunctionControlCabinet.restoreContent(methodAddStorageFormat);
        setPropertyCombobox.setSelectedItem(
                FunctionUseProperty.getFunctionUsePropertyBy(methodAddStorageFormat.getSetProperty()));

    }

    /**
     * 获取该方法的储存格式
     *
     * @return
     */
    public MethodAddStorageFormat getMethodAddStorageFormat() {
        MethodAddStorageFormat operatingModel = new MethodAddStorageFormat();
        operatingModel.setShowText(controlCabinet.getShowText());
        operatingModel.setDefaultControlStatementFormat(controlCabinet.getDefaultControlStatementFormat());
        operatingModel.setHiddenControlStatementFormat(controlCabinet.getHiddenControlStatementFormat());
        operatingModel.setControlComponentCorrespondingInformation(
                AbstractEditContainerModel.getControlComponentCorrespondingInformationListJsonStr(theModel));
        operatingModel.setNumberOfComponents(GeneralOperatingModel.getUseComponentNumParam(theModel.getUseComponentNum()));
        operatingModel.setOrdinal(operatingOrdinalNumber);
        operatingModel.setCodeModelList(((MethodAddFunctionCodeCabinet) codeCabinet).getCodeModelList());
        int hiddenState = GeneralCommandOperatingModel.TRUE_;
        if (controlCabinet.getHiddenInputPane().getUseState() == false) {
            hiddenState = GeneralCommandOperatingModel.FALSE_;
        }
        operatingModel.setHiddenState(hiddenState);
        operatingModel.setNoteListParam(controlCabinet.getNoteListParam());
        operatingModel.setSetProperty(setPropertyCombobox.getSelectedItem().getSysDictionaryValue());

        return operatingModel;
    }

}
