package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base;

import com.lazycoder.service.vo.BasePane;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.base.StringElement;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.component.LableMenuItem;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.LabelComponentIntetface;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.MarkComponentInterface;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.MathUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseTextPane extends JTextPane implements BasePane {

    public int fontSize = 18;

    protected final static boolean CONTROL_PANE = true, CODE_PANE = false;

    /**
     *
     */
    private static final long serialVersionUID = -8239263681443034350L;

    public SimpleAttributeSet attr = new SimpleAttributeSet();

    @Getter
    protected AbstractEditContainerModel model;

    @Getter
    @Setter
    protected PassingComponentParams passingComponentParams;

    @Getter
    protected StyledDocument doc = null;

    protected JPopupMenu theMenu;

    protected JMenuItem copyMenu = new JMenuItem("复制"), cutMenu = new JMenuItem("剪切"),
            pasteMenu = new JMenuItem("粘贴");

    @Getter
    @Setter
    private JScrollPane updateScrollpane = null;

    public BaseTextPane() {
        // TODO Auto-generated constructor stub
        doc = this.getStyledDocument();
        theMenu = new JPopupMenu();

        this.addMouseListener(mouseAdapter);
        copyActionListener();
        pasteActionListener();
        cutActionListener();
        setFontSize();
    }

    /**
     * 删除组件
     *
     * @param name
     * @param labelType
     * @param whatPane
     */
    public void deleteLabel(String name, String labelType, boolean whatPane) {
        int end = 0;
        Component a;
        String componentName;
        String type;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            Element e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());
                componentName = a.getName(); // 获取组件名

                if (a instanceof Component) {
                    if (a instanceof LabelComponentIntetface) {
                        if (componentName.equals(name)) {

                            type = ((LabelComponentIntetface) a).getLabelType();
                            if (labelType.equals(type)) {
                                ((LabelComponentIntetface) a).deleteFromPanel();

                                try {
                                    doc.remove(end, e0.getEndOffset() - end);
                                } catch (BadLocationException e1) {
                                    // TODO 自动生成的 catch 块
                                    e1.printStackTrace();
                                }
                                if (whatPane == CONTROL_PANE) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            end = e0.getEndOffset();
        }
    }


    /**
     * 右键弹出菜单
     */
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            if (e.getButton() == MouseEvent.BUTTON3) {
                showPopupMenu(e.getComponent(), e.getX(), e.getY());
            }
        }
    };

    /**
     * 让对应的标签组件响应起来
     *
     * @param lableElement 响应的对应属性
     * @param border       响应时使用的边框
     */
    public void makeCorrespondingLabelScutcheonRespond(BaseLableElement lableElement, Border border) {
        if (updateScrollpane != null) {
            LabelComponentIntetface labelComponent;
            boolean flag = true;
            int end = 0, cusorTemp = 0;
            Component a;
            Element e0;
            while (end < doc.getLength()) { // 把jtextpane的内容依次获取
                e0 = doc.getCharacterElement(end); // 获取第end个元素
                if ("component".equals(e0.getName())) {
                    a = StyleConstants.getComponent(e0.getAttributes());

                    if (a instanceof LabelComponentIntetface) {
                        labelComponent = ((LabelComponentIntetface) a);
                        if (labelComponent.property().match(lableElement)) {
                            ((JComponent) a).setBorder(border);
                            if (flag == true) {
                                cusorTemp = end;
                                flag = false;
                            }
                        }
                    }
                }
                end = e0.getEndOffset();
            }
            if (DataSourceEditHolder.autoPositionCheckBox != null && DataSourceEditHolder.autoPositionCheckBox.isSelected() == true) {
//                setCaretPosition(cusorTemp);
                SysUtil.scrollToProportion(MathUtil.txfloat(cusorTemp, doc.getLength()), updateScrollpane);
            }
        }
    }

    /**
     * 把滑动面板定位到组件 theComponent 所在的位置
     *
     * @param theComponent
     */
    final protected void scrollToComponent(Object theComponent) {
        if (updateScrollpane != null) {
            if (DataSourceEditHolder.autoPositionCheckBox != null && DataSourceEditHolder.autoPositionCheckBox.isSelected() == true) {
                int end = 0;
                Component a;
                Element e0;
                while (end < doc.getLength()) { // 把jtextpane的内容依次获取
                    e0 = doc.getCharacterElement(end); // 获取第end个元素
                    if ("component".equals(e0.getName())) {
                        a = StyleConstants.getComponent(e0.getAttributes());

                        if (a instanceof Component) {
                            if (a == theComponent) {
                                break;
                            }
                        }
                    }
                    end = e0.getEndOffset();
                }
                setCaretPosition(end);
            }
        }
    }

    /**
     * 获取使用状态
     *
     * @return
     */
    public boolean getUseState() {
        boolean useState = true;// 使用状态，默认有
        if (this.doc.getLength() == 0) {
            useState = false;
        }
        return useState;
    }

    /*
     * 删除组件
     */
    public void deleteAllComponents() {
        int end = 0;
        Component a;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            Element e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                a = StyleConstants.getComponent(e0.getAttributes());
                if (a instanceof Component) {
                    if (a instanceof LabelComponentIntetface) {
                        ((LabelComponentIntetface) a).deleteFromPanel();
                    } else if (a instanceof MarkComponentInterface) {
                        ((MarkComponentInterface) a).deleteFromPanel();
                    }
                    try {
                        doc.remove(end, e0.getEndOffset() - end);
                    } catch (BadLocationException e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }
                }
            }
            end = e0.getEndOffset();
        }
    }

    /**
     * 把菜单选项失能
     */
    protected void menuItemSetEnabledFalse(JMenu menu, String delMenuItemName) {
        LableMenuItem menuItem;
        for (int i = 0; i < menu.getMenuComponentCount(); i++) {
            menuItem = (LableMenuItem) menu.getItem(i);
            if (menuItem.getThisName().trim().equals(delMenuItemName)) {
                menuItem.setEnabled(false);
                break;
            }
        }
    }

    /**
     * 显示右键菜单
     */
    protected void showPopupMenu(Component invoker, int x, int y) {
        theMenu.show(invoker, x, y);
    }

    /**
     * 给"复制"菜单添加监听器
     */
    private void copyActionListener() {
        copyMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO 自动生成的方法存根
                String temp = BaseTextPane.this.getSelectedText();
                StringSelection selectText = new StringSelection(temp);
                SysUtil.clipboard.setContents(selectText, null);
            }
        });
    }

    /**
     * 给“粘贴菜单添加监听器
     */
    private void pasteActionListener() {
        pasteMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 自动生成的方法存根
                Transferable contexts = SysUtil.clipboard.getContents(null);// 获取剪切板中的内容
                DataFlavor flavor = DataFlavor.stringFlavor;// 剪切板的风格（系统的标准风格）
                try {
                    String str = (String) contexts.getTransferData(flavor);
                    BaseTextPane.this.replaceSelection(str); // 替换光标所在位置的文本
                } catch (Exception e1) {
                }
            }
        });

    }

    /**
     * 给"剪切"菜单添加监听器
     */
    private void cutActionListener() {
        cutMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 自动生成的方法存根
                String temp = BaseTextPane.this.getSelectedText();// 获得鼠标拖动选取的文本
                StringSelection text = new StringSelection(temp);// 把待剪切的文本传递给
                // text 对象
                SysUtil.clipboard.setContents(text, null);// 将文本放入剪切板中
                BaseTextPane.this.replaceSelection("");
            }
        });
    }

    /**
     * 根据文字、标签、标记获取设置参数
     *
     * @return
     * @throws BadLocationException
     */
    protected ArrayList<BaseElementInterface> getStatementFormatParams() throws BadLocationException {
        int end = 0;
        ArrayList<BaseElementInterface> list = new ArrayList<>();
        String stringTemp;
        Component cTemp;
        StringElement sElement;
        Element e0;
        while (end < doc.getLength()) { // 把内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            // 判断该元素是什么组件或者是string
            if ("content".equals(e0.getName())) {
                stringTemp = e0.getDocument().getText(end, e0.getEndOffset() - end);
                sElement = new StringElement(stringTemp);
                list.add(sElement);

            } else if ("component".equals(e0.getName())) {
                cTemp = StyleConstants.getComponent(e0.getAttributes());
                if (cTemp instanceof LabelComponentIntetface) {
                    list.add(((LabelComponentIntetface) cTemp).property());
                } else if (cTemp instanceof MarkComponentInterface) {
                    list.add(((MarkComponentInterface) cTemp).property());
                }
            }
            end = e0.getEndOffset();
        }
        return list;
    }

    /**
     * 定位到对应的标签组件
     *
     * @param lableScutcheon
     * @param flag
     * @return
     */
    public boolean setNavigateCorrespondingLable(LabelComponentIntetface lableScutcheon, boolean flag) {
        boolean haveFlag = false;
        int end = 0;
        Object object = null;
        LabelComponentIntetface lableElement;
        Element e0;
        while (end < doc.getLength()) { // 把jtextpane的内容依次获取
            e0 = doc.getCharacterElement(end); // 获取第end个元素
            if ("component".equals(e0.getName())) {
                object = StyleConstants.getComponent(e0.getAttributes());
                if (object instanceof LabelComponentIntetface) {
                    lableElement = (LabelComponentIntetface) object;
                    if (lableElement.property().match(lableScutcheon.property())) {
                        lableElement.setNavigate(flag);
                        haveFlag = true;
                    }
                }
            }
            end = e0.getEndOffset();
        }
        return haveFlag;
    }


    /**
     * 生成语句格式
     *
     * @return
     * @throws BadLocationException
     */
    public String getStatementFormat() throws BadLocationException {
        ArrayList<BaseElementInterface> list = getStatementFormatParams();
        String out = JsonUtil.getJsonStr(list);
        return out;
    }


    /**
     * 添加文字
     *
     * @param text
     */
    public void insertString(String text) {
        try {
            doc.insertString(doc.getLength(), text, attr);
        } catch (BadLocationException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    /**
     * 添加文字
     *
     * @param text
     */
    public void insertString(String text, int fontSize, Color foreground) {
        SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(simpleAttributeSet, fontSize);//字体大小
        if(foreground!=null) {
            StyleConstants.setForeground(simpleAttributeSet, foreground);//背景色
        }
//            StyleConstants.setFontFamily(set, font.getFontName());//字体名称
//            if(font.isBold()){//粗体
//                StyleConstants.setBold(set, true);
//            }
//            if(font.isItalic()){//斜体
//                StyleConstants.setItalic(set, true);
//            }
//        doc.remove(0, doc.getLength());//移除所以内容，如果是添加或者插入文本，注释掉这行
        try {
            doc.insertString(doc.getLength(), text, simpleAttributeSet);//插入字符，位置，字符串，属性集
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void setFontSize() {
        StyleConstants.setFontSize(attr, fontSize);
        this.setParagraphAttributes(attr, true);
    }


    public void setModel(AbstractEditContainerModel model) {
        this.model = model;
        passingComponentParamsInit();
    }


    private void passingComponentParamsInit() {
        passingComponentParams = new PassingComponentParams();
        passingComponentParams.setGeneralModel(model);
        passingComponentParams.setThisPane(this);
    }

    @Override
    public abstract void clear();


    /**
     * 根据字符串格式还原之前的内容
     *
     * @param statementFormat
     */
//	public abstract void reductionContent(String statementFormat);

    /**
     * 根据传入数据还原之前内容
     */
    public abstract void reductionContent(ArrayList<BaseElementInterface> list);


}
