package com.lazycoder.uidatasourceedit.inputmeta.editcontainer;

import com.lazycoder.database.model.command.InitCodeModel;
import com.lazycoder.database.model.command.InitOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.InitFeatureSelectonModel;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.InitMetaModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.InitCodeCabinet;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet.InitControlCabinet;
import com.lazycoder.uiutils.folder.MyContainerPane;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class InitEditContainer extends AbstractEditContainer {

    /**
     *
     */
    private static final long serialVersionUID = -6075122457314700185L;

    private static final double PROPORTION = 0.256;

    /**
     * 组件放置面板
     */
    private MyContainerPane componentPane;

    private JRadioButton setToDefaultButton;

    private MyButton addBt, delBt;

    public InitEditContainer() {
        super(15);
        // TODO Auto-generated constructor stub
        // blankPanelInit();
    }

    /**
     * 新建
     *
     * @param operatingOrdinalNumber
     */
    public InitEditContainer(int operatingOrdinalNumber) {
        this();
        this.operatingOrdinalNumber = operatingOrdinalNumber;
        controlPaneInit();
        controlCabinet.paneStyteInit(true);
        codePaneInit();
        addCodePane();
    }

    /**
     * 还原
     *
     * @param initMetaModel
     */
    public InitEditContainer(InitMetaModel initMetaModel) {
        this();
        this.operatingOrdinalNumber = initMetaModel.getOperatingModel().getOrdinal();
        controlPaneInit();
        controlCabinet.paneStyteInit(false);
        codePaneInit();
        theModel.reductionContent(initMetaModel.getOperatingModel());
        if (initMetaModel.getOperatingModel().getWhetherTheDefault() == InitOperatingModel.TRUE_) {
            setToDefaultButton.setSelected(true);
        }
        if (initMetaModel.getCodeModelList() != null) {
            ((InitCodeCabinet) codeCabinet).reductionContent(initMetaModel, theModel);
        }
        controlCabinet.reductionContent(initMetaModel.getOperatingModel());
        CommandCodeControl.updateCodePaneMenu(theModel);
    }

    public void controlPaneInit() {
        controlCabinet = new InitControlCabinet(theModel, this.operatingOrdinalNumber, true, PROPORTION) {
            @Override
            public void clearContainer() {
                InitEditContainer.this.clearAndBlankCodePane();
            }

            @Override
            public void restoreContainer() {
                InitFeatureSelectonModel featureSelectionModel = new InitFeatureSelectonModel();
                featureSelectionModel.setOrdinal(operatingOrdinalNumber);
                featureSelectionModel.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
                InitMetaModel theMetaModel = SysService.INIT_SERVICE.getModuleSetMetaModel(featureSelectionModel);
                if (theMetaModel != null) {
                    InitEditContainer.this.clear();
                    theModel.reductionContent(theMetaModel.getOperatingModel());
                    if (theMetaModel.getOperatingModel().getWhetherTheDefault() == InitOperatingModel.TRUE_) {
                        setToDefaultButton.setSelected(true);
                    }
                    ((InitCodeCabinet) codeCabinet).reductionContent(theMetaModel, theModel);
                    controlCabinet.reductionContent(theMetaModel.getOperatingModel());
                    CommandCodeControl.updateCodePaneMenu(theModel);

                    Container parent = InitEditContainer.this.getParent();
                    if (parent!=null){
                        parent.validate();
                    }
                }
            }
        };
        addContainer(controlCabinet);
    }

    public void setToDefault() {
        setToDefaultButton.setSelected(true);
    }

    public void codePaneInit() {
        componentPane = new MyContainerPane();
        addBt = new MyButton("添加");
        addBt.addActionListener(listener);
        addBt.setBounds(10, 0, 80, 30);
        componentPane.add(addBt);
        delBt = new MyButton("删除");
        delBt.addActionListener(listener);
        delBt.setBounds(100, 0, 80, 30);
        componentPane.add(delBt);
        setToDefaultButton = new JRadioButton("设为默认");
        setToDefaultButton.setFocusPainted(false);

        DataSourceEditHolder.defaultButtonGroup.add(setToDefaultButton);

        setToDefaultButton.setBounds(190, 0, 120, 30);
        componentPane.add(setToDefaultButton);
        tabs.add(componentPane);
        this.add(componentPane);
        codeCabinet = new InitCodeCabinet(theModel, operatingOrdinalNumber, true, PROPORTION);
        addContainer(codeCabinet);
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
            Window window = SwingUtilities.getWindowAncestor(InitEditContainer.this);
            if (window != null) {
                window.validate();
            }
        }
    };

    /**
     * 获取操作模型
     *
     * @return
     */
    public InitOperatingModel getOperatingModel() {
        InitOperatingModel operatingModel = new InitOperatingModel();
        operatingModel.setShowText(controlCabinet.getShowText());
        operatingModel.setDefaultControlStatementFormat(controlCabinet.getDefaultControlStatementFormat());
        operatingModel.setHiddenControlStatementFormat(controlCabinet.getHiddenControlStatementFormat());
        operatingModel.setHiddenState(controlCabinet.getHiddenState());
        operatingModel.setControlComponentCorrespondingInformation(
                AbstractEditContainerModel.getControlComponentCorrespondingInformationListJsonStr(theModel));
        operatingModel.setNumberOfComponents(GeneralOperatingModel.getUseComponentNumParam(theModel.getUseComponentNum()));
        operatingModel.setOrdinal(operatingOrdinalNumber);
        operatingModel.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
        operatingModel.setNoteListParam(controlCabinet.getNoteListParam());
        if (setToDefaultButton.isSelected() == true) {
            operatingModel.setWhetherTheDefault(InitOperatingModel.TRUE_);
        } else {
            operatingModel.setWhetherTheDefault(InitOperatingModel.FALSE_);
        }
        return operatingModel;
    }

    /**
     * 获取代码模型列表
     *
     * @return
     */
    public ArrayList<InitCodeModel> getCodeModelList() {
        ArrayList<InitCodeModel> codeModelList = ((InitCodeCabinet) codeCabinet).getCodeModelList();
        if (setToDefaultButton.isSelected() == true) {
            for (int i = 0; i < codeModelList.size(); i++) {
                codeModelList.get(i).setWhetherTheDefault(InitCodeModel.TRUE_);
            }
        } else {
            for (int i = 0; i < codeModelList.size(); i++) {
                codeModelList.get(i).setWhetherTheDefault(InitCodeModel.FALSE_);
            }
        }
        return codeModelList;
    }

    /**
     * 获取该初始化代码的条数
     *
     * @return
     */
    public int getCodeNum() {
        return codeCabinet.getCodeNum();
    }

}
