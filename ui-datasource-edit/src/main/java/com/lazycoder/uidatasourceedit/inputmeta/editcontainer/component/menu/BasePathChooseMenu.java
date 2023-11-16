package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.service.service.impl.FormatCodeFileServiceImpl;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.uidatasourceedit.component.component.CodeFileEditPaneInterface;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.util.ArrayList;
import javax.swing.JMenu;

public abstract class BasePathChooseMenu extends JMenu implements CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = 8525099725142028463L;

    public static final String NO_STRING = HTMLText.createHtmlContent("无", HtmlPar.BLUE, true);

    public BasePathChooseMenu() {
        // TODO Auto-generated constructor stub
        super();
    }

    /**
     * 根据当前的代码面板添加选项，继承该面板都要重写此方法
     */
    protected void addAllMenuItems() {
        ArrayList<CodeFormatFlagParam> list = getSelectedCodeFileList();
        if (list != null) {
            CodeFormatFlagParam codeFormatFlagParam;
            PathChooseMenuItem pathChooseMenuItem;
            if (list.size() > 0) {
                int currentCodeFileType = list.get(0).getFormatType(), currentAdditionalCodeFileType = -1;
                String currentModuleId = list.get(0).getModuleId();
//                String currentClassName = list.get(0).getClassName(), currentModuleName = list.get(0).getModuleName();
                for (int i = 0; i < list.size(); i++) {
                    codeFormatFlagParam = list.get(i);

                    if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MODULE_TYPE) {//模块菜单
                        if (i != 0) {
                            if (!(codeFormatFlagParam.getModuleId().equals(currentModuleId))) {
                                addSeparator();
                                currentModuleId = codeFormatFlagParam.getModuleId();
                                currentCodeFileType = codeFormatFlagParam.getFormatType();
                                currentAdditionalCodeFileType = codeFormatFlagParam.getAdditionalSerialNumber();

                            }
                        }
                    } else {
                        if ((codeFormatFlagParam.getFormatType() != currentCodeFileType)
                                || (currentAdditionalCodeFileType != codeFormatFlagParam.getAdditionalSerialNumber())) {
                            if (i != 0) {
                                addSeparator();
                            }
                            currentCodeFileType = codeFormatFlagParam.getFormatType();
                            currentAdditionalCodeFileType = codeFormatFlagParam.getAdditionalSerialNumber();

                        }
                    }
                    pathChooseMenuItem = new PathChooseMenuItem(codeFormatFlagParam, this);
                    this.add(pathChooseMenuItem);
                }
            }
        }
    }

    /**
     * 有添加对应标记就定位到对应标记，没有就直接提示
     */
    protected void navigateToTheCorrespondingMarkOrShowToolTip(CodeFileEditPaneInterface codeFileEditPane,
                                                               BaseMarkElement markElement) {
        updateItems();
        ArrayList<CodeFormatFlagParam> list = getSelectedCodeFormatFlagParams();
        showWritePath(list);
        if (getText().equals(NO_STRING) == false) {// 如果有选写在哪里，定位过去
            if (codeFileEditPane != null) {
                boolean flag = codeFileEditPane.navigateToTheCorrespondingMark(list, markElement, true);
                if (flag == false) {
                    if (list.size() == 1) {
                        setToolTipText("(*^▽^*)  别忘了，要在\"" + getText() + "\"中标记一下，这句代码要写在哪里");
                    } else {
                        setToolTipText("(*^▽^*)  记得在这几个源码文件里都标记一下，这句代码要写在哪里");
                    }
                } else {
                    setToolTipText(null);
                }
            }
        } else {
            setToolTipText(null);
        }
    }

    /**
     * 获取当前选中的路径参数
     *
     * @return
     */
    protected ArrayList<CodeFormatFlagParam> getSelectedCodeFormatFlagParams() {
        ArrayList<CodeFormatFlagParam> list = new ArrayList<>();
        PathChooseMenuItem pathChooseMenuItem;
        for (int i = 0; i < getMenuComponentCount(); i++) {
            if (getItem(i) instanceof PathChooseMenuItem) {
                pathChooseMenuItem = (PathChooseMenuItem) getItem(i);
                if (pathChooseMenuItem.isSelected() == true) {
                    list.add(pathChooseMenuItem.getCodeFormatFlagParam());
                }
            }
        }
        return list;
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        ArrayList<CodeFormatFlagParam> list = getSelectedCodeFormatFlagParams();
        if (list.size() == 0) {
            flag = false;
        }
        return flag;
    }

    /**
     * 显示设置的填写路径
     */
    protected void showWritePath(ArrayList<CodeFormatFlagParam> selectedCodeFormatFlagParamList) {
        String text = NO_STRING;

        if (selectedCodeFormatFlagParamList != null) {
            if (selectedCodeFormatFlagParamList.size() == 1) {
                text = selectedCodeFormatFlagParamList.get(0).getFileName();
            } else if (selectedCodeFormatFlagParamList.size() > 1) {
                StringBuilder out = new StringBuilder();
                for (int i = 0; i < selectedCodeFormatFlagParamList.size(); i++) {
                    if (i == selectedCodeFormatFlagParamList.size() - 1) {
                        out.append(selectedCodeFormatFlagParamList.get(i).getFileName());
                    } else {
                        out.append(selectedCodeFormatFlagParamList.get(i).getFileName() + "、");
                    }
                }
                text = out.toString();
            }
        }
        setText(text);
    }

    /**
     * 检查有没有添加对应的标记
     *
     * @return
     */
    public abstract boolean checkHaveAddTheMarkScutcheonOrNot();

    /**
     * 获取路径参数
     *
     * @return
     */
    public String getPathParam() {
        return FormatCodeFileServiceImpl.getPathParamToString(getSelectedCodeFormatFlagParams());
    }

    /**
     * 更新选项值
     */
    private void updateItems() {
        ArrayList<CodeFormatFlagParam> selectedList = getSelectedCodeFormatFlagParams();
        this.removeAll();
        addAllMenuItems();
        setSelectedMenuItems(selectedList);
    }

    /**
     * 设置需要选中的面板
     *
     * @param getSelectedList
     */
    private void setSelectedMenuItems(ArrayList<CodeFormatFlagParam> getSelectedList) {
        PathChooseMenuItem pathChooseMenuItemTemp;
        CodeFormatFlagParam codeFormatFlagParamTemp1, codeFormatFlagParamTemp2;
        boolean flag;
        for (int i = 0; i < getMenuComponentCount(); i++) {
            if (getItem(i) instanceof PathChooseMenuItem) {
                pathChooseMenuItemTemp = (PathChooseMenuItem) getItem(i);
                if (pathChooseMenuItemTemp != null) {
                    for (int a = 0; a < getSelectedList.size(); a++) {
                        codeFormatFlagParamTemp1 = getSelectedList.get(a);
                        codeFormatFlagParamTemp2 = pathChooseMenuItemTemp.getCodeFormatFlagParam();
                        if (codeFormatFlagParamTemp1 != null && codeFormatFlagParamTemp2 != null) {
                            flag = CodeFormatFlagParam.compare(codeFormatFlagParamTemp1, codeFormatFlagParamTemp2);
                            if (flag == true) {
                                pathChooseMenuItemTemp.setSelected(true);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置选择路径
     *
     * @param pathParam
     */
    public void setSelectedPath(String pathParam) {
        ArrayList<CodeFormatFlagParam> list = JSON.parseObject(pathParam,
                new TypeReference<ArrayList<CodeFormatFlagParam>>() {
                });
        setSelectedMenuItems(list);
    }

    /**
     * 所有的菜单项都使能
     */
    protected void setEnabledTrueForAllMenuItem() {
        PathChooseMenuItem item;
        for (int i = 0; i < getMenuComponentCount(); i++) {
            if (getItem(i) instanceof PathChooseMenuItem) {
                item = (PathChooseMenuItem) getItem(i);
                item.setEnabled(true);
            }
        }
    }

    /**
     * 把 pathChooseMenuItem 以外的菜单项都设置为false
     *
     * @param pathChooseMenuItem
     */
    protected void setEnabledFalseExceptFor(PathChooseMenuItem pathChooseMenuItem) {
        PathChooseMenuItem item;
        for (int i = 0; i < getMenuComponentCount(); i++) {
            if (getItem(i) instanceof PathChooseMenuItem) {
                item = (PathChooseMenuItem) getItem(i);
                if (item == pathChooseMenuItem) {
                    item.setEnabled(true);
                } else {
                    item.setEnabled(false);
                }
            }
        }
    }

    /**
     * 菜单需要显示代码文件的对应信息，继承该类都要重写此方法
     *
     * @return
     */
    protected abstract ArrayList<CodeFormatFlagParam> getSelectedCodeFileList();

    /**
     * 里面的菜单项点击后要做的处理（继承该类都要重写此方法）
     *
     * @param pathChooseMenuItem
     */
    protected abstract void processing(PathChooseMenuItem pathChooseMenuItem);

}
