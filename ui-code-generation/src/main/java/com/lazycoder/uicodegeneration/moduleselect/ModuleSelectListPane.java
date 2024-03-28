package com.lazycoder.uicodegeneration.moduleselect;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.TheClassification;
import com.lazycoder.database.model.formodule.ModuleStaticMethod;
import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.service.ModuleUseSetting;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.AssociatedModule;
import com.lazycoder.uiutils.mycomponent.multistatecomponent.LazyCoderMultiStateComponentInterface;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 改自 https://www.jiweichengzhu.com/article/5ff1be1e8abe463794aa1011b4bcff96
 *
 * @author admin
 */
//@Slf4j
public class ModuleSelectListPane extends JTree {

    /**
     *
     */
    private static final long serialVersionUID = -1514112386499240616L;

    /**
     * 首次选择
     */
    public static final int FIRST_SELECTION = 0;

    /**
     * 再次选择
     */
    public static final int RESELECT = 1;

    private DefaultTreeModel model;

    //                         用来显示的根节点     真正传递数据的、所有的跟节点
    private DefaultMutableTreeNode showRoot, allRoot;

    @Setter
    private UsingObject usingObject = null;

    private ArrayList<ModuleRelatedParam> allModuleRelatedParamList = null;//所有需要选择的模块

    private Border inBorder = BorderFactory.createLineBorder(new Color(228, 228, 228), 1);

    public ModuleSelectListPane() {
        // TODO Auto-generated constructor stub
        ToolTipManager.sharedInstance().registerComponent(this);
        setRootVisible(false);
        setToggleClickCount(1);
        setUI(new ModuleSelectPaneUI());
        setCellRenderer(new ModuleSelectCellRenderer());

        initListener();
    }

    /**
     * 查看module模块在不在moduleInfoList里面
     *
     * @param module
     * @param moduleRelatedParamList
     * @return
     */
    private static boolean checkTheModuleHasBeenAdded(Module module, ArrayList<ModuleRelatedParam> moduleRelatedParamList) {
        boolean flag = false;
        if (moduleRelatedParamList != null) {
            for (ModuleRelatedParam moduleRelatedParam : moduleRelatedParamList) {
                if (moduleRelatedParam.getModule().getModuleId().equals(module.getModuleId())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }


    /**
     * 显示当前模块列表，并根据moduleInfoList选中预先对应的模块
     *
     * @param usingObject            要添加模块的格式范围
     * @param moduleRelatedParamList 之前添加的模块列表，没有填null
     * @param selectState            选择状态，是第一次选择（FIRST_SELECTION）还是再次选择（RESELECT）
     */
    public void showModuleList(UsingObject usingObject, ArrayList<ModuleRelatedParam> moduleRelatedParamList, int selectState) {
        //这里要加个状态，判断是添加还是修改，添加的话，把生成程序一定要使用的、还有对用户屏蔽的就行状态修改，暂定把prinull没有关联模块设置为对用户屏蔽，把preselected没有关联状态设置为一定要使用
        showRoot = new DefaultMutableTreeNode();
        allRoot = new DefaultMutableTreeNode();
        model = new DefaultTreeModel(showRoot);
        this.allModuleRelatedParamList = moduleRelatedParamList;
        List<Module> moduleList = SysService.MODULE_SERVICE.getAllModulesUsedby(usingObject);
        if (moduleList != null) {
            ArrayList<Integer> useSettingValues;
            String currentClassName = "";
            TheClassification classification;
            ClassNameNode showClassNameNode = null, classNameNodeForAll = null;
            ModuleNameNode moduleNameNode, moduleNameNodeForAll;
            for (Module moduleTemp : moduleList) {
                if (moduleTemp.getClassName().equals(currentClassName) == false) {
                    classification = new TheClassification();
                    classification.setClassName(moduleTemp.getClassName());

                    //创建一个用来展示的分类，添加到展示使用的节点
                    showClassNameNode = new ClassNameNode(classification);
                    showRoot.add(showClassNameNode);

                    //再创建一个分类，用来添加到存放所有数据的节点
                    classNameNodeForAll = new ClassNameNode(classification);
                    allRoot.add(classNameNodeForAll);

                    currentClassName = moduleTemp.getClassName();
                }

                //创建模块节点
                moduleNameNode = new ModuleNameNode(moduleTemp, this);
                if (selectState == FIRST_SELECTION) {//第一次选择
                    if (moduleTemp.getEnabledState() == Module.FALSE_) {//不能使用（未曾写过任何内容）的
                        moduleNameNode.setPreCannotUse();
                    }
                } else {//再次选择
                    if (checkTheModuleHasBeenAdded(moduleTemp, moduleRelatedParamList) == true) {
                        moduleNameNode.setPreFirUseModule(null);
                    } else if (moduleTemp.getEnabledState() == Module.FALSE_) {
                        moduleNameNode.setPreCannotUse();
                    } else {
//                        if (useSettingValues.contains(ModuleUseSetting.USER_SHIELDING)) {
//                            moduleNameNode.setCannotChooseManual();
//                        }
                    }
                }
                /**
                 * 首次选择的时候，如果是还没编辑内容，导致不能使用的模块，设置对应状态
                 * 如果是生成程序的时候一定要是用的，设置为对应状态（第一次的时候还要进行校验，看看生成程序时一定要使用的模块里有没有还没编辑过的）
                 * 如果是对用户屏蔽的，选上对应状态
                 *
                 * 再次选择的时候，如果是之前的列表里面有的，通通选上，设置为对应状态
                 * 如果是还没编辑的内容，导致不能使用的模块，设置对应状态
                 * 如果是对用户屏蔽的，选上对应状态
                 *
                 */

                useSettingValues = ModuleStaticMethod.getUseSettingValues(moduleTemp);
                if (!useSettingValues.contains(ModuleUseSetting.USER_SHIELDING)) {//再改版的改动：选择对用户屏蔽的模块都不显示出来
                    showClassNameNode.add(moduleNameNode);

                    if (selectState == FIRST_SELECTION) {//第一次选择
                        if (moduleTemp.getEnabledState() == Module.TRUE_) {//不能使用（未曾写过任何内容）的
                            if (useSettingValues.contains(ModuleUseSetting.USER_SHIELDING)) {
                                moduleNameNode.setCannotChooseManual();
                            }
                        }
                    } else {//再次选择
                        if (checkTheModuleHasBeenAdded(moduleTemp, moduleRelatedParamList) == true) {
                            //该做的上面那里做了，这里不用
                            //moduleNameNode.setPreFirUseModule(null);
                        } else if (moduleTemp.getEnabledState() == Module.FALSE_) {
                            //该做的上面那里做了，这里不用
                            //moduleNameNode.setPreCannotUse();
                        } else {
                            //这里代码真正是为了在需要的时候执行这里
                            if (useSettingValues.contains(ModuleUseSetting.USER_SHIELDING)) {
                                moduleNameNode.setCannotChooseManual();
                            }
                        }
                    }
                }
                moduleNameNodeForAll = ModuleNameNode.cloneModuleNameNode(moduleNameNode);
                classNameNodeForAll.add(moduleNameNodeForAll);
            }
        }
        setModel(model);
        expandNode();
    }

    private void expandNode() {
        ArrayList<Integer> useSettingValues;
        boolean collapseFlag = true;//是否折叠
        Module moduleTemp;

        TreePath path;
        TreeNode nodeTemp;
        ClassNameNode classNameNode;
        ModuleNameNode moduleNameNode;
        boolean flag = false;
        for (int i = 0; i < showRoot.getChildCount(); i++) {
            nodeTemp = showRoot.getChildAt(i);
            if (nodeTemp instanceof ClassNameNode) {
                classNameNode = (ClassNameNode) nodeTemp;
                collapseFlag = true;
                flag = false;
                for (int a = 0; a < classNameNode.getChildCount(); a++) {//只要里面有一个是没选的，或者是用户手动选择的，都展开， 其他的没必要展开(不是用户选的或者需要选的，没必要一定要用户看见)
                    nodeTemp = classNameNode.getChildAt(a);
                    if (nodeTemp instanceof ModuleNameNode) {
                        moduleNameNode = (ModuleNameNode) nodeTemp;
                        if (moduleNameNode.isPreUseModule() ||
                                moduleNameNode.isModuleSelectedNull() ||
                                moduleNameNode.isPreFirUseModule()) {
                            flag = true;
                            collapseFlag = false;
                            break;
                        } else {//看看有没有可能这个分类里面的模块都是对用户屏蔽的，如果是，通通折叠
                            moduleTemp = moduleNameNode.getModule();
                            if (moduleTemp != null) {
                                useSettingValues = ModuleStaticMethod.getUseSettingValues(moduleTemp);
                                if (useSettingValues.contains(ModuleUseSetting.USER_SHIELDING) == false) {//只要有一个不是对用户屏蔽的，都要展开
                                    collapseFlag = false;
                                    moduleNameNode.setCannotChooseManual();
                                }
                            }
                        }
                    }
                }
                if (flag == true) {
                    if (collapseFlag == true) {
                        path = new TreePath(model.getPathToRoot(classNameNode));
                        collapsePath(path);
                    } else {
                        path = new TreePath(model.getPathToRoot(classNameNode));
                        expandPath(path);
                    }
                } else {
                    path = new TreePath(model.getPathToRoot(classNameNode));
                    collapsePath(path);
                }
            }
        }
    }

    private void initListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TreePath path = getSelectionPath();
                if (null != path) {
                    Object object = path.getLastPathComponent();
                    if (object instanceof ModuleNameNode) {
                        ModuleNameNode node = (ModuleNameNode) object;
                        // 二级节点（分组）咱就不管了
                        if (node.getLevel() == 2) {
                            if (node.isPreUseModule() || node.isModuleSelectedNull()) {//如果点击了某个模块，把该模块背景设为其他颜色，并换上边框
                                node.nodePane.setBorder(inBorder);
                                node.nodePane.setBackground(Color.ORANGE);
                                node.setSelected(!node.isSelected());
                            }

                            AbstractModuleSelectBaseNode cate;
                            ModuleNameNode buddy;
                            // 去掉选中之外其他所有节点的特效
                            for (int i = 0; i < showRoot.getChildCount(); i++) {
                                cate = (AbstractModuleSelectBaseNode) showRoot.getChildAt(i);
                                // 三级节点
                                for (int j = 0; j < cate.getChildCount(); j++) {
                                    buddy = (ModuleNameNode) cate.getChildAt(j);
                                    if (node != buddy) {
//										buddy.nodePane.setBorder(null);
                                        if (buddy.isPreUseModule() || buddy.isModuleSelectedNull()) {
                                            buddy.nodePane.setBackground(null);
                                            buddy.nodePane.setBorder(ModuleNameNode.BORDER);
                                        }
                                    }
                                    model.reload(buddy);
                                }
                            }
                            model.reload(node);
                        }
                    }
                }
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                TreePath path = getPathForLocation(e.getX(), e.getY());
                if (null != path) {
                    Object object = path.getLastPathComponent();
                    AbstractModuleSelectBaseNode node = (AbstractModuleSelectBaseNode) object;
                    if (object instanceof ModuleNameNode) {
                        ModuleNameNode moduleNameNode = (ModuleNameNode) object;

                        if (node.getLevel() == 2 && node.nodePane.getBorder() != inBorder
                                && moduleNameNode.isPreUseModule() || moduleNameNode.isModuleSelectedNull()) {
                            node.nodePane.setBorder(inBorder);
                        }
                        model.reload(node);
                    }

                    AbstractModuleSelectBaseNode cate = null;
                    ModuleNameNode buddy;
                    // 去掉选中之外其他所有节点的特效
                    for (int i = 0; i < showRoot.getChildCount(); i++) {
                        cate = (AbstractModuleSelectBaseNode) showRoot.getChildAt(i);
//						if (node != cate && cate.getNodeView().getBackground() != Color.ORANGE) {
//							cate.getNodeView().setBorder(null);
//						}
//						model.reload(cate);
                        // 三级节点
                        for (int j = 0; j < cate.getChildCount(); j++) {
                            buddy = (ModuleNameNode) cate.getChildAt(j);
                            if (node != buddy && buddy.nodePane.getBackground() != Color.ORANGE) {
                                buddy.nodePane.setBorder(ModuleNameNode.BORDER);
                            }
                            model.reload(buddy);
                        }
                    }
                }
            }
        });

    }

    /**
     * 获取选择的模块列表
     *
     * @return
     */
    public ArrayList<Module> getSelectedModuleList() {
        ArrayList<Module> list = new ArrayList<Module>();
        TreeNode nodeTemp;
        ClassNameNode classNameNode;
        ModuleNameNode moduleNameNode;
        for (int i = 0; i < allRoot.getChildCount(); i++) {
            nodeTemp = allRoot.getChildAt(i);
            if (nodeTemp instanceof ClassNameNode) {
                classNameNode = (ClassNameNode) nodeTemp;
                for (int a = 0; a < classNameNode.getChildCount(); a++) {
                    nodeTemp = classNameNode.getChildAt(a);
                    if (nodeTemp instanceof ModuleNameNode) {
                        moduleNameNode = (ModuleNameNode) nodeTemp;

                        if (moduleNameNode.isPreUseModule() ||
                                moduleNameNode.isAlsoUseModule() ||
                                moduleNameNode.isParasiticModule()) {
                            list.add(moduleNameNode.getModule());
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 选中某个模块
     *
     * @param module
     */
    public boolean selectedModule(Module module) {//这里要想想是用all还是show
        //先查看这个模块需要使用什么模块A，不需要使用什么模块B
        //查看当前【不能选这个】、【该模块不能使用】 的所有模块有没有这个和 A 一样的，如果有，就不给选，给提示
        //查看当前【之前已经选过】以及【某些模块需要】、【当前已经选中】 的所有模块有没有这个和 B 一样的，如果有，也不给选，给提示
        boolean flag = false;

        List<AssociatedModule> needAssociatedModuleListTemp = SysService.MODULE_SERVICE.getAllNeedUsedModuleList(module);
        ArrayList<Module> needListTemp = new ArrayList<>();//所有使用module的时候需要使用的模块
        for (AssociatedModule needTemp : needAssociatedModuleListTemp) {
            needListTemp.add(needTemp.getModule());
        }
        //看看需要使用的模块有没有不是这个使用范围的，或者还没录入过任何内容的，有的话，不给选
        for (Module useModule : needListTemp) {
            if (useModule.getUsingRangeParam().equals(Module.NEW_STATE)) {//这个模块没有被编辑过
                if (useModule.getEnabledState() == Module.FALSE_) {//有异常情况，这个模块需要使用到的模块，有一个没有被编辑过
                    LazyCoderOptionPane.showMessageDialog(null, "≡٩(๑>₃<)۶   不能选" + module.getModuleName() + "模块！！！\n"
                                    + "这模块需要用到\"" + useModule.getModuleName() + "\"模块，" + "该模块还没有录入过任何内容！",
                            "Σ(っ°Д°;)っ  这个数据源的数据有问题喔", JOptionPane.PLAIN_MESSAGE);
                    flag = true;
                    break;
                }
            } else {
                if (this.usingObject != null) {
                    List<com.lazycoder.database.model.formodule.UsingObject> usingObjectList = ModuleStaticMethod
                            .getUsingRange(useModule);
                    if (UsingObject.contantOrNot(this.usingObject, usingObjectList) == false) {//有异常情况，这个模块需要使用到的模块，有一个不是这个使用范围的
                        LazyCoderOptionPane.showMessageDialog(null, "≡٩(๑>₃<)۶   不能选" + module.getModuleName() + "模块！！！\n"
                                        + "这模块需要用到\"" + useModule.getModuleName() + "\"模块，" + "该模块设置的可使用范围和当前不匹配！",
                                "Σ(っ°Д°;)っ  这个数据源的数据有问题喔", JOptionPane.PLAIN_MESSAGE);
                        flag = true;
                        break;
                    }
                }
            }
        }

        ArrayList<AssociatedModule> noUseAssociatedModuleListTemp = SysService.MODULE_SERVICE.getAllNoUsedModuleList(module);
        ArrayList<Module> noUseListTemp = new ArrayList<>();//所有使用module时，不能使用的模块
        for (AssociatedModule noUseAssociatedModuleTemp : noUseAssociatedModuleListTemp) {
            noUseListTemp.add(noUseAssociatedModuleTemp.getModule());
        }


        if (flag == false && needAssociatedModuleListTemp.size() > 0) {

            ArrayList<ModuleNameNode> disableModuleNodeList = getPreCannotUseNodeList();
            for (ModuleNameNode moduleNameNode : disableModuleNodeList) {//查看不能使用的模块里面，有没有需要使用的模块，如果有，不给选（实际上也不会显示不能使用的模块，这里不需要）
                for (AssociatedModule associatedModule : needAssociatedModuleListTemp) {
                    if (moduleNameNode.getModule().getModuleId().equals(associatedModule.getModule().getModuleId())) {
                        LazyCoderOptionPane.showMessageDialog(null, "≡٩(๑>₃<)۶   不能选" + module.getModuleName() + "模块！！！"
                                + "使用这个模块需要用到\"" +
                                associatedModule.getModule().getModuleName() + "\"模块，\n\"可" +
                                associatedModule.getModule().getModuleName() + "\"这个模块现在什么都没写！");
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    break;
                }
            }
            if (flag == false) {
                ArrayList<ModuleNameNode> currentNoUseModuleNodeList = getAlsoDisableModuleNodeList();//当前选择的模块不允许同时使用的模块
                for (ModuleNameNode moduleNameNode : currentNoUseModuleNodeList) {
                    for (AssociatedModule associatedModule : needAssociatedModuleListTemp) {
                        if (moduleNameNode.getModule().getModuleId().equals(associatedModule.getModule().getModuleId())) {
                            LazyCoderOptionPane.showMessageDialog(null, "≡٩(๑>₃<)۶   不能选\"" + module.getModuleName() + "\"模块！！！"
                                    + "使用这个模块需要\"" +
                                    associatedModule.getModule().getModuleName() + "\"模块，\n\"可现在使用到的模块不允许使用" +
                                    associatedModule.getModule().getModuleName() + "\"模块");
                            flag = true;
                            break;
                        }
                    }
                    if (flag == true) {
                        break;
                    }
                }
            }
        }

        if (flag == false) {
            if (noUseAssociatedModuleListTemp.size() > 0) {
                ArrayList<ModuleNameNode> currentNeedModuleNodeList = getPreFirUseModuleNodeList();
                currentNeedModuleNodeList.addAll(getPreUseModuleNodeList());
                currentNeedModuleNodeList.addAll(getAlsoUseModuleNodeList());
                for (ModuleNameNode moduleNameNode : currentNeedModuleNodeList) {
                    for (AssociatedModule associatedModule : noUseAssociatedModuleListTemp) {
                        if (moduleNameNode.getModule().getModuleId().equals(associatedModule.getModule().getModuleId())) {
                            LazyCoderOptionPane.showMessageDialog(null, "≡٩(๑>₃<)۶   不能选\"" + module.getModuleName() + "\"模块！！！"
                                    + "使用这个模块不能同时使用用\"" +
                                    associatedModule.getModule().getModuleName() + "\"模块！");
                            flag = true;
                            break;
                        }
                    }
                    if (flag == true) {
                        break;
                    }
                }
            }
        }
        if (flag == false) {
            boolean allbltemp = false;
            TreeNode allnodeTemp;
            ClassNameNode allclassNameNode;
            ModuleNameNode allmoduleNameNode, showModuleNameNode1, showModuleNameNode2;
            for (int i = 0; i < allRoot.getChildCount(); i++) {
                allnodeTemp = allRoot.getChildAt(i);
                if (allnodeTemp instanceof ClassNameNode) {
                    allclassNameNode = (ClassNameNode) allnodeTemp;
                    for (int a = 0; a < allclassNameNode.getChildCount(); a++) {
                        allnodeTemp = allclassNameNode.getChildAt(a);
                        if (allnodeTemp instanceof ModuleNameNode) {
                            allmoduleNameNode = (ModuleNameNode) allnodeTemp;

                            allbltemp = false;
                            for (AssociatedModule associatedModule : needAssociatedModuleListTemp) {
                                if (associatedModule.getModule().getModuleId().equals(allmoduleNameNode.getModule().getModuleId())) {
                                    if (allmoduleNameNode.isModuleSelectedNull() ||
                                            allmoduleNameNode.isAlsoUseModule() ||
                                            allmoduleNameNode.isPreUseModule() ||
                                            allmoduleNameNode.isCannotChooseManual()
                                    ) {//还没选的，或者已经设置为其他模块需要的，已经选了的，就设置为对应关联模块需要的模块
                                        allmoduleNameNode.setAlsoUseModule(associatedModule);

                                        //这里只是重复了上一步的步骤，对allRoot对应模块做了上述处理后，对showRoot对应模块也做同样处理
                                        showModuleNameNode1 = getCorrespondingShowModuleNameNode(allmoduleNameNode);
                                        if (showModuleNameNode1 != null) {
                                            if (showModuleNameNode1.isModuleSelectedNull() ||
                                                    showModuleNameNode1.isAlsoUseModule() ||
                                                    showModuleNameNode1.isPreUseModule() ||
                                                    showModuleNameNode1.isCannotChooseManual()
                                            ) {//还没选的，或者已经设置为其他模块需要的，已经选了的，就设置为对应关联模块需要的模块
                                                showModuleNameNode1.setAlsoUseModule(associatedModule);

                                            }
                                        }
                                        //到这里结束
                                    }
                                    //剩下的状态，如果是【之前选中】的，就不处理
                                    //由于之前的校验，不会是【不能选这个】、【不能使用】
                                    allbltemp = true;
                                    break;
                                }
                            }
                            if (allbltemp == false) {
                                for (AssociatedModule associatedModule : noUseAssociatedModuleListTemp) {
                                    if (associatedModule.getModule().getModuleId().equals(allmoduleNameNode.getModule().getModuleId())) {
                                        if (allmoduleNameNode.isModuleSelectedNull() || allmoduleNameNode.isAlsoDisableModule()) {//还没选的，或者已经设置为【不能选这个，（因为当前选中的某些模块不允许）】的，就设置为【不能选这个，（因为当前选中的某些模块不允许）】
                                            allmoduleNameNode.setAlsoDisableModule(associatedModule);

                                            //这里只是重复了上一步的步骤，对allRoot对应模块做了上述处理后，对showRoot对应模块也做同样处理
                                            showModuleNameNode2 = getCorrespondingShowModuleNameNode(allmoduleNameNode);
                                            if (showModuleNameNode2 != null) {
                                                if (showModuleNameNode2.isModuleSelectedNull() || showModuleNameNode2.isAlsoDisableModule()) {//还没选的，或者已经设置为【不能选这个，（因为当前选中的某些模块不允许）】的，就设置为【不能选这个，（因为当前选中的某些模块不允许）】
                                                    showModuleNameNode2.setAlsoDisableModule(associatedModule);

                                                }
                                            }
                                            //到这里结束
                                        }
                                        //剩下的状态，由于在上一步的排查，不会是【已经选中】或者【之前选中】的，就不处理
                                        //由于之前的校验，不会是【不能选这个】、【不能使用】
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return flag == false ? true : false;
    }

    /**
     * ——取消选中某个模块时
     * 先查看这个模块需要使用什么模块A，不需要使用什么模块B
     * 拿到只是A这个模块才需要使用的模块C，使用A和当前选中的其他模块都需要使用的模块D
     * 拿到只是A这个模块不能使用的模块E，使用A和当前选中的其他模块都不能使用使用的模块F
     * 把C改成【不选（手动）】，把D的关联模块去掉A，再重新选择【某些模块需要】，并刷新提示
     * 把E改成【不选（手动）】，把F的关联模块去掉A，再重新选择【不能选这个（因为当前选中的某些模块不允许）】，并刷新提示
     */
    public void cancelSelectedModule(Module module) {
        List<AssociatedModule> needAssociatedModuleListTemp = SysService.MODULE_SERVICE.getAllNeedUsedModuleList(module),
                noUseAssociatedModuleListTemp = SysService.MODULE_SERVICE.getAllNoUsedModuleList(module);
        List<Module> needListTemp = new ArrayList<>(), noUseListTemp = new ArrayList<>();
        for (AssociatedModule associatedModule : needAssociatedModuleListTemp) {
            needListTemp.add(associatedModule.getModule());//获取所有使用模块module的时候，需要使用的模块
        }
//        log.info(module.getModuleName() + "模块需要使用的模块：" + JsonUtil.getJsonStr(needListTemp));

        for (AssociatedModule associatedModule : noUseAssociatedModuleListTemp) {
            noUseListTemp.add(associatedModule.getModule());//获取所有使用模块module的时候，不能使用的模块
        }
//        log.info(module.getModuleName() + "模块不能使用的模块：" + JsonUtil.getJsonStr(noUseListTemp));


        ArrayList<Module> selectedModuleList = new ArrayList<>();
        ArrayList<ModuleNameNode> currentNeedModuleNodeList = getPreFirUseModuleNodeList();
        currentNeedModuleNodeList.addAll(getPreUseModuleNodeList());
        for (ModuleNameNode moduleNameNode : currentNeedModuleNodeList) {
            if (moduleNameNode.getModule().getModuleId().equals(module.getModuleId()) == false) {
                selectedModuleList.add(moduleNameNode.getModule());
            }
        }
//        log.info("当前选中的模块还有：" + JsonUtil.getJsonStr(selectedModuleList));

        ArrayList<Module> onlyANeedModuleList = new ArrayList<>();
        ArrayList<Module> aModuleUseInCommonWithOtherList = new ArrayList<>();//使用A模块和其他模块都需要使用的模块
        LazyCoderMultiStateComponentInterface.setNeedModuleParams(needListTemp, selectedModuleList, onlyANeedModuleList, aModuleUseInCommonWithOtherList);
//        log.info("只是" + module.getModuleName() + "模块需要使用的模块：" + JsonUtil.getJsonStr(onlyANeedModuleList));
//        log.info(module.getModuleName() + "模块和其他模块共用的模块" + JsonUtil.getJsonStr(aModuleUseInCommonWithOtherList));

        ArrayList<Module> onlyANoNeedModuleList = new ArrayList<>();
        ArrayList<Module> aModuleNoUseInCommonWithOtherList = new ArrayList<>();//使用A模块和其他模块都不能使用的模块
        LazyCoderMultiStateComponentInterface.setNoNeedModuleParams(noUseListTemp, selectedModuleList, onlyANoNeedModuleList, aModuleNoUseInCommonWithOtherList);
//        log.info("只是" + module.getModuleName() + "模块不能使用的模块：" + JsonUtil.getJsonStr(onlyANoNeedModuleList));
//        log.info(module.getModuleName() + "模块和其他模块都不能使用用的模块" + JsonUtil.getJsonStr(aModuleUseInCommonWithOtherList));

        ModuleNameNode showmoduleNameNode;

        TreeNode allnodeTemp;
        ClassNameNode allclassNameNode;
        ModuleNameNode allmoduleNameNode;
        for (int i = 0; i < allRoot.getChildCount(); i++) {
            allnodeTemp = allRoot.getChildAt(i);
            if (allnodeTemp instanceof ClassNameNode) {
                allclassNameNode = (ClassNameNode) allnodeTemp;
                for (int a = 0; a < allclassNameNode.getChildCount(); a++) {
                    allnodeTemp = allclassNameNode.getChildAt(a);
                    if (allnodeTemp instanceof ModuleNameNode) {
                        allmoduleNameNode = (ModuleNameNode) allnodeTemp;
                        showmoduleNameNode = getCorrespondingShowModuleNameNode(allmoduleNameNode);

                        if (SysService.MODULE_SERVICE.haveTheModuleInList(onlyANeedModuleList, allmoduleNameNode.getModule())) {//该模块只是选中的模块才需要
                            //这里只可能是三种情况，某些模块需要，手动选择了，还有之前选好的，只处理某些模块需要的，其他不处理
                            if (allmoduleNameNode.isAlsoUseModule()) {
                                allmoduleNameNode.setModuleSelectedNull();

                                //这里只是重复了上一步的步骤，对allRoot对应模块做了上述处理后，对showRoot对应模块也做同样处理
                                if (showmoduleNameNode != null && showmoduleNameNode.isAlsoUseModule()) {//
                                    showmoduleNameNode.setModuleSelectedNull();
                                }
                                //到这里结束
                            }

                        } else if (SysService.MODULE_SERVICE.haveTheModuleInList(aModuleUseInCommonWithOtherList, allmoduleNameNode.getModule())) {//除了选中的模块以外，其他的模块也要这个模块
                            //这里只可能是三种情况，某些模块需要，手动选择了，还有之前选好的，只处理某些模块需要的，其他的，由于之前就没添加关联模块，不处理
                            if (allmoduleNameNode.isAlsoUseModule()) {
                                allmoduleNameNode.removeAssociatedModule(module);
                                allmoduleNameNode.setAlsoUseModule(null);

                                //这里只是重复了上一步的步骤，对allRoot对应模块做了上述处理后，对showRoot对应模块也做同样处理
                                if (showmoduleNameNode != null && showmoduleNameNode.isAlsoUseModule()) {
                                    showmoduleNameNode.removeAssociatedModule(module);
                                    showmoduleNameNode.setAlsoUseModule(null);
                                }
                                //到这里结束
                            }

                        } else if (SysService.MODULE_SERVICE.haveTheModuleInList(onlyANoNeedModuleList, allmoduleNameNode.getModule())) {//该模块只是选中的模块不能用
                            if (allmoduleNameNode.isAlsoDisableModule()) {
                                allmoduleNameNode.setModuleSelectedNull();

                                //这里只是重复了上一步的步骤，对allRoot对应模块做了上述处理后，对showRoot对应模块也做同样处理
                                if (showmoduleNameNode != null && showmoduleNameNode.isAlsoDisableModule()) {
                                    showmoduleNameNode.setModuleSelectedNull();
                                }
                                //到这里结束
                            }

                        } else if (SysService.MODULE_SERVICE.haveTheModuleInList(aModuleNoUseInCommonWithOtherList, allmoduleNameNode.getModule())) {//除了选中的模块以外，其他的模块也不能使用这个模块
                            if (allmoduleNameNode.isAlsoDisableModule()) {
                                allmoduleNameNode.removeAssociatedModule(module);
                                allmoduleNameNode.setAlsoDisableModule(null);

                                //这里只是重复了上一步的步骤，对allRoot对应模块做了上述处理后，对showRoot对应模块也做同样处理
                                if (showmoduleNameNode != null && showmoduleNameNode.isAlsoDisableModule()) {
                                    showmoduleNameNode.removeAssociatedModule(module);
                                    showmoduleNameNode.setAlsoDisableModule(null);
                                }
                                //到这里结束
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 生成代码那里，删除模块的检测（看看能不能删）也要改
     */

    /**
     * 获取对应的显示数组的模块
     */
    public ModuleNameNode getCorrespondingShowModuleNameNode(ModuleNameNode correspondingModuleNameNodeInAllList) {
        String moduleID = correspondingModuleNameNodeInAllList.getModule().getModuleId();
        TreeNode nodeTemp;
        ClassNameNode classNameNode;
        ModuleNameNode moduleNameNode, retuenModuleNameNode = null;
        for (int i = 0; i < showRoot.getChildCount(); i++) {
            nodeTemp = showRoot.getChildAt(i);
            if (nodeTemp instanceof ClassNameNode) {
                classNameNode = (ClassNameNode) nodeTemp;
//                if (className.equals(classNameNode.getClassification().getClassName())) {
                for (int a = 0; a < classNameNode.getChildCount(); a++) {
                    nodeTemp = classNameNode.getChildAt(a);
                    if (nodeTemp instanceof ModuleNameNode) {
                        moduleNameNode = (ModuleNameNode) nodeTemp;
                        if (moduleID.equals(moduleNameNode.getModule().getModuleId())) {
                            retuenModuleNameNode = moduleNameNode;
                            break;
                        }
                    }
                }
            }
            //           }
        }
        return retuenModuleNameNode;
    }

    /**
     * 获取对应在存放所有数据的数组
     * @param correspondingModuleNameNodeInShowList
     * @return
     */
    public ModuleNameNode getCorrespondingAllModuleNameNode(ModuleNameNode correspondingModuleNameNodeInShowList) {
        String moduleID = correspondingModuleNameNodeInShowList.getModule().getModuleId();
        TreeNode nodeTemp;
        ClassNameNode classNameNode;
        ModuleNameNode moduleNameNode, retuenModuleNameNode = null;
        for (int i = 0; i < allRoot.getChildCount(); i++) {
            nodeTemp = allRoot.getChildAt(i);
            if (nodeTemp instanceof ClassNameNode) {
                classNameNode = (ClassNameNode) nodeTemp;
                for (int a = 0; a < classNameNode.getChildCount(); a++) {
                    nodeTemp = classNameNode.getChildAt(a);
                    if (nodeTemp instanceof ModuleNameNode) {
                        moduleNameNode = (ModuleNameNode) nodeTemp;
                        if (moduleID.equals(moduleNameNode.getModule().getModuleId())) {
                            retuenModuleNameNode = moduleNameNode;
                            break;
                        }
                    }
                }
            }
            //           }
        }
        return retuenModuleNameNode;
    }


    /**
     * 获取已经选中的模块的对应节点
     *
     * @return
     */
    private ArrayList<ModuleNameNode> getPreUseModuleNodeList() {
        ArrayList<ModuleNameNode> list = new ArrayList<>();
        TreeNode nodeTemp;
        ClassNameNode classNameNode;
        ModuleNameNode moduleNameNode;
        for (int i = 0; i < allRoot.getChildCount(); i++) {
            nodeTemp = allRoot.getChildAt(i);
            if (nodeTemp instanceof ClassNameNode) {
                classNameNode = (ClassNameNode) nodeTemp;
                for (int a = 0; a < classNameNode.getChildCount(); a++) {
                    nodeTemp = classNameNode.getChildAt(a);
                    if (nodeTemp instanceof ModuleNameNode) {
                        moduleNameNode = (ModuleNameNode) nodeTemp;
                        if (moduleNameNode.isPreUseModule()) {
                            list.add(moduleNameNode);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取之前已经选中的模块的对应节点
     *
     * @return
     */
    private ArrayList<ModuleNameNode> getPreFirUseModuleNodeList() {
        ArrayList<ModuleNameNode> list = new ArrayList<>();
        TreeNode nodeTemp;
        ClassNameNode classNameNode;
        ModuleNameNode moduleNameNode;
        for (int i = 0; i < allRoot.getChildCount(); i++) {
            nodeTemp = allRoot.getChildAt(i);
            if (nodeTemp instanceof ClassNameNode) {
                classNameNode = (ClassNameNode) nodeTemp;
                for (int a = 0; a < classNameNode.getChildCount(); a++) {
                    nodeTemp = classNameNode.getChildAt(a);
                    if (nodeTemp instanceof ModuleNameNode) {
                        moduleNameNode = (ModuleNameNode) nodeTemp;
                        if (moduleNameNode.isPreFirUseModule()) {
                            list.add(moduleNameNode);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取不能选的模块的对应节点
     *
     * @return
     */
    private ArrayList<ModuleNameNode> getAlsoDisableModuleNodeList() {
        ArrayList<ModuleNameNode> list = new ArrayList<>();
        TreeNode nodeTemp;
        ClassNameNode classNameNode;
        ModuleNameNode moduleNameNode;
        for (int i = 0; i < allRoot.getChildCount(); i++) {
            nodeTemp = allRoot.getChildAt(i);
            if (nodeTemp instanceof ClassNameNode) {
                classNameNode = (ClassNameNode) nodeTemp;
                for (int a = 0; a < classNameNode.getChildCount(); a++) {
                    nodeTemp = classNameNode.getChildAt(a);
                    if (nodeTemp instanceof ModuleNameNode) {
                        moduleNameNode = (ModuleNameNode) nodeTemp;
                        if (moduleNameNode.isAlsoDisableModule()) {
                            list.add(moduleNameNode);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取由于某些模块需要，而被自动选中的模块
     *
     * @return
     */
    private ArrayList<ModuleNameNode> getAlsoUseModuleNodeList() {
        ArrayList<ModuleNameNode> list = new ArrayList<>();
        TreeNode nodeTemp;
        ClassNameNode classNameNode;
        ModuleNameNode moduleNameNode;
        for (int i = 0; i < allRoot.getChildCount(); i++) {
            nodeTemp = allRoot.getChildAt(i);
            if (nodeTemp instanceof ClassNameNode) {
                classNameNode = (ClassNameNode) nodeTemp;
                for (int a = 0; a < classNameNode.getChildCount(); a++) {
                    nodeTemp = classNameNode.getChildAt(a);
                    if (nodeTemp instanceof ModuleNameNode) {
                        moduleNameNode = (ModuleNameNode) nodeTemp;
                        if (moduleNameNode.isAlsoUseModule()) {
                            list.add(moduleNameNode);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取不能使用的模块（只要模块有编辑过，都不会不能使用，所以该方法可忽略）
     *
     * @return
     */
    private ArrayList<ModuleNameNode> getPreCannotUseNodeList() {
        ArrayList<ModuleNameNode> list = new ArrayList<>();
        TreeNode nodeTemp;
        ClassNameNode classNameNode;
        ModuleNameNode moduleNameNode;
        for (int i = 0; i < allRoot.getChildCount(); i++) {
            nodeTemp = allRoot.getChildAt(i);
            if (nodeTemp instanceof ClassNameNode) {
                classNameNode = (ClassNameNode) nodeTemp;
                for (int a = 0; a < classNameNode.getChildCount(); a++) {
                    nodeTemp = classNameNode.getChildAt(a);
                    if (nodeTemp instanceof ModuleNameNode) {
                        moduleNameNode = (ModuleNameNode) nodeTemp;
                        if (moduleNameNode.isPreCannotUse()) {
                            list.add(moduleNameNode);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取对应节点的模块
     *
     * @param nodeList
     * @return
     */
    private ArrayList<Module> getCurrentNodeModuleList(ArrayList<ModuleNameNode> nodeList) {
        ArrayList<Module> moduleArrayList = new ArrayList<>();
        if (nodeList != null) {
            for (ModuleNameNode moduleNameNode : nodeList) {
                moduleArrayList.add(moduleNameNode.getModule());
            }
        }
        return moduleArrayList;
    }


}

