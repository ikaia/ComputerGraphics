package assignment7;
//Ikaia Melton
import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedTriangleArray;
import javax.vecmath.Point3f;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;
import javax.vecmath.Vector3d;


//Define the Octahedron class by extending the IndexedTriangleArray
public class Octahedron extends IndexedTriangleArray {

	
	//Number of vertices private static 
    private static final int NumberofVertices = 6;
    //Number of faces private static 
    private static final int NumberofFaces = 8;
    
    
    //Index Array of the Data
    private static final int[] Index = {
            0, 1, 2,
            0, 2, 3,
            0, 3, 4,
            0, 4, 1,
            5, 2, 1,
            5, 3, 2,
            5, 4, 3,
            5, 1, 4
    };
    //Vertex Array of the Data
    private static final Point3f[] Vertex = {
            new Point3f(0, 0, 1),
            new Point3f(-1, 0, 0),
            new Point3f(0, -1, 0),
            new Point3f(1, 0, 0),
            new Point3f(0, 1, 0),
            new Point3f(0, 0, -1)
    };

    
    //The Template of Octahedron 
    public Octahedron() {
        super(NumberofVertices, GeometryArray.COORDINATES, 24);
        setCoordinates(0, Vertex);
        setCoordinateIndices(0, Index);
    }
}