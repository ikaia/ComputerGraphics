package assingment11;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.TextArea;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.behaviors.mouse.*;


public class assingment11 extends Applet {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
	        System.setProperty("sun.awt.noerasebackground", "true");
	        new MainFrame(new assingment11(), 640, 480);
	    }

	    public void init() {
	        // create canvas
	        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
	        Canvas3D cv = new Canvas3D(gc);
	        setLayout(new BorderLayout());
	        add(cv, BorderLayout.CENTER);
	        TextArea ta = new TextArea("",3,30,TextArea.SCROLLBARS_NONE);
	        ta.setText("Rotation: Drag with left button\n");
	        ta.append("Translation: Drag with right button\n");
	        ta.append("Zoom: Hold Alt key and drag with left button");
	        ta.setEditable(false);
	        add(ta, BorderLayout.SOUTH);
	        BranchGroup bg = createBranchGraph();
	        bg.compile();
	        SimpleUniverse su = new SimpleUniverse(cv);
	        su.getViewingPlatform().setNominalViewingTransform();
	        su.addBranchGraph(bg);
	    }

	    private BranchGroup createBranchGraph() {
	        BranchGroup root = new BranchGroup();
	        
	     //Create the blue background
			Background background = new Background(new Color3f(1f,1f,1f));
			BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000); //background is inside this sphere
			background.setApplicationBounds(sphere);
			root.addChild(background); //add to the scene
			
		//Create a color that shines on object
			Color3f lightColor=new Color3f(.75f,.75f,.75f);
			//Light is 100m away from origin
			BoundingSphere boundSphere=new BoundingSphere(new Point3d(0,0,0),100);
			//Create a vector for lights direction
			Vector3f direction=new Vector3f(4.0f,-7.0f,-12.0f);
			//Actually create the light
			DirectionalLight light=new DirectionalLight(lightColor,direction);
			light.setInfluencingBounds(boundSphere);
			//add to group
			root.addChild(light);
			
	        TransformGroup spin = new TransformGroup();
	        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	        root.addChild(spin);
	        //object
	        Appearance ap = new Appearance();
	        ap.setPolygonAttributes(new PolygonAttributes(
	                PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0));
	        Shape3D shape = new Shape3D();
	        shape.setGeometry(mobius().getIndexedGeometryArray());
	        
	        Appearance a=createTextureAppearance();
	    
	       
	        shape.setAppearance(a);
	        //transform
	        Transform3D tr = new Transform3D();
	        tr.setScale(0.25);
	        tr.setTranslation(new Vector3d(0, -0.25, 0));
	        TransformGroup tg = new TransformGroup(tr);
	        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	        spin.addChild(tg);
	        tg.addChild(shape);
	        
	     // rotation
	        MouseRotate rotator = new MouseRotate(spin);
	        BoundingSphere bounds = new BoundingSphere();
	        rotator.setSchedulingBounds(bounds);
	        spin.addChild(rotator);
	        // translation
	        MouseTranslate translator = new MouseTranslate(spin);
	        translator.setSchedulingBounds(bounds);
	        spin.addChild(translator);
	        // zoom
	        MouseZoom zoom = new MouseZoom(spin);
	        zoom.setSchedulingBounds(bounds);
	        spin.addChild(zoom);
	       
	        return root;
	    }
	
	    private GeometryInfo mobius() {
	        int row = 100;
	        int col = 100;

	        int p = 4 * ((row - 1) * (col - 1));

	        IndexedQuadArray arr = new IndexedQuadArray(row * col,
	                IndexedQuadArray.COORDINATES | IndexedQuadArray.TEXTURE_COORDINATE_2,
	                p);

	        Point3d[] vertices = new Point3d[row * col];
	        int index = 0;

	        // Create the vertices
	        for (int i = 0; i < row; i++) {
	            for (int j = 0; j < col; j++) {
	                double u = i * (4 * (Math.PI)) / (row - 1);
	                double v = -0.3 + (j * (0.6 / (col - 1)));
	                double x = (1 + v * Math.cos(u / 2)) * Math.cos(u);
	                double y = (1 + v * Math.cos(u / 2)) * Math.sin(u);
	                double z = v * Math.sin(u / 2);
	                vertices[index] = new Point3d(x, y, z);
	                index++;
	            }
	        }

	        arr.setCoordinates(0, vertices);

	        index = 0;

	        // Set index for coordinates
	        for (int i = 0; i < row - 1; i++) {
	            for (int j = 0; j < col - 1; j++) {
	                arr.setCoordinateIndex(index, i * row + j);
	                index++;
	                arr.setCoordinateIndex(index, i * row + j + 1);
	                index++;
	                arr.setCoordinateIndex(index, (i + 1) * row + j + 1);
	                index++;
	                arr.setCoordinateIndex(index, (i + 1) * row + j);
	                index++;
	            }
	        }

	      
	        GeometryInfo g = new GeometryInfo(arr);
	        NormalGenerator norm = new NormalGenerator();
	        norm.generateNormals(g);
	        return g;
	    }


	
	Appearance createTextureAppearance(){  
		
		TexCoordGeneration textCoorder = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR,TexCoordGeneration.TEXTURE_COORDINATE_2);
	    Appearance ap = new Appearance();
	    String filename = "res/sky.jpg";
	    TextureLoader loader = new TextureLoader(filename, this);
	    ImageComponent2D image = loader.getImage();
	    if(image == null) {
	      System.out.println("can't find texture file.");
	    }
	    Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
	    image.getWidth(), image.getHeight());
	    texture.setImage(0, image);
	    texture.setEnable(true);
	    texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	    texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	    ap.setTexture(texture);
	    ap.setTexCoordGeneration(textCoorder);
	    return ap;
	  }

}
