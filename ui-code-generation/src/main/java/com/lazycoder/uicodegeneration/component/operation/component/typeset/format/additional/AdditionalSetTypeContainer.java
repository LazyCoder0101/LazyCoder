package com.lazycoder.uicodegeneration.component.operation.component.typeset.format.additional;

import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.container.AdditionalSetContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.AdditionalSetTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.AdditionalSetTypeContainerModel;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

public class AdditionalSetTypeContainer extends Folder {

    /**
     * 组件长度所在屏幕比例
     */
    public final static double PROPORTION = 0.3;

    private static ImageIcon moreIcon = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "更多.png");

    /**
     *
     */
    private static final long serialVersionUID = -7330966037128908641L;

    private Thread t = null;

    private static final int HIDDEN_HEIGHT = 30, additionalSetTypeJSPHeight = 350, additionalSetButtonWidth = 80;

    private AdditionalSetTypeHiddenButton hiddenButton;

    private AdditionalSetCodeControlPane codeControlPane;

    private JScrollPane scrollPane;

    private MyButton additionalSetTypeButton;

    private JPopupMenu menu;

    private String additionalSetTypeName;

    private AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam;

    public AdditionalSetTypeContainer(AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam,
                                      boolean expanded) {
        // TODO Auto-generated constructor stub
        super();
        this.additionalSetTypeOperatingContainerParam = additionalSetTypeOperatingContainerParam;
        this.additionalSetTypeName = additionalSetTypeOperatingContainerParam.getAdditionalSetType();
        init(expanded);
        autoGenerateOpratingContainers();
    }

    public AdditionalSetTypeContainer(AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam,
                                      AdditionalSetTypeContainerModel additionalSetTypeContainerModel, boolean expanded) {
        // TODO Auto-generated constructor stub
        this.additionalSetTypeOperatingContainerParam = additionalSetTypeOperatingContainerParam;
        this.additionalSetTypeName = additionalSetTypeOperatingContainerParam.getAdditionalSetType();
        init(expanded);
        codeControlPane.restoreContent(additionalSetTypeContainerModel);
    }

    private void init(boolean expanded) {
        int width = (int) (PROPORTION * SysUtil.SCREEN_SIZE.getWidth());
        setLayout(new AdditionalSetTypeLayout());

        menu = new JPopupMenu();
        hiddenButton = new AdditionalSetTypeHiddenButton(additionalSetTypeName, expanded) {
            @Override
            public void doSomethingWhenMousePressed(boolean expanded) {
                codeControlPane.collapseThis();
                super.doSomethingWhenMousePressed(expanded);
            }

//            @Override
//            public void doClick() {
//                codeControlPane.collapseThis();
//                super.doClick();
//            }
        };
        hiddenButton.setSize(width, HIDDEN_HEIGHT);
        setHiddenButton(hiddenButton);
        add(hiddenButton);

        additionalSetTypeButton = new MyButton(moreIcon);
        additionalSetTypeButton.setSize(additionalSetButtonWidth, HIDDEN_HEIGHT);
        additionalSetTypeButton.addMouseListener(mouseAdapter);
        add(additionalSetTypeButton);

        codeControlPane = new AdditionalSetCodeControlPane(additionalSetTypeOperatingContainerParam);
        scrollPane = codeControlPane.getParentScrollPane();
        scrollPane.setSize(width, additionalSetTypeJSPHeight);

        Drawer drawer = new Drawer(expanded ? 1 : 0, scrollPane);
        this.setDrawer(drawer);
        add(drawer);
    }


    /**
     * 删除某个模块的所有的功能容器
     *
     * @param moduleId
     */
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        codeControlPane.delModuleOpratingContainerFromComponent(moduleId);
    }

    /**
     * 收起自身组件里面所有的组件
     */
    public void collapseThis() {
        // TODO Auto-generated method stub
        codeControlPane.collapseThis();
    }

    public void delThis() {
        codeControlPane.delThis();
    }

    /**
     * 获取添加到这个面板以及里面组件里面的所有的功能容器
     *
     * @return
     */
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        return codeControlPane.getAllOpratingContainer();
    }

    public AdditionalSetTypeContainerModel getFormatStructureModel() {
        AdditionalSetTypeContainerModel model = codeControlPane.getFormatStructureModel();
        model.setAdditionalSetTypeName(additionalSetTypeName);
        return model;
    }

    private void updateMenuItems() {
        menu.removeAll();
        List<FormatTypeFeatureSelectionModel> list = SysService.ADDITIONAL_SET_SERVICE.getFeatureList(
                this.additionalSetTypeOperatingContainerParam.getAdditionalInfo().getAdditionalSerialNumber(), additionalSetTypeName);
        if (list != null) {
            AdditionalSetTypeMenuItem tempItem;
            for (FormatTypeFeatureSelectionModel tempModel : list) {
                tempItem = new AdditionalSetTypeMenuItem(tempModel);
                menu.add(tempItem);

                //如果该菜单是只能添加一次的和自动生成只能添加一次的，查看当前添加的功能里面有没有和添加这个菜单对应的功能，有的话，将该菜单失能
                if (tempModel.getSetProperty() == FunctionUseProperty.onlyBeUsedOnce.getSysDictionaryValue() ||
                        tempModel.getSetProperty() == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue()) {
                    AdditionalSetContainer container;
                    ArrayList<OpratingContainerInterface> opratingContainerList = codeControlPane.getAllOpratingContainerListInThisPane();
                    for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                        if (opratingContainer instanceof AdditionalSetContainer) {
                            container = (AdditionalSetContainer) opratingContainer;
                            if (tempModel.getOrdinal() == container.getOrdinalInUserDB()) {
                                tempItem.setEnabled(false);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 生成事先设置的需要生成的方法
     */
    private void autoGenerateOpratingContainers() {
        List<FormatTypeFeatureSelectionModel> list = SysService.ADDITIONAL_SET_SERVICE.getFeatureList(
                this.additionalSetTypeOperatingContainerParam.getAdditionalInfo().getAdditionalSerialNumber(), additionalSetTypeName);
        if (list != null) {
            AdditionalSetContainer opratingContainer = null;
            for (FormatTypeFeatureSelectionModel selectionModel : list) {
                if (selectionModel.getSetProperty() == FunctionUseProperty.autoGenerateOnce.getSysDictionaryValue() ||
                        selectionModel.getSetProperty() == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue()) {
                    opratingContainer = codeControlPane.addOpratingPane(selectionModel, true);
                    if (opratingContainer == null) {
                        String text = "无法添加\"" + selectionModel.getTypeName() + "\"分类的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应位置！	(✪ω✪)";
                        String logtext = getClass() + "（添加功能异常）" +
                                "无法添加\"" + selectionModel.getTypeName() + "\"模块的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应标记！";
                        CodeGenerationFrameHolder.errorLogging(text, logtext);
                    }
                } else if (selectionModel.getSetProperty() == FunctionUseProperty.autoGenerateOnceAndCanNotDel.getSysDictionaryValue()) {
                    opratingContainer = codeControlPane.addOpratingPane(selectionModel, false);
                    if (opratingContainer == null) {
                        String text = "无法添加\"" + selectionModel.getTypeName() + "\"分类的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应位置！	(✪ω✪)";
                        String logtext = getClass() + "（添加功能异常）" +
                                "无法添加\"" + selectionModel.getTypeName() + "\"模块的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应标记！";
                        CodeGenerationFrameHolder.errorLogging(text, logtext);
                    }
                }
            }
        }
    }


    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            updateMenuItems();
            menu.show(additionalSetTypeButton, e.getX(), e.getY());
            super.mouseEntered(e);
        }
    };

    @Override
    public Dimension getRequiredDimension() {
        int w = getDrawer().getContentWidth(),
                h = (int) (getDrawer().getContentHeight() * getDrawer().getRatio()) + HIDDEN_HEIGHT;
        return new Dimension(w, h);
    }

    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        if (currentOpratingContainer.getFirstCommandOpratingContainer() != null && currentOpratingContainer.getFirstCommandOpratingContainer() instanceof AdditionalSetContainer) {
            AdditionalSetContainer container = (AdditionalSetContainer) currentOpratingContainer.getFirstCommandOpratingContainer();
            if (container != null) {
                if (container.getAdditionalSerialNumber() == additionalSetTypeOperatingContainerParam.getAdditionalInfo().getAdditionalSerialNumber()) {
                    if (this.additionalSetTypeName.equals(container.getAdditionalSetTypeName())) {
                        expandHiddenPanel();
                        codeControlPane.autoCollapse(currentOpratingContainer);
                    } else {
                        packUpHiddenPanel();
                    }
                }
            }
        }
    }

    /**
     * 负责布局面板组件
     */
    class AdditionalSetTypeLayout implements LayoutManager {

        /**
         * x坐标
         */
        private int xCoordinates = 0;

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return parent.getPreferredSize();
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return parent.getMinimumSize();
        }

        @Override
        public void layoutContainer(Container parent) {

            int w = parent.getWidth(), h = parent.getHeight(), dHeight = HIDDEN_HEIGHT;

            getHiddenButton().setBounds(0, 0, w - additionalSetButtonWidth, HIDDEN_HEIGHT);

            additionalSetTypeButton.setBounds(w - additionalSetButtonWidth, 0, additionalSetButtonWidth, HIDDEN_HEIGHT);
            // 抽屉只显示抽出的比例
            getDrawer().setBounds(xCoordinates, dHeight, w, h - dHeight);
        }
    }

    class AdditionalSetTypeMenuItem extends JMenuItem {

        /**
         *
         */
        private static final long serialVersionUID = -490331634707256399L;

        private FormatTypeFeatureSelectionModel featureSelectionModel;

        public AdditionalSetTypeMenuItem(FormatTypeFeatureSelectionModel featureSelectionModel) {
            // TODO Auto-generated constructor stub
            super(featureSelectionModel.getShowText());
            this.featureSelectionModel = featureSelectionModel;
            addActionListener(listener);

            if (featureSelectionModel != null && ("[]".equals(featureSelectionModel.getNoteListParam()) == false)) {
                HTMLText noteTip = CodeGenerationFrameHolder.getNoteToolTip(featureSelectionModel.getNoteListParam());
                if (noteTip != null) {
                    setToolTipText(noteTip.getHtmlContent());
                }
            }
        }

        private ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (isEnabled() == true) {
                    CodeGenerationFrameHolder.temporaryErrorList.clear();//先把临时错误列表清空
                    AdditionalSetContainer container = codeControlPane.addOpratingPane(featureSelectionModel, true);
                    if (container == null) {
                        LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o    亲，这个功能的数据有点问题，没法写进去喔", "系统信息",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        if (CodeGenerationFrameHolder.autoCollapseCheckBox != null) {
                            if (CodeGenerationFrameHolder.autoCollapseCheckBox.isSelected() == true) {
                                if (CodeGenerationFrameHolder.currentFormatControlPane != null) {
                                    CodeGenerationFrameHolder.currentFormatControlPane.autoCollapse(container);
                                }
                            }
                        }
                        //该功能的组件自动选择
                        //container.setNoUserSelectionIsRequiredValue();

                        //所有功能自动选择
                        ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
                        if (opratingContainerList != null) {
                            for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                                opratingContainer.setNoUserSelectionIsRequiredValue();
                            }
                        }
                        CodeGenerationFrameHolder.showErrorListIfNeed("这个功能有点异常喔   (=ω=；)");
                    }
                }
            }
        };

    }

}
