package com.lazycoder.uidatasourceedit.moduleedit.toolbar.relatedmoduleinfomenu;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.AssociatedModule;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.uiutils.mycomponent.multistatecomponent.LazyCoderMultiStateComponentInterface;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class RelatedModuleInfoMenu extends JMenu implements ModuleEditComponentInterface {

    /**
     * 模块相关信息菜单对应业务功能
     * 【寄生模块】：例如模块A已经设置需要使用模块B，A则为B的寄生模块
     * 【冲突模块】：例如模块A已经设置不能使用模块B，A则为B的冲突模块
     * 【事先使用】：例如模块A已经设置需要使用模块B，B则为A的事先使用模块
     * 【不能使用】：例如模块A已经设置不能使用模块B，B则为A的不能使用模块
     * 【还要使用】：例如模块A已经设置需要使用模块B，而使用模块B又要使用模块C，C则为A的预先使用模块
     * 【还要禁用】：例如模块A已经设置需要使用模块B，而使用模块B不能使用模块C，C则为A的还要禁用模块
     * 【事先并优先使用】：例如用户选了A模块，后来选了B模块，但B模块也需要事先使用A模块，则A为事先并优先使用的模块
     * <p>
     * ——预显示原来的数据：
     * 再查看有哪些模块B是设置了需要使用这个模块A，把这些模块B都设置为【寄生模块】，并提示”该模块已设置需要使用模块“+currentmodule
     * 再查看有哪些模块C是设置了不能使用这个模块A，把这些模块C都设置为【冲突模块】，并提示”该模块已经设置不能使用模块“+currentmodule
     * 查看这个模块A设置可以选择使用什么模块D，把这些模块D都设置为 【事先使用】，再查看这个模块不能选择什么模块E，把这些模块E设置为【不能使用】
     * <p>
     * ——当有某个模块对应的菜单项被选中以后
     * 当选中一个模块A以后，找出该模块A有什么模块B是可以使用的，
     * 检查A在不在【寄生模块】以及【冲突模块】这两个的范围之内，如果在这范围之内，给出提示”该模块已设置需要使用模块“+currentmodule （正常情况都不会，可略过该步骤）
     * 检查B里面，有没有什么模块在D【寄生模块】、【冲突模块】、【不能使用】、【还要禁用】的范围之内，如果在这范围之内，给出提示“模块”+A+"需要使用的模块D和本模块冲突"，不能使用模块"+A，然后取消选中模块A
     * 找出还有什么模块C是不能使用的，
     * 检查C里面，有没有模块E在【事先使用】、【事先并优先使用】、【还要使用】范围内，如果在这范围之内，提示“模块”+A+"已设置不能使用E，和本模块冲突，不能使用模块"+A，然后取消选中模块A
     * 如果以上都正常，找出B里面所有的模块，当前没有选中的都设置为【还要使用】，有选中的设置为，把C里面所有模块设置为【还要禁用】
     * 菜单更新，显示当前选中的模块名称
     * <p>
     * ——当有某个模块对应的菜单项被设置为不选以后
     * 菜单更新，显示当前选中的模块名称
     * <p>
     * ——当有某个模块被选为不能使用以后
     * 当一个模块A被设置为不能使用时，找出该模块A有什么模块B是需要使用的，有什么模块C是不能使用的（因为菜单组件的选择次序是不选、选中、禁止，所以之前肯定已经选中该模块了，要把它之前引入的可以使用还有不能使用的模块都翻出来），
     * 找出当前所有选中的模块D（不包括A），
     * 获取模块D里面所有需要使用的模块E
     * 用E里面的模块一个个在B里面遍历，只要是E里面有的，就从B里面去掉，得到F，那剩下来的F，肯定只是A模块才需要用到这些模块
     * 获取模块D里面所有不能使用的模块G
     * 把G里面的模块一个个在C里面遍历，只要是G里面有的，就从C里面去掉，得到H，剩下来的H，肯定只是A模块中设置了不用这些模块
     * 把模块F还有H通通设置为不选
     * 菜单更新，显示当前选中的模块名称
     */

    public RelatedModuleInfoMenu() {
        // TODO Auto-generated constructor stub
        super();
        setForeground(new Color(160, 32, 240));
        showText();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                doClick();
            }
        });
        Dimension dimension = new Dimension(160, 30);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
        /**
         * 再查看有哪些模块B是设置了需要使用这个模块A，把这些模块B都设置为【寄生模块】，并提示”该模块已设置需要使用模块“+currentmodule
         * 再查看有哪些模块C是设置了不能使用这个模块A，把这些模块C都设置为【冲突模块】，并提示”该模块已经设置不能使用模块“+currentmodule
         * 查看这个模块A设置可以选择使用什么模块D，把这些模块D都设置为 【事先使用】，再查看这个模块不能选择什么模块E，把这些模块E设置为【不能使用】
         */
        if (Module.NEW_STATE.equals(module.getNeedModuleParam()) == false) {// 非新建状态
            addAllNeedModuleItems();
            ArrayList<RelatedModuleInfoMenuItem> allMenuItems = getAllMenuItems();

            List<Module> needThisModuleList = SysService.MODULE_SERVICE.getModulesListThatUsedTheModule(module.getModuleId());//查看有哪些模块needModuleList是设置了需要使用这个模块
            if (needThisModuleList != null) {
                for (RelatedModuleInfoMenuItem menuItem : allMenuItems) {//把这些模块needModuleList都设置为【寄生模块】，并提示
                    for (Module needModuleTemp : needThisModuleList) {
                        if (menuItem.isModule(needModuleTemp)) {
                            setParasiticModule(menuItem);
                            break;
                        }
                    }
                }
            }

            List<Module> canNotUsedThisModuleList = SysService.MODULE_SERVICE.getModulesListThatCanNotUsedTheModule(module.getModuleId());//查看有哪些模块needModuleList是设置了不能这个模块
            if (canNotUsedThisModuleList != null) {
                for (RelatedModuleInfoMenuItem menuItem : allMenuItems) {//把这些模块needModuleList都设置为【冲突模块】，并提示
                    if (menuItem.isModuleSelectedNull()) {
                        for (Module canNotUsedThisModuleTemp : canNotUsedThisModuleList) {
                            if (menuItem.isModule(canNotUsedThisModuleTemp)) {
                                setConflictModule(menuItem);
                                break;
                            }
                        }
                    }
                }
            }

            List<Module> needUseModuleListForThisModule = SysService.MODULE_SERVICE.getNeedModuleList(module);//查看模块module设置需要哪些模块
            if (needUseModuleListForThisModule != null) {
                for (RelatedModuleInfoMenuItem menuItem : allMenuItems) {//把这些模块D都设置为 【事先使用】，并提示
                    if (menuItem.isModuleSelectedNull()) {
                        for (Module needUseModuleListForThisModuleTemp : needUseModuleListForThisModule) {
                            if (menuItem.isModule(needUseModuleListForThisModuleTemp)) {
                                setPreUseModule(menuItem);
                                break;
                            }
                        }
                    }
                }
            }

            List<Module> noUseModuleListForThisModule = SysService.MODULE_SERVICE.getNoUseModuleList(module);//查看模块module设置不能使用哪些模块
            if (noUseModuleListForThisModule != null) {
                for (RelatedModuleInfoMenuItem menuItem : allMenuItems) {//把这些模块设置为【不能使用】
                    if (menuItem.isModuleSelectedNull()) {
                        for (Module noUseModuleListForThisModuleTemp : noUseModuleListForThisModule) {
                            if (menuItem.isModule(noUseModuleListForThisModuleTemp)) {
                                setCannotUseModule(menuItem);
                                break;
                            }
                        }
                    }
                }
            }
        }
        showText();
    }

    /**
     * 添加所有选项
     */
    private void addAllNeedModuleItems() {
        removeAll();
        RelatedModuleInfoMenuItem item;
        List<Module> list = SysService.MODULE_SERVICE.getModuleListExceptNonModuleAnd(DataSourceEditHolder.currentModule.getModuleId());
        List<TheClassification> classList = SysService.CLASSIFICATION_SERVICE.getAllClassificationList();
        if (list != null) {
            JMenu classMenu;
            for (TheClassification classification : classList) {
                classMenu = new JMenu(classification.getClassName());
                this.add(classMenu);
                for (Module temp : list) {
                    if (classification.getClassName().equals(temp.getClassName())) {
                        item = new RelatedModuleInfoMenuItem(temp);
                        setModuleSelectedNull(item);
                        classMenu.add(item);
                    }
                }
            }
        }
    }

    private void showText() {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> selectedList = new ArrayList<>(),
                noSelectedList = new ArrayList<>();

        ArrayList<RelatedModuleInfoMenuItem> allMenuItems = getAllMenuItems();
        ArrayList<RelatedModuleInfoMenuItem> selectedMenuItems = getSelectedModuleInfoMenuItemList(allMenuItems),
                noSelectedMenuItems = getNoSelectedModuleInfoMenuItemList(allMenuItems);
        for (RelatedModuleInfoMenuItem relatedModuleInfoMenuItem : selectedMenuItems) {
            selectedList.add(relatedModuleInfoMenuItem.getModule().getModuleName());
        }
        for (RelatedModuleInfoMenuItem relatedModuleInfoMenuItem : noSelectedMenuItems) {
            noSelectedList.add(relatedModuleInfoMenuItem.getModule().getModuleName());
        }

        if (selectedList.size() > 0) {
            stringBuilder.append("需要使用模块：");
            stringBuilder.append(StringUtils.join(selectedList, "、"));
        }
        if (noSelectedList.size() > 0) {
            stringBuilder.append("不能使用模块：");
            stringBuilder.append(StringUtils.join(noSelectedList, "、"));
        }
        setText(stringBuilder.toString());

        //用提示的方式再次显示，避免字符太多用setText看不到后面的内容
        if (selectedList.size() > 0 || noSelectedList.size() > 0) {
            HTMLText htmlText = new HTMLText();
            if (selectedList.size() > 0) {
                HtmlPar par1 = new HtmlPar();
                par1.addColorText("需要使用模块：", HtmlPar.YELLOW, false);
                par1.addText(StringUtils.join(selectedList, "、"), true);
                htmlText.addPar(par1);
            }
            if (noSelectedList.size() > 0) {
                HtmlPar par2 = new HtmlPar();
                par2.addColorText("不能使用模块：", HtmlPar.YELLOW, false);
                par2.addText(StringUtils.join(noSelectedList, "、"), true);
                htmlText.addPar(par2);
            }
            setToolTipText(htmlText.getHtmlContent());
        } else {
            setToolTipText(null);
        }
    }

    /**
     * 获取已选的模块的对应菜单
     *
     * @param allMenuItems 所有的菜单项
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getSelectedModuleInfoMenuItemList(ArrayList<RelatedModuleInfoMenuItem> allMenuItems) {
        ArrayList<RelatedModuleInfoMenuItem> selectedList = new ArrayList<>();
        for (RelatedModuleInfoMenuItem menuItem : allMenuItems) {
            if (menuItem.isPreUseModule() || menuItem.isPreFirUseModule()) {
                selectedList.add(menuItem);
            }
        }
        return selectedList;
    }

    /**
     * 获取当前设置的该模块需要使用的模块的模块信息
     *
     * @return
     */
    public ArrayList<Module> getSelectedNeedUseModuleList() {
        ArrayList<Module> list = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> moduleInfoMenuItems = getSelectedModuleInfoMenuItemList(getAllMenuItems());
        for (RelatedModuleInfoMenuItem relatedModuleInfoMenuItem : moduleInfoMenuItems) {
            list.add(relatedModuleInfoMenuItem.getModule());
        }
        return list;
    }


    /**
     * 获取已选的模块的对应菜单
     *
     * @param allMenuItems
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getNoSelectedModuleInfoMenuItemList(ArrayList<RelatedModuleInfoMenuItem> allMenuItems) {
        ArrayList<RelatedModuleInfoMenuItem> noSelectedList = new ArrayList<>();
        for (RelatedModuleInfoMenuItem menuItem : allMenuItems) {
            if (menuItem.isCannotUseModule() || menuItem.isPreCannotUseModule()) {
                noSelectedList.add(menuItem);
            }
        }
        return noSelectedList;
    }

    /**
     * 获取当前设置的该模块不能一起使用的模块的模块信息
     *
     * @return
     */
    public ArrayList<Module> getSelectedNoUseModuleList() {
        ArrayList<Module> list = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> moduleInfoMenuItems = getNoSelectedModuleInfoMenuItemList(getAllMenuItems());
        for (RelatedModuleInfoMenuItem relatedModuleInfoMenuItem : moduleInfoMenuItems) {
            list.add(relatedModuleInfoMenuItem.getModule());
        }
        return list;
    }

    /**
     * 当有某个模块对应的菜单项被选中
     *
     * @param theRelatedModuleInfoMenuItem
     */
    public boolean usingModule(RelatedModuleInfoMenuItem theRelatedModuleInfoMenuItem) {
        // 当选中一个模块A以后，找出该模块A有什么模块B是需要使用的，
        // 检查B里面，有没有什么模块在D【寄生模块】、【冲突模块】、【不能使用】、【还要禁用】的范围之内，如果在这范围之内，给出提示“模块”+A+"需要使用的模块D和本模块冲突"，不能使用模块"+A，然后取消选中模块A
        // 找出还有什么模块C是不能使用的，
        // 检查C里面，有没有模块E在【事先使用】、【事先并优先使用】、【还要使用】范围内，如果在这范围之内，提示“模块”+A+"已设置不能使用E，和本模块冲突，不能使用模块"+A，然后取消选中模块A
        // 如果以上都正常，把B里面所有模块都设置为【还要使用】，把C里面所有模块设置为【还要禁用】
        // 菜单更新，显示当前选中的模块名称
        Module module = theRelatedModuleInfoMenuItem.getModule();
        boolean state = true;
        ArrayList<RelatedModuleInfoMenuItem> currentParasiticModuleMenuItems = getCurrentParasiticModuleMenuItems(),
                currentConflictModuleMenuItems = getCurrentConflictModuleMenuItems(),
                currentCannotUseModuleItems = getCurrentCannotUseModuleItems(),
                currentAlsoDisableModuleItems = getCurrentAlsoDisableModuleItems();
        boolean flag = false;
        List<AssociatedModule> needAssociatedModuleListTemp = SysService.MODULE_SERVICE.getAllNeedUsedModuleList(module);
//        log.info(module.getModuleName() + "使用模块：" + JsonUtil.getJsonStr(needAssociatedModuleListTemp));
        ArrayList<Module> needListTemp = new ArrayList<>();//所有使用module的时候需要使用的模块
        for (AssociatedModule needTemp : needAssociatedModuleListTemp) {
            needListTemp.add(needTemp.getModule());
        }
        for (Module needTemp : needListTemp) {//有没有什么模块在D【寄生模块】、【冲突模块】、【不能使用】、【还要禁用】的范围之内，如果在这范围之内，给出提示
            flag = checkIfModuleIsIn(needTemp, currentParasiticModuleMenuItems,
                    "模块\"" + module.getModuleName() + "\"需要使用的模块\"" + needTemp.getModuleName() + "\"和本模块冲突\"，不能使用模块\"" + module.getModuleName() + "\"");
            if (flag == false) {
                flag = checkIfModuleIsIn(needTemp, currentConflictModuleMenuItems,
                        "模块\"" + module.getModuleName() + "\"需要使用的模块\"" + needTemp.getModuleName() + "\"和本模块冲突\"，不能使用模块\"" + module.getModuleName() + "\"");
            }
            if (flag == false) {
                flag = checkIfModuleIsIn(needTemp, currentCannotUseModuleItems,
                        "模块\"" + module.getModuleName() + "\"需要使用的模块\"" + needTemp.getModuleName() + "\"和本模块冲突\"，不能使用模块\"" + module.getModuleName() + "\"");
            }
            if (flag == false) {
                flag = checkIfModuleIsIn(needTemp, currentAlsoDisableModuleItems,
                        "模块\"" + module.getModuleName() + "\"需要使用的模块\"" + needTemp.getModuleName() + "\"和本模块冲突\"，不能使用模块\"" + module.getModuleName() + "\"");
            }
            if (flag == true) {
                break;
            }
        }
        if (flag == false) {
            //查看该模块不能使用的模块，有没有和【事先使用】、【事先并优先使用】、【还要使用】一样的，有的话，则补不能使用
            ArrayList<RelatedModuleInfoMenuItem> currentPreUseModuleItems = getCurrentPreUseModuleItems(),
                    currentPreFirUseModuleItems = getCurrentPreFirUseModuleItems(),
                    currentAlsoUseModuleMenuItems = getCurrentAlsoUseModuleMenuItems();
            ArrayList<AssociatedModule> noUseAssociatedModuleListTemp = SysService.MODULE_SERVICE.getAllNoUsedModuleList(module);
            ArrayList<Module> noUseListTemp = new ArrayList<>();//所有使用module时，不能使用的模块
            for (AssociatedModule noUseAssociatedModuleTemp : noUseAssociatedModuleListTemp) {
                noUseListTemp.add(noUseAssociatedModuleTemp.getModule());
            }
            for (Module noUseTemp : noUseListTemp) {//检查该模块不能使用的模块中，有没有和【事先使用】、【事先并优先使用】、【还要使用】一样的，有的话，提示
                flag = checkIfModuleIsIn(noUseTemp, currentPreUseModuleItems,
                        "模块\"" + module.getModuleName() + "\"已设置不能使用\"" + noUseTemp.getModuleName() + "\"，和本模块冲突，不能使用模块" + module.getModuleName());
                if (flag == false) {
                    flag = checkIfModuleIsIn(noUseTemp, currentPreFirUseModuleItems,
                            "模块\"" + module.getModuleName() + "\"已设置不能使用\"" + noUseTemp.getModuleName() + "\"，和本模块冲突，不能使用模块" + module.getModuleName());
                }
                if (flag == false) {
                    flag = checkIfModuleIsIn(noUseTemp, currentAlsoUseModuleMenuItems,
                            "模块\"" + module.getModuleName() + "\"已设置不能使用\"" + noUseTemp.getModuleName() + "\"，和本模块冲突，不能使用模块" + module.getModuleName());
                }
                if (flag == true) {
                    break;
                }
            }
            if (flag == false) {//确认当前选择的模块，和现在添加的其他模块没有冲突后，可以选择，进行以下操作
                ArrayList<RelatedModuleInfoMenuItem> allMenuItems = getAllMenuItems();

                //根据当前这个模块所需要的模块，找出对应模块所有的菜单项，原来是【不选】的改成【还要使用】，原来是【事先使用】的改成【事先且首先使用】
                for (AssociatedModule associatedModuleTemp : needAssociatedModuleListTemp) {
                    for (RelatedModuleInfoMenuItem menuItem : allMenuItems) {
//                            log.info("菜单对应模块：" + menuItem.getModule().getModuleName() +
//                                    "\t当前比对模块：" + associatedModuleTemp.getModule().getModuleName() +
//                                    "\t菜单当前状态：" + menuItem.getCurrentState());
                        if (menuItem.isModule(associatedModuleTemp.getModule())) {
                            if (menuItem.isPreUseModule()) {//如果之前选中了这个模块，就设为【事先并优先使用该模块】
                                setPreFirUseModule(menuItem, associatedModuleTemp);
//                                    log.info("已把" + menuItem.getModule().getModuleName()
//                                            + "从【事先使用】改为【事先提前使用】，关联模块：" + JsonUtil.getJsonStr(menuItem.getAssociatedModuleListStr()));
                            } else if (menuItem.isModuleSelectedNull()) {//通通设置为还要使用
                                setAlsoUseModule(menuItem, associatedModuleTemp);
//                                    log.info("已把" + menuItem.getModule().getModuleName()
//                                            + "从【不选】改为【还要使用】，关联模块：" + JsonUtil.getJsonStr(menuItem.getAssociatedModuleListStr()));

                            } else if (menuItem.isAlsoUseModule()) {//如果原来就是还要使用，把当前这个模块的关联模块也添加上去
                                setAlsoUseModule(menuItem, associatedModuleTemp);
//                                    log.info("已把" + menuItem.getModule().getModuleName()
//                                            + "从新设置为【还要使用】，关联模块：" + JsonUtil.getJsonStr(menuItem.getAssociatedModuleListStr()));
                            }
                            break;
                        }
                    }
                }
                for (AssociatedModule associatedModuleTemp : noUseAssociatedModuleListTemp) {
                    for (RelatedModuleInfoMenuItem menuItem : allMenuItems) {
//                            log.info("菜单对应模块：" + menuItem.getModule().getModuleName() +
//                                    "\t当前比对模块：" + associatedModuleTemp.getModule().getModuleName() +
//                                    "\t菜单当前状态：" + menuItem.getCurrentState());
                        if (menuItem.isModule(associatedModuleTemp.getModule())) {
                            if (menuItem.isCannotUseModule()) {//如果之前选中禁用了这个模块，就设为【事先并优先使用该模块】
                                setPreCannotUseModule(menuItem, associatedModuleTemp);
//                                    log.info("已把" + menuItem.getModule().getModuleName()
//                                            + "从【禁用】改为【禁用且其他模块不能使用】，关联模块：" + JsonUtil.getJsonStr(menuItem.getAssociatedModuleListStr()));

                            } else if (menuItem.isModuleSelectedNull()) {//通通设置为还要使用
                                setAlsoDisableModule(menuItem, associatedModuleTemp);
//                                    log.info("已把" + menuItem.getModule().getModuleName()
//                                            + "从【还要禁用】改为【还要禁用】，关联模块：" + JsonUtil.getJsonStr(menuItem.getAssociatedModuleListStr()));

                            } else if (menuItem.isAlsoDisableModule()) {//如果设置【还要禁用】了这个模块，就重新设值
                                setAlsoDisableModule(menuItem, associatedModuleTemp);
//                                    log.info("已把" + menuItem.getModule().getModuleName()
//                                            + "的【还要禁用】重新设值，关联模块：" + JsonUtil.getJsonStr(menuItem.getAssociatedModuleListStr()));
                            }
                            break;
                        }
                    }
                }

                //把需要的模块的代码面板都加载出来
                ArrayList<Module> addList = new ArrayList<>();
                addList.add(module);//只加载自己选的，其他没选的，使用的时候才需要添加的模块不加载代码
                ModuleEditPaneHolder.needUseCodeFileEditPane.addNeedUseCodeFormatPaneByModuleList(addList);

                HTMLText htmlText = new HTMLText();
                HtmlPar par2 = new HtmlPar();
                par2.addColorText("所属分类：", HtmlPar.YELLOW, true);
                par2.add(theRelatedModuleInfoMenuItem.getModule().getClassName());
                htmlText.addPar(par2);
                setModuleDisableTip(theRelatedModuleInfoMenuItem, htmlText);
                theRelatedModuleInfoMenuItem.setPreUseModule(htmlText.getHtmlContent());

                showText();
            }
        }
//        if (flag == false) {
//            showText();
//        }
        state = flag == false ? true : false;
        return state;
    }

    /**
     * 当某个模块被设置不能使用
     *
     * @param theRelatedModuleInfoMenuItem
     */
    public void doNotUseModule(RelatedModuleInfoMenuItem theRelatedModuleInfoMenuItem) {
        Module module = theRelatedModuleInfoMenuItem.getModule();
//        log.info("不使用" + module.getModuleName());
        /**
         * --由于模块对应的菜单组件，手动点击后是按照：选中-禁止-不选的顺序依次进行，所以当设置为不能使用时，需要把之前选中的业务操作逆向处理
         * 找出只是这个模块才需要的模块A，把A对应的菜单项，【预选】的设置为【不选】，【优先预选】的改为【选中】
         * 找出只是这个模块才要禁止的模块B，把B对应的菜单项，【预定禁止】的设置为【不选】，【优先预定禁止】的改为【禁止】
         */
        List<AssociatedModule> needAssociatedModuleListTemp = SysService.MODULE_SERVICE.getAllNeedUsedModuleList(module),
                noUseAssociatedModuleListTemp = SysService.MODULE_SERVICE.getAllNoUsedModuleList(module);
        List<Module> needListTemp = new ArrayList<>(), noUseListTemp = new ArrayList<>();
        for (AssociatedModule associatedModule : needAssociatedModuleListTemp) {
            needListTemp.add(associatedModule.getModule());//获取所有使用模块module的时候，需要使用的模块
        }
//        log.info("使用模块" + module.getModuleName() + "需要" + JsonUtil.getJsonStr(needAssociatedModuleListTemp));
        for (AssociatedModule associatedModule : noUseAssociatedModuleListTemp) {
            noUseListTemp.add(associatedModule.getModule());//获取所有使用模块module的时候，不能使用的模块
        }
//        log.info("使用模块" + module.getModuleName() + "不能使用" + JsonUtil.getJsonStr(noUseAssociatedModuleListTemp));

        //--找出只是这个模块才需要的模块A，把A对应的菜单项，【预选】的设置为【不选】，【优先预选】的改为【选中】
        // 找出当前所有【事先使用】的模块D（不包括A），（预选中的可能有些也是用户选的，但可能是用户后面选的模块里刚好也需要它，导致它变成预选的，如果对应的用户后面选的模块是module，取消以后这个应该也要改为选中，如果不包含module，则无需操作）
        // 获取模块D里面所有需要使用的模块E
        // 用E里面的模块一个个在B里面遍历，只要是E里面有的，就从B里面去掉，得到F，那剩下来的F，肯定只是A模块才需要用到这些模块
        ArrayList<Module> selectedModuleList = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> selectedMenuItemList = getCurrentPreUseModuleItems();//找出当前所有【事先使用】的模块D（不包括A）
        for (RelatedModuleInfoMenuItem menuItem : selectedMenuItemList) {//获取当前选中的模块（不包括模块module）
            if (menuItem.isModule(module) == false) {
                selectedModuleList.add(menuItem.getModule());
            }
        }
        selectedMenuItemList = getCurrentPreFirUseModuleItems();//找出当前所有【事先使用且优先使用】的模块D（不包括A）
        for (RelatedModuleInfoMenuItem menuItem : selectedMenuItemList) {//获取当前选中的模块（不包括模块module）
            if (menuItem.isModule(module) == false) {
                selectedModuleList.add(menuItem.getModule());
            }
        }
//        log.info("去掉以后，剩下的模块还有" + JsonUtil.getJsonStr(selectedModuleList));

        ArrayList<Module> onlyANeedModuleList = new ArrayList<>();
        ArrayList<Module> A_ModuleUseInCommonWithOtherList = new ArrayList<>();//使用A模块和其他模块都需要使用的模块

        LazyCoderMultiStateComponentInterface.setNeedModuleParams(needListTemp, selectedModuleList, onlyANeedModuleList, A_ModuleUseInCommonWithOtherList);

        ArrayList<Module> onlyANoNeedModuleList = new ArrayList<>();
        ArrayList<Module> A_ModuleNoUseInCommonWithOtherList = new ArrayList<>();//使用A模块和其他模块都不能使用的模块
        LazyCoderMultiStateComponentInterface.setNoNeedModuleParams(noUseListTemp, selectedModuleList, onlyANoNeedModuleList, A_ModuleNoUseInCommonWithOtherList);

        ArrayList<RelatedModuleInfoMenuItem> allMenuItems = getAllMenuItems();

        for (RelatedModuleInfoMenuItem relatedModuleInfoMenuItem : allMenuItems) {
            if (SysService.MODULE_SERVICE.haveTheModuleInList(onlyANeedModuleList, relatedModuleInfoMenuItem.getModule())) {//如果只是这个模块才使用到这个模块
                if (relatedModuleInfoMenuItem.isAlsoUseModule()) {//可能这个模块直接就是这个状态
                    //原本是【还要使用】状态的，直接设为【不选】
                    setModuleSelectedNull(relatedModuleInfoMenuItem);
//                        log.info("去掉" + relatedModuleInfoMenuItem.getModule().getModuleName());

                } else if (relatedModuleInfoMenuItem.isPreFirUseModule()) {//也有可能用户之前也选了这个模块，后来又选了module，因为module需要这个模块，系统把它设置为这个状态了
                    //原本是【事先使用并优先使用状态】的，直接设为【事先使用】
                    setPreUseModule(relatedModuleInfoMenuItem);
//                        log.info("把" + relatedModuleInfoMenuItem.getModule().getModuleName() + "设置为事先使用");
                }

            } else if (SysService.MODULE_SERVICE.haveTheModuleInList(onlyANoNeedModuleList, relatedModuleInfoMenuItem.getModule())) {//如果这模块是之前设置使用module不能使用，另外选中的其他模块没有这样设置的
                if (relatedModuleInfoMenuItem.isAlsoDisableModule()) {
                    //原本是【还要禁用状态】的，直接设为【不选】
                    setModuleSelectedNull(relatedModuleInfoMenuItem);
//                        log.info("把" + relatedModuleInfoMenuItem.getModule().getModuleName() + "从【还要禁用】改为【不选】");

                } else if (relatedModuleInfoMenuItem.isPreCannotUseModule()) {
                    //原本是【禁用且其他模块也不能使用】状态的，直接设为【禁止】
                    setCannotUseModule(relatedModuleInfoMenuItem);
//                        log.info("把" + relatedModuleInfoMenuItem.getModule().getModuleName() + "从【禁用且其他模块不能使用】改为【不选】");
                }

            } else if (SysService.MODULE_SERVICE.haveTheModuleInList(A_ModuleUseInCommonWithOtherList, relatedModuleInfoMenuItem.getModule())) {//如果relatedModuleInfoMenuItem对应的模块，之前module已经设置需要使用，且选中的其他模块也有设置需要使用它的
                if (relatedModuleInfoMenuItem.isAlsoUseModule()) {
                    removeAssociatedModule(relatedModuleInfoMenuItem, module);
//                        log.info(relatedModuleInfoMenuItem.getModule().getModuleName() + "（还要使用）去掉关联" + module.getModuleName());

                } else if (relatedModuleInfoMenuItem.isPreFirUseModule()) {
                    removeAssociatedModule(relatedModuleInfoMenuItem, module);
//                        log.info(relatedModuleInfoMenuItem.getModule().getModuleName() + "（提前事先使用）去掉关联" + module.getModuleName());
                }

            } else if (SysService.MODULE_SERVICE.haveTheModuleInList(A_ModuleNoUseInCommonWithOtherList, relatedModuleInfoMenuItem.getModule())) {//如果relatedModuleInfoMenuItem对应的模块，之前module已经设置不能使用，且选中的其他模块也有设置不能使用它的
                if (relatedModuleInfoMenuItem.isAlsoDisableModule()) {
                    removeAssociatedModule(relatedModuleInfoMenuItem, module);
//                        log.info(relatedModuleInfoMenuItem.getModule().getModuleName() + "（还要禁用）去掉关联" + module.getModuleName());

                } else if (relatedModuleInfoMenuItem.isPreCannotUseModule()) {
                    removeAssociatedModule(relatedModuleInfoMenuItem, module);
//                        log.info(relatedModuleInfoMenuItem.getModule().getModuleName() + "（禁用且其他模块不能用）去掉关联" + module.getModuleName());
                }
            }
        }

        //把module模块还有只是module模块才需要使用的模块，对应的代码面板都去掉
        ArrayList<Module> removeModuleList = new ArrayList<>();
        removeModuleList.add(module);
        ModuleEditPaneHolder.needUseCodeFileEditPane.removeModuleFilePane(removeModuleList);

        HTMLText htmlText = new HTMLText();
        HtmlPar par2 = new HtmlPar();
        par2.addColorText("所属分类：", HtmlPar.YELLOW, true);
        par2.add(theRelatedModuleInfoMenuItem.getModule().
                getClassName());
        htmlText.addPar(par2);

        setModuleDisableTip(theRelatedModuleInfoMenuItem, htmlText);
        theRelatedModuleInfoMenuItem.setCannotUseModule(htmlText.getHtmlContent());

        showText();
    }

    /**
     * 查看某个状态的菜单项中有没有模块module
     *
     * @param module       需要查看的模块
     * @param menuItemList 需要查看的菜单列表
     * @param tip          如果有，对应的提示
     * @return
     */
    private static boolean checkIfModuleIsIn(Module module, ArrayList<RelatedModuleInfoMenuItem> menuItemList, String tip) {
        boolean flag = false;
        for (RelatedModuleInfoMenuItem menuItem : menuItemList) {
            if (menuItem.isModule(module)) {
                LazyCoderOptionPane.showMessageDialog(DataSourceEditHolder.dataSourceEditFrame, tip);
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        addAllNeedModuleItems();
        showText();
    }

    /**
     * 刷新菜单
     */
    public void updateMenuItems() {
        ArrayList<Module> parasiticModuleList = getCurrentModuleList(getCurrentParasiticModuleMenuItems()),
                conflictModuleList = getCurrentModuleList(getCurrentConflictModuleMenuItems()),
                alsoUseModuleList = getCurrentModuleList(getCurrentAlsoUseModuleMenuItems()),
                alsoDisableModuleList = getCurrentModuleList(getCurrentAlsoDisableModuleItems()),
                preUseModuleList = getCurrentModuleList(getCurrentPreUseModuleItems()),
                cannotUseModuleList = getCurrentModuleList(getCurrentCannotUseModuleItems()),
                preFirUseModuleList = getCurrentModuleList(getCurrentPreFirUseModuleItems()),
                preCannotUseModuleList = getCurrentModuleList(getCurrentPreCannotUseModuleItems());
        addAllNeedModuleItems();
        ArrayList<RelatedModuleInfoMenuItem> parasiticModuleMenuItemList = getCurrentModuleMenuItemList(parasiticModuleList),
                conflictModuleMenuItemList = getCurrentModuleMenuItemList(conflictModuleList),
                alsoUseModuleMenuItemList = getCurrentModuleMenuItemList(alsoUseModuleList),
                alsoDisableModuleMenuItemList = getCurrentModuleMenuItemList(alsoDisableModuleList),
                preUseModuleMenuItemList = getCurrentModuleMenuItemList(preUseModuleList),
                cannotUseModuleMenuItemList = getCurrentModuleMenuItemList(cannotUseModuleList),
                preFirUseModuleMenuItemList = getCurrentModuleMenuItemList(preFirUseModuleList),
                preCannotUseModuleMenuItemList = getCurrentModuleMenuItemList(preCannotUseModuleList);

        for (RelatedModuleInfoMenuItem menuItem : parasiticModuleMenuItemList) {
            setParasiticModule(menuItem);
        }
        for (RelatedModuleInfoMenuItem menuItem : conflictModuleMenuItemList) {
            setConflictModule(menuItem);
        }
        for (RelatedModuleInfoMenuItem menuItem : alsoUseModuleMenuItemList) {
            setAlsoUseModule(menuItem, null);
        }
        for (RelatedModuleInfoMenuItem menuItem : alsoDisableModuleMenuItemList) {
            setAlsoDisableModule(menuItem, null);
        }
        for (RelatedModuleInfoMenuItem menuItem : preUseModuleMenuItemList) {
            setPreUseModule(menuItem);
        }
        for (RelatedModuleInfoMenuItem menuItem : cannotUseModuleMenuItemList) {
            setCannotUseModule(menuItem);
        }
        for (RelatedModuleInfoMenuItem menuItem : preFirUseModuleMenuItemList) {
            setPreFirUseModule(menuItem, null);
        }
        for (RelatedModuleInfoMenuItem menuItem : preCannotUseModuleMenuItemList) {
            setPreCannotUseModule(menuItem, null);
        }
    }

    /**
     * 把菜单对应模块设为需要事先使用的模块
     *
     * @param menuItem
     */
    private void setParasiticModule(RelatedModuleInfoMenuItem menuItem) {
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.YELLOW, true);
        par1.add("该模块已设置需要使用模块\"" + DataSourceEditHolder.currentModule.getModuleName() + "\"");
        htmlText.addPar(par1);
        HtmlPar par2 = new HtmlPar();
        par2.addColorText("所属分类：", HtmlPar.YELLOW, true);
        par2.add(menuItem.getModule().getClassName());
        htmlText.addPar(par2);
        setModuleDisableTip(menuItem, htmlText);
        menuItem.setParasiticModule(htmlText.getHtmlContent());
    }

    /**
     * 把菜单对应模块设为需要和本模块冲突的模块
     *
     * @param menuItem
     */
    private void setConflictModule(RelatedModuleInfoMenuItem menuItem) {
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.YELLOW, true);
        par1.add("该模块已经设置不能使用模块\"" + DataSourceEditHolder.currentModule.getModuleName() + "\"");
        htmlText.addPar(par1);
        HtmlPar par2 = new HtmlPar();
        par2.addColorText("所属分类：", HtmlPar.YELLOW, true);
        par2.add(menuItem.getModule().getClassName());
        htmlText.addPar(par2);
        setModuleDisableTip(menuItem, htmlText);
        menuItem.setConflictModule(htmlText.getHtmlContent());
    }

    /**
     * 从某个模块对应的菜单中去掉某个关联模块
     *
     * @param module
     */
    private void removeAssociatedModule(RelatedModuleInfoMenuItem menuItem, Module module) {
        menuItem.removeAssociatedModule(module);
        if (menuItem.isAlsoUseModule()) {
            setAlsoUseModule(menuItem, null);
        } else if (menuItem.isAlsoDisableModule()) {
            setAlsoDisableModule(menuItem, null);
        } else if (menuItem.isPreFirUseModule()) {
            setPreFirUseModule(menuItem, null);
        } else if (menuItem.isPreCannotUseModule()) {
            setPreCannotUseModule(menuItem, null);
        }
    }

    /**
     * 设置为其他模块需要使用的模块
     *
     * @param menuItem
     * @param associatedModule
     */
    private void setAlsoUseModule(RelatedModuleInfoMenuItem menuItem, AssociatedModule associatedModule) {
        if (associatedModule != null) {
            menuItem.addAssociatedModule(associatedModule);
        }
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.YELLOW, true);
        par1.add("当前选中的模块中，模块" + menuItem.getAssociatedModuleListStr() + "需要事先使用该模块");
        par1.nextLine();
        par1.blankSpace(4);
        par1.addColorText("如果模块", HtmlPar.GRAY, false);
        par1.add(DataSourceEditHolder.currentModule.getModuleName());
        par1.addColorText("要设置使用该模块，请先取消使用以上模块后，选中本模块以后，再选以上模块", HtmlPar.GRAY, false);
        par1.nextLine();
        htmlText.addPar(par1);
        HtmlPar par2 = new HtmlPar();
        par2.addColorText("所属分类：", HtmlPar.YELLOW, true);
        par2.add(menuItem.getModule().getClassName());
        htmlText.addPar(par2);
        setModuleDisableTip(menuItem, htmlText);
        menuItem.setAlsoUseModule(htmlText.getHtmlContent());
    }

    /**
     * 设置为其他模块需要禁用的模块
     *
     * @param menuItem
     * @param associatedModule
     */
    private void setAlsoDisableModule(RelatedModuleInfoMenuItem menuItem, AssociatedModule associatedModule) {
        if (associatedModule != null) {
            menuItem.addAssociatedModule(associatedModule);
        }
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.YELLOW, true);
        par1.add("当前需要用到的模块中，模块" + menuItem.getAssociatedModuleListStr() + "需要事先使用该模块");
        par1.nextLine();
        par1.blankSpace(4);
        par1.addColorText("如果模块", HtmlPar.GRAY, false);
        par1.add(DataSourceEditHolder.currentModule.getModuleName());
        par1.addColorText("要设置禁用该模块，请先取消使用以上模块后，选择禁用本模块以后，再选以上模块", HtmlPar.GRAY, false);
        par1.nextLine();
        htmlText.addPar(par1);

        HtmlPar par2 = new HtmlPar();
        par2.addColorText("所属分类：", HtmlPar.YELLOW, true);
        par2.add(menuItem.getModule().getClassName());
        htmlText.addPar(par2);
        setModuleDisableTip(menuItem, htmlText);
        menuItem.setAlsoDisableModule(htmlText.getHtmlContent());
    }

    /**
     * 把菜单对应模块设为本模块需要使用的模块
     *
     * @param menuItem
     */
    private void setPreUseModule(RelatedModuleInfoMenuItem menuItem) {
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.YELLOW, true);
        par1.add("模块\"" + DataSourceEditHolder.currentModule.getModuleName() + "\"已设置需要该模块");
        htmlText.addPar(par1);
        HtmlPar par2 = new HtmlPar();
        par2.addColorText("所属分类：", HtmlPar.YELLOW, true);
        par2.add(menuItem.getModule().getClassName());
        htmlText.addPar(par2);
        setModuleDisableTip(menuItem, htmlText);
        menuItem.setPreUseModule(htmlText.getHtmlContent());
    }

    /**
     * 把菜单对应模块设为本模块不能使用的模块
     *
     * @param menuItem
     */
    private void setCannotUseModule(RelatedModuleInfoMenuItem menuItem) {
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.YELLOW, true);
        par1.add("模块\"" + DataSourceEditHolder.currentModule.getModuleName() + "\"已设置不能需要该模块");
        htmlText.addPar(par1);
        HtmlPar par2 = new HtmlPar();
        par2.addColorText("所属分类：", HtmlPar.YELLOW, true);
        par2.add(menuItem.getModule().getClassName());
        htmlText.addPar(par2);
        setModuleDisableTip(menuItem, htmlText);
        menuItem.setCannotUseModule(htmlText.getHtmlContent());
    }

    /**
     * 设置为其他模块需要使用，且优先使用的模块
     *
     * @param menuItem
     * @param associatedModule
     */
    private void setPreFirUseModule(RelatedModuleInfoMenuItem menuItem, AssociatedModule associatedModule) {
        if (associatedModule != null) {
            menuItem.addAssociatedModule(associatedModule);
        }
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.YELLOW, true);
        par1.add("已设置需要使用该模块，另外，选中的" + menuItem.getAssociatedModuleListStr() + "模块需要事先使用该模块");
        htmlText.addPar(par1);
        HtmlPar par2 = new HtmlPar();
        par2.addColorText("所属分类：", HtmlPar.YELLOW, true);
        par2.add(menuItem.getModule().getClassName());
        htmlText.addPar(par2);
        setModuleDisableTip(menuItem, htmlText);
        menuItem.setPreFirUseModule(htmlText.getHtmlContent());
    }

    /**
     * 设置为不能使用的模块，且其他需要的模块中不能使用该模块
     *
     * @param menuItem
     * @param associatedModule
     */
    private void setPreCannotUseModule(RelatedModuleInfoMenuItem menuItem, AssociatedModule associatedModule) {
        if (associatedModule != null) {
            menuItem.addAssociatedModule(associatedModule);
        }
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.YELLOW, true);
        par1.add("已设置不能使用该模块，另外，选中的" + menuItem.getAssociatedModuleListStr() + "模块不能使用该模块");
        htmlText.addPar(par1);
        HtmlPar par2 = new HtmlPar();
        par2.addColorText("所属分类：", HtmlPar.YELLOW, true);
        par2.add(menuItem.getModule().getClassName());
        htmlText.addPar(par2);
        setModuleDisableTip(menuItem, htmlText);
        menuItem.setPreCannotUseModule(htmlText.getHtmlContent());
    }

    /**
     * 设置需要的模块参数
     *
     * @param module
     */
    public void setNeedModuleParam(Module module) {
        List<Module> moduleList = new ArrayList<>();

        ArrayList<RelatedModuleInfoMenuItem> menuItems = getCurrentPreUseModuleItems();
        ArrayList<Module> tempList = getCurrentModuleList(menuItems);
        moduleList.addAll(tempList);

        menuItems = getCurrentPreFirUseModuleItems();
        tempList = getCurrentModuleList(menuItems);
        moduleList.addAll(tempList);

        ArrayList<String> list = SysService.MODULE_SERVICE.getModuleIdList(moduleList);
        module.setNeedModuleParam(JsonUtil.getJsonStr(list));
    }

    /**
     * 记录不能调用的模块
     *
     * @param module
     */
    public void setNoUseModuleParam(Module module) {
        List<Module> moduleList = new ArrayList<>();

        ArrayList<RelatedModuleInfoMenuItem> menuItems = getCurrentCannotUseModuleItems();
        ArrayList<Module> tempList = getCurrentModuleList(menuItems);
        moduleList.addAll(tempList);

        menuItems = getCurrentPreCannotUseModuleItems();
        tempList = getCurrentModuleList(menuItems);
        moduleList.addAll(tempList);

        ArrayList<String> list = SysService.MODULE_SERVICE.getModuleIdList(moduleList);
        module.setNoUseModuleParam(JsonUtil.getJsonStr(list));
    }

    public void setModuleSelectedNull(RelatedModuleInfoMenuItem menuItem) {
        HTMLText htmlText = new HTMLText();
        HtmlPar par = new HtmlPar();
        par.addColorText("所属分类：", HtmlPar.YELLOW, true);
        par.add(menuItem.getModule().getClassName());
        htmlText.addPar(par);
        setModuleDisableTip(menuItem, htmlText);
        menuItem.setModuleSelectedNull(htmlText.getHtmlContent());
        showText();
    }

    /**
     * 获取当前所有的寄生模块的菜单项
     *
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getCurrentParasiticModuleMenuItems() {
        ArrayList<RelatedModuleInfoMenuItem> list = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> menuItems = getAllMenuItems();
        for (RelatedModuleInfoMenuItem menuItem : menuItems) {
            if (menuItem.isParasiticModule()) {
                list.add(menuItem);
            }
        }
        return list;
    }

    /**
     * 获取当前所有的冲突模块的菜单项
     *
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getCurrentConflictModuleMenuItems() {
        ArrayList<RelatedModuleInfoMenuItem> list = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> menuItems = getAllMenuItems();
        for (RelatedModuleInfoMenuItem menuItem : menuItems) {
            if (menuItem.isConflictModule()) {
                list.add(menuItem);
            }
        }
        return list;
    }

    /**
     * 获取当初所有还要使用的模块的菜单项
     *
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getCurrentAlsoUseModuleMenuItems() {
        ArrayList<RelatedModuleInfoMenuItem> list = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> menuItems = getAllMenuItems();
        for (RelatedModuleInfoMenuItem menuItem : menuItems) {
            if (menuItem.isAlsoUseModule()) {
                list.add(menuItem);
            }
        }
        return list;
    }

    /**
     * 获取当前所有还要禁用的模块的菜单项
     *
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getCurrentAlsoDisableModuleItems() {
        ArrayList<RelatedModuleInfoMenuItem> list = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> menuItems = getAllMenuItems();
        for (RelatedModuleInfoMenuItem menuItem : menuItems) {
            if (menuItem.isAlsoDisableModule()) {
                list.add(menuItem);
            }
        }
        return list;
    }

    /**
     * 获取当前所有事先使用的模块的菜单项
     *
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getCurrentPreUseModuleItems() {
        ArrayList<RelatedModuleInfoMenuItem> list = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> menuItems = getAllMenuItems();
        for (RelatedModuleInfoMenuItem menuItem : menuItems) {
            if (menuItem.isPreUseModule()) {
                list.add(menuItem);
            }
        }
        return list;
    }

    /**
     * 获取当前所有不能使用的模块的菜单项
     *
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getCurrentCannotUseModuleItems() {
        ArrayList<RelatedModuleInfoMenuItem> list = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> menuItems = getAllMenuItems();
        for (RelatedModuleInfoMenuItem menuItem : menuItems) {
            if (menuItem.isCannotUseModule()) {
                list.add(menuItem);
            }
        }
        return list;
    }

    /**
     * 获取当前所有事先并且优先使用的模块对应的菜单项
     *
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getCurrentPreFirUseModuleItems() {
        ArrayList<RelatedModuleInfoMenuItem> list = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> menuItems = getAllMenuItems();
        for (RelatedModuleInfoMenuItem menuItem : menuItems) {
            if (menuItem.isPreFirUseModule()) {
                list.add(menuItem);
            }
        }
        return list;
    }

    /**
     * 获取当前所有用户选择设置不能使用，且其他模块也设置了不能使用的模块，对应的菜单项
     *
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getCurrentPreCannotUseModuleItems() {
        ArrayList<RelatedModuleInfoMenuItem> list = new ArrayList<>();
        ArrayList<RelatedModuleInfoMenuItem> menuItems = getAllMenuItems();
        for (RelatedModuleInfoMenuItem menuItem : menuItems) {
            if (menuItem.isPreCannotUseModule()) {
                list.add(menuItem);
            }
        }
        return list;
    }

    /**
     * 获取对应模块的菜单项
     *
     * @param moduleList
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getCurrentModuleMenuItemList(ArrayList<Module> moduleList) {
        ArrayList<RelatedModuleInfoMenuItem> list = new ArrayList<>(), menuItems = getAllMenuItems();
        for (Module moduleTemp : moduleList) {
            for (RelatedModuleInfoMenuItem menuItem : menuItems) {
                if (menuItem.isModule(moduleTemp)) {
                    list.add(menuItem);
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 获取对应菜单的模块
     *
     * @param menuItemList
     * @return
     */
    private ArrayList<Module> getCurrentModuleList(ArrayList<RelatedModuleInfoMenuItem> menuItemList) {
        ArrayList<Module> moduleList = new ArrayList<>();
        if (menuItemList != null) {
            for (RelatedModuleInfoMenuItem relatedModuleInfoMenuItem : menuItemList) {
                if (relatedModuleInfoMenuItem != null && relatedModuleInfoMenuItem.getModule() != null) {
                    moduleList.add(relatedModuleInfoMenuItem.getModule());
                }
            }
        }
        return moduleList;
    }

    /**
     * 如果模块无法使用，给出对应提示（并改变字体颜色以区分）
     *
     * @param menuItem 要给出提示的菜单项
     * @param tip      对应的提示
     */
    public void setModuleDisableTip(RelatedModuleInfoMenuItem menuItem, HTMLText tip) {
        if (menuItem.getModule().getEnabledState() == Module.FALSE_) {
            HtmlPar par3 = new HtmlPar();
            par3.addColorText("该模块目前没有录入任何内容：导致无法使用，请尽快进行录入补充", HtmlPar.Munsell, true);
            par3.nextLine();
            par3.add(menuItem.getModule().getModuleName());
            tip.addPar(par3);
            menuItem.setForeground(Color.LIGHT_GRAY);
        }
    }

    /**
     * 获取所有的菜单项
     *
     * @return
     */
    private ArrayList<RelatedModuleInfoMenuItem> getAllMenuItems() {
        ArrayList<RelatedModuleInfoMenuItem> list = new ArrayList<>();
        JMenu jmenuTemp;
        RelatedModuleInfoMenuItem menuItemTemp;
        Component[] menuList = getMenuComponents(), menuItemList;
        for (Component menuComponent : menuList) {
            if (menuComponent instanceof JMenu) {
                jmenuTemp = (JMenu) menuComponent;
                menuItemList = jmenuTemp.getMenuComponents();
                for (Component menuItemComponent : menuItemList) {
                    if (menuItemComponent instanceof RelatedModuleInfoMenuItem) {
                        menuItemTemp = (RelatedModuleInfoMenuItem) menuItemComponent;
                        list.add(menuItemTemp);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 更新模块数据，（给保存模块编辑的数据以后调用，刷新数据）
     *
     * @param newData
     */
    public void updateModuleData(Module newData) {
        ArrayList<RelatedModuleInfoMenuItem> allMenuItems = getAllMenuItems();
        for (RelatedModuleInfoMenuItem relatedModuleInfoMenuItemTemp : allMenuItems) {
            if (relatedModuleInfoMenuItemTemp.updateModuleData(newData)) {
                break;
            }
        }
    }

}
