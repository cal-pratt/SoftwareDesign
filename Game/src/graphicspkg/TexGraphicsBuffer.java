package graphicspkg;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import silvertiger.tutorial.lwjgl.graphic.Texture;
import silvertiger.tutorial.lwjgl.graphic.VertexArrayObject;
import silvertiger.tutorial.lwjgl.graphic.VertexBufferObject;

class TexGraphicsBuffer implements IGraphicsBuffer {

    private VertexArrayObject vao;
    private VertexBufferObject vbo;
    private VertexBufferObject ebo;
    private Texture texture;
    
    public TexGraphicsBuffer(TexProgram program, String pngFilename){
        
        texture = Texture.loadTexture(pngFilename);
        texture.bind();
        
        vao = new VertexArrayObject();
        vao.bind();
        
        long window = GLFW.glfwGetCurrentContext();
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
        int width = widthBuffer.get();
        int height = heightBuffer.get();

        /* Get coordinates for centering the texture on screen */
        float x1 = (width - texture.getWidth()) / 2f;
        float y1 = (height - texture.getHeight()) / 2f;
        float x2 = x1 + texture.getWidth();
        float y2 = y1 + texture.getHeight();

        /* Vertex data */
        FloatBuffer vertices = BufferUtils.createFloatBuffer(4 * 7);
        vertices.put(0).put(0).put(1f).put(1f).put(1f).put(0f).put(0f);
        vertices.put(texture.getWidth()).put(0).put(1f).put(1f).put(1f).put(1f).put(0f);
        vertices.put(texture.getWidth()).put(texture.getHeight()).put(1f).put(1f).put(1f).put(1f).put(1f);
        vertices.put(0).put(texture.getHeight()).put(1f).put(1f).put(1f).put(0f).put(1f);
        vertices.flip();

        /* Generate Vertex Buffer Object */
        vbo = new VertexBufferObject();
        vbo.bind(GL_ARRAY_BUFFER);
        vbo.uploadData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        /* Element data */
        IntBuffer elements = BufferUtils.createIntBuffer(2 * 3);
        elements.put(2).put(1).put(0);
        elements.put(0).put(3).put(2);
        elements.flip();

        /* Generate Element Buffer Object */
        ebo = new VertexBufferObject();
        ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
        ebo.uploadData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);
        
        program.use();
        
        /* Specify Vertex Pointer */
        int posAttrib = program.getAttributeLocation("position");
        program.enableVertexAttribute(posAttrib);
        program.pointVertexAttribute(posAttrib, 2, 7 * 4, 0);

        /* Specify Color Pointer */
        int colAttrib = program.getAttributeLocation("color");
        program.enableVertexAttribute(colAttrib);
        program.pointVertexAttribute(colAttrib, 3, 7 * 4, 2 * 4);

        /* Specify Texture Pointer */
        int texAttrib = program.getAttributeLocation("texcoord");
        program.enableVertexAttribute(texAttrib);
        program.pointVertexAttribute(texAttrib, 2, 7 * 4, 5 * 4);
        
    }
    
    public void delete(){
        vao.delete();
        ebo.delete();
        vbo.delete();
    }
    
    public void bind(){
        vao.bind();
        texture.bind();
        
    }
    
    public void draw(){
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
    }
    
}
