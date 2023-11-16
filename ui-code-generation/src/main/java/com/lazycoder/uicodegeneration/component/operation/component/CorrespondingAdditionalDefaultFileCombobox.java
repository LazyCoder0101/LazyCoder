package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.service.vo.element.lable.control.CorrespondingAdditionalDefaultFileControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CorrespondingAdditionalDefaultFileMeta;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class CorrespondingAdditionalDefaultFileCombobox extends JComboBox<String> implements CodeGenerationComponentInterface,
        CodeGenerationFormatUIComonentInterface {

    private final int comboboxWidth = 100;

    private CorrespondingAdditionalDefaultFileControl controlElement = new CorrespondingAdditionalDefaultFileControl();

    private PathFind pathFind;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    public CorrespondingAdditionalDefaultFileCombobox() {
        // TODO Auto-generated constructor stub
        super();
        setPreferredSize(new Dimension(comboboxWidth,30));
        addPopupMenuListener(popupMenuListener);
    }

    public CorrespondingAdditionalDefaultFileCombobox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                                      CorrespondingAdditionalDefaultFileControl controlElement) {
        // TODO Auto-generated constructor stub
        this();
        this.controlElement = controlElement;
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;
    }


    public CorrespondingAdditionalDefaultFileCombobox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                                      CorrespondingAdditionalDefaultFileMeta meta) {
        this();
        this.controlElement = meta.getControlElement();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        this.pathFind = meta.getPathFind();

        if (!"".equals(meta.getSelectedFileName())) {
            addItem(meta.getSelectedFileName());
            setSelectedIndex(0);
        }
    }

    PopupMenuListener popupMenuListener = new PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            setThisModel();
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            updateValue();
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {

        }
    };


    private void setThisModel() {
        Object selectedItemTemp = getModel().getSelectedItem();
        if (CodeGenerationFrameHolder.codeShowPanel != null) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            ArrayList<String> tempList = CodeGenerationFrameHolder.codeShowPanel.
                    getCorrespondingAdditionalDefaultFileName(codeGenerationalOpratingContainerParam.getThisOpratingContainer().getAdditionalSerialNumber());
            for (String temp : tempList) {
                model.addElement(temp);
            }
            setModel(model);
            boolean flag = false;
            for (int i = 0; i < model.getSize(); i++) {
                if (model.getElementAt(i).equals(selectedItemTemp)) {
                    setSelectedIndex(i);
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                if (model.getSize() > 0) {
                    setSelectedIndex(0);
                }
            }
        }
    }


    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
        if (getSelectedItem() != null) {
            CodeGenerationStaticFunction.setValue(this,codeGenerationalOpratingContainerParam.getPaneType(), (String) getSelectedItem());
        }
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
    }

    @Override
    public CorrespondingAdditionalDefaultFileControl getControlElement() {
        return controlElement;
    }

    @Override
    public PathFind getPathFind() {
        return pathFind;
    }

    @Override
    public int getCodeSerialNumber() {
        return codeGenerationalOpratingContainerParam.getCodeSerialNumber();
    }

    @Override
    public AbstractOperatingPane getOperatingComponentPlacePane() {
        // TODO Auto-generated method stub
        return codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane();
    }

    @Override
    public void setParam(FormatStructureModelInterface model) {
        // TODO Auto-generated method stub
        CorrespondingAdditionalDefaultFileMeta theModel = (CorrespondingAdditionalDefaultFileMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(pathFind);
        if (getSelectedItem() != null) {
            theModel.setSelectedFileName((String) getSelectedItem());
        }
    }

    @Override
    public CorrespondingAdditionalDefaultFileMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        CorrespondingAdditionalDefaultFileMeta model = new CorrespondingAdditionalDefaultFileMeta();
        setParam(model);
        return model;
    }

    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return comboboxWidth;
    }

    @Override
    public void collapseThis() {

    }

}
