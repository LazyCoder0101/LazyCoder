package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.database.model.TheOptionModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.ContentChooseMeta;
import com.lazycoder.uiutils.component.CustomComboBox;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * 内容选择（单选）
 *
 * @author admin
 */
public class ContentChooseRadioCombobox extends CustomComboBox<String>
        implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {

    /**
     *
     */
    private static final long serialVersionUID = 6061597821556960576L;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    private PathFind pathFind;

    private ContentChooseControl controlElement;

    private int componentWidth = 0;

    private OptionDataModel option = null;

    /**
     * 新建
     *
     * @param codeGenerationalOpratingContainerParam
     * @param contentChooseControl
     */
    public ContentChooseRadioCombobox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                      ContentChooseControl contentChooseControl) {
        // TODO Auto-generated constructor stub
        super();

        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        this.controlElement = contentChooseControl;
        option = SysService.OPTION_SERVICE.getOptionById(controlElement.getOptionId());

        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;

        generateItems();
        setTheSize();

        addPopupMenuListener(listener);

        ArrayList<Integer> list = controlElement.getSelectList();

        for (Integer selectedTemp: list) {
            if (selectedTemp < option.getValueNum()) {
                setSelectedIndex(selectedTemp);
            } else {
                String text = "有个功能默认选\"" + this.controlElement.getOptionName() + "\"选项第" + (selectedTemp + 1) + "个选项值，可这个选项值被删掉了！	(✪ω✪)";
                String logtext = getClass() + "（添加功能异常）————\"" + this.controlElement.getOptionName() + "\"这个选项，当前有" + option.getValueNum() + "个选项值，有个单选组件却默认选第" + (selectedTemp + 1) + "个";
                CodeGenerationFrameHolder.errorLogging(text, logtext);

                setSelectedIndex(0);
            }
        }
        updateValue();
    }


    /**
     * 还原
     *
     * @param codeGenerationalOpratingContainerParam
     * @param contentChooseMeta
     */
    public ContentChooseRadioCombobox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                      ContentChooseMeta contentChooseMeta) {
        // TODO Auto-generated constructor stub
        super();

        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        this.controlElement = contentChooseMeta.getControlElement();
        option = SysService.OPTION_SERVICE.getOptionById(controlElement.getOptionId());
        this.pathFind = contentChooseMeta.getPathFind();

        generateItems();
        setTheSize();

        addPopupMenuListener(listener);

        ArrayList<Integer> list = contentChooseMeta.getSelectList();
        for (int i = 0; i < list.size(); i++) {
            Integer selectedTemp = list.get(i);
            if (selectedTemp < option.getValueNum()) {
                setSelectedIndex(selectedTemp);
            } else {
                String text = "有个功能选\"" + this.controlElement.getOptionName() + "\"选项第" + (selectedTemp + 1) + "个选项值，可这个选项值被删掉了！	(✪ω✪)";
                String logtext = getClass() + "（打开文件异常）————\"" + this.controlElement.getOptionName() + "\"这个选项，当前有" + option.getValueNum() + "个选项值，有个单选组件却选第" + (selectedTemp + 1) + "个";
                CodeGenerationFrameHolder.errorLogging(text, logtext);

                setSelectedIndex(0);
            }
        }
        updateValue();
    }

    @Override
    protected void init() {
        super.init();
        setRenderer(new CustomComboBoxTextSizeRenderer(6));
    }

    private PopupMenuListener listener = new PopupMenuListener() {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
            updateValue();
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }
    };

    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    private void generateItems() {
        setToolTipText(controlElement.getOptionName());
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        ArrayList<String> list = TheOptionModel.getOptionNameList(option);
        for (int i = 0; i < list.size(); i++) {
            model.addElement(list.get(i));
        }
        setModel(model);
    }

    @Override
    public void updateValue() {
        if (getSelectedIndex() >= 0) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(getSelectedIndex());
            OptionDataModel optionDataModel = SysService.OPTION_SERVICE.getOptionById(controlElement.getOptionId());

            String[] valueList = new String[optionDataModel.getValueListGroupNum()];
            for (int i = 0; i < optionDataModel.getValueListGroupNum(); i++) {
                valueList[i] = TheOptionModel.getValue(optionDataModel, i, list);
            }//获取全部第getSelectedIndex个的选项

//			String value = TheOptionModel.getValue(optionDataModel, 0, list);
            CodeGenerationStaticFunction.setValue(this,codeGenerationalOpratingContainerParam.getPaneType(), valueList);

        }
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
    }

    @Override
    public ContentChooseControl getControlElement() {
        // TODO Auto-generated method stub
        return controlElement;
    }

    @Override
    public PathFind getPathFind() {
        // TODO Auto-generated method stub
        return this.pathFind;
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

    public void setTheSize() {
        // TODO Auto-generated constructor stub
        int lenth = 0;
        String temp;
        for (int i = 0; i < getModel().getSize(); i++) {
            temp = getModel().getElementAt(i);
            if (temp.length() > lenth) {
                lenth = temp.length();
            }
        }
        componentWidth = 50 + lenth * 16;
        Dimension dd = new Dimension(componentWidth, 30);
        if (lenth > 5) {
            dd = new Dimension(120, 30);
            componentWidth = 120;
        }
        this.setPreferredSize(dd);
        this.setMinimumSize(dd);
        this.setMaximumSize(dd);
    }

    @Override
    public void setParam(FormatStructureModelInterface model) {
        // TODO Auto-generated method stub
        ContentChooseMeta theModel = (ContentChooseMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(pathFind);

        ArrayList<Integer> list = new ArrayList<>();
        if (getSelectedIndex() > 0) {
            list.add(getSelectedIndex());
        }
        theModel.setSelectList(list);

    }

    @Override
    public ContentChooseMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        ContentChooseMeta model = new ContentChooseMeta();
        setParam(model);
        return model;
    }

    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return componentWidth;
    }

    @Override
    public void collapseThis() {}

}
