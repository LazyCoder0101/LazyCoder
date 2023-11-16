package com.lazycoder.uicodegeneration.moduleselect;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.formodule.ModuleStaticMethod;
import com.lazycoder.service.ModuleUseSetting;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.AssociatedModule;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.uiutils.mycomponent.multistatecomponent.LazyCoderMultiStateCheckBox;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import lombok.Getter;

/**
 * 改自 https://www.jiweichengzhu.com/article/5ff1be1e8abe463794aa1011b4bcff96
 *
 * @author admin
 */
public class ModuleNameNode extends AbstractModuleSelectBaseNode {

    public static final Border BORDER = BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY);
    /**
     *
     */
    private static final long serialVersionUID = -3364771451064488501L;
    private final ImageIcon moduleNameIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
            + "ModuleSelect" + File.separator + "moduleName.png");
    //    private final Color lastSelectedColor = new Color(224, 224, 224);
    private JLabel iconLabel, moduleNameLabel;

    private JTextArea noteLabel;

    private LazyCoderMultiStateCheckBox currentCheckBox;

    private boolean selected = false;

    @Getter
    private Module module = null;

    /**
     * 关联模块
     */
    @Getter
    private ArrayList<Module> associatedModuleList = new ArrayList<>();

    private ModuleSelectListPane moduleSelectListPane = null;

    public ModuleNameNode(Module module, ModuleSelectListPane moduleSelectListPane) {
        // TODO Auto-generated constructor stub
        this.module = module;
        this.moduleSelectListPane = moduleSelectListPane;
        initNodeGUI();
    }

    @Override
    protected void initNodeGUI() {
//        nodePane = new JPanel();
//        nodePane.setLayout(null);
////		nodePane.setOpaque(false);
//        nodePane.setBackground(null);
        super.initNodeGUI();
        nodePane.setPreferredSize(new Dimension(320, 70));
//		nodePane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        currentCheckBox = new LazyCoderMultiStateCheckBox("");
//        radioButton.addChangeListener(changeListener);
        currentCheckBox.setBounds(30, 4, 20, 42);
        currentCheckBox.setBackground(null);
        nodePane.add(currentCheckBox);

        iconLabel = new JLabel(moduleNameIcon);
        iconLabel.setBounds(currentCheckBox.getX() + currentCheckBox.getWidth() + 10, 4, moduleNameIcon.getIconWidth(), moduleNameIcon.getIconHeight());
        nodePane.add(iconLabel);

        moduleNameLabel = new JLabel(module.getModuleName());
        moduleNameLabel.setBounds(iconLabel.getX() + iconLabel.getWidth() + 10, 0, 132, 30);
        moduleNameLabel.setFont(new Font(iconLabel.getFont().getName(), Font.PLAIN, iconLabel.getFont().getSize() + 2));
        nodePane.add(moduleNameLabel);
        ArrayList<Integer> useSetting = ModuleStaticMethod.getUseSettingValues(this.module);
        if (useSetting.contains(ModuleUseSetting.USER_SHIELDING)) {
            moduleNameLabel.setForeground(Color.gray);
        } else {
            moduleNameLabel.setForeground(Color.black);
        }

        noteLabel = new JTextArea(module.getNote());
        noteLabel.setLineWrap(true);//自动换行
        noteLabel.setEditable(false);
        noteLabel.setBackground(nodePane.getBackground());
        noteLabel.setBounds(iconLabel.getX() + iconLabel.getWidth() + 7, 22, 190, 40);
        noteLabel.setForeground(new Color(192, 193, 199));
        if (module.getNote() != null && module.getNote().length() > 30) {
            noteLabel.setToolTipText(module.getNote());
        }
//        noteLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        Font font = new Font(iconLabel.getFont().getName(), Font.PLAIN, iconLabel.getFont().getSize());
        noteLabel.setFont(font);
        nodePane.add(noteLabel);

        nodePane.setBorder(BORDER);
    }


//    public boolean isEnabled() {
//        return currentCheckBox.isEnabled();
//    }
//
//    /**
//     * 设置其不能使用
//     *
//     * @param b
//     */
//    public void setEnabled(boolean b) {
//        currentCheckBox.setEnabled(b);
//        iconLabel.setEnabled(b);
//        moduleNameLabel.setEnabled(b);
//        noteLabel.setEnabled(b);
//    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selectedState) {
        if (selectedState) {
            if (moduleSelectListPane != null) {
                ArrayList<Integer> useSettingValues = ModuleStaticMethod.getUseSettingValues(module);
                if (useSettingValues.contains(ModuleUseSetting.USER_SHIELDING)) {//对用户屏蔽
                    LazyCoderOptionPane.showMessageDialog(null, "≡٩(๑>₃<)۶   亲，这个模块不用看了，需要的时候我会选的");
                } else {
                    boolean flag = moduleSelectListPane.selectedModule(module);
                    if (flag) {
                        selected = selectedState;
                        setPreUseModule(null);
                    } else {
                        currentCheckBox.setModuleSelectedNull(null);
                        if (moduleSelectListPane != null) {
                            moduleSelectListPane.cancelSelectedModule(module);
                        }
                    }
                }
            }
        } else {
            selected = selectedState;
            currentCheckBox.setModuleSelectedNull(null);
            if (moduleSelectListPane != null) {
                moduleSelectListPane.cancelSelectedModule(module);
            }
        }
    }

    public Module getModule() {
        return module;
    }

    @Override
    public JCheckBox getCurrentCheckBox() {
        return currentCheckBox;
    }

    /**
     * 清空关联模块
     */
    public void clearAssociatedModuleList() {
        associatedModuleList = new ArrayList<>();
    }

    /**
     * 去掉关联模块
     *
     * @param module
     */
    public void removeAssociatedModule(Module module) {
        Module associatedModule;
        for (int i = 0; i < this.associatedModuleList.size(); i++) {
            associatedModule = this.associatedModuleList.get(i);
            if (associatedModule.getModuleId().equals(module.getModuleId())) {
                this.associatedModuleList.remove(i);
                break;
            }
        }
    }

    /**
     * 添加关联模块
     *
     * @param associatedModule
     */
    public void addAssociatedModule(AssociatedModule associatedModule) {
        if (associatedModule != null) {
            boolean flag = false;
            ArrayList<Module> amList = associatedModule.getCurrentAssociatedModuleList();//获取关联的模块
            for (Module moduleTemp : amList) {
                flag = false;
                for (Module associatedModuleTemp : associatedModuleList) {
                    if (associatedModuleTemp.getModuleId().equals(moduleTemp.getModuleId())) {//如果现在这个菜单的关联模块，包含了associatedModule里面的关联模块
                        flag = true;//进行标记
                        break;
                    }
                }
                if (flag == false) {//没有包含里面的关联模块，则添加该模块进去
                    this.associatedModuleList.add(moduleTemp);
                }
            }
        }
    }

    /**
     * 选中（手动）
     */
    public void setPreUseModule(AssociatedModule associatedModule) {
        if (associatedModule != null) {
            addAssociatedModule(associatedModule);
        }
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.Munsell, true);
        par1.add("已选择使用该模块");
        if (associatedModuleList.size() > 0) {
            par1.add("，且模块" + getAssociatedModuleListStr() + "也需要使用该模块");
        }
        htmlText.addPar(par1);
        nodePane.setToolTipText(htmlText.getHtmlContent());
        currentCheckBox.setPreUseModule(null);
    }

    /**
     * 不选（手动）
     */
    public void setModuleSelectedNull() {
        nodePane.setToolTipText(null);
        currentCheckBox.setModuleSelectedNull(null);
    }

    /**
     * 之前已经选过
     */
    public void setPreFirUseModule(AssociatedModule associatedModule) {
        if (associatedModule != null) {
            addAssociatedModule(associatedModule);
        }
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.Munsell, true);
        par1.add("该模块之前已经选过");
        if (associatedModuleList.size() > 0) {
            par1.add("，且模块" + getAssociatedModuleListStr() + "也需要使用该模块");
        }
        htmlText.addPar(par1);
        nodePane.setToolTipText(htmlText.getHtmlContent());
        currentCheckBox.setPreFirUseModule(null);
    }

    /**
     * 不能选这个（因为当前选中的某些模块不允许）
     *
     * @param associatedModule
     */
    public void setAlsoDisableModule(AssociatedModule associatedModule) {
        if (associatedModule != null) {
            addAssociatedModule(associatedModule);
        }
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.Munsell, true);
        par1.add("当前需要用到的模块中，模块" + getAssociatedModuleListStr() + "不能同时使用该模块");
        htmlText.addPar(par1);
        nodePane.setToolTipText(htmlText.getHtmlContent());
        currentCheckBox.setAlsoDisableModule(null);
    }

    /**
     * 生成程序的时候一定要使用的模块
     */
    public void setParasiticModule() {
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.Munsell, true);
        par1.add("生成程序时，一定要使用该模块");
        htmlText.addPar(par1);
        nodePane.setToolTipText(htmlText.getHtmlContent());
        currentCheckBox.setParasiticModule(null);
    }

    /**
     * 是不是生成程序的时候一定要使用的模块
     *
     * @return
     */
    public boolean isParasiticModule() {
        return currentCheckBox.isParasiticModule();
    }

    /**
     * 设置为对用户进行屏蔽，不能让用户手动选择的模块
     */
    public void setCannotChooseManual() {
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.Munsell, true);
        par1.add("需要到该模块时，系统会自动进行选择，无需操作");
        htmlText.addPar(par1);
        nodePane.setToolTipText(htmlText.getHtmlContent());
        currentCheckBox.setCannotChooseManual(null);
    }

    /**
     * 是不是对用户进行屏蔽，不能让用户手动选择的模块
     *
     * @return
     */
    public boolean isCannotChooseManual() {
        return currentCheckBox.isCannotChooseManual();
    }

    /**
     * 某些模块需要
     *
     * @param associatedModule
     */
    public void setAlsoUseModule(AssociatedModule associatedModule) {
        if (associatedModule != null) {
            addAssociatedModule(associatedModule);
        }
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.Munsell, true);
        par1.add("当前需要用到的模块中，模块" + getAssociatedModuleListStr() + "需要事先使用该模块");
        htmlText.addPar(par1);
        nodePane.setToolTipText(htmlText.getHtmlContent());
        currentCheckBox.setAlsoUseModule(null);
    }

    /**
     * 该模块没有任何内容，无法使用
     */
    public void setPreCannotUse() {
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("注：", HtmlPar.Munsell, true);
        par1.add("该模块还没录入任何内容，无法使用");
        htmlText.addPar(par1);
        nodePane.setToolTipText(htmlText.getHtmlContent());
        currentCheckBox.setPreCannotUseModule(null);
    }

    /**
     * 是不是已经选中的模块
     *
     * @return
     */
    public boolean isPreUseModule() {
        return currentCheckBox.isPreUseModule();
    }

    /**
     * 是不是还没选
     *
     * @return
     */
    public boolean isModuleSelectedNull() {
        return currentCheckBox.isModuleSelectedNull();
    }

    /**
     * 是不是之前已经选过了
     *
     * @return
     */
    public boolean isPreFirUseModule() {
        return currentCheckBox.isPreFirUseModule();
    }

    /**
     * 是不是不能选这个（因为当前选中的某些模块不允许）
     *
     * @return
     */
    public boolean isAlsoDisableModule() {
        return currentCheckBox.isAlsoDisableModule();
    }

    /**
     * 该模块是不是某些模块需要的
     *
     * @return
     */
    public boolean isAlsoUseModule() {
        return currentCheckBox.isAlsoUseModule();
    }

    /**
     * 该模块是不是不能使用
     *
     * @return
     */
    public boolean isPreCannotUse() {
        return currentCheckBox.isPreCannotUseModule();
    }

    /**
     * 这个菜单项里面对应的模块是不是某个模块需要事先使用的，或者不能使用的模块
     *
     * @param list 一般传某个模块需要使用的，或者不能使用的模块的所有模块（调用 ModuleServiceImpl 里面的 getAllNeedUsedModuleList() 以及 getAllNoUsedModuleList()方法）
     * @return
     */
    public boolean containAssociatedModule(List<AssociatedModule> list) {
        boolean flag = false;
        if (list != null) {
            ArrayList<Module> tempList;
            for (AssociatedModule associatedModule : list) {
                tempList = associatedModule.getCurrentAssociatedModuleList();
                for (Module temp : tempList) {
                    if (this.module.getModuleId().equals(temp.getModuleId())) {
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 获取关联模块的字符串
     *
     * @return
     */
    public String getAssociatedModuleListStr() {
        return SysService.MODULE_SERVICE.getModuleListStr(associatedModuleList);
    }

}

/**
 * 存在以下几种状态
 * 1、【选中（手动）】：选中使能    （PreUseModule）
 * 2、【不选（手动）】：不选使能    （ModuleSelectedNull）
 * 3、【之前已经选过】：选中失能    （PreFirUse）
 * 4、【不能选这个（因为当前选中的某些模块不允许）】： 预先禁止使能   （AlsoDisableModule）
 * 5、【某些模块需要】：预选使能    （AlsoUseModule）
 * 6、【该模块不能使用】：禁止失能     （PreCannotUse）
 */
