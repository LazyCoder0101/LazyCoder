package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class PictureLabel extends JMenuBar implements LabelComponentIntetface {

    /**
     *
     */
    private static final long serialVersionUID = 7297432196825955774L;

    protected JMenu menu;

    /**
     * 添加图片组件
     */
//    private static ImageIcon pitureIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator
//            + "base_code_content_edit" + "label_element" + File.separator + "piture" +
//            File.separator + "piture.png");

    private PassingComponentParams passingComponentParams = null;

    public PictureLabel() {
        super();
        menu = new JMenu();
        menu.setBorder(BorderFactory.createRaisedBevelBorder());
//			menu.setUI(new BEMenuUI());
        this.add(menu);
//			super(piture_icon);

        Dimension dd = new Dimension(100, 25);
        this.setPreferredSize(dd);
        this.setMinimumSize(dd);
        this.setMaximumSize(dd);
    }

    @Override
    public void deleteFromPanel() {
        // TODO Auto-generated method stub
    }

    @Override
    public void setNavigate(boolean flag) {
    }

    @Override
    public BaseLableElement property() {
        return null;
    }

    @Override
    public String getLabelType() {
        // TODO Auto-generated method stub
        return LabelElementName.PICTURE;
    }

    @Override
    public PassingComponentParams getPassingComponentParams() {
        // TODO Auto-generated method stub
        return passingComponentParams;
    }

    @Override
    public void setPassingComponentParams(PassingComponentParams passingComponentParams) {
        // TODO Auto-generated method stub
        this.passingComponentParams = passingComponentParams;
//        label_component_intetface.addCorrespondingComponentResponseListener(this);
    }

}
