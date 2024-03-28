package com.lazycoder.uicodegeneration.component.operation.component.typeset.module;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.InitOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.ModuleTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.InitOpratingContainerModel;
import com.lazycoder.uiutils.mycomponent.MyPopupButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import lombok.Getter;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class SetButton extends MyPopupButton {

    /**
     *
     */
    private static final long serialVersionUID = 8849176446451476503L;

    private static final int MAX_PANE_WIDTH = (int) (0.33 * SysUtil.SCREEN_SIZE.width), maxPaneHight = (int) (0.53 * SysUtil.SCREEN_SIZE.height);

    /**************************************************************************/
    private static ImageIcon setIcon = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "set" + File.separator + "set.png");

    @Getter
    private SettingContainer settingContainer = null;

    private JScrollPane scrollPane;
    /**************************************************************************/

    private InitOperatingContainerParam initOperatingContainerParam;

    public SetButton() {
        // TODO Auto-generated constructor stub
        super(setIcon);
        buttonHeight = setIcon.getIconHeight();
        buttonWidth = setIcon.getIconWidth();
        paneWidth = MAX_PANE_WIDTH;
        paneHeight = maxPaneHight;
        Dimension dd = new Dimension(setIcon.getIconWidth(), setIcon.getIconHeight());
        this.setMaximumSize(dd);
        this.setMinimumSize(dd);
        this.setPreferredSize(dd);
        setUI(new BEButtonUI());
    }

    /**
     * 新建
     *
     * @param initOperatingContainerParam
     */
    public SetButton(InitOperatingContainerParam initOperatingContainerParam) {
        this();
        this.initOperatingContainerParam = initOperatingContainerParam;
        newInitFrame();
    }

    /**
     * 还原
     *
     * @param initOpratingContainerModel
     * @param initOperatingContainerParam
     */
    public SetButton(InitOpratingContainerModel initOpratingContainerModel,
                     InitOperatingContainerParam initOperatingContainerParam) {
        this();
        this.initOperatingContainerParam = initOperatingContainerParam;
        restoreInitFrame(initOpratingContainerModel);
    }

    /**
     * 新建窗口
     */
    private void newInitFrame() {
        ModuleTypeOperatingContainerParam moduleTypeContainerParam = new ModuleTypeOperatingContainerParam();
        setModuleTypeOperatingContainerParam(moduleTypeContainerParam);

        settingContainer = new SettingContainer(moduleTypeContainerParam);
        scrollPane = new JScrollPane(settingContainer);
        setInternalComponent(scrollPane);
    }


    /**
     * 还原窗口
     *
     * @param initOpratingContainerModel
     */
    private void restoreInitFrame(InitOpratingContainerModel initOpratingContainerModel) {
        ModuleTypeOperatingContainerParam moduleTypeContainerParam = new ModuleTypeOperatingContainerParam();
        setModuleTypeOperatingContainerParam(moduleTypeContainerParam);

        settingContainer = new SettingContainer(moduleTypeContainerParam,
                initOpratingContainerModel.getSettingContainerModel());
        scrollPane = new JScrollPane(settingContainer);
        setInternalComponent(scrollPane);
    }

    @Override
    public void doClick() {
        if (isExpanded()) {
            settingContainer.collapseThis();
        }
        super.doClick();
    }

    private void setModuleTypeOperatingContainerParam(ModuleTypeOperatingContainerParam moduleTypeContainerParam) {
        moduleTypeContainerParam.setFormatControlPane(this.initOperatingContainerParam.getFormatControlPane());
        moduleTypeContainerParam.setModule(this.initOperatingContainerParam.getModule());
        moduleTypeContainerParam.setModuleInfo(this.initOperatingContainerParam.getModuleInfo());
        //moduleTypeContainerParam.setSetButton(this);
    }


    /**
     * 从组件那里删掉该模块的对应命令
     *
     * @param moduleId
     */
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        settingContainer.delModuleOpratingContainerFromComponent(moduleId);
    }

    /**
     * 删掉对应模块的命令
     */
    public void delModuleOpratingContainer() {
        // TODO Auto-generated method stub
        settingContainer.delModuleOpratingContainer();
        hidePopupPanel();
    }

    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        // TODO Auto-generated method stub
        return settingContainer.getAllOpratingContainer();
    }


    public void collapseThis() {
        // TODO Auto-generated method stub
        if (isExpanded()) {
            doClick();
        }
    }

    @Override
    protected void doSomeThingWhenPackUpPanel() {
        settingContainer.collapseThis();
    }

//    @Override
//    public int getPaneHeight() {
//        if (this.settingContainer != null) {
//            int hightTemp = settingContainer.getRequiredDimension().height;
//            if (hightTemp > maxPaneHight) {
//                hightTemp = maxPaneHight;
//            }
//            return hightTemp;
//        }
//        return super.getPaneHeight();
//    }

    @Override
    public int getPaneWidth() {
        if (this.settingContainer != null) {
            int widthTemp = settingContainer.getRequiredDimension().width;
            if (widthTemp > MAX_PANE_WIDTH) {
                widthTemp = MAX_PANE_WIDTH;
            }
            return widthTemp;
        }
        return super.getPaneWidth();
    }
}
