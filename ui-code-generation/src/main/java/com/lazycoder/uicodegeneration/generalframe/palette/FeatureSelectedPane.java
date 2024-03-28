package com.lazycoder.uicodegeneration.generalframe.palette;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FeatureSelectedPane extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1814985979019819288L;

//    private static final ImageIcon rename_icon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit"
//            + File.separator + "SysSettingPane" + File.separator + "DBEditPane" + File.separator + "rename_file.png");

    private JPopupMenu menu;
    private JMenuItem del;
    private Box vBox;

    private FeatureSelectedTabPane featureSelectedTabPane;

//    private AbstractFormatControlPane formatControlPane;

    /**
     * Create the panel.
     */
    public FeatureSelectedPane(FeatureSelectedTabPane featureSelectedTabPane) {
        this.featureSelectedTabPane = featureSelectedTabPane;
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        this.setLayout(fl);
//        this.setLayout(new BorderLayout());
        vBox = Box.createVerticalBox();
        this.add(vBox, BorderLayout.CENTER);
        menuInit();

        this.setBackground(Color.white);
//        this.setSize(200, 500);
        this.setVisible(true);
    }

    public void clearContent(){
        vBox.removeAll();
    }

    /**
     * 添加模块
     *
     * @param list
     */
    public void addModuleList(ArrayList<ModuleRelatedParam> list) {
        FeatureSelectionLabel featureSelectionLabels[] = new FeatureSelectionLabel[list.size()];
        for (int i = 0; i < featureSelectionLabels.length; i++) {
            featureSelectionLabels[i] = new FeatureSelectionLabel(list.get(i));
            featureSelectionLabels[i].addMouseListener(mouseAdapter);
            vBox.add(featureSelectionLabels[i]);
        }
        this.repaint();
    }

    private ActionListener menuLisener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            FeatureSelectionLabel label = ((FeatureSelectionLabel) menu.getInvoker());
            if (e.getSource() == del) {
                delModule(label);
            }
        }
    };

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent e) {
            FeatureSelectionLabel theLabel = (FeatureSelectionLabel) e.getSource();
            theLabel.updateMenu(menu, del);
            menu.show(theLabel, e.getX(), e.getY());
        }
    };

//    /**
//     * 更新模块列表
//     *
//     * @param formatControlPane
//     */
//    public void updateModuleList(AbstractFormatControlPane formatControlPane) {
//        this.formatControlPane = formatControlPane;
//        vBox.removeAll();
//        addModuleList(formatControlPane.getUseModuleRelatedParamList());
////        Window window = SwingUtilities.getWindowAncestor(this);
////        if (window != null) {
////            window.validate();
////        }
//    }

    private void menuInit() {
        menu = new JPopupMenu();

        del = new JMenuItem("删除");
        del.setForeground(Color.gray);
        del.addActionListener(menuLisener);
    }

    /**
     * 删除某个模块
     *
     * @param label
     */
    private void delModule(FeatureSelectionLabel label) {
        ModuleRelatedParam moduleRelatedParam = label.getModuleRelatedParam();
        if (moduleRelatedParam != null) {
            if (featureSelectedTabPane.getFormatControlPane() != null) {
                String moduleId = moduleRelatedParam.getModule().getModuleId();
                ArrayList<ModuleRelatedParam> currentUseModuleRelatedParamList = featureSelectedTabPane.getFormatControlPane().getUseModuleRelatedParamList();
                ArrayList<Module> needModuleList = getNeedModuleList(moduleId, currentUseModuleRelatedParamList);
                if (needModuleList.size() > 0) {//当前添加的模块有些模块需要用到 moduleId 模块，给出提示，不给删除
                    ArrayList<String> needModuleStrList = new ArrayList<>();
                    for (Module module : needModuleList) {
                        needModuleStrList.add(module.getModuleName());
                    }
                    LazyCoderOptionPane.showMessageDialog((Component) CodeGenerationFrameHolder.codeGenerationFrame, "无法删除\"" + moduleRelatedParam.getModule().getModuleName() + "\"模块，因为还有模块 \"" + StringUtils.join(needModuleStrList, "、") + " \"需要用到它！", "来自系统的回复",
                            JOptionPane.INFORMATION_MESSAGE);

                } else if (needModuleList.size() == 0) {//没有哪个模块需要使用该模块，可以删除
                    int show = LazyCoderOptionPane.showConfirmDialog((Component) CodeGenerationFrameHolder.codeGenerationFrame, "o(￣▽￣)ｄ 真的要去掉\"" + moduleRelatedParam.getModule().getModuleName() + "\"这个模块吗？删除以后会立即保存当前内容无法还原", "来自系统的确认",
                            JOptionPane.YES_NO_OPTION);
                    if (show == JOptionPane.YES_OPTION) {
                        ArrayList<ModuleRelatedParam> moduleRelatedParamList = new ArrayList<ModuleRelatedParam>();

                        //再找找有哪些对用户屏蔽的模块，只是这个模块才需要用的，把它也删了
                        ArrayList<Module> moduleList = featureSelectedTabPane.getFormatControlPane().getModuleListForOnlyAddThisModule(moduleRelatedParam.getModule());
                        ArrayList<ModuleRelatedParam> moduleRelatedParamArrayList = AbstractFormatControlPane.arrayListModuletrantoArrayListModuleRelatedParam(moduleList);

                        moduleRelatedParamList.add(moduleRelatedParam);
                        moduleRelatedParamList.addAll(moduleRelatedParamArrayList);

                        featureSelectedTabPane.getFormatControlPane().delModuleList(moduleRelatedParamList);
                        CodeGenerationFrameHolder.featureSelectedPane.updateModuleList(CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane());

                        CodeGenerationFrameHolder.generateCode();
                        LazyCoderOptionPane.showMessageDialog((Component) CodeGenerationFrameHolder.codeGenerationFrame, "已经去掉\"" + moduleRelatedParam.getModule().getModuleName() + "\"模块，并保存当前代码内容!", "来自系统的回复",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }

    /**
     * 从 currentModuleList 中找出一定用到 moduleId 这个模块的所有模块
     *
     * @param moduleId
     * @param currentModuleList
     * @return
     */
    private ArrayList<Module> getNeedModuleList(String moduleId, ArrayList<ModuleRelatedParam> currentModuleList) {
        ArrayList<Module> list = new ArrayList<>();

        List<Module> useModuleList = SysService.MODULE_SERVICE.getModulesListThatUsedTheModule(moduleId);//获取数据库里需要用到 moduleId 这个模块的所有模块
        for (ModuleRelatedParam moduleRelatedParam : currentModuleList) {//如果 currentModuleList 里面有和 useModuleList 一样的模块，把该数据返回
            for (Module temp : useModuleList) {
                if (moduleRelatedParam.getModuleInfo().getModuleId().equals(temp.getModuleId())) {
                    list.add(temp);
                    break;
                }
            }
        }
        return list;
    }


}
