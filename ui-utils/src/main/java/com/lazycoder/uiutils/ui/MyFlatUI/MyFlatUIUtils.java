package com.lazycoder.uiutils.ui.MyFlatUI;

import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class MyFlatUIUtils extends FlatUIUtils {

    public static Path2D getPaintComponentOuterBorderImplShape(Graphics2D g, int x, int y, int width, int height,
                                                               float focusWidth, float lineWidth, float arc) {
        float ow = focusWidth + lineWidth;
        float outerArc = arc + (focusWidth * 2);
        float innerArc = arc - (lineWidth * 2);

        // reduce outer arc slightly for small arcs to make the curve slightly wider
        if (focusWidth > 0 && arc > 0 && arc < UIScale.scale(10)) {
            outerArc -= UIScale.scale(2f);
        }

        Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
        path.append(createComponentRectangle(x, y, width, height, outerArc), false);
        path.append(createComponentRectangle(x + ow, y + ow, width - (ow * 2), height - (ow * 2), innerArc), false);
        return path;
    }

}
