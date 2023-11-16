package com.lazycoder.uicodegeneration.component.operation.component.typeset.format.main;

import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.container.MainSetContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.MainSetTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.MainSetTypeContainerModel;
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
import lombok.Getter;

public class MainSetTypeContainer extends Folder {

    public final static double PROPORTION = 0.87 * 0.35;

    private static ImageIcon moreIcon = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "更多.png");

    /**
     *
     */
    private static final long serialVersionUID = -5089021262623704531L;

    private static final int HIDDEN_HEIGHT = 30, MAIN_SET_TYPE_JSP_HEIGHT = 350, MAIN_SET_BUTTON_WIDTH = 80;

    private MainSetTypeHiddenButton hiddenButton;

    private MainSetCodeControlPane mainSetCodeControlPane;

    private JScrollPane mainSetCodeControlJSP;

    private MyButton mainSetTypeButton;

    private JPopupMenu menu;

    @Getter
    private String mainSetTypeName = "";

    private MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam;


    public MainSetTypeContainer(MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam,
                                boolean expanded) {
        // TODO Auto-generated constructor stub
        super();
        this.mainSetTypeOperatingContainerParam = mainSetTypeOperatingContainerParam;
        this.mainSetTypeName = mainSetTypeOperatingContainerParam.getMainSetType();
        init(expanded);
        autoGenerateOpratingContainers();

    }

    public MainSetTypeContainer(MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam,
                                MainSetTypeContainerModel codeControlPaneModel, boolean expanded) {
        // TODO Auto-generated constructor stub
        this.mainSetTypeOperatingContainerParam = mainSetTypeOperatingContainerParam;
        this.mainSetTypeName = mainSetTypeOperatingContainerParam.getMainSetType();
        init(expanded);
        mainSetCodeControlPane.restoreContent(codeControlPaneModel);
    }

    private void init(boolean expanded) {
        int width = (int) (PROPORTION * SysUtil.SCREEN_SIZE.getWidth());
        setLayout(new MainSetTypeLayout());

        menu = new JPopupMenu();

        hiddenButton = new MainSetTypeHiddenButton(mainSetTypeName, expanded) {

            @Override
            public void doSomethingWhenMousePressed(boolean expanded) {
                mainSetCodeControlPane.collapseThis();
                super.doSomethingWhenMousePressed(expanded);
            }

//            @Override
//            public void doClick() {
//                mainSetCodeControlPane.collapseThis();
//                super.doClick();
//            }
        };
        hiddenButton.setSize(width, HIDDEN_HEIGHT);
        setHiddenButton(hiddenButton);
        add(hiddenButton);

        mainSetTypeButton = new MyButton(moreIcon);
        mainSetTypeButton.setSize(MAIN_SET_BUTTON_WIDTH, HIDDEN_HEIGHT);
        mainSetTypeButton.addMouseListener(mouseAdapter);
        add(mainSetTypeButton);

        mainSetCodeControlPane = new MainSetCodeControlPane(mainSetTypeOperatingContainerParam);
        mainSetCodeControlJSP = mainSetCodeControlPane.getParentScrollPane();
        mainSetCodeControlJSP.setSize(width, MAIN_SET_TYPE_JSP_HEIGHT);

        Drawer drawer = new Drawer(expanded ? 1 : 0, mainSetCodeControlJSP);
        this.setDrawer(drawer);
        add(drawer);
    }


    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            updateMenuItems();
            menu.show(mainSetTypeButton, e.getX(), e.getY());
            super.mouseEntered(e);
        }
    };

    public void delModuleOpratingContainerFromComponent(String moduleId) {
        mainSetCodeControlPane.delModuleOpratingContainerFromComponent(moduleId);
    }

    public void delThis() {
        mainSetCodeControlPane.delThis();
    }

    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        // TODO Auto-generated method stub
        return mainSetCodeControlPane.getAllOpratingContainer();
    }

    public void collapseThis() {
        // TODO Auto-generated method stub
        mainSetCodeControlPane.collapseThis();
    }

    public MainSetTypeContainerModel getFormatStructureModel() {
        MainSetTypeContainerModel model = mainSetCodeControlPane.getFormatStructureModel();
        model.setMainSetTypeName(mainSetTypeName);
        return model;
    }

    private void updateMenuItems() {
        menu.removeAll();
        List<FormatTypeFeatureSelectionModel> list = SysService.MAIN_SET_SERVICE.getFeatureList(mainSetTypeName);
        if (list != null) {
            MainSetTypeMenuItem tempItem;
            for (FormatTypeFeatureSelectionModel tempModel : list) {
                tempItem = new MainSetTypeMenuItem(tempModel);
                menu.add(tempItem);

                //如果该菜单是只能添加一次的和自动生成只能添加一次的，查看当前添加的功能里面有没有和添加这个菜单对应的功能，有的话，将该菜单失能
                if (tempModel.getSetProperty() == FunctionUseProperty.onlyBeUsedOnce.getSysDictionaryValue() ||
                        tempModel.getSetProperty() == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue()) {
                    MainSetContainer theOpratingContainer;
                    ArrayList<OpratingContainerInterface> opratingContainerList = mainSetCodeControlPane.getAllOpratingContainerListInThisPane();
                    for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                        if (opratingContainer instanceof MainSetContainer) {
                            theOpratingContainer = (MainSetContainer) opratingContainer;
                            if (tempModel.getOrdinal() == theOpratingContainer.getOrdinalInUserDB()) {
                                tempItem.setEnabled(false);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Dimension getRequiredDimension() {
        int w = getDrawer().getContentWidth(),
                h = (int) (getDrawer().getContentHeight() * getDrawer().getRatio()) + HIDDEN_HEIGHT;
        return new Dimension(w, h);
    }

    /**
     * 生成事先设置的需要生成的方法
     */
    private void autoGenerateOpratingContainers() {
        List<FormatTypeFeatureSelectionModel> list = SysService.MAIN_SET_SERVICE.getFeatureList(mainSetTypeName);
        if (list != null) {
            MainSetContainer opratingContainer = null;
            for (FormatTypeFeatureSelectionModel selectionModel : list) {
                if (selectionModel.getSetProperty() == FunctionUseProperty.autoGenerateOnce.getSysDictionaryValue()
                        || selectionModel.getSetProperty() == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue()) {
                    opratingContainer = mainSetCodeControlPane.addOpratingPane(selectionModel, true);
                    if (opratingContainer == null) {
                        String text = "必填模板无法添加\"" + selectionModel.getTypeName() + "\"分类的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应位置！	(✪ω✪)";
                        String logtext = getClass() + "（添加功能异常）" +
                                "必填模板无法添加\"" + selectionModel.getTypeName() + "\"模块的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应标记！";
                        CodeGenerationFrameHolder.errorLogging(text, logtext);
                    }
                } else if (selectionModel.getSetProperty() == FunctionUseProperty.autoGenerateOnceAndCanNotDel.getSysDictionaryValue()) {
                    opratingContainer = mainSetCodeControlPane.addOpratingPane(selectionModel, false);
                    if (opratingContainer == null) {
                        String text = "必填模板无法添加\"" + selectionModel.getTypeName() + "\"模块的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应位置！	(✪ω✪)";
                        String logtext = getClass() + "（添加功能异常）" +
                                "必填模板无法添加\"" + selectionModel.getTypeName() + "\"模块的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应标记！";
                        CodeGenerationFrameHolder.errorLogging(text, logtext);
                    }
                }
            }
        }
    }

    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        if (currentOpratingContainer.getFirstCommandOpratingContainer() != null && currentOpratingContainer.getFirstCommandOpratingContainer() instanceof MainSetContainer) {
            MainSetContainer mainSetContainer = (MainSetContainer) currentOpratingContainer.getFirstCommandOpratingContainer();
            if (mainSetContainer != null) {
                if (this.mainSetTypeName.equals(mainSetContainer.getMainSetTypeName())) {
                    expandHiddenPanel();
                    mainSetCodeControlPane.autoCollapse(currentOpratingContainer);
                } else {
                    packUpHiddenPanel();
                }
            }
        }
    }


    /**
     * 负责布局面板组件
     */
    class MainSetTypeLayout implements LayoutManager {

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

            getHiddenButton().setBounds(0, 0, w - MAIN_SET_BUTTON_WIDTH, HIDDEN_HEIGHT);

            mainSetTypeButton.setBounds(w - MAIN_SET_BUTTON_WIDTH, 0, MAIN_SET_BUTTON_WIDTH, HIDDEN_HEIGHT);
            // 抽屉只显示抽出的比例
            getDrawer().setBounds(xCoordinates, dHeight, w, h - dHeight);
        }
    }

    class MainSetTypeMenuItem extends JMenuItem {// implements Runnable {

        /**
         *
         */
        private static final long serialVersionUID = -490331634707256399L;

        private FormatTypeFeatureSelectionModel featureSelectionModel;

        public MainSetTypeMenuItem(FormatTypeFeatureSelectionModel featureSelectionModel) {
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
                    MainSetContainer mainSetContainer = mainSetCodeControlPane.addOpratingPane(featureSelectionModel, true);
                    if (mainSetContainer == null) {
                        LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o    亲，这个功能的数据有点问题，没法写进去喔", "系统信息",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        if (CodeGenerationFrameHolder.autoCollapseCheckBox != null) {
                            if (CodeGenerationFrameHolder.autoCollapseCheckBox.isSelected() == true) {
                                if (CodeGenerationFrameHolder.currentFormatControlPane != null) {
                                    CodeGenerationFrameHolder.currentFormatControlPane.autoCollapse(mainSetContainer);

                                }
                            }
                        }

                        //该功能的组件自动选择
                        //mainSetContainer.setNoUserSelectionIsRequiredValue();

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
