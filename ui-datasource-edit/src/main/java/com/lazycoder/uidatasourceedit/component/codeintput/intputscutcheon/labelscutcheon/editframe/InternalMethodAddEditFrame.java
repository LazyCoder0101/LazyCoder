package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FunctionAddControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.methodadd.MethodAddCategoryPane;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.methodadd.MethodAddOtherAttributeMenu;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public class InternalMethodAddEditFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = -7106270440492029558L;

    private JPanel contentPane;

    private JTabbedPane tabbedPane;

    private FunctionAddControlLabel controlLabel;

    private JRadioButton radioButton;

    private MethodAddCategoryPane methodAddCategoryPane;

    private OperatingTipButton operatingTip;

    private MethodAddOtherAttributeMenu methodAddOtherAttributeMenu;

    /**
     * Create the frame.
     */
    private InternalMethodAddEditFrame() {
        super();
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        contentPane.add(toolBar, BorderLayout.NORTH);

        radioButton = new JRadioButton("只能添加内部功能");
        radioButton.setFocusPainted(false);
        toolBar.add(radioButton);

        toolBar.add(Box.createHorizontalStrut(15));

        JLabel label = new JLabel("属性");
        toolBar.add(label);

        methodAddOtherAttributeMenu = new MethodAddOtherAttributeMenu();
        toolBar.add(methodAddOtherAttributeMenu);
        toolBar.add(Box.createHorizontalStrut(15));

        ok.addActionListener(listener);
        cancel.addActionListener(listener);
        toolBar.add(ok);
        toolBar.add(cancel);

        operatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "3.png").getAbsolutePath()
        );
        toolBar.add(operatingTip);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        int width = (int) (SysUtil.SCREEN_SIZE.getWidth() * 0.32);
        setBounds((SysUtil.SCREEN_SIZE.width - width) / 2, SysUtil.SCREEN_SIZE.height / 2 - 350, width, 700);
    }

    public InternalMethodAddEditFrame(FunctionAddControlLabel controlLabel, FunctionAddParam functionAddParam) {
        this();
        methodAddCategoryPane = new MethodAddCategoryPane(functionAddParam);
        JScrollPane scrollPane = new JScrollPane(methodAddCategoryPane);
        tabbedPane.addTab("内部功能", null, scrollPane, null);

        this.controlLabel = controlLabel;
        methodAddCategoryPane.setControlElement(controlLabel.getControl());

        this.setTitle("更改功能拓展组件\"" + this.controlLabel.getControl().getThisName() + "\"属性");
        DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空
        reductionOfTheContent();
        DataSourceEditHolder.showErrorListIfNeed("这个功能拓展组件原来写的内容有点异常喔   (=ω=；)");
        this.setVisible(true);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == ok) {
                if (methodAddCategoryPane.getInternalMethodsNum() > 0) {
                    if (methodAddCategoryPane.check() == true) {
                        setValue();
                        InternalMethodAddEditFrame.this.dispose();

                        String[] options = new String[]{"知道了"};
                        LazyCoderOptionPane.showOptionDialog(null,
                                "(^_^)  刚才添加的内部功能，记得添加其对应代码的相应标签，\n否则生成代码时将无法添加", "注意一下！",
                                JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
                                options[0]);
                    }
                } else if (methodAddCategoryPane.getInternalMethodsNum() == 0) {
                    setValue();
                    InternalMethodAddEditFrame.this.dispose();
                }
            } else if (e.getSource() == cancel) {
                InternalMethodAddEditFrame.this.dispose();
            }
        }
    };

    /**
     * 还原内容
     */
    public void reductionOfTheContent() {
        radioButton.setSelected(controlLabel.isOnlInternalCodeIsAllowed());
        methodAddCategoryPane.reductionOfTheContent(this.controlLabel.getFunctionList());
        methodAddOtherAttributeMenu.setOtherAttribute(controlLabel.getControl().getOtherAttribute());
        methodAddOtherAttributeMenu.showText();
    }

    public void setValue() {
        controlLabel.setOnlInternalCodeIsAllowed(radioButton.isSelected());
        ArrayList<MethodAddStorageFormat> list = methodAddCategoryPane.getMethodAddStorageFormatList();
        controlLabel.setFunctionList(list);
        controlLabel.setOtherAttribute(methodAddOtherAttributeMenu.getSelectedOtherAttribute());
    }


}
