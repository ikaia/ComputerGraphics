package assignment4;

import java.awt.geom.Path2D;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



import java.awt.geom.*;

import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.Shape;

public class assignment4 {
    public static Shape createYingYang() {
    	
        GeneralPath yingYang = new GeneralPath();

        // create the large circle
        Ellipse2D circle = new Ellipse2D.Double(-100, -100, 200, 200);
        yingYang.append(circle, false);

        // create the smaller circles
        Ellipse2D whiteCircle = new Ellipse2D.Double(-70, -100, 40, 200);
        yingYang.append(whiteCircle, false);
        Ellipse2D blackCircle = new Ellipse2D.Double(30, -100, 40, 200);
        yingYang.append(blackCircle, false);

        // create the teardrop shapes
        GeneralPath whiteTeardrop = new GeneralPath();
        whiteTeardrop.moveTo(-30, -100);
        whiteTeardrop.quadTo(-50, 0, -30, 100);
        whiteTeardrop.closePath();
        yingYang.append(whiteTeardrop, false);

        GeneralPath blackTeardrop = new GeneralPath();
        blackTeardrop.moveTo(70, -100);
        blackTeardrop.quadTo(50, 0, 70, 100);
        blackTeardrop.closePath();
        yingYang.append(blackTeardrop, false);

        return yingYang;
    }

    public static Shape createSpirograph() {
        Path2D spirograph = new Path2D.Double();

        int r1 = 30, r2 = 40, p = 60;
        double x, y;
        spirograph.moveTo(r1 + r2, 0);

        for (double t = 0; t <= 8 * Math.PI; t += 0.01) {
            x = (r1 + r2) * Math.cos(t) - p * Math.cos((r1 + r2) * t / r2);
            y = (r1 + r2) * Math.sin(t) - p * Math.sin((r1 + r2) * t / r2);
            spirograph.lineTo(x, y);
        }

        return spirograph;
    }
    
      
}

