package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.init;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.command.InitCodeModel;
import com.lazycoder.database.model.command.InitOperatingModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.InitMetaModel;
import com.lazycoder.service.vo.save.InitInputData;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.EditContainerPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.InitFolderPane;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

/**
 * 放置所有初始化方法的面板 待改部分，要添加方法，根据当前模块名和分类名获取状态信息，新增的采用新增，原来有的还原数据
 *
 * @author admin
 */
public class InitCodeFolder extends Folder implements ModuleEditComponentInterface, CheckInterface, EditContainerPane {

    /**
     *
     */
    private static final long serialVersionUID = 8723326074752460532L;

    private Border defaultborder;

    private Box vBox;

    private JScrollPane jsp;

    /**
     * 当前添加方法数量（清空的时候要把它设为0）
     */
    private int currentNum = 0;

    /**
     * 构造组件
     *
     * @param expanded 是否展开
     */
    public InitCodeFolder(boolean expanded) {
        // TODO Auto-generated constructor stub
        setDefaultLayout();
        DataSourceEditHolder.defaultButtonGroup = new ButtonGroup();
        InitCodeFolderHiddenButton hiddenButton = new InitCodeFolderHiddenButton(expanded);
        setHiddenButton(hiddenButton);
        add(hiddenButton);
        defaultborder = hiddenButton.getBorder();

        vBox = Box.createHorizontalBox();
        jsp = new JScrollPane(vBox);
        jsp.setBorder(null);// 无边框，更贴合容器
//		jsp.getVerticalScrollBar().setUI(new MyScrollBarUI());
        int spHeight = (int) (0.48 * SysUtil.SCREEN_SIZE.getHeight());
        jsp.setSize((int) (InitFolderPane.WIDTH_PROPORTION * SysUtil.SCREEN_SIZE.getWidth()), spHeight);//520

        // 生成并添加抽屉
        Drawer drawer = new Drawer(expanded ? 1 : 0, jsp);
        setDrawer(drawer);
        add(drawer);
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
        InitEditMetaPane initEditMetaPane;
        currentNum = 0;
        DataSourceEditHolder.defaultButtonGroup = new ButtonGroup();
        if (Module.FALSE_ == module.getEnabledState()) {// 此前没添加过内容
            currentNum++;
            initEditMetaPane = new InitEditMetaPane(currentNum, true);
            vBox.add(initEditMetaPane);

        } else {
            if (moduleInfo.getNumOfInit() > 0) {// 此前添加过内容，显示之前编辑过的内容
                ArrayList<InitMetaModel> list = SysService.INIT_SERVICE.getInitMetaModelList(moduleInfo.getModuleId());
                for (InitMetaModel initMetaModel : list) {
                    currentNum++;
                    initEditMetaPane = new InitEditMetaPane(initMetaModel);
                    vBox.add(initEditMetaPane);
                }
            }
        }
        vBox.updateUI();
        vBox.repaint();
        this.updateUI();
        this.repaint();
    }

    public void restore() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            ModuleInfo moduleInfo = SysService.MODULE_INFO_SERVICE.getModuleInfo(DataSourceEditHolder.currentModule.getModuleId());
            Module module = SysService.MODULE_SERVICE.getModuleById(DataSourceEditHolder.currentModule.getModuleId());
            if (moduleInfo != null && module != null) {
                if (moduleInfo.getNumOfInit() > 0) {
                    clearModuleContents();
                    displayModuleContents(moduleInfo, module);
                } else {
                    LazyCoderOptionPane.showMessageDialog(null, "ヽ(ー_ー)ノ  我查了一下，这里之前没写有什么内容，没什么可以还原的。");
                }
            }
        }
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        currentNum = 0;
        vBox.removeAll();
        vBox.updateUI();
        vBox.repaint();
    }

    /**
     * 获取初始化代码数量
     *
     * @return
     */
    public int getNumOfInitCode() {
        return vBox.getComponentCount();
    }

    public void addInitFunction() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            currentNum++;
            boolean setToDefault = vBox.getComponentCount() == 0 ? true : false;
            InitEditMetaPane initEditMetaPane = new InitEditMetaPane(currentNum, setToDefault);
            vBox.add(initEditMetaPane);

            vBox.updateUI();
            vBox.repaint();
            this.updateUI();
            this.repaint();
        }
    }

    public void delInitFunction() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            if (vBox.getComponentCount() >= 0) {
                currentNum--;
                vBox.remove(vBox.getComponentCount() - 1);
                vBox.updateUI();
                vBox.repaint();
                this.updateUI();
                this.repaint();
            }
        }
    }

    public InitInputData getInitInputData() {
        InitInputData initInputData = new InitInputData();

        ArrayList<InitOperatingModel> operatingList = new ArrayList<>();
        ArrayList<InitCodeModel> codeList = new ArrayList<>(), codeTempList;
        InitEditMetaPane paneTemp;
        for (int i = 0; i < vBox.getComponentCount(); i++) {
            paneTemp = (InitEditMetaPane) vBox.getComponent(i);

            operatingList.add(paneTemp.getOperatingModel());
            codeTempList = paneTemp.getCodeModelList();
            codeList.addAll(codeTempList);
        }
        initInputData.setOperatingList(operatingList);
        initInputData.setCodeList(codeList);
        return initInputData;
    }

    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
        list.add(ModuleEditPaneHolder.moduleFormatModel);
        InitEditMetaPane paneTemp;
        for (int i = 0; i < vBox.getComponentCount(); i++) {
            paneTemp = (InitEditMetaPane) vBox.getComponent(i);
            list.add(paneTemp.getContainerModel());
        }
        return list;
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        InitEditMetaPane paneTemp1, paneTemp2;

        for (int i = 0; i < vBox.getComponentCount(); i++) {
            paneTemp1 = (InitEditMetaPane) vBox.getComponent(i);
            if (paneTemp1.check() == false) {
                flag = false;
                break;
            }

            for (int k = i + 1; k < vBox.getComponentCount(); k++) {
                paneTemp2 = (InitEditMetaPane) vBox.getComponent(k);
                if (paneTemp1.getShowText().trim().equals(paneTemp2.getShowText().trim())) {
                    LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   再检查一下吧，第" + (i + 1) + "个初始化方法和第" + (k + 1) + "个初始化方法名称一样，这样我没法区分", "系统信息",
                            JOptionPane.PLAIN_MESSAGE);
                    flag = false;
                    break;
                }
            }
            if (flag == false) {
                break;
            }
        }
        return flag;
    }

    /**
     * 获取初始化方法数量
     *
     * @return
     */
    public int getInitSerialNum() {
        return vBox.getComponentCount();
    }

    /**
     * 获取代码条数
     *
     * @param ordinal
     * @return
     */
    public int getCodeNum(int ordinal) {
        int codeNum = 0;
        InitEditMetaPane teMetaPane;
        for (int i = 0; i < vBox.getComponentCount(); i++) {
            teMetaPane = (InitEditMetaPane) vBox.getComponent(i);
            if (teMetaPane.getOrdinal() == ordinal) {
                codeNum = teMetaPane.getCodeNum();
                break;
            }
        }
        return codeNum;
    }

    @Override
    public void setUIResponse(boolean status) {
        if (status) {
//            getDrawer().setBorder(new MyColoursBorder(3));
            getHiddenButton().setBorder(DataSourceEditHolder.RESPONSE_TRUE_BORDER);
            jsp.getVerticalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_TRUE_COLOR);
            jsp.getHorizontalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_TRUE_COLOR);
        } else {
//            getDrawer().setBorder(defaultborder);
            getHiddenButton().setBorder(defaultborder);
            jsp.getVerticalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_FALSE_COLOR);
            jsp.getHorizontalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_FALSE_COLOR);
        }
    }

}
