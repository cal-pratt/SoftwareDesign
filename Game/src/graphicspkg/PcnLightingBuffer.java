package graphicspkg;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import silvertiger.tutorial.lwjgl.graphic.VertexArrayObject;
import silvertiger.tutorial.lwjgl.graphic.VertexBufferObject;
import silvertiger.tutorial.lwjgl.math.Vector3f;
import silvertiger.tutorial.lwjgl.math.Vector4f;
import utilpkg.FileInterpreter;

class PcnLightingBuffer implements IGraphicsBuffer {

    private VertexArrayObject vao;
    private VertexBufferObject vbo;
    private VertexBufferObject ebo;
    
    private int vertexCount;
    private int drawCount;
    
    public PcnLightingBuffer(PcnLightingProgram program, String plyFilename){
        vao = new VertexArrayObject();
        vao.bind();
        
        ArrayList<Vector4f> verticies = new ArrayList<>();
        ArrayList<Vector4f> colors = new ArrayList<>();
        ArrayList<Integer> indicies = new ArrayList<>();

        FileInterpreter.readPlyFile(plyFilename, verticies, colors, indicies);
        
        vertexCount = verticies.size();
        drawCount = indicies.size();
        
    	List<Integer> normalsPerVertex = new ArrayList<>();
    	for(int i = 0; i < vertexCount; i++){
    		normalsPerVertex.add(0);
    	}
    	List<Vector3f> normals = new ArrayList<>(vertexCount);
    	for(int i = 0; i < vertexCount; i++){
    		normals.add(new Vector3f());
    	}
        
        for (int i = 0; i < indicies.size(); i += 3)
    	{
        	
    		Vector4f a4 = verticies.get(indicies.get(i + 1)).subtract(
    				verticies.get(indicies.get(i)));
    		
    		Vector4f b4 = verticies.get(indicies.get(i + 2)).subtract(
    				verticies.get(indicies.get(i)));
    		
    		Vector3f a3 = new Vector3f(a4.x, a4.y, a4.z);
    		Vector3f b3 = new Vector3f(b4.x, b4.y, b4.z);
    		
    		Vector3f currNormal = a3.cross(b3).normalize();
    		
			for (int j = 0; j < 3; j++)
			{
				int index = indicies.get(i + j);
				normalsPerVertex.set(index, normalsPerVertex.get(index) + 1);
				if (normalsPerVertex.get(index) == 1)
				{
					normals.set(index, currNormal);
				}
				else
				{
					float normalWeight = 1.0f / normalsPerVertex.get(index);
					normals.set(index, 
							normals.get(index).scale(1.0f - normalWeight).add(
									currNormal.scale(normalWeight)).normalize());
				}
			}
    	}
        
        FloatBuffer vectordata = BufferUtils.createFloatBuffer(vertexCount * 9);
        for(int index = 0; index < vertexCount; index++){
            Vector4f v = verticies.get(index);
            Vector4f c = colors.get(index);
            Vector3f n = normals.get(index);
            vectordata.put(v.x).put(v.y).put(v.z).put(c.x).put(c.y).put(c.z).put(n.x).put(n.y).put(n.z);
        }
        vectordata.flip();
        
        IntBuffer edata = BufferUtils.createIntBuffer(indicies.size());
        for(int index : indicies){
            edata.put(index);
        }
        edata.flip();
        
        vbo = new VertexBufferObject();
        vbo.bind(GL_ARRAY_BUFFER);
        vbo.uploadData(GL_ARRAY_BUFFER, vectordata, GL_STATIC_DRAW);
        
        program.use();
        
        int posAttrib = program.getAttributeLocation("position");
        program.enableVertexAttribute(posAttrib);
        program.pointVertexAttribute(posAttrib, 3, 9 * 4, 0);

        int colAttrib = program.getAttributeLocation("color");
        program.enableVertexAttribute(colAttrib);
        program.pointVertexAttribute(colAttrib, 3, 9 * 4, 3 * 4);
        
        int normalAttrib = program.getAttributeLocation("normal");
        program.enableVertexAttribute(normalAttrib);
        program.pointVertexAttribute(normalAttrib, 3, 9 * 4, 6 * 4);
        
        
        ebo = new VertexBufferObject();
        ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
        ebo.uploadData(GL_ELEMENT_ARRAY_BUFFER, edata, GL_STATIC_DRAW);
        
    }
    
    public void delete(){
        vao.delete();
        ebo.delete();
        vbo.delete();
    }
    
    public void bind(){
        vao.bind();
    }
    
    public void draw(){
        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
    }
    
}
