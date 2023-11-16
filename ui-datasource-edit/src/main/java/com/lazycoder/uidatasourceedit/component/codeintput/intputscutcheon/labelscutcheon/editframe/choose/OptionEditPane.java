package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.utils.JsonUtil;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.Scrollable;

/**
 * 信息值放置面板
 *
 * @author admin
 */
public class OptionEditPane extends JComponent implements Scrollable {

    public static final int SPACING = 10;
    /**
     *
     */
    private static final long serialVersionUID = -4780210239347718603L;

    private OptionNameTextFieldEditPane optionNameTextFieldEditPane;

    private NoteRowHeaderView noteRowHeaderView = null;

    /**
     * 新建
     */
    public OptionEditPane(NoteRowHeaderView noteRowHeaderView) {
        // TODO Auto-generated constructor stub
        this.noteRowHeaderView = noteRowHeaderView;
        init();
    }

    private void init() {
        setLayout(new OptionValuePlacePaneLayout());
        optionNameTextFieldEditPane = new OptionNameTextFieldEditPane();
        add(optionNameTextFieldEditPane);
    }

    /**
     * 新建
     */
    public void buildModel() {
        addOption();
        addAline();
    }

    /**
     * 还原
     */
    public void recoverModel(OptionDataModel optionDataModel) {
        String optionNameListParam = optionDataModel.getOptionNameListParam(),
                optionValueListParam = optionDataModel.getOptionValueListParam();
        ArrayList<String> optionNameList = JSON.parseObject(optionNameListParam,
                new TypeReference<ArrayList<String>>() {
                });
        optionNameTextFieldEditPane.recover(optionNameList);

        ArrayList<String> noteList = JSON.parseObject(optionDataModel.getRowNoteParam(),
                new TypeReference<ArrayList<String>>() {
                });
        ArrayList<ArrayList<String>> optionValueList = JSON.parseObject(optionValueListParam,
                new TypeReference<ArrayList<ArrayList<String>>>() {
                });
        OptionValueTextFieldEditPane optionValueTextFieldEditPane;
        String tempStr;
        for (int k = 0; k < optionValueList.size(); k++) {

            optionValueTextFieldEditPane = new OptionValueTextFieldEditPane();
            add(optionValueTextFieldEditPane);
            optionValueTextFieldEditPane.recover(optionValueList.get(k));
            if (this.noteRowHeaderView != null) {
                tempStr = k < noteList.size() ? noteList.get(k) : "";
                this.noteRowHeaderView.addValueNote(tempStr);
            }
        }

    }

    /**
     * 获取选项值的参数
     *
     * @return
     */
    public String getOptionValueListParam() {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        ArrayList<String> temp;
        OptionValueTextFieldEditPane optionValueTextFieldEditPane;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof OptionValueTextFieldEditPane) {
                optionValueTextFieldEditPane = (OptionValueTextFieldEditPane) getComponent(i);
                temp = optionValueTextFieldEditPane.getContentList();
                list.add(temp);
            }
        }
        return JsonUtil.getJsonStr(list);
    }

    public int getValueGroupNum() {
        return getComponentCount() - 1;
    }

    /**
     * 获取选项的名称列表参数
     *
     * @return
     */
    public String getOptionNameListParam() {
        ArrayList<String> list = optionNameTextFieldEditPane.getContentList();
        return JsonUtil.getJsonStr(list);
    }

    /**
     * 获取选项名称列表的数量
     *
     * @return
     */
    public int getValueNum() {
        return optionNameTextFieldEditPane.getComponentCount();
    }

    /**
     * 添加选项
     */
    public void addOption() {
        // int num = optionNameTextFieldEditPane.getComponentCount();
        AbstractOptionTextFieldEditPane optionTextFieldEditPane;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof AbstractOptionTextFieldEditPane) {
                optionTextFieldEditPane = (AbstractOptionTextFieldEditPane) getComponent(i);
                optionTextFieldEditPane.addOptionTextField();
            }
        }
    }

    /**
     * 删除最后一个选项
     */
    public void delOption() {
        AbstractOptionTextFieldEditPane optionTextFieldEditPane;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof AbstractOptionTextFieldEditPane) {
                optionTextFieldEditPane = (AbstractOptionTextFieldEditPane) getComponent(i);
                optionTextFieldEditPane.delOptionTextField();
            }
        }
    }

    public boolean check() {
        boolean flag = true;
        OptionNameTextFieldEditPane optionTextFieldEditPane;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof OptionNameTextFieldEditPane) {
                optionTextFieldEditPane = (OptionNameTextFieldEditPane) getComponent(i);
                flag = optionTextFieldEditPane.check();
                if (flag == false) {
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 添加一行
     */
    public void addAline() {
        int num = optionNameTextFieldEditPane.getComponentCount();
        OptionValueTextFieldEditPane optionValueTextFieldEditPane = new OptionValueTextFieldEditPane();
        add(optionValueTextFieldEditPane);
        optionValueTextFieldEditPane.addAline(num);
        if (this.noteRowHeaderView != null) {
            this.noteRowHeaderView.addValueNote("");
        }
    }

    /**
     * 删除一行
     */
    public void delAline() {
        int num = getComponentCount();
        if (num > 2) {
            for (int i = 0; i < num; i++) {
                if (i == num - 1) {
                    remove(getComponent(i));
                    if (this.noteRowHeaderView != null) {
                        this.noteRowHeaderView.delValueNote();
                    }
                }
            }
        }
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 100;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    // 该布局类的布局管理器
    class OptionValuePlacePaneLayout implements LayoutManager {

        public OptionValuePlacePaneLayout() {
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            // 补充实现preferredSize以便在JScrollPane调整时使用
            Insets insets = parent.getInsets();
            AbstractOptionTextFieldEditPane optionValueTextFieldEditPane = (AbstractOptionTextFieldEditPane) getComponent(
                    0);
            Dimension dimension = optionValueTextFieldEditPane.getRequiredDimension();
            int w = dimension.width, h = 10 + (dimension.height + SPACING) * getComponentCount();
            w += insets.left + insets.right;
            h += insets.top + insets.bottom;
            return new Dimension(w, h);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        @Override
        public void layoutContainer(Container parent) {
            Insets insets = parent.getInsets();
            int x = insets.left;
            int y = insets.top + 10;
            AbstractOptionTextFieldEditPane optionValueTextFieldEditPane;
            Dimension dimension;
            for (int i = 0; i < getComponentCount(); i++) {
                optionValueTextFieldEditPane = (AbstractOptionTextFieldEditPane) getComponent(i);
                dimension = optionValueTextFieldEditPane.getRequiredDimension();
                optionValueTextFieldEditPane.setBounds(x, y, dimension.width, dimension.height);

                optionValueTextFieldEditPane.doLayout();
                y += dimension.height + SPACING;
            }
        }
    }
}
