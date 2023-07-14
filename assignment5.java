package assignment5;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.*;

public class assignment5 extends JFrame{

    public assignment5() {
    	super("Assignment 5");

        
        MyPanel Panel = new MyPanel();
        Container con = this.getContentPane();
        con.setLayout(new BorderLayout());
        JButton button = new JButton("Save Image"); //Create a "Save Image" Button
        button.addActionListener(e->{
            Panel.button();
        });
        con.add(button,BorderLayout.NORTH);
        con.add(Panel,BorderLayout.CENTER);
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ev) {
                
                if(Panel.Timer.isRunning() == true){
                    Panel.Timer.stop();
                }
                else{
                    Panel.Timer.start();
                }
            }
        });
    }
    public static void main(String[] args) throws Exception {
        assignment5 frame = new assignment5();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Program will stops when exits
            frame.setBounds(0, 0, 1300, 900);//Set size of window

            
            frame.setVisible(true);//Set the visibility of the window
    }
}

class MyPanel extends JPanel implements ActionListener{
    public Timer Timer;
    private double Rotate;
    public MyPanel()
    {
        Timer=new Timer(1,this); //Create Object Timer
        Timer.start(); //Start Timer
        setPreferredSize(new Dimension(500,500));
        this.setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);
        Graphics2D g2=(Graphics2D) graphic;
        g2.rotate(Rotate,450,475);
        Ellipse2D circle=new Ellipse2D.Double(150,175,600,600);
        Ellipse2D yellow=new Ellipse2D.Double(25,400,700,600);
        g2.setClip(circle);
        g2.setColor(new Color(1,30,80));
        g2.fill(circle);
        g2.setColor(new Color(135,113,60));
        g2.fill(yellow);
        g2.setColor(Color.WHITE);
        g2.fill(new Eagle());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if(Rotate<360) {
        	
        	Rotate+=0.05; //Speed and Rotation the eagle to 360 degrees
        }
        else
        {
        	Rotate=0; //Stop when paused
        }
        	repaint();
    }

    public void button()
    {
        BufferedImage image=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=image.createGraphics();
        paint(g2);
        try{
        	g2.dispose();
            String fileName = "Eagle.png";
            String folderName = "Downloads";
            String filePath = System.getProperty("User") + File.separator + folderName + File.separator + fileName;
            File file = new File(filePath);
            ImageIO.write(image, "png", file);
            System.out.println("Image saved to: " + file.getAbsolutePath()); //Save and Store Image in File
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
