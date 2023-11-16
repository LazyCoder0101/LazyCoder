package com.lazycoder.uidatasourceedit.inputmeta.editcontainer;

import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.database.model.command.FunctionOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.FunctionFeatureSelectionModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.FunctionMetaModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.FunctionCodeCabinet;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.OperatingPropertyCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet.FunctionControlCabinet;
import com.lazycoder.uiutils.folder.MyContainerPane;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;


public class FunctionEditContainer extends AbstractEditContainer {

    /**
     *
     */
    private static final long serialVersionUID = -6075122457314700185L;

    private static final double PROPORTION = 0.29;

    /**
     * 组件放置面板
     */
    private MyContainerPane componentPane;

    private MyButton addBt, delBt;

    private OperatingPropertyCombobox propertyCombobox;

    /**
     * 模块设置序号
     */
    private int typeSerialNumber = 0;

    public FunctionEditContainer() {
        super(15);
    }

    /**
     * 新建
     *
     * @param operatingOrdinalNumber
     */
    public FunctionEditContainer(int typeSerialNumber, int operatingOrdinalNumber) {
        this();
        this.typeSerialNumber = typeSerialNumber;
        this.operatingOrdinalNumber = operatingOrdinalNumber;
        controlPaneInit();
        controlCabinet.paneStyteInit(true);
        codePaneInit();
        addNewCodeCabinet();
    }

    /**
     * 还原
     *
     * @param typeSerialNumber
     * @param metaModel
     */
    public FunctionEditContainer(int typeSerialNumber, FunctionMetaModel metaModel) {
        this();
        this.typeSerialNumber = typeSerialNumber;
        this.operatingOrdinalNumber = metaModel.getOperatingModel().getOrdinal();
        controlPaneInit();
        controlCabinet.paneStyteInit(false);
        codePaneInit();
        theModel.reductionContent(metaModel.getOperatingModel());
        ArrayList<FunctionCodeModel> codeList;
        if (metaModel.getCodeModelList() != null) {
            codeList = metaModel.getCodeModelList();
            ((FunctionCodeCabinet) codeCabinet).reductionContent(codeList, theModel, operatingOrdinalNumber);
        }
        controlCabinet.reductionContent(metaModel.getOperatingModel());
        propertyCombobox.setSelectedItem(FunctionUseProperty.getFunctionUsePropertyBy(metaModel.getOperatingModel().getSetProperty()));
        CommandCodeControl.updateCodePaneMenu(theModel);
    }

    public void addNewCodeCabinet() {
        codeCabinet.addCodePane(theModel);
        CommandCodeControl.updateCodePaneMenu(theModel);
    }

    public void controlPaneInit() {
        controlCabinet = new FunctionControlCabinet(typeSerialNumber, theModel, this.operatingOrdinalNumber, true, PROPORTION) {
            @Override
            public void restoreContainer() {
                FunctionFeatureSelectionModel featureSelectionModel = new FunctionFeatureSelectionModel();
                featureSelectionModel.setTypeSerialNumber(typeSerialNumber);
                featureSelectionModel.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
                featureSelectionModel.setOrdinal(operatingOrdinalNumber);
                FunctionMetaModel theMetaModel = SysService.FUNCTION_SERVICE.getFunctionMetaModel(featureSelectionModel);
                if (theMetaModel!=null){
                    FunctionEditContainer.this.clear();
                    theModel.reductionContent(theMetaModel.getOperatingModel());
                    ArrayList<FunctionCodeModel> codeList;
                    if (theMetaModel.getCodeModelList() != null) {
                        codeList = theMetaModel.getCodeModelList();
                        ((FunctionCodeCabinet) codeCabinet).reductionContent(codeList, theModel, operatingOrdinalNumber);
                    }
                    controlCabinet.reductionContent(theMetaModel.getOperatingModel());
                    propertyCombobox.setSelectedItem(FunctionUseProperty.getFunctionUsePropertyBy(theMetaModel.getOperatingModel().getSetProperty()));
                    CommandCodeControl.updateCodePaneMenu(theModel);

                    Container parent = FunctionEditContainer.this.getParent();
                    if (parent!=null){
                        parent.validate();
                    }
                }
            }

            @Override
            public void clearContainer() {
                FunctionEditContainer.this.clearAndBlankCodePane();
            }
        };
        addContainer(controlCabinet);
    }

    public void codePaneInit() {
        componentPane = new MyContainerPane();

        JLabel label = new JLabel("使用限制：");
        label.setBounds(190, 0, 80, 30);
        componentPane.add(label);
        propertyCombobox = OperatingPropertyCombobox.creatBusinessPropertyCombobox();
        propertyCombobox.setBounds(260, 0, 160, 30);
        componentPane.add(propertyCombobox);

        addBt = new MyButton("添加");
        addBt.addActionListener(listener);
        addBt.setBounds(10, 0, 80, 30);
        componentPane.add(addBt);
        delBt = new MyButton("删除");
        delBt.addActionListener(listener);
        delBt.setBounds(100, 0, 80, 30);
        componentPane.add(delBt);

        tabs.add(componentPane);
        this.add(componentPane);

        codeCabinet = new FunctionCodeCabinet(typeSerialNumber, theModel, operatingOrdinalNumber, true, PROPORTION);
        codeCabinet.setCodeJspHeight(PROPORTION, 130);
        addContainer(codeCabinet);
    }

    @Override
    public void clearAndBlankCodePane() {
        super.clearAndBlankCodePane();
        propertyCombobox.clearInit();
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
     * 获取操作模型
     *
     * @return
     */
    public FunctionOperatingModel getOperatingModel() {
        FunctionOperatingModel operatingModel = new FunctionOperatingModel();
        operatingModel.setShowText(controlCabinet.getShowText());
        operatingModel.setDefaultControlStatementFormat(controlCabinet.getDefaultControlStatementFormat());
        operatingModel.setHiddenControlStatementFormat(controlCabinet.getHiddenControlStatementFormat());
        operatingModel.setHiddenState(controlCabinet.getHiddenState());
        operatingModel.setControlComponentCorrespondingInformation(
                AbstractEditContainerModel.getControlComponentCorrespondingInformationListJsonStr(theModel));
        operatingModel.setNumberOfComponents(GeneralOperatingModel.getUseComponentNumParam(theModel.getUseComponentNum()));
        operatingModel.setOrdinal(operatingOrdinalNumber);
        operatingModel.setClassName(DataSourceEditHolder.currentModule.getClassName());
        operatingModel.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
        operatingModel.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
        operatingModel.setTypeSerialNumber(this.typeSerialNumber);
        operatingModel.setNoteListParam(controlCabinet.getNoteListParam());
        operatingModel.setSetProperty(propertyCombobox.getSelectedItem().getSysDictionaryValue());
        return operatingModel;
    }

    /**
     * 获取代码模型
     *
     * @return
     */
    public ArrayList<FunctionCodeModel> getCodeModelList() {
        return ((FunctionCodeCabinet) codeCabinet).getCodeModelList();
    }

}
