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

import org.lwjgl.BufferUtils;

import silvertiger.tutorial.lwjgl.graphic.VertexArrayObject;
import silvertiger.tutorial.lwjgl.graphic.VertexBufferObject;
import silvertiger.tutorial.lwjgl.math.Vector4f;
import utilpkg.FileInterpreter;

class PcGraphicsBuffer implements IGraphicsBuffer {

    private VertexArrayObject vao;
    private VertexBufferObject vbo;
    private VertexBufferObject ebo;
    
    private int vertexCount;
    private int drawCount;
    
    public PcGraphicsBuffer(PcProgram program, String plyFilename){
        vao = new VertexArrayObject();
        vao.bind();
        
        ArrayList<Vector4f> verticies = new ArrayList<Vector4f>();
        ArrayList<Vector4f> colors = new ArrayList<Vector4f>();
        ArrayList<Integer> indicies = new ArrayList<Integer>();

        FileInterpreter.readPlyFile(plyFilename, verticies, colors, indicies);
        
        vertexCount = verticies.size();
        drawCount = indicies.size();
        
        FloatBuffer vectordata = BufferUtils.createFloatBuffer(vertexCount * 6);
        for(int index = 0; index < vertexCount; index++){
            Vector4f v = verticies.get(index);
            Vector4f c = colors.get(index);
            vectordata.put(v.x).put(v.y).put(v.z).put(c.x).put(c.y).put(c.z);
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
        program.pointVertexAttribute(posAttrib, 3, 6 * 4, 0);

        int colAttrib = program.getAttributeLocation("color");
        program.enableVertexAttribute(colAttrib);
        program.pointVertexAttribute(colAttrib, 3, 6 * 4, 3 * 4);
        
        
        ebo = new VertexBufferObject();
        ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
        ebo.uploadData(GL_ELEMENT_ARRAY_BUFFER, edata, GL_STATIC_DRAW);
        
    }
    
    public void delete(){
        vao.delete();
        ebo.delete();
        vbo.delete();
    }
    
    public void bindVertexArrayObject(){
        vao.bind();
    }
    
    public void draw(){
        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
    }
    
}
