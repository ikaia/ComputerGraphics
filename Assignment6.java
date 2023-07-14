package assignment6;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;


public class Assignment6 {
	public static void main(String args[]) {
		System.setProperty("sun.awt.noerasebackground", "true");
		new Assignment6();
	}

	public Assignment6(){

		//Creating the layout of Simple Universe 
		SimpleUniverse universe=new SimpleUniverse();
		//Creating the layout of BranchGroup
		BranchGroup group=new BranchGroup();
		//Creating the layout of Apperance
		Appearance apperance=new Appearance();
		apperance.setMaterial(new Material());
		Font3D font=new Font3D(new Font("Roboto", Font.PLAIN,1), new FontExtrusion());

		//Create text and shape objects to hold first and last name
		Text3D FirstName=new Text3D(font,"Ikaia");
		Shape3D FirstShape=new Shape3D(FirstName,apperance);
		Text3D LastName=new Text3D(font,"Melton");
		Shape3D LastShape=new Shape3D(LastName,apperance);

		//First Name Scale/Position
		Transform3D tr1 = new Transform3D();
		tr1.setScale(0.2);
		tr1.setTranslation(new Vector3d(-0.100,0,0.100));

		//Last Name Scale/Position
		Transform3D tr2 = new Transform3D();
		tr2.setScale(0.35);
		tr2.setTranslation(new Vector3d(-0.5,-0.3,0.75));

		TransformGroup trans1 = new TransformGroup(tr1);
		TransformGroup trans2 = new TransformGroup(tr2);


		//Add Child Group
		group.addChild(trans1);
		group.addChild(trans2);
		trans1.addChild(FirstShape);
		trans2.addChild(LastShape);

		//Background added to the scene
		Background background = new Background(new Color3f(0,0,1f));
		BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
		background.setApplicationBounds(sphere);
		group.addChild(background); 

		//Light
		Color3f lightColor=new Color3f(.75f,.75f,.75f);
		BoundingSphere bounds=new BoundingSphere(new Point3d(0,0,0),100);
		Vector3f direction=new Vector3f(4.0f,-7.0f,-12.0f);
		DirectionalLight light=new DirectionalLight(lightColor,direction);
		light.setInfluencingBounds(bounds);
		group.addChild(light);

		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group); 
	}

}
