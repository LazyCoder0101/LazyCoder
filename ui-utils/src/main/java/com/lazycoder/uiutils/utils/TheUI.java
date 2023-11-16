package com.lazycoder.uiutils.utils;

import com.formdev.flatlaf.FlatLightLaf;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uiutils.ui.MyFlatUI.MyFlatOptionPaneUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class TheUI {


//	 mvn install:install-file -DgroupId=com.jtattoo -DartifactId=jtattoo
//	 -Dversion=1.0 -Dpackaging=jar -Dfile=F:\jar\jtattoo.jar

    /**
     * UIManager中UI字体相关的key
     * 启动圆角参考：https://www.formdev.com/flatlaf/customizing/
     */
    public static String[] DEFAULT_FONT = new String[]{"Table.font", "TableHeader.font", "CheckBox.font", "Tree.font",
            "Viewport.font", "ProgressBar.font", "RadioButtonMenuItem.font", "ToolBar.font", "ColorChooser.font",
            "ToggleButton.font", "Panel.font", "TextArea.font", "Menu.font", "TableHeader.font"
            // ,"TextField.font"
            , "OptionPane.font", "MenuBar.font", "Button.font", "Label.font", "PasswordField.font", "ScrollPane.font",
            "MenuItem.font", "ToolTip.font", "List.font", "EditorPane.font", "Table.font", "TabbedPane.font",
            "RadioButton.font", "CheckBoxMenuItem.font", "TextPane.font", "PopupMenu.font", "TitledBorder.font",
            "ComboBox.font"};


    public static void setPaneColor(Color color) {
//		UIManager.put("List.background",color);
        UIManager.put("EditorPane.background", color);
        UIManager.put("ToolBar.background", color);
//		UIManager.put("TabbedPane.tabAreaBackground",color);
        UIManager.put("PopupMenu.background", color);
//		UIManager.put("Desktop.background",color);
        UIManager.put("ScrollPane.background", color);
        UIManager.put("TabbedPane.unselectedBackground", color);
//		UIManager.put("Tree.selectionBackground",color);
        UIManager.put("SplitPane.background", color);
        UIManager.put("MenuBar.background", color);
        UIManager.put("Viewport.background", color);
//		UIManager.put("TabbedPane.background",color);
        UIManager.put("Panel.background", color);
//		UIManager.put("ScrollBar.background",color);
        UIManager.put("RadioButtonMenuItem.background", color);
//		UIManager.put("Menu.background",color);

//		UIManager.put("Tree.background",color);
//		UIManager.put("Label.background",color);
//		UIManager.put("TextPane.background",color);
//		UIManager.put("Table.background",color);
        //frame.setBackground(Color.Red);
    }

    public static void flatLookAndFeel() {
        try {
//			UIManager.setLookAndFeel(new FlatDarculaLaf());
//			UIManager.setLookAndFeel(new FlatDarkLaf());
//			UIManager.setLookAndFeel(new FlatIntelliJLaf());

//			UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("Component.arrowType", "triangle");
            UIManager.put("Button.arc", 15);
            UIManager.put("TextComponent.arc", 15);
            UIManager.put("Component.arc", 15);

            UIManager.put("TabbedPane.underlineColor", new ColorUIResource(new Color(24, 144, 255)));
            UIManager.put("TabbedPane.hoverColor", new ColorUIResource(new Color(254, 254, 254)));
            UIManager.put("TabbedPane.focusColor", new ColorUIResource(new Color(246, 250, 255)));

            UIManager.put("Table.gridColor", new ColorUIResource(new Color(244, 244, 244)));
            UIManager.put("Table.selectionBackground", new ColorUIResource(new Color(246, 249, 252)));//24,144,255
            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.showVerticalLines", true);
            UIManager.put("Table.selectionForeground", new ColorUIResource(Color.black));
            UIManager.put("TableHeader.background", new ColorUIResource(new Color(242, 242, 242)));


            UIManager.put("ToolTip.background", new ColorUIResource(new Color(60, 60, 60, 200)));//80,80,80
            UIManager.put("ToolTip.foreground", new ColorUIResource(Color.white));

//			FlatLightLaf.install();
            UIManager.setLookAndFeel(new FlatLightLaf());

            JFrame.setDefaultLookAndFeelDecorated(true);
            ToolTipManager.sharedInstance().setDismissDelay(5000);// 设置为5秒

            //设置菜单项
            UIManager.put("MenuItem.selectionBackground", new Color(236, 245, 255));
            UIManager.put("MenuItem.background", new Color(255, 255, 255));
//			UIManager.put("MenuItem.disabledForeground",new Color(192,196,204));
//			UIManager.put("MenuItem.foreground",new Color(96,98,102));
            UIManager.put("MenuItem.selectionForeground", new Color(94, 158, 255));

            UIManager.put("RadioButtonMenuItem.selectionBackground", new Color(236, 245, 255));
            UIManager.put("RadioButtonMenuItem.background", new Color(255, 255, 255));
//			UIManager.put("RadioButtonMenuItem.disabledForeground",new Color(192,196,204));
//			UIManager.put("RadioButtonMenuItem.foreground",new Color(96,98,102));
            UIManager.put("RadioButtonMenuItem.selectionForeground", new Color(94, 158, 255));

            UIManager.put("CheckBoxMenuItem.selectionBackground", new Color(236, 245, 255));
            UIManager.put("CheckBoxMenuItem.background", new Color(255, 255, 255));
//			UIManager.put("CheckBoxMenuItem.disabledForeground",new Color(192,196,204));
//			UIManager.put("CheckBoxMenuItem.foreground",new Color(96,98,102));
            UIManager.put("CheckBoxMenuItem.selectionForeground", new Color(94, 158, 255));

            //设置菜单Menu
            UIManager.put("Menu.selectionBackground", new Color(236, 245, 255));
            UIManager.put("Menu.background", new Color(255, 255, 255));
//			UIManager.put("Menu.disabledForeground",new Color(192,196,204));
//			UIManager.put("Menu.foreground",new Color(96,98,102));
            UIManager.put("Menu.selectionForeground", new Color(94, 158, 255));

//			UIManager.put("ComboBox.selectionBackground",new Color(237,246,254));
//			UIManager.put("ComboBox.selectionForeground",new Color(33,150,234));
            UIManager.put("ComboBox.selectionBackground", new Color(245, 247, 250));
            UIManager.put("ComboBox.selectionForeground", new Color(64, 158, 255));

            UIManager.put("OptionPane.background", Color.white);
            UIManager.put("OptionPaneUI", MyFlatOptionPaneUI.class.getName());
            //提示框的自定义按钮通过Container container 的container.getComponents()方法获取组件在设置UI

            setPaneColor(new Color(248, 248, 248));
        } catch (Exception ex) {
            SysService.SYS_SERVICE_SERVICE.log_error(
                    "切换FlatLaf皮肤异常 " + ex.getMessage()
            );
        }

        /**
         * 圆角方角：
         *
         * UIManager.put( "Button.arc", 0 );
         *
         * UIManager.put( "Component.arc", 0 );
         *
         * 箭头类型：
         *
         * UIManager.put( "Component.arrowType", "chevron" );
         *
         * UIManager.put( "Component.arrowType", "triangle" );
         *
         * 滚动条的上一个/下一个箭头按钮默认情况下是隐藏的，可以配置，宽度也可以修改：
         *
         * UIManager.put( "ScrollBar.showButtons", true );
         *
         * UIManager.put( "ScrollBar.width", 16 );
         *
         */
    }

    public static void beautyEyeLookAndFeel() {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
        }
        try {
            // 设置本属性将改变窗口边框样式定义
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);
            BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
        }

        Border bd = new org.jb2011.lnf.beautyeye.ch8_toolbar.BEToolBarUI.ToolBarBorder(
                UIManager.getColor("ToolBar.shadow") // Floatable时触点的颜色
                , UIManager.getColor("ToolBar.highlight")// Floatable时触点的阴影颜色
                , new Insets(6, 0, 11, 0)); // border的默认insets
        UIManager.put("ToolBar.border", new BorderUIResource(bd));
        // 调整默认字体
        for (int i = 0; i < DEFAULT_FONT.length; i++) {
            UIManager.put(DEFAULT_FONT[i], new Font("微软雅黑", Font.PLAIN, 14));
        }
        UIManager.put("RootPane.setupButtonVisible", false);
        UIManager.put("TabbedPane.tabAreaInsets", new InsetsUIResource(0, 0, 0, 0));
        UIManager.put("TabbedPane.contentBorderInsets", new InsetsUIResource(0, 0, 2, 0));
        UIManager.put("TabbedPane.tabInsets", new InsetsUIResource(3, 10, 9, 10));
        Font frameTitleFont = (Font) UIManager.get("InternalFrame.titleFont");
        frameTitleFont = frameTitleFont.deriveFont(Font.PLAIN);
        UIManager.put("InternalFrame.titleFont", frameTitleFont);

        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//	public static void littleLuckLookAndFeel() {
//		try {
//			LittleLuck.getSingleton().luanchLookAndFeel();
//			JFrame.setDefaultLookAndFeelDecorated( true );
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public static void CoolToolTip() {
//		try {
//			ToolTipManager.sharedInstance().setDismissDelay(50000);
//			PopupFactory.setSharedInstance(new NonRectanglePopupFactory());
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception ex) {
//		}
//	}


}
