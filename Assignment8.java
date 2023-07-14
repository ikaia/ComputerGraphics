package assignmentt8;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.Material;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class Assignment8 extends Applet {
    public static void main(String[] args){
        new MainFrame(new Assignment8(), 800, 700);      
    }

    @Override
    //Create Public Graphics Configuration with a Simple Universe
    public void init(){
        GraphicsConfiguration GraphicsConfiguration = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(GraphicsConfiguration);
        this.setLayout(new BorderLayout());
        this.add(canvas3D, BorderLayout.CENTER);
        SimpleUniverse SimpleUniverse = new SimpleUniverse(canvas3D);
        SimpleUniverse.getViewingPlatform().setNominalViewingTransform();
        BranchGroup BranchGroup = createSceneGraph();
        BranchGroup.compile();
        SimpleUniverse.addBranchGraph(BranchGroup);
    }
    //Create Private Branch Group to create ScenceGraph
    private BranchGroup createSceneGraph(){
        BranchGroup Root = new BranchGroup();       
        Shape3D Shape3D = new Shape3D();
        Shape3D.setGeometry(mobius().getIndexedGeometryArray());
        //Scaling transform
        Transform3D Transform3D = new Transform3D();
        Transform3D.setScale(0.5);
        //Twist transform group object
        TransformGroup Twist = new TransformGroup();
        Twist.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Root.addChild(Twist);
        //Set appearance object
        Appearance Appearance = new Appearance();
        PointAttributes pa = new PointAttributes(10, true);
        Appearance.setPointAttributes(pa);
        Appearance.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_BACK, 0));
        //Set materials object
        Material Material = new Material();
        Material.setLightingEnable(true);
        Material.setShininess(30);
        Appearance.setMaterial(Material);
        TransformGroup TransformGroup = new TransformGroup(Transform3D);
        TransformGroup.addChild(Shape3D);
        Twist.addChild(TransformGroup);
        Shape3D.setAppearance(Appearance);                

        //Set rotation
        Alpha alpha = new Alpha(-1, 6000);
        RotationInterpolator rotate = new RotationInterpolator(alpha, Twist);
        BoundingSphere bounds = new BoundingSphere();
        rotate.setSchedulingBounds(bounds);
        Twist.addChild(rotate);
        //Create a background
        Background background = new Background(1.0f, 1.0f, 1.0f);
        background.setApplicationBounds(bounds);
        Root.addChild(background);
        //Setting the light
        AmbientLight light = new AmbientLight(true, new Color3f(Color.BLACK));
        light.setInfluencingBounds(bounds);
        Root.addChild(light);
        PointLight PointLight = new PointLight(new Color3f(Color.white),
        new Point3f(0.5f,0.5f,1f),
        new Point3f(1f,0.2f,0f));
        PointLight.setInfluencingBounds(bounds);
        Root.addChild(PointLight);
        return Root;
    }

    //Create the Mobius shape assignment is needed
    private GeometryInfo mobius()
      {
        int m = 100; //Number of row 
        int n = 100; //Number of colomun 
        int p = 4*((m-1)*(n-1)); //faces x points per faces of Mobius Shape
        IndexedQuadArray iqa = new IndexedQuadArray(m*n, 
                      GeometryArray.COORDINATES, p);
        Point3d[] vertices = new Point3d[m*n];
        int index = 0;
        //Create vertices
        for(int i = 0; i < m; i++)
        {
            for(int j = 0; j < n; j++)
             {//Input each equations into doubles variables
                double u = i * (4*(Math.PI))/(m - 1);
                double v = -0.3 + (j * (0.6/(n-1)));
                double x=(1+v*Math.cos(u/2))*Math.cos(u); //The Equations that was provided
                double y=(1+v*Math.cos(u/2))*Math.sin(u);
                double z=v*Math.sin(u/2);
                vertices[index]=new Point3d(x,y,z);
                index++;
            }
         }
        iqa.setCoordinates(0, vertices);
        index = 0;
                for(int i = 0; i < m-1; i++){
            for(int j = 0; j < n-1; j++){
                iqa.setCoordinateIndex(index, i*m+j);
                index++;
                iqa.setCoordinateIndex(index, i*m+j+1);
                index++;
                iqa.setCoordinateIndex(index, (i+1)*m+j+1);
                index++;
                iqa.setCoordinateIndex(index, (i+1)*m+j);
                index++;
            }
        }

        //Create geometry info & generate normals for the Mobius shape
        GeometryInfo gi = new GeometryInfo(iqa);
        NormalGenerator ng = new NormalGenerator();
        ng.generateNormals(gi);
        return gi;
    }
}