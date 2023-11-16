package com.lazycoder.uidatasourceedit.inputmeta.editcontainer;

import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.MainSetMetaModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.MainSetCodeCabinet;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.OperatingPropertyCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet.MainSetControlCabinet;
import com.lazycoder.uiutils.folder.MyContainerPane;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;


public class MainSetEditContainer extends AbstractEditContainer {

    /**
     *
     */
    private static final long serialVersionUID = -6075122457314700185L;

    private static final double PROPORTION = 0.34;

    /**
     * 组件放置面板
     */
    private MyContainerPane componentPane;

    private MyButton addBt, delBt;

    private OperatingPropertyCombobox setPropertyCombobox;

    /**
     * 设置序号
     */
    private int typeSerialNumber;

    public MainSetEditContainer() {
        super(15);
        // TODO Auto-generated constructor stub
    }

    /**
     * 新建
     *
     * @param typeSerialNumber
     * @param operatingOrdinalNumber
     */
    public MainSetEditContainer(int typeSerialNumber, int operatingOrdinalNumber) {
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
     * @param mainSetMetaModel
     */
    public MainSetEditContainer(MainSetMetaModel mainSetMetaModel) {
        this();
        this.operatingOrdinalNumber = mainSetMetaModel.getOperatingModel().getOrdinal();
        this.typeSerialNumber = mainSetMetaModel.getOperatingModel().getTypeSerialNumber();

        controlPaneModuleSet();
        controlCabinet.paneStyteInit(false);
        codePaneModuleSet();
        theModel.reductionContent(mainSetMetaModel.getOperatingModel());
        ArrayList<FormatTypeCodeModel> codeList;
        if (mainSetMetaModel.getCodeModelList() != null) {
            codeList = mainSetMetaModel.getCodeModelList();
            ((MainSetCodeCabinet) codeCabinet).reductionContent(codeList, theModel, this.operatingOrdinalNumber);
        }

        controlCabinet.reductionContent(mainSetMetaModel.getOperatingModel());
        CommandCodeControl.updateCodePaneMenu(theModel);
        setPropertyCombobox.setSelectedItem(
                FunctionUseProperty.getFunctionUsePropertyBy(mainSetMetaModel.getOperatingModel().getSetProperty()));

    }

    public void controlPaneModuleSet() {
        controlCabinet = new MainSetControlCabinet(typeSerialNumber, theModel, this.operatingOrdinalNumber, true, PROPORTION) {
            @Override
            public void clearContainer() {
                MainSetEditContainer.this.clearAndBlankCodePane();
            }

            @Override
            public void restoreContainer() {
                FormatTypeFeatureSelectionModel featureSelectionModel = FormatTypeFeatureSelectionModel.getMainFormatTypeFeatureSelectionModel();
                featureSelectionModel.setTypeSerialNumber(typeSerialNumber);
                featureSelectionModel.setOrdinal(operatingOrdinalNumber);
                MainSetMetaModel theMetaModel = SysService.MAIN_SET_SERVICE.getMainSetMetaModel(featureSelectionModel);
                if (theMetaModel != null) {
                    MainSetEditContainer.this.clear();
                    theModel.reductionContent(theMetaModel.getOperatingModel());

                    ArrayList<FormatTypeCodeModel> codeList;
                    if (theMetaModel.getCodeModelList() != null) {
                        codeList = theMetaModel.getCodeModelList();
                        ((MainSetCodeCabinet) codeCabinet).reductionContent(codeList, theModel, operatingOrdinalNumber);
                    }
                    controlCabinet.reductionContent(theMetaModel.getOperatingModel());
                    setPropertyCombobox.setSelectedItem(
                            FunctionUseProperty.getFunctionUsePropertyBy(theMetaModel.getOperatingModel().getSetProperty()));
                    CommandCodeControl.updateCodePaneMenu(theModel);

                    Container parent = MainSetEditContainer.this.getParent();
                    if (parent!=null){
                        parent.validate();
                    }
                }
            }
        };
        addContainer(controlCabinet);
    }

    @Override
    public void clearAndBlankCodePane() {
        super.clearAndBlankCodePane();
        setPropertyCombobox.clearInit();
    }

    public void codePaneModuleSet() {
        componentPane = new MyContainerPane();

        JLabel label = new JLabel("使用限制：");
        label.setBounds(190, 0, 80, 30);
        componentPane.add(label);
        setPropertyCombobox = OperatingPropertyCombobox.creatSetPropertyCombobox();
        setPropertyCombobox.setBounds(260, 0, 200, 30);
        componentPane.add(setPropertyCombobox);

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

        codeCabinet = new MainSetCodeCabinet(theModel, this.typeSerialNumber, this.operatingOrdinalNumber, true, PROPORTION);
        addContainer(codeCabinet);
    }

    public void addNewCodeCabinet() {
        codeCabinet.addCodePane(theModel);
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


    /**
     * 获取操作模型
     *
     * @return
     */
    public FormatTypeOperatingModel getOperatingModel() {
        FormatTypeOperatingModel operatingModel = FormatTypeOperatingModel.getMainFormatTypeOperatingModel();
        operatingModel.setTypeSerialNumber(this.typeSerialNumber);
        operatingModel.setOrdinal(operatingOrdinalNumber);
        operatingModel.setShowText(controlCabinet.getShowText());
        operatingModel.setDefaultControlStatementFormat(controlCabinet.getDefaultControlStatementFormat());
        operatingModel.setHiddenControlStatementFormat(controlCabinet.getHiddenControlStatementFormat());
        operatingModel.setHiddenState(controlCabinet.getHiddenState());
        operatingModel.setSetProperty(setPropertyCombobox.getSelectedItem().getSysDictionaryValue());
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
    public ArrayList<FormatTypeCodeModel> getCodeModelList() {
        return ((MainSetCodeCabinet) codeCabinet).getCodeModelList();
    }

    /**
     * 获取代码总数
     */
    public int getMainSetCodeNum() {
        return ((MainSetCodeCabinet) codeCabinet).getMainSetCodeNum();
    }

}
