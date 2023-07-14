package assignment10;

import javax.vecmath.*;
import java.awt.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;

public class assignment10 extends Applet {

    public static void main(String[] args) {
        new MainFrame(new assignment10(), 700, 600);
    }

    public void init() {
        //Create Canvas3D object
        this.setLayout(new GridLayout(1,2));
        GraphicsConfiguration GraphicsConfiguration = SimpleUniverse.getPreferredConfiguration();
        //Standard View
        Canvas3D Canvas3D = new Canvas3D(GraphicsConfiguration);    
        SimpleUniverse SimpleUniverse = new SimpleUniverse(Canvas3D);
        SimpleUniverse.getViewingPlatform().setNominalViewingTransform();
        
        //There's need to be a First View--------> Located at: 0,0,1, Looking at:0,0,0, +Y is up direction
        Canvas3D = new Canvas3D(GraphicsConfiguration);
        add(Canvas3D);
        BranchGroup BranchGroupView = createView(Canvas3D, new Point3d(0,0,1), 
        new Point3d(0,0,0), new Vector3d(0,1,0));
        SimpleUniverse.addBranchGraph(BranchGroupView);
        
        
        //Thers's need to be a Second View--------->  Located at: 1,1,1,  Looking at:0,0,0, +X is up direction 
        Canvas3D = new Canvas3D(GraphicsConfiguration);
        add(Canvas3D);
        BranchGroupView = createView(Canvas3D, new Point3d(1, 1, 1),
        new Point3d(0,0,0), new Vector3d(1,0,0));
        SimpleUniverse.addBranchGraph(BranchGroupView);
        
        //Creating a Content Branch Object for First and Second View then Using it for Scene Group
        BranchGroup bg = createSceneGraph();
        bg.compile();
        SimpleUniverse.addBranchGraph(bg);
      }

        private BranchGroup createView(Canvas3D Canvas3D, Point3d point, Point3d center, Vector3d vector) {
        	    //Setting the Specified FieldOfView 
        	    View view = new View();
        	    view.setFieldOfView(0.04 * Math.PI);
        	    view.setFrontClipDistance(0.01);
        	    view.setProjectionPolicy(View.PARALLEL_PROJECTION);
        	    ViewPlatform ViewPlatform = new ViewPlatform();
        	    view.addCanvas3D(Canvas3D);
        	    view.attachViewPlatform(ViewPlatform);
        	    view.setPhysicalBody(new PhysicalBody());
        	    view.setPhysicalEnvironment(new PhysicalEnvironment());
        	    //Create Transform Object
        	    Transform3D Transform = new Transform3D();
        	    //Create LookAt with Transform Object with an invert
        	    Transform.lookAt(point, center, vector);
        	    Transform.invert();
        	    //Create TransformGroup Object with adding a child
        	    TransformGroup TransformGroup = new TransformGroup(Transform);
        	    TransformGroup.addChild(ViewPlatform);
        	    //Create an BranchGroup Object with adding a child
        	    BranchGroup bgView = new BranchGroup();
        	    bgView.addChild(TransformGroup);
        	    return bgView;
        	  }
	  
	 private static BranchGroup createSceneGraph() {
		 //Create a BranchGroup Object that will be used as a root
		 BranchGroup BranchGroup = new BranchGroup();
		    //Create TransformGroup Object will be used to spinning 
		    TransformGroup Transformm = new TransformGroup();
		    Transformm.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		    BranchGroup.addChild(Transformm);
			//Create Appearance Object
			Appearance Appearance = new Appearance();
			Appearance.setMaterial(new Material());
		    PolygonAttributes PolygonAttributes = new PolygonAttributes();
		    PolygonAttributes.setBackFaceNormalFlip(true);
		    PolygonAttributes.setCullFace(PolygonAttributes.CULL_NONE);
		    Appearance.setPolygonAttributes(PolygonAttributes);
			//Turn BackFace Normal
			Material Material=new Material();
			Material.setAmbientColor(0.5f, 0.5f, 0.5f);
	        Appearance.setMaterial(Material);
			Shape3D shape = new Hexnut();
			shape.setAppearance(Appearance);
			Transform3D Transformmm = new Transform3D();
			Transformmm.setScale(0.15);
			TransformGroup tg = new TransformGroup(Transformmm);
			Transformm.addChild(tg);
			tg.addChild(shape);
			// rotator
		    Alpha alpha = new Alpha(-1, 24000);
		    RotationInterpolator rotator = new RotationInterpolator(alpha, Transformm);
		    BoundingSphere Bounding = new BoundingSphere();
		    rotator.setSchedulingBounds(Bounding);
		    Transformm.addChild(rotator);
			// background and light
			Background background = new Background(1.0f, 1.0f, 1.0f);
			background.setApplicationBounds(Bounding);
			BranchGroup.addChild(background);
			AmbientLight light = new AmbientLight(true, new Color3f(Color.black));
			light.setInfluencingBounds(Bounding);
			BranchGroup.addChild(light);
			PointLight ptlight = new PointLight(new Color3f(Color.gray), new Point3f(1f, 1f,1f), new Point3f(1f, 0f, 0f));
			ptlight.setInfluencingBounds(Bounding);
			BranchGroup.addChild(ptlight);
			PointLight ptlight2 = new PointLight(new Color3f(Color.gray), new Point3f(-2f, 2f, 2f), new Point3f(1f, 0f, 0f));
			ptlight2.setInfluencingBounds(Bounding);
			BranchGroup.addChild(ptlight2);
			return BranchGroup;
		}

}