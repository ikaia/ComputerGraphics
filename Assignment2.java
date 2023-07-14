
/**
 *
 *Exercise 2.7) and a spirograph as Shape objects.
public class Assignment2 {
 public static Shape createYingYang()
 public static Shape createSpirograph()
}
The spirograph is defined by the parametric equation:

Use constructive area geometry to create the Ying-Yang. Use a Path2D.Double object
(with the even-odd winding rule) to construct the spirograph with 1000 line segments.
Both Shape objects are centered at the origin with dimensions approximately 200 by 200.
A test program TestAssignment2.java for the project can be downloaded from folio.
Follow the specific coding formats and conventions for this course. Place the class
Assignment2 in the package assignment2. Submit the source file online to the dropbox on
folio.

*
* 
* @author Ikaia Melton
 */

package assignment2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Assignment2 extends JPanel {
	//Creating A Shape Array to Hold The Two Shapes
	Shape[] shapes_array = new Shape[2];
	int shape = 0;

	//Creating YinYang Method
	public static Shape createYinYang() {

		//Creates A Path2D.Double Object to Form Shape Object
		Path2D.Double YinYang = new Path2D.Double();
		YinYang.setWindingRule(Path2D.WIND_EVEN_ODD);
		Ellipse2D.Double shape1 = new Ellipse2D.Double(-105, -105, 210, 210);
		YinYang.append(shape1, false);
		Ellipse2D.Double shape2 = new Ellipse2D.Double(-100, -100, 200, 200);
		YinYang.append(shape2, false);
		
		//Adding the White UpperSemiCircles to Form Shape
		Arc2D.Double UpperSemiCircle = new Arc2D.Double(-100, -100, 200, 200, 90, 180, Arc2D.OPEN);
		YinYang.append(UpperSemiCircle, false);
		Arc2D.Double UpperSemiCircle2 = new Arc2D.Double(-50, -100, 100, 100, -90, 180, Arc2D.OPEN);
		YinYang.append(UpperSemiCircle2, false);
		
		//Adding the Black LowerSemiCircles to Form Shape
		Arc2D.Double LowerSemiCircle = new Arc2D.Double(-100, -100, 200, 200, 270, 180, Arc2D.OPEN);
		YinYang.append(LowerSemiCircle, false);
		Arc2D.Double LowerSemiCircle2 = new Arc2D.Double(-50, 0, 100, 100, 90, 180, Arc2D.OPEN);
		YinYang.append(LowerSemiCircle2, false);
		
		//Black Outline The Circle 
		Arc2D BlackOutline = new Arc2D.Double(-100, -100, 200 , 200 , 90, 180, Arc2D.OPEN);
		YinYang.append(BlackOutline, false);
		
		//Adding Small Circles in the Middle of Shape
		Ellipse2D.Double WhiteSmallCircle = new Ellipse2D.Double(0, -50, 15, 15);
		YinYang.append(WhiteSmallCircle, false);
		Ellipse2D.Double BlackSmallCircle = new Ellipse2D.Double(0, 50, 15, 15);
		YinYang.append(BlackSmallCircle, false);

		return YinYang;
	}

	//Creating Spirograph Method
	public static Shape createSpirograph(double r1, double r2, double p) {

		Path2D.Double Spirograph = new Path2D.Double();
		double limit = 8 * Math.PI / 1000;
		//Implement The Winding Rule 
		Spirograph.setWindingRule(Path2D.WIND_EVEN_ODD);
		//Include The Spirograph Which is Defined By The Parametric Equation
		for (double t = 0; t <= 8 * Math.PI; t += limit) {
			double x = (r1 + r2) * Math.cos(t) - p * Math.cos(((r1 + r2) / r2) * t);
			double y = (r1 + r2) * Math.sin(t) - p * Math.sin(((r1 + r2) / r2) * t);
			if (t == 0) {
				Spirograph.moveTo(x, y);
			} else {
				Spirograph.lineTo(x, y);
			}
		}
		return Spirograph;
	}

	public Assignment2() {
		//Demo Arrays from Folio
		shapes_array[0] = Assignment2.createYinYang();
		shapes_array[1] = Assignment2.createSpirograph(30, 40, 60);

		//Setting A Default Size Panel 
		setPreferredSize(new Dimension(400, 400));
		//Setting A Default Background Color
		setBackground(Color.white);
		//Adding A Default Mouse Listener For When User Clicks
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				shape ^= 1; 
				//Repaint Shapes By Default
				repaint();
			}
		});
	}

	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		Graphics2D graphic2D = (Graphics2D) graphic;
		graphic2D.translate(200, 200);
		graphic2D.fill(shapes_array[shape]);
		graphic2D.setColor(Color.black);
		
		//Optional - Helps Clean The Shapes 
		graphic2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	    
		
	}

	public static void main(String s[]) {
		//Default Close Operation Button to Frame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new Assignment2();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}