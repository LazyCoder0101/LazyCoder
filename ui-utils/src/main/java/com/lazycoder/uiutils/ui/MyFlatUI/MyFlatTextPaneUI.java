package com.lazycoder.uiutils.ui.MyFlatUI;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import com.formdev.flatlaf.ui.FlatTextPaneUI;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.plaf.ComponentUI;

public class MyFlatTextPaneUI extends FlatTextPaneUI {

    public static ComponentUI createUI(JComponent c ) {
        return new FlatTextPaneUI();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        JTextPane textPane = (JTextPane) c;
        textPane.putClientProperty("TextComponent.arc", 18);
        textPane.setBorder(new MyFlatRoundBorder());
    }
//
////    @Override
////    protected void paintBackground( Graphics g ) {
//////        super.paintBackground(g);
////        JTextComponent c = getComponent();
////
////        // for compatibility with IntelliJ themes
//////        if( isIntelliJTheme && (!c.isEnabled() || !c.isEditable()) && (c.getBackground() instanceof UIResource) ) {
////            paintParentBackground( g, c );
//////            return;
//////        }
////
//////        super.paintBackground( g );
////    }
//
////    @Override
////    public void update(Graphics g, JComponent c) {
////        if (c.isOpaque()) {
//////            g.setColor(c.getBackground());
//////            g.fillRect(0, 0, c.getWidth(),c.getHeight());
////            Graphics2D g2 = (Graphics2D) g.create();
////            if( c != null ) {
////                g.setColor(c.getBackground());
////
////                float focusWidth = scale((float) border.getThisFocusWidth(c));
////                float borderWidth = scale((float) border.getThisBorderWidth(c));
////                float arc = scale((float) border.getThisArc(c));
////                Color outlineColor = border.getThisOutlineColor(c);
////
////                // paint outer border
////                if (outlineColor != null || border.isThisFocused(c)) {
////                    float innerWidth = !border.isThisCellEditor(c) && !(c instanceof JScrollPane)
////                            ? (outlineColor != null ? border.innerOutlineWidth() : border.getThisInnerFocusWidth(c))
////                            : 0;
////
////                    if (focusWidth > 0 || innerWidth > 0) {
////                        Path2D rect = MyFlatUIUtils.getPaintComponentOuterBorderImplShape(g2, 0, 0, c.getWidth(), c.getHeight(),
////                                focusWidth, borderWidth + scale(innerWidth), arc);
//////                    g.setColor( parent.getBackground() );
////                        g.setColor(Color.yellow);
////                        g2.fill(rect);
////                    }
////                }
////            }
////        }
////        paint(g, c);
////    }
//
//    private static Container findOpaqueParent( Container c ) {
//        while( (c = c.getParent()) != null ) {
//            if( c.isOpaque() )
//                return c;
//        }
//        return null;
//    }
//
//    public static void paintParentBackground( Graphics g, JComponent c ) {
//        Graphics2D g2 = (Graphics2D) g.create();
//        Container parent = findOpaqueParent( c );
//        if( parent != null ) {
//            g.setColor( parent.getBackground() );
//
//            float focusWidth = scale( (float) border.getThisFocusWidth( c ) );
//            float borderWidth = scale( (float) border.getThisBorderWidth( c ) );
//            float arc = scale( (float) border.getThisArc( c ) );
//            Color outlineColor = border.getThisOutlineColor( c );
//
//            // paint outer border
//            if( outlineColor != null || border.isThisFocused( c ) ) {
//                float innerWidth = !border.isThisCellEditor( c ) && !(c instanceof JScrollPane)
//                        ? (outlineColor != null ? border.innerOutlineWidth() : border.getThisInnerFocusWidth( c ))
//                        : 0;
//
//                if( focusWidth > 0 || innerWidth > 0 ) {
//                    Path2D rect = MyFlatUIUtils.getPaintComponentOuterBorderImplShape(g2, 0, 0, c.getWidth(), c.getHeight(),
//                            focusWidth, borderWidth + scale(innerWidth), arc);
////                    g.setColor( parent.getBackground() );
//                    g.setColor(Color.yellow);
//                    g2.fill(rect);
//                }
//            }
////            Path2D rect =MyFlatUIUtils.getPaintComponentOuterBorderImplShape(g2, 0, 0, c.getWidth(), c.getHeight(),
////                    focusWidth, borderWidth, arc);
////
////            g.setColor( parent.getBackground() );
//////            g.fillRect( 0, 0, c.getWidth(), c.getHeight() );
////            g2.fill(rect);
//        }
//
//    }
//
//    protected boolean isCellEditor( Component c ) {
//        return FlatUIUtils.isCellEditor( c );
//    }

    static class MyFlatRoundBorder extends FlatRoundBorder{

        public int getThisFocusWidth(Component c) {
            return super.getFocusWidth(c);
        }

        public int getThisBorderWidth(Component c) {
            return super.getBorderWidth(c);
        }

        public Color getThisOutlineColor(Component c) {
            return super.getOutlineColor(c);
        }

        public boolean isThisFocused(Component c) {
            return super.isFocused(c);
        }

        public boolean isThisCellEditor(Component c) {
            return super.isCellEditor(c);
        }

        public float getThisInnerFocusWidth(Component c) {
            return super.getInnerFocusWidth(c);
        }

        public int getThisArc(Component c) {
            return super.getArc(c);
        }

        public float innerOutlineWidth(){
            return innerOutlineWidth;
        }

    }

}
