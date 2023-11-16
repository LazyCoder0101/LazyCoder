package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.OptionDataModelTempHolder;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.base.BaseOperatingPane;
import com.lazycoder.service.vo.base.StringElement;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.ContentChooseElement;
import com.lazycoder.service.vo.element.lable.code.ContentChooseCodeElement;
import com.lazycoder.service.vo.element.lable.control.CodeInputControl;
import com.lazycoder.service.vo.element.lable.control.ConstantControl;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.service.vo.element.lable.control.CorrespondingAdditionalDefaultFileControl;
import com.lazycoder.service.vo.element.lable.control.CustomMethodNameControl;
import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.service.vo.element.lable.control.FileSelectorControl;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.element.lable.control.MethodChooseControl;
import com.lazycoder.service.vo.element.lable.control.NoteControl;
import com.lazycoder.service.vo.element.lable.control.PictureControl;
import com.lazycoder.service.vo.element.lable.control.TextInputControl;
import com.lazycoder.service.vo.element.lable.control.VariableControl;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.component.ContentChooseControlMenuItem;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.component.LableMenuItem;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.ControlCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.ScucheonComponentInterface;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CodeInputControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.ConstantControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CorrespondingAdditionalDefaultFileControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CustomMethodNameControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CustomVariableControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FileSelectorControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FunctionAddControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.MethodChooseControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.MultiComboboxControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.NoteControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.PictureControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.RadioComboboxControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.TextInputControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose.ContentChooseFrameUpdateForBaseControlTextFrame;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipMenuItem;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;


//https://blog.csdn.net/geman1985/article/details/102776790
public abstract class BaseControlTextPane extends BaseTextPane implements BaseOperatingPane {

    private static final long serialVersionUID = 8767601690734330815L;

    protected JMenu addLabelMenu = new JMenu("添加组件"), addGeneralContentChooseMenu = new JMenu("添加通用选择"),
            addModuleContentChooseMenu = new JMenu("添加对应选择"),
            updateContentChooseContentMenu = new JMenu("更改选择内容"), delLabelMenu = new JMenu("删除组件"),
            delCorrespondingContentChooseByDatabaseMenu = new JMenu("删除对应选择（从系统中彻底删除）");

    /************** "添加"菜单项的内容 ********************************/
    protected JMenuItem addTextInputMenu = new JMenuItem("内容输入"), addContentChooseMenu = new JMenuItem("选择"),
            addFunctionAddMenu = new JMenuItem("功能拓展"), addCustomVariableMenu = new JMenuItem("自定义变量"),
            addVariableMenu = new JMenuItem("变量选择"), addConstantMenu = new JMenuItem("常量数组"),
            addFileSelectorMenu = new JMenuItem("文件选择"), addNoteMenu = new JMenuItem("注释"),
            addPictureMenu = new JMenuItem("图片"), addCodeInputMenu = new JMenuItem("代码输入"),
            addCustomMethodNameMenu = new JMenuItem("自定义方法名"), addMethodChooseMenu = new JMenuItem("方法选择"),

    addInfrequentlyUsedSettingMenu = new JMenuItem("不常用设置"),
            addDefaultFileMenu = new JMenuItem("所添加该可选模板的默认文件名");

    /****************************************************************/

    /************** "删除"菜单项的内容 ********************************/
    protected JMenu delTextInputMenu = new JMenu("内容输入"), delContentChooseMenu = new JMenu("选择"),
            delFunctionAddMenu = new JMenu("功能拓展"), delCustomVariableMenu = new JMenu("自定义变量"),
            delVariableMenu = new JMenu("变量选择"), delConstantMenu = new JMenu("常量数组"),
            delFileSelectorMenu = new JMenu("文件选择"), delNoteMenu = new JMenu("注释"),
            delPictureMenu = new JMenu("图片"), delCodeInputMenu = new JMenu("代码输入"),
            delCustomMethodNameMenu = new JMenu("自定义方法名"), delMethodChooseMenu = new JMenu("方法选择"),

    delInfrequentlyUsedSettingMenu = new JMenu("不常用设置"),
            delDefaultFileMenu = new JMenu("所添加该可选模板的默认文件名");

    private OperatingTipMenuItem operatingTipMenuItem;

    /****************************************************************/

    public BaseControlTextPane() {
        // TODO Auto-generated constructor stub
        super();
    }

    protected ActionListener updateContentChooseContentMenuItemListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            ContentOptionMenuItem menuItem = (ContentOptionMenuItem) e.getSource();
            new ContentChooseFrameUpdateForBaseControlTextFrame(menuItem.getOptionDataModel().getOptionId());
        }
    };

    /**
     * 模块对应内容选择的菜单项的监听
     */
    protected ActionListener addModuleContentChooseMenuItemListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            ContentOptionMenuItem menuItem = (ContentOptionMenuItem) e.getSource();
            String optionId = menuItem.getOptionDataModel().getOptionId();
            addContentChoose(optionId);
        }
    };

    /**
     * 删除对应内容选择组件（从数据库）
     */
    protected ActionListener delCorrespondingContentChooseByDatabaseMenuItemListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            ContentOptionMenuItem menuItem = (ContentOptionMenuItem) e.getSource();
            String optionName = menuItem.getText();

            int ii = LazyCoderOptionPane.showConfirmDialog(null,
                    "确定要完全删除\"" + optionName + "\"选项吗？\n删除的话，其他用到这个选项的代码面板也会删掉对应选项哦",
                    "系统信息", JOptionPane.YES_NO_OPTION);
            if (ii == JOptionPane.YES_OPTION) {
                OptionDataModel option = menuItem.getOptionDataModel();
                if (option != null) {
                    DataSourceEditHolder.delCorrespondingContentChooseModel(option);//把对应可以添加这个选项的面板，里面的选项组件，还有方法组件和不常用组件里面这个选项的数据都删掉
                    SysService.OPTION_SERVICE.delOptionById(option.getOptionId());
                    LazyCoderOptionPane.showMessageDialog(null, "已经删掉\"" + optionName + "\"选项了！\n记得写完以后要保存喔，不然可能会出错的	ヽ(￣▽￣)ノ");
                    OptionDataModelTempHolder.delOption(option.getOptionId());
                }
            }
        }
    };

    private ActionListener generalContentChooseMenuItemListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            ContentOptionMenuItem menuItem = (ContentOptionMenuItem) e.getSource();
            String optionId = menuItem.getOptionDataModel().getOptionId();
            addContentChoose(optionId);
        }
    };


    @Override
    public void clear() {
        deleteAllComponents();
        setText("");
        delTextInputMenu.removeAll();
        delContentChooseMenu.removeAll();
        delFunctionAddMenu.removeAll();
        delCustomVariableMenu.removeAll();
        delVariableMenu.removeAll();
        delConstantMenu.removeAll();
        delFileSelectorMenu.removeAll();
        delNoteMenu.removeAll();
        delPictureMenu.removeAll();
        delCodeInputMenu.removeAll();
        delCustomMethodNameMenu.removeAll();
        delMethodChooseMenu.removeAll();
        if (delInfrequentlyUsedSettingMenu != null) {
            delInfrequentlyUsedSettingMenu.removeAll();
        }
        if (delDefaultFileMenu != null) {
            delDefaultFileMenu.removeAll();
        }
    }

    protected void menuInit(ControlCondition controlCondition) {
        theMenu.removeAll();
        theMenu.add(addLabelMenu);
        theMenu.add(addGeneralContentChooseMenu);
        theMenu.add(addModuleContentChooseMenu);
        theMenu.add(delLabelMenu);

        theMenu.addSeparator();

        if (controlCondition.isUpdateContentChooseMenu() == true) {
            theMenu.add(updateContentChooseContentMenu);
        }
        if (controlCondition.isDelContentChooseFromDBMenu() == true) {
            theMenu.add(delCorrespondingContentChooseByDatabaseMenu);
        }

        addLabelMenu.add(addTextInputMenu);
        addLabelMenu.add(addContentChooseMenu);
        addLabelMenu.add(addFunctionAddMenu);
        addLabelMenu.add(addCustomVariableMenu);
        addLabelMenu.add(addVariableMenu);
        addLabelMenu.add(addConstantMenu);
        addLabelMenu.add(addFileSelectorMenu);
        addLabelMenu.add(addNoteMenu);
        addLabelMenu.add(addPictureMenu);
        addLabelMenu.add(addCodeInputMenu);
        addLabelMenu.add(addCustomMethodNameMenu);
        addLabelMenu.add(addMethodChooseMenu);

        // removeListener();

        delLabelMenu.add(delTextInputMenu);
        delLabelMenu.add(delContentChooseMenu);
        delLabelMenu.add(delFunctionAddMenu);
        delLabelMenu.add(delCustomVariableMenu);
        delLabelMenu.add(delVariableMenu);
        delLabelMenu.add(delConstantMenu);
        delLabelMenu.add(delFileSelectorMenu);
        delLabelMenu.add(delNoteMenu);
        delLabelMenu.add(delPictureMenu);
        delLabelMenu.add(delCodeInputMenu);
        delLabelMenu.add(delCustomMethodNameMenu);
        delLabelMenu.add(delMethodChooseMenu);

        theMenu.addSeparator();

        theMenu.add(copyMenu);
        theMenu.add(cutMenu);
        theMenu.add(pasteMenu);

        theMenu.addSeparator();
        operatingTipMenuItem = new OperatingTipMenuItem(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明").getAbsolutePath()
        );
        operatingTipMenuItem.setText("组件操作提示");
        theMenu.add(operatingTipMenuItem);

        if (controlCondition.isInfrequentlyUsedSettingState() == true) {
            addLabelMenu.add(addInfrequentlyUsedSettingMenu);
            delLabelMenu.add(delInfrequentlyUsedSettingMenu);
        }
        if (controlCondition.isCorrespondingAdditionalDefaultFileState() == true) {
            addLabelMenu.add(addDefaultFileMenu);
            delLabelMenu.add(delDefaultFileMenu);
        }
    }


    /**
     * 根据文字、标签、标记获取设置参数
     *
     * @return
     */
    public ArrayList<String> getUsageReminderList() {
        int end = 0;
        ArrayList<String> list = new ArrayList<>();
        Component cTemp;
        Element e0;
        NoteControlLabel noteControlLabel;
        NoteControl noteControl;
        InfrequentlyUsedSettingControl infrequentlyUsedSettingControl;
        ArrayList<BaseElementInterface> elementList;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());
                if (cTemp instanceof NoteControlLabel) {
                    noteControlLabel = (NoteControlLabel) cTemp;
                    if (noteControlLabel.isAsAUsageReminder()) {
                        list.add(noteControlLabel.getNote());
                    }
                } else if (cTemp instanceof InfrequentlyUsedSettingControlLabel) {
                    infrequentlyUsedSettingControl = ((InfrequentlyUsedSettingControlLabel) cTemp).getControl();
                    elementList = DeserializeElementMethods.getControlPaneElmentList(infrequentlyUsedSettingControl.getControlStatementFormat());
                    if (elementList != null) {
                        for (BaseElementInterface element : elementList) {
                            if (element instanceof NoteControl) {
                                noteControl = (NoteControl) element;
                                if (noteControl.isAsAUsageReminder()) {
                                    list.add(noteControl.getNote());
                                }
                            }
                        }
                    }
                }
            }
            end = e0.getEndOffset();
        }
        return list;
    }

    /**
     * 显示通用信息菜单项
     */
    private void showGeneralChooseMenuItems() {
        addGeneralContentChooseMenu.removeAll();
        List<OptionDataModel> generalNameList = SysService.OPTION_SERVICE.getGeneralOptionNameList();
        if (generalNameList != null) {
            ContentOptionMenuItem menuItem;
            for (OptionDataModel optionDataModelTemp : generalNameList) {
                menuItem = new ContentOptionMenuItem(optionDataModelTemp);
                menuItem.addActionListener(generalContentChooseMenuItemListener);
                addGeneralContentChooseMenu.add(menuItem);
            }
        }
    }

    /**
     * 显示更改对应的内容选择菜单项
     */
    private void showUpdateChooseMenuItems() {
        updateContentChooseContentMenu.removeAll();
        List<OptionDataModel> listTemp = getUpdateChooseMenuList();
        if (listTemp != null) {
            ContentOptionMenuItem menuItem;
            for (OptionDataModel optionDataModelTemp : listTemp) {
                menuItem = new ContentOptionMenuItem(optionDataModelTemp);
                menuItem.addActionListener(updateContentChooseContentMenuItemListener);
                updateContentChooseContentMenu.add(menuItem);
            }
        }
    }

    /**
     * 显示删除对应选择（从数据库）的菜单项
     */
    private void showDelCorrespondingChooseFromDBMenuItems() {
        delCorrespondingContentChooseByDatabaseMenu.removeAll();
        List<OptionDataModel> listTemp = getDelCorrespondingChooseFromDBMenuList();
        if (listTemp != null) {
            ContentOptionMenuItem menuItem;
            for (OptionDataModel optionDataModelTemp : listTemp) {
                menuItem = new ContentOptionMenuItem(optionDataModelTemp);
                menuItem.addActionListener(delCorrespondingContentChooseByDatabaseMenuItemListener);
                delCorrespondingContentChooseByDatabaseMenu.add(menuItem);
            }
        }
    }

    @Override
    protected void showPopupMenu(Component invoker, int x, int y) {
        // TODO Auto-generated method stub
        showGeneralChooseMenuItems();
        showAddCorrespondingChooseMenuItems();
        showUpdateChooseMenuItems();
        showDelCorrespondingChooseFromDBMenuItems();
        super.showPopupMenu(invoker, x, y);
    }

    /**
     * 添加对应选项
     */
    protected void showAddCorrespondingChooseMenuItems() {
        addModuleContentChooseMenu.removeAll();
        List<OptionDataModel> listTemp = getCorrespondingChooseMenuList();
        if (listTemp != null) {
            ContentOptionMenuItem menuItem;
            for (OptionDataModel optionDataModelTemp : listTemp) {
                menuItem = new ContentOptionMenuItem(optionDataModelTemp);
                menuItem.addActionListener(addModuleContentChooseMenuItemListener);
                addModuleContentChooseMenu.add(menuItem);
            }
        }
    }

    /**
     * 删除ID为optionId的选项
     *
     * @param optionId
     */
    @Override
    public void delContentChoose(String optionId) {
        setDelContentChooseItemEnabledFalse(optionId);
        deleteContentChooseLabel(optionId);
    }

    /**
     * 点击删除内容选择的菜单后的操作
     *
     * @param optionId
     * @param useNumbered
     */
    protected void delContentChoose(String optionId, int useNumbered) {
        setDelContentChooseItemEnabledFalse(optionId, useNumbered);
        deleteContentChooseLabel(optionId, useNumbered);
    }

    /**
     * 删除内容选择组件
     *
     * @param optionId
     * @param useNumbered
     */
    public void deleteContentChooseLabel(String optionId, int useNumbered) {
        int end = 0;
        Component a;
        RadioComboboxControlLabel radioComboboxLabel;
        MultiComboboxControlLabel multiComboboxLabel;
        Element e0;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());

                if (a instanceof RadioComboboxControlLabel) {
                    radioComboboxLabel = (RadioComboboxControlLabel) a;
                    if (radioComboboxLabel.getControl().getUseNumbered() == useNumbered) {
                        if (radioComboboxLabel.getControl().getOptionId().equals(optionId)) {
                            radioComboboxLabel.deleteFromPanel();
                            try {
                                doc.remove(end, e0.getEndOffset() - end);
                            } catch (BadLocationException e1) {
                                // TODO 自动生成的 catch 块
                                e1.printStackTrace();
                            }
                        }
                    }
                } else if (a instanceof MultiComboboxControlLabel) {
                    multiComboboxLabel = (MultiComboboxControlLabel) a;
                    if (multiComboboxLabel.getControl().getUseNumbered() == useNumbered) {
                        if (multiComboboxLabel.getControl().getOptionId().equals(optionId)) {
                            multiComboboxLabel.deleteFromPanel();
                            try {
                                doc.remove(end, e0.getEndOffset() - end);
                            } catch (BadLocationException e1) {
                                // TODO 自动生成的 catch 块
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
            end = e0.getEndOffset();
        }
    }

    /**
     * 更改选项
     */
    @Override
    public void updateItems(OptionDataModel option) {
        int end = 0;
        Component a;
        RadioComboboxControlLabel radioComboboxLabel;
        MultiComboboxControlLabel multiComboboxLabel;
        Element e0;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());

                if (a instanceof RadioComboboxControlLabel) {
                    radioComboboxLabel = (RadioComboboxControlLabel) a;
                    if (radioComboboxLabel.getControl().getOptionId().equals(option.getOptionId())) {
                        radioComboboxLabel.updateItems(option);
                    }
                } else if (a instanceof MultiComboboxControlLabel) {
                    multiComboboxLabel = (MultiComboboxControlLabel) a;
                    if (multiComboboxLabel.getControl().getOptionId().equals(option.getOptionId())) {
                        multiComboboxLabel.updateItems(option);
                    }
                }
            }
            end = e0.getEndOffset();
        }
    }

    /**
     * 删除内容选择组件
     *
     * @param optionId
     */
    public void deleteContentChooseLabel(String optionId) {
        int end = 0;
        Component a;
        RadioComboboxControlLabel radioComboboxLabel;
        MultiComboboxControlLabel multiComboboxLabel;
        Element e0;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());

                if (a instanceof RadioComboboxControlLabel) {
                    radioComboboxLabel = (RadioComboboxControlLabel) a;
                    if (radioComboboxLabel.getControl().getOptionId().equals(optionId)) {
                        radioComboboxLabel.deleteFromPanel();
                        try {
                            doc.remove(end, e0.getEndOffset() - end);
                        } catch (BadLocationException e1) {
                            // TODO 自动生成的 catch 块
                            e1.printStackTrace();
                        }
                    }
                } else if (a instanceof MultiComboboxControlLabel) {
                    multiComboboxLabel = (MultiComboboxControlLabel) a;
                    if (multiComboboxLabel.getControl().getOptionId().equals(optionId)) {
                        multiComboboxLabel.deleteFromPanel();
                        try {
                            doc.remove(end, e0.getEndOffset() - end);
                        } catch (BadLocationException e1) {
                            // TODO 自动生成的 catch 块
                            e1.printStackTrace();
                        }
                    }
                }
            }
            end = e0.getEndOffset();
        }
    }

    /**
     * 把删除内容选择的菜单组件失能
     *
     * @param optionId
     * @param useNumbered
     */
    private void setDelContentChooseItemEnabledFalse(String optionId, int useNumbered) {
        ContentChooseControlMenuItem menuItem;
        for (int i = 0; i < delContentChooseMenu.getMenuComponentCount(); i++) {
            menuItem = (ContentChooseControlMenuItem) delContentChooseMenu.getItem(i);
            if (menuItem.getContentChooseElement().getOptionId().trim().equals(optionId)) {
                if (menuItem.isEnabled() == true) {//判断这里可避免一种极端情况，之前有添加同名的选项，然后删了，现在又删，此时之前添加的选项，对应菜单项失能
                    if (menuItem.getContentChooseElement().getUseNumbered() == useNumbered) {
                        menuItem.setEnabled(false);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 把删除内容选择的菜单组件失能
     *
     * @param optionId
     */
    private void setDelContentChooseItemEnabledFalse(String optionId) {
        ContentChooseControlMenuItem menuItem;
        for (int i = 0; i < delContentChooseMenu.getMenuComponentCount(); i++) {
            menuItem = (ContentChooseControlMenuItem) delContentChooseMenu.getItem(i);
            if (menuItem.getContentChooseElement().getOptionId().trim().equals(optionId)) {
                menuItem.setEnabled(false);
            }
        }
    }

    @Override
    public ContentChooseControl getContentChooseControlModel(ContentChooseCodeElement contentChooseCodeElement) {
        int end = 0;
        Component a;
        ContentChooseControl control = null;
        RadioComboboxControlLabel radioComboboxLabel;
        MultiComboboxControlLabel multiComboboxLabel;
        Element e0;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());

                if (a instanceof RadioComboboxControlLabel) {
                    radioComboboxLabel = (RadioComboboxControlLabel) a;
                    if (contentChooseCodeElement.match(radioComboboxLabel.getControl()) == true) {
                        control = radioComboboxLabel.getControl();
                        break;
                    }
                } else if (a instanceof MultiComboboxControlLabel) {
                    multiComboboxLabel = (MultiComboboxControlLabel) a;
                    if (contentChooseCodeElement.match(multiComboboxLabel.getControl()) == true) {
                        control = multiComboboxLabel.getControl();
                        break;
                    }
                }
            }
            end = e0.getEndOffset();
        }
        return control;
    }


    /**
     * 添加内容选择时使用，根据当前内容选择组件添加情况生成合适的内容选择模型信息
     *
     * @param optionId
     * @return
     */
    protected ContentChooseControl getNewContentChooseControl(String optionId) {
        OptionDataModel option = OptionDataModelTempHolder.getOption(optionId);

        if (option != null) {
            ContentChooseControl chooseControl = new ContentChooseControl(optionId, option.getOptionName());

            if (option.getValueNum() > 0) {
                chooseControl.getSelectList().add(0);
            }

            GeneralControl.setContentChooseLabelName(model, chooseControl);
            String thisName = ContentChooseElement.getThisNameForOprating(chooseControl);
            chooseControl.setThisName(thisName);
            return chooseControl;
        }
        return null;
    }

    /**
     * 根据控制模型添加内容选择组件
     *
     * @param chooseControl
     */
    protected void addContentChooseComponent(ContentChooseControl chooseControl) {
        OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(chooseControl.getOptionId());
        if (optionDataModel != null) {
            chooseControl.setOptionName(optionDataModel.getOptionName());

            if (optionDataModel.getOptionType() == OptionDataModel.EXECLUSIVE) {
                RadioComboboxControlLabel label = new RadioComboboxControlLabel(chooseControl);
                label.setPassingComponentParams(passingComponentParams);
//			label.setSelectedIndex(0);
                insertComponent(label);
                label.updateCodeComponetValue();

            } else if (optionDataModel.getOptionType() == OptionDataModel.MULTIPLE) {
                MultiComboboxControlLabel label = new MultiComboboxControlLabel(chooseControl);
                label.setPassingComponentParams(passingComponentParams);
                insertComponent(label);
                label.updateCodeComponetValue();
            }
        }
    }


    /**
     * 添加内容选项后的业务操作
     *
     * @param chooseControl
     */
    protected void performServiceOperationsAfterClickAddContentChooseComponent(ContentChooseControl chooseControl) {
        OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(chooseControl.getOptionId());
        if (optionDataModel != null) {
            ContentChooseControlMenuItem menuItem = new ContentChooseControlMenuItem(
                    "选项组件\"" + ContentChooseControl.getShowNameForOprating(chooseControl, optionDataModel) + "\"", chooseControl);
            delContentChooseMenu.add(menuItem);
            addDelContentChooseMenuItemAndActionListener(menuItem);
        }
    }


    /**
     * 添加内容选择组件的删除菜单项和监听
     *
     * @param menuItem
     */
    private void addDelContentChooseMenuItemAndActionListener(JMenuItem menuItem) {
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                ContentChooseControlMenuItem menuItem = (ContentChooseControlMenuItem) e.getSource();
                delContentChoose(
                        menuItem.getContentChooseElement().getOptionId(),
                        menuItem.getContentChooseElement().getUseNumbered());
            }
        });
    }


    /**
     * 根据标签元素还原为标签组件
     *
     * @param lableElement
     */
    protected void reductionControlLabelComponent(BaseElementInterface lableElement) {
        if (lableElement instanceof TextInputControl) {
            TextInputControlLabel label = new TextInputControlLabel((TextInputControl) lableElement);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(), LabelElementName.TEXT_INPUT,
                    delTextInputMenu);

        } else if (lableElement instanceof ContentChooseControl) {
            ContentChooseControl controlElement = (ContentChooseControl) lableElement;
            OptionDataModel option = OptionDataModelTempHolder.getOption(controlElement.getOptionId());
            if (option == null) {
                String text = "有个代码操作面板要添加一个\"" + controlElement.getOptionName() + "\"内容选择组件，可这个选项已经被删了！	(✪ω✪)";
                String logtext = getClass() + "（还原数据异常）————\"有个代码操作面板要添加" + controlElement.getOptionName() + "\"这个选项的内容选择组件，该选项早已被删除";
                DataSourceEditHolder.errorLogging(text, logtext);

            } else {
                controlElement.setOptionName(option.getOptionName());

                addContentChooseComponent(controlElement);
                //这里直接复制现在这个类performServiceOperationsAfterClickAddContentChooseComponent()方法里面的内容，因为该方法在继承面板重写，会向代码面板添加 添加该选项的菜单项，后面还原会直接统一在各个代码面板上添加菜单项，这里直接调用该方法会导致添加菜单项重复
                ContentChooseControlMenuItem menuItem =
                        new ContentChooseControlMenuItem("选项组件\"" + ContentChooseControl.getShowNameForOprating(controlElement, option),
                        controlElement);
                delContentChooseMenu.add(menuItem);
                addDelContentChooseMenuItemAndActionListener(menuItem);
            }
        } else if (lableElement instanceof FunctionAddControl) {
            FunctionAddControlLabel label = getFunctionAddControlLabel((FunctionAddControl) lableElement);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(), LabelElementName.FUNCTION_ADD,
                    delFunctionAddMenu);

        } else if (lableElement instanceof CustomVariableControl) {
            CustomVariableControlLabel label = new CustomVariableControlLabel((CustomVariableControl) lableElement);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(),
                    LabelElementName.CUSTOM_VARIABLE, delCustomVariableMenu);

        } else if (lableElement instanceof VariableControl) {
            VariableControlLabel label = new VariableControlLabel((VariableControl) lableElement);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(), LabelElementName.VARIABLE,
                    delVariableMenu);

        } else if (lableElement instanceof ConstantControl) {
            ConstantControlLabel label = new ConstantControlLabel((ConstantControl) lableElement);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(), LabelElementName.CONSTANT,
                    delConstantMenu);

        } else if (lableElement instanceof FileSelectorControl) {
            FileSelectorControlLabel label = new FileSelectorControlLabel((FileSelectorControl) lableElement, passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(), LabelElementName.FILE_SELECTOR,
                    delFileSelectorMenu);

        } else if (lableElement instanceof NoteControl) {
            NoteControlLabel label = new NoteControlLabel((NoteControl) lableElement);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(), LabelElementName.NOTE,
                    delNoteMenu);

        } else if (lableElement instanceof PictureControl) {
            PictureControlLabel label = new PictureControlLabel((PictureControl) lableElement, passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(), LabelElementName.PICTURE,
                    delPictureMenu);

        } else if (lableElement instanceof CodeInputControl) {
            CodeInputControlLabel label = new CodeInputControlLabel((CodeInputControl) lableElement);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(), LabelElementName.CODE_INPUT,
                    delCodeInputMenu);

        } else if (lableElement instanceof InfrequentlyUsedSettingControl) {
            InfrequentlyUsedSettingControl infrequentlyUsedSettingControl = (InfrequentlyUsedSettingControl) lableElement;
            InfrequentlyUsedSettingControlLabel label = getInfrequentlyUsedSettingControlLabel(infrequentlyUsedSettingControl, model);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(),
                    LabelElementName.INFREQUENTLY_USED_SETTING, delInfrequentlyUsedSettingMenu);

            ArrayList<BaseElementInterface> iusElementList = DeserializeElementMethods.getControlPaneElmentList(infrequentlyUsedSettingControl.getControlStatementFormat());
            if (iusElementList != null) {
                ContentChooseControl contentChooseControl;
                OptionDataModel optionDataModel;
                for (BaseElementInterface elementTemp : iusElementList) {
                    if (elementTemp instanceof ContentChooseControl) {
                        contentChooseControl = (ContentChooseControl) elementTemp;
                        optionDataModel = OptionDataModelTempHolder.getOption(contentChooseControl.getOptionId());
                        if (optionDataModel==null){
                            String text = "有个代码操作面板里面的不常用组件需要添加一个\"" + contentChooseControl.getOptionName() + "\"内容选择组件，可这个选项已经被删了！	(✪ω✪)";
                            String logtext = getClass() + "（还原数据异常）————\"有个代码操作面板的不常用组件里面要添加" + contentChooseControl.getOptionName() + "\"这个选项的内容选择组件，该选项早已被删除";
                            DataSourceEditHolder.errorLogging(text, logtext);
                        }
                    }
                }
            }

        } else if (lableElement instanceof CustomMethodNameControl) {
            CustomMethodNameControlLabel label = new CustomMethodNameControlLabel((CustomMethodNameControl) lableElement);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(),
                    LabelElementName.CUSTOM_METHOD_NAME, delCustomMethodNameMenu);

        } else if (lableElement instanceof MethodChooseControl) {
            MethodChooseControlLabel label = new MethodChooseControlLabel((MethodChooseControl) lableElement);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(), LabelElementName.METHOD_CHOOSE,
                    delMethodChooseMenu);

        } else if (lableElement instanceof CorrespondingAdditionalDefaultFileControl) {
            CorrespondingAdditionalDefaultFileControlLabel label = new CorrespondingAdditionalDefaultFileControlLabel((CorrespondingAdditionalDefaultFileControl) lableElement);
            label.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(label, label.getName(), LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE,
                    delDefaultFileMenu);
        }
    }

    @Override
    public void reductionContent(ArrayList<BaseElementInterface> list) {
        if (list != null) {
            StringElement stringElement;
            for (BaseElementInterface element : list) {
                setCaretPosition(doc.getLength());
                if (element instanceof StringElement) {
                    stringElement = (StringElement) element;
                    insertString(stringElement.getText());
                } else if (element instanceof BaseLableElement) {
                    reductionControlLabelComponent(element);
                }
            }
        }
    }

    /**
     * 生成控制语句格式
     *
     * @return
     * @throws BadLocationException
     */
    public String getControlStatementFormat() throws BadLocationException {
        String out = getStatementFormat();
        return out;
    }

    /**
     * 添加对应组件函数 (选择组件不使用这方法)
     *
     * @param component 要添加的组件
     * @param name      对应名字
     * @param delMenu   删除该组件对应放置的菜单
     *                  <p>
     *                  操作： 添加对应组件 添加对应删除该组件的菜单项，还有对应监听，点击删除菜单项会从面板上删除该组件，并且对应菜单失能
     */
    protected void addCorrespondingComponentMethod(JComponent component, String name, String labelType, JMenu delMenu) {
        component.setName(name);
        this.insertComponent(component);
        LableMenuItem menuItem = new LableMenuItem(name, labelType, name);
        delMenu.add(menuItem);
        addCorrespondingMenuItemAndActionListener(delMenu, menuItem);
    }

    /**
     * 添加对应组件的删除菜单项和监听
     *
     * @param delMenu
     */
    private void addCorrespondingMenuItemAndActionListener(JMenu delMenu, JMenuItem menuItem) {

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (delMenu == delInfrequentlyUsedSettingMenu) {
                    String[] options = new String[]{"算了，不删除这个组件了", "直接删除"};
                    int temp = LazyCoderOptionPane.showOptionDialog(null,
                            "!!!∑(ﾟДﾟノ)ノ  真的要删除这个不常用组件吗，删掉它，里面的内容还有添加的组件都看不到了", "系统消息",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
                            options[0]);
                    if (1 == temp) {//直接删除
                        LableMenuItem menuItem = (LableMenuItem) e.getSource();
                        String delName = menuItem.getThisName(), delLabelType = menuItem.getLabelType();

                        menuItemSetEnabledFalse(delMenu, delName);
                        deleteLabel(delName, delLabelType, CONTROL_PANE);
                        delControlLabel(model, delLabelType, delName);
                    }

                } else {
                    LableMenuItem menuItem = (LableMenuItem) e.getSource();
                    String delName = menuItem.getThisName(), delLabelType = menuItem.getLabelType();

                    menuItemSetEnabledFalse(delMenu, delName);
                    deleteLabel(delName, delLabelType, CONTROL_PANE);
                    /**
                     * 让模块通知其他代码面板删除组件
                     */
                    delControlLabel(model, delLabelType, delName);
                }
            }
        });
    }


    /**
     * 删除组件的方法
     *
     * @param model
     * @param delLabelType
     * @param delName
     */
    protected abstract void delControlLabel(AbstractEditContainerModel model, String delLabelType, String delName);

    @Override
    public ArrayList<BaseElementInterface> getTheSpecifiedLabelModels(ArrayList<Class<?>> specifiedClassList) {
        int end = 0;
        ArrayList<BaseElementInterface> list = new ArrayList<>();
        Component cTemp;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());

                if (cTemp instanceof ScucheonComponentInterface) {
                    for (Class<?> c : specifiedClassList) {
                        if (c.isInstance(cTemp)) {
                            list.add(((ScucheonComponentInterface) cTemp).property());
                        }
                    }
                }
            }
            end = e0.getEndOffset();
        }
        return list;
    }

    @Override
    public void makeCorrespondingLabelScutcheonRespond(BaseLableElement lableElement, Border border) {
        super.makeCorrespondingLabelScutcheonRespond(lableElement, border);
    }

    /**
     * 对应选择菜单显示的选项名称列表（继承该类都要重写此方法）
     *
     * @return
     */
    protected abstract List<OptionDataModel> getCorrespondingChooseMenuList();

    /**
     * 更新对应选择菜单的选项名称列表（继承该类都要重写此方法）
     *
     * @return
     */
    protected abstract List<OptionDataModel> getUpdateChooseMenuList();

    /**
     * 删除对应选择菜单的选项名称列表（继承该类都要重写此方法）
     *
     * @return
     */
    protected abstract List<OptionDataModel> getDelCorrespondingChooseFromDBMenuList();

    @Override
    public abstract File getImageRootPath();

    @Override
    public abstract File getFileSelectorRootPath();

    /**
     * 添加功能拓展操作组件
     */
    protected abstract void addFunctionAddOpratingLabel();

    protected abstract FunctionAddControlLabel getFunctionAddControlLabel(FunctionAddControl controlElement);

    /**
     * 添加不常用组件（如有需要，需重写此方法）
     *
     * @param controlLabel 设置className、moduleName、paneType、additionalSerialNumber 即可
     */
    protected abstract void addInfrequentlyUsedSettingOpratingLabel(
            InfrequentlyUsedSettingControlLabel controlLabel);

    /**
     * 点击提交内容选择菜单项的操作（继承该类都要重写此方法）
     */
    protected abstract void clickAddContentChooseMenuItem();

    /**
     * 还原时生成不常用组件的方法（继承该类都要重写此方法）
     *
     * @param controlElement
     * @param model
     * @return
     */
    protected abstract InfrequentlyUsedSettingControlLabel getInfrequentlyUsedSettingControlLabel(
            InfrequentlyUsedSettingControl controlElement, AbstractEditContainerModel model);

    /**
     * 添加内容选择组件
     */
    public void addContentChoose(String optionId) {
        ContentChooseControl control = getNewContentChooseControl(optionId);
//		addContentChooseComponentAndPerformServiceOperations(control);
        addContentChooseComponent(control);
        performServiceOperationsAfterClickAddContentChooseComponent(control);
    }

    /**
     * 添加模块编辑的对应的选项菜单
     */
    protected final void addModuleEditCorrespondingChooseMenuItems() {
        addModuleContentChooseMenu.removeAll();
        JMenu menuTemp;
        ContentOptionMenuItem menuItem;
        ArrayList<Module> needModuleList = ModuleEditPaneHolder.relatedModuleInfoMenu.getSelectedNeedUseModuleList();

        List<OptionDataModel> listTemp = getCorrespondingChooseMenuList();
        if (listTemp != null) {
            if (needModuleList.size() == 0) {
                for (OptionDataModel optionDataModelTemp : listTemp) {
                    menuItem = new ContentOptionMenuItem(optionDataModelTemp);
                    menuItem.addActionListener(addModuleContentChooseMenuItemListener);
                    addModuleContentChooseMenu.add(menuItem);
                }
            } else if (needModuleList.size() > 0) {
                if (DataSourceEditHolder.currentModule != null) {
                    menuTemp = new JMenu(DataSourceEditHolder.currentModule.getModuleName());
                    for (OptionDataModel optionDataModelTemp : listTemp) {
                        menuItem = new ContentOptionMenuItem(optionDataModelTemp);
                        menuItem.addActionListener(addModuleContentChooseMenuItemListener);
                        menuTemp.add(menuItem);
                    }
                    addModuleContentChooseMenu.add(menuTemp);
                }
            }
        }
        if (needModuleList.size() > 0) {
            for (Module module : needModuleList) {
                listTemp = SysService.OPTION_SERVICE.getCorrespondingOptionNameList(module.getModuleId());
                if (listTemp.size() > 0) {
                    menuTemp = new JMenu(module.getModuleName());
                    for (OptionDataModel optionDataModelTemp : listTemp) {
                        menuItem = new ContentOptionMenuItem(optionDataModelTemp);
                        menuItem.addActionListener(addModuleContentChooseMenuItemListener);
                        menuTemp.add(menuItem);
                    }
                    addModuleContentChooseMenu.add(menuTemp);
                }
            }
        }
    }

}
