package com.lazycoder.uidatasourceedit.moduleedit.toolbar.usingrange;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.formodule.ModuleStaticMethod;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uiutils.component.MultiSelectComboBox;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * 可使用范围选择
 *
 * @param <UsingObject>
 * @author admin
 */
public class UsingRangeCombobox<UsingObject> extends MultiSelectComboBox<UsingObject>
        implements ModuleEditComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -1584741096952249466L;

    public UsingRangeCombobox() {
        // TODO Auto-generated constructor stub
        super();
        setRenderer(new UsingRangeComboBoxRenderer());
        addPopupMenuListener(listener);
        // setModel((ComboBoxModel<UsingObject>)
    }

    /**
     * 刷新并回复原来的选项,每次点击下拉框时调用
     */
    @SuppressWarnings("unchecked")
    private void updateAndRestoreSelectedValue() {
        List<com.lazycoder.database.model.formodule.UsingObject> originalSelectedlist = (List<com.lazycoder.database.model.formodule.UsingObject>) getSelectedItems();
        removeAllItems();
        ArrayList<com.lazycoder.database.model.formodule.UsingObject> list = FormatEditPaneHolder.getUsingRange();
        updateItem(list);
        restoreSelectedValue(list, originalSelectedlist);
    }

    /**
     * 重新设置选项值(当点击下拉框进行选择时，根据当前必填模板和可选模板的内容进行下拉列表的更新)
     */
    @SuppressWarnings("unchecked")
    private void updateItem(ArrayList<com.lazycoder.database.model.formodule.UsingObject> list) {
        DefaultComboBoxModel<com.lazycoder.database.model.formodule.UsingObject> model = new DefaultComboBoxModel<>();
        if (list != null) {
            for (com.lazycoder.database.model.formodule.UsingObject temp : list) {
                model.addElement(temp);
            }
        }
        setModel((ComboBoxModel<UsingObject>) model);
    }

    /**
     * 查看有没有选中这个范围
     *
     * @param usingObject
     * @return
     */
    public boolean checkHaveSelected(com.lazycoder.database.model.formodule.UsingObject usingObject) {
        boolean flag = false;
        if (DataSourceEditHolder.currentModule == null) {
            return false;
        }
        List<com.lazycoder.database.model.formodule.UsingObject> list = getUsingRange();
        for (com.lazycoder.database.model.formodule.UsingObject temp : list) {
            if (temp.getType() == usingObject.getType()) {
                if (usingObject.getType() == com.lazycoder.database.model.formodule.UsingObject.MAIN_TYPE) {
                    flag = true;
                    break;
                } else if (usingObject.getType() == com.lazycoder.database.model.formodule.UsingObject.ADDITIONAL_TYPE) {
                    if (usingObject.getSerial() == temp.getSerial()) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 还原选项
     *
     * @param getUsingRange        系统获取的当前的可使用范围
     * @param originalSelectedlist 要还原的选项
     */
    private void restoreSelectedValue(ArrayList<com.lazycoder.database.model.formodule.UsingObject> getUsingRange,

                                      List<com.lazycoder.database.model.formodule.UsingObject> originalSelectedlist) {

        clearSelectedIndexs();
        com.lazycoder.database.model.formodule.UsingObject newTemp;
        for (com.lazycoder.database.model.formodule.UsingObject oldTemp : originalSelectedlist) {// 按已选列表的内容一个个对
            for (int a = 0; a < getUsingRange.size(); a++) {// 在使用范围一个个查
                newTemp = getUsingRange.get(a);
                if (newTemp.getType() == oldTemp.getType()) {
                    if (newTemp.getType() == com.lazycoder.database.model.formodule.UsingObject.MAIN_TYPE) {
                        addSelectedIndex(a);
                    } else if (newTemp.getType() == com.lazycoder.database.model.formodule.UsingObject.ADDITIONAL_TYPE) {
                        if (newTemp.getSerial() == oldTemp.getSerial()) {
                            addSelectedIndex(a);
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置使用范围参数
     *
     * @param module
     */
    public void setUsingRangeParam(Module module) {
        List<com.lazycoder.database.model.formodule.UsingObject> theUsingRange = getUsingRange();
        ModuleStaticMethod.setUsingRangeParam(module, theUsingRange);
    }

    private PopupMenuListener listener = new PopupMenuListener() {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
            updateAndRestoreSelectedValue();
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
            uploadCurrentCodePanes();
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            // TODO Auto-generated method stub
        }
    };

    /**
     * 加载对应代码面板
     */
    private void uploadCurrentCodePanes() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            List<com.lazycoder.database.model.formodule.UsingObject> usingRangeList = getUsingRange();
            ModuleEditPaneHolder.needUseCodeFileEditPane.addNeedUseCodeFormatPaneByUsing(usingRangeList);
            removeNoUseCodePane();
        }
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
        removeAllItems();
        ArrayList<com.lazycoder.database.model.formodule.UsingObject> list = FormatEditPaneHolder.getUsingRange();
        updateItem(list);

        if (module.getUsingRangeParam().equals(Module.NEW_STATE)) {// 此前未被编辑过
            for (int i = 0; i < getModel().getSize(); i++) {// 新建状态默认全部选中
                addSelectedIndex(i);
            }
            uploadCurrentCodePanes();

        } else {// 此前被编辑过 根据mdule里面的信息
            List<com.lazycoder.database.model.formodule.UsingObject> originalSelectedlist = ModuleStaticMethod
                    .getUsingRange(module);// 在模块信息那里提取原来选择的可使用范围
            restoreSelectedValue(list, originalSelectedlist);
        }
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        removeAllItems();
    }


    /**
     * 获取使用范围
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<com.lazycoder.database.model.formodule.UsingObject> getUsingRange() {
        return (List<com.lazycoder.database.model.formodule.UsingObject>) getSelectedItems();
    }

    /**
     * 返回选择的值，用','拼接的字符串
     *
     * @return
     */
    @Override
    public String getSelectedItemsString() {
        List<String> list = new ArrayList<>();
        for (Integer index : getSelectedSortedIndexs()) {
            UsingObject elementAt = getModel().getElementAt(index);
            list.add(elementAt == null ? ""
                    : ((com.lazycoder.database.model.formodule.UsingObject) elementAt).getShowName());
        }
        return spliceCollectionValue(list, "、");
    }

    /**
     * 查看现在有哪些是没有选的，传给ModuleEditPaneHolder.needUseCodeFileEditPane（如果原来有加载，就删掉）
     */
    private void removeNoUseCodePane() {
        List<com.lazycoder.database.model.formodule.UsingObject> list = getNoSelectedList();
        if (list != null) {
            for (com.lazycoder.database.model.formodule.UsingObject temp : list) {
                if (temp.getType() == com.lazycoder.database.model.formodule.UsingObject.MAIN_TYPE) {
                    if (ModuleEditPaneHolder.needUseCodeFileEditPane != null) {
                        ModuleEditPaneHolder.needUseCodeFileEditPane.removeAllMainCodePane();
                    }
                } else if (temp.getType() == com.lazycoder.database.model.formodule.UsingObject.ADDITIONAL_TYPE) {
                    int additionalSerialNumber = temp.getSerial();
                    ModuleEditPaneHolder.needUseCodeFileEditPane.removeAllAdditionalCodePane(additionalSerialNumber);
                }
            }
        }
    }

    /**
     * 获取没有选中的选项
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<com.lazycoder.database.model.formodule.UsingObject> getNoSelectedList() {
        List<com.lazycoder.database.model.formodule.UsingObject> originalSelectedlist = (List<com.lazycoder.database.model.formodule.UsingObject>) getSelectedItems();
        ComboBoxModel<UsingObject> moduleList = getModel();
        List<com.lazycoder.database.model.formodule.UsingObject> outList = new ArrayList<>();
        for (int i = 0; i < moduleList.getSize(); i++) {// 添加所有选项
            outList.add((com.lazycoder.database.model.formodule.UsingObject) moduleList.getElementAt(i));
        }
        com.lazycoder.database.model.formodule.UsingObject selectTemp, removeTemp;
        for (int i = 0; i < originalSelectedlist.size(); i++) {// 按已选列表的内容一个个对
            selectTemp = originalSelectedlist.get(i);// 已选内容
            for (int b = 0; b < outList.size(); b++) {
                removeTemp = outList.get(b);
                if (selectTemp.getType() == removeTemp.getType()) {
                    if (selectTemp.getType() == com.lazycoder.database.model.formodule.UsingObject.MAIN_TYPE) {
                        outList.remove(b);
                        break;

                    } else if (selectTemp.getType() == com.lazycoder.database.model.formodule.UsingObject.ADDITIONAL_TYPE) {
                        if (selectTemp.getSerial() == removeTemp.getSerial()) {
                            outList.remove(b);
                            break;
                        }
                    }
                }
            }
        }
        return outList;
    }

    /**
     * 获取其他被选中的使用范围
     *
     * @return
     */
    public List<com.lazycoder.database.model.formodule.UsingObject> getAdditionalUsingRangeThatHaveBeenSelected() {
        List<com.lazycoder.database.model.formodule.UsingObject> outList = new ArrayList<>();
        List<com.lazycoder.database.model.formodule.UsingObject> selectedList = getUsingRange();
        for (com.lazycoder.database.model.formodule.UsingObject temp : selectedList) {
            if (temp.getType() == com.lazycoder.database.model.formodule.UsingObject.ADDITIONAL_TYPE) {
                outList.add(temp);
            }
        }
        return outList;
    }

    /**
     * 获取选中的可选模板的序号
     *
     * @return
     */
    public ArrayList<Integer> getAdditionalSerialNumberThatHaveBeenSelected() {
        List<com.lazycoder.database.model.formodule.UsingObject> olist = getAdditionalUsingRangeThatHaveBeenSelected();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (com.lazycoder.database.model.formodule.UsingObject temp : olist) {
            list.add(temp.getSerial());
        }
        return list;
    }

    /**
     * 查看有没有选中必填模板
     *
     * @return
     */
    public boolean chekMainUsingRangeThatHaveBeenSelected() {
        boolean flag = false;
        List<com.lazycoder.database.model.formodule.UsingObject> selectedList = getUsingRange();
        for (com.lazycoder.database.model.formodule.UsingObject temp : selectedList) {
            if (temp.getType() == com.lazycoder.database.model.formodule.UsingObject.MAIN_TYPE) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取除了XX可选模板以外，另外的其他被选中的使用范围
     *
     * @return
     */
    public List<com.lazycoder.database.model.formodule.UsingObject> getAdditionalUsingRangeThatHaveBeenSelectedExceptFor(
            int ordinal) {
        List<com.lazycoder.database.model.formodule.UsingObject> outList = new ArrayList<>();
        List<com.lazycoder.database.model.formodule.UsingObject> selectedList = getUsingRange();
        for (com.lazycoder.database.model.formodule.UsingObject temp : selectedList) {
            if (temp.getType() == com.lazycoder.database.model.formodule.UsingObject.ADDITIONAL_TYPE) {
                if (temp.getSerial() != ordinal) {
                    outList.add(temp);

                }
            }
        }
        return outList;
    }

}