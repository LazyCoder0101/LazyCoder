package com.lazycoder.uidatasourceedit.inputmeta.editcontainer;

import com.lazycoder.database.model.command.ModuleSetCodeModel;
import com.lazycoder.database.model.command.ModuleSetOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.ModuleSetFeatureSelectionModel;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.ModuleSetMetaModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.ModuleSetCodeCabinet;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.OperatingPropertyCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet.ModuleSetControlCabinet;
import com.lazycoder.uiutils.folder.MyContainerPane;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;

public class ModuleSetEditContainer extends AbstractEditContainer {

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

    private OperatingPropertyCombobox setPropertyCombobox;

    /**
     * 模块设置序号
     */
    private int typeSerialNumber;

    public ModuleSetEditContainer() {
        super(15);
        // TODO Auto-generated constructor stub
    }

    /**
     * 新建
     *
     * @param typeSerialNumber
     * @param operatingOrdinalNumber
     */
    public ModuleSetEditContainer(int typeSerialNumber, int operatingOrdinalNumber) {
        this();
        this.operatingOrdinalNumber = operatingOrdinalNumber;
        this.typeSerialNumber = typeSerialNumber;
        controlPaneModuleSet();
        controlCabinet.paneStyteInit(true);
        codePaneModuleSet();
        addNewCodeCabinet();
    }

    /**
     * 还原
     *
     * @param moduleSetMetaModel
     */
    public ModuleSetEditContainer(ModuleSetMetaModel moduleSetMetaModel) {
        this();
        this.operatingOrdinalNumber = moduleSetMetaModel.getOperatingModel().getOrdinal();
        this.typeSerialNumber = moduleSetMetaModel.getOperatingModel().getTypeSerialNumber();

        controlPaneModuleSet();
        controlCabinet.paneStyteInit(false);
        codePaneModuleSet();
        theModel.reductionContent(moduleSetMetaModel.getOperatingModel());
        ArrayList<ModuleSetCodeModel> codeList;
        if (moduleSetMetaModel.getCodeModelList() != null) {
            codeList = moduleSetMetaModel.getCodeModelList();
            ((ModuleSetCodeCabinet) codeCabinet).reductionContent(codeList, theModel, this.operatingOrdinalNumber);
        }
        controlCabinet.reductionContent(moduleSetMetaModel.getOperatingModel());
        setPropertyCombobox.setSelectedItem(
                FunctionUseProperty.getFunctionUsePropertyBy(moduleSetMetaModel.getOperatingModel().getSetProperty()));
        CommandCodeControl.updateCodePaneMenu(theModel);
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

    @Override
    public void clearAndBlankCodePane() {
        super.clearAndBlankCodePane();
        setPropertyCombobox.clearInit();
    }

    public void controlPaneModuleSet() {
        controlCabinet = new ModuleSetControlCabinet(this.typeSerialNumber, theModel, this.operatingOrdinalNumber, true, PROPORTION) {
            @Override
            public void clearContainer() {
                ModuleSetEditContainer.this.clearAndBlankCodePane();
            }

            @Override
            public void restoreContainer() {
                ModuleSetFeatureSelectionModel featureSelectionModel = new ModuleSetFeatureSelectionModel();
                featureSelectionModel.setTypeSerialNumber(typeSerialNumber);
                featureSelectionModel.setOrdinal(operatingOrdinalNumber);
                featureSelectionModel.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
                ModuleSetMetaModel theMetaModel = SysService.MODULE_SET_SERVICE.getModuleSetMetaModel(featureSelectionModel);
                if (theMetaModel != null) {
                    ModuleSetEditContainer.this.clear();
                    theModel.reductionContent(theMetaModel.getOperatingModel());

                    ArrayList<ModuleSetCodeModel> codeList;
                    if (theMetaModel.getCodeModelList() != null) {
                        codeList = theMetaModel.getCodeModelList();
                        ((ModuleSetCodeCabinet) codeCabinet).reductionContent(codeList, theModel, operatingOrdinalNumber);
                    }
                    controlCabinet.reductionContent(theMetaModel.getOperatingModel());
                    setPropertyCombobox.setSelectedItem(
                            FunctionUseProperty.getFunctionUsePropertyBy(theMetaModel.getOperatingModel().getSetProperty()));
                    CommandCodeControl.updateCodePaneMenu(theModel);

                    Container parent = ModuleSetEditContainer.this.getParent();
                    if (parent!=null){
                        parent.validate();
                    }
                }
            }
        };
        addContainer(controlCabinet);
    }

    public void codePaneModuleSet() {
        componentPane = new MyContainerPane();

        addBt = new MyButton("添加");
        addBt.addActionListener(listener);
        addBt.setBounds(10, 0, 80, 30);
        componentPane.add(addBt);
        delBt = new MyButton("删除");
        delBt.addActionListener(listener);
        delBt.setBounds(100, 0, 80, 30);
        componentPane.add(delBt);

        JLabel label = new JLabel("使用限制：");
        label.setBounds(190, 0, 80, 30);
        componentPane.add(label);
        setPropertyCombobox = OperatingPropertyCombobox.creatSetPropertyCombobox();
        setPropertyCombobox.setBounds(250, 0, 190, 30);
        componentPane.add(setPropertyCombobox);

        tabs.add(componentPane);
        this.add(componentPane);

        codeCabinet = new ModuleSetCodeCabinet(theModel, this.typeSerialNumber, this.operatingOrdinalNumber, true, PROPORTION);
        addContainer(codeCabinet);
    }

    public void addNewCodeCabinet() {
        codeCabinet.addCodePane(theModel);
        CommandCodeControl.updateCodePaneMenu(theModel);
    }

    /**
     * 获取操作模型
     *
     * @return
     */
    public ModuleSetOperatingModel getOperatingModel() {
        ModuleSetOperatingModel operatingModel = new ModuleSetOperatingModel();
        operatingModel.setTypeSerialNumber(this.typeSerialNumber);
        operatingModel.setOrdinal(operatingOrdinalNumber);
        operatingModel.setClassName(DataSourceEditHolder.currentModule.getClassName());
        operatingModel.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
        operatingModel.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
        operatingModel.setShowText(controlCabinet.getShowText());
        operatingModel.setDefaultControlStatementFormat(controlCabinet.getDefaultControlStatementFormat());
        operatingModel.setHiddenControlStatementFormat(controlCabinet.getHiddenControlStatementFormat());
        operatingModel.setHiddenState(controlCabinet.getHiddenState());
        operatingModel.setSetProperty(setPropertyCombobox.getSelectedItem().getSysDictionaryValue());
        operatingModel.setTypeSerialNumber(this.typeSerialNumber);
        operatingModel.setControlComponentCorrespondingInformation(
                AbstractEditContainerModel.getControlComponentCorrespondingInformationListJsonStr(theModel));
        operatingModel.setNumberOfComponents(GeneralOperatingModel.getUseComponentNumParam(theModel.getUseComponentNum()));
        operatingModel.setNoteListParam(controlCabinet.getNoteListParam());
        return operatingModel;
    }

    /**
     * 获取代码模型列表
     *
     * @return
     */
    public ArrayList<ModuleSetCodeModel> getCodeModelList() {
        return ((ModuleSetCodeCabinet) codeCabinet).getCodeModelList();
    }

    /**
     * 获取代码总数
     */
    public int getModuleSetCodeNum() {
        return ((ModuleSetCodeCabinet) codeCabinet).getModuleSetCodeNum();
    }

}
