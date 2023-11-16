package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general;

import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.HiddenCodeButton;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JScrollPane;
import lombok.Getter;

public class GeneralCodePane extends Folder {

    /**
     *
     */
    private static final long serialVersionUID = -5375483330233057549L;

    protected JScrollPane scrollPane;

    @Getter
    private Box vBox;
    /**
     * 添加的代码数量
     */
//    @Setter
//    @Getter
//    private int codeNum = 0;

    /**
     * Creates a new instance of Folder
     */
    public GeneralCodePane(double proportion) {
        this(true, proportion);
    }

    public GeneralCodePane(boolean expanded, double proportion) {
        // 设置自己的layout
        setDefaultLayout();

        int width = (int) (proportion * SysUtil.SCREEN_SIZE.getWidth());

        // 生成并添加标题组件
        HiddenCodeButton hiddenButton = new HiddenCodeButton(expanded);
        setHiddenButton(hiddenButton);
        add(hiddenButton);

        vBox = Box.createVerticalBox();
        scrollPane = new JScrollPane(vBox);
//		jsp.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setSize(width, 300);

        // 生成并添加抽屉
        Drawer drawer = new Drawer(expanded ? 1 : 0, scrollPane);
        setDrawer(drawer);
        add(drawer);
    }

    /**
     * 重新设置滑动面板的高
     *
     * @param proportion
     * @param height
     */
    public void setCodeJspHeight(double proportion, int height) {
        int width = (int) (proportion * SysUtil.SCREEN_SIZE.getWidth());
        scrollPane.setSize(width, height);
    }

    @Override
    public Dimension getRequiredDimension() {
        Drawer drawerTemp = getDrawer();
        int w = drawerTemp.getContentWidth();
        int hiddenBtHeight = 0;
        if (getHiddenButton() != null) {
            hiddenBtHeight = getHiddenButton().getHeight();
        }
        // 高度是抽屉的高度加上标题的高度，抽屉的高度是目前抽出的长度
        int h = (int) (drawerTemp.getContentHeight() * drawerTemp.getRatio()) + hiddenBtHeight;
        return new Dimension(w, h);

    }

    /**
     * 继承该类的时候该方法都要重写
     *
     * @return
     */
    @Override
    public int getRequiredHeight() {
        Drawer drawerTemp = getDrawer();
        int hiddenBtHeight = 0;
        if (getHiddenButton() != null) {
            hiddenBtHeight = getHiddenButton().getHeight();
        }
        return (int) (drawerTemp.getContentHeight() * drawerTemp.getRatio()) + hiddenBtHeight;
    }


    /**
     * 添加代码面板
     */
    public void addCodePane() {
        this.repaint();
    }

    public void delCodePane() {
        if (vBox.getComponentCount() > 1) {
            vBox.remove(vBox.getComponentCount() - 1);

        }
        scrollPane.updateUI();
        scrollPane.repaint();
        this.updateUI();
        this.repaint();
    }

    public void clear() {
        //setCodeNum(0);
        vBox.removeAll();
    }


}
