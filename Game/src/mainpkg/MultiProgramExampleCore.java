package mainpkg;

import inputpkg.UserInput;

import java.nio.IntBuffer;

import menupkg.Menu;
import menupkg.MenuButton;
import menupkg.MenuSprite;
import menupkg.StartMenu;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import eventpkg.IKeyEventListener;
import eventpkg.KeyEventPublisher;
import objectpkg.APcObject3D;
import objectpkg.ATexObject2D;
import objectpkg.Object2DFactory;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import graphicspkg.GraphicsManager;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;


// Class definition ---------------------------------------------------------------------------- //
public class MultiProgramExampleCore extends ACore {
    
    StartMenu menu;
    APcObject3D cube;
    GraphicsManager gm;
    
    float xplace = 0;
    float yplace = -10f;
    float dx = 0;
    float dy = 0;
    
    private float previousAngle = 0f;
    private float angle = 0f;
    private final float angelPerSecond = 50f;
    
    
    // Customize core setup -------------------------------------------------------------------- //
    public MultiProgramExampleCore(){
        windowWidth = 1920;
        windowHeight = 1080;
        windowFullscreen = GL_FALSE;
        windowTitle = "Game Core";
        exitKey = GLFW_KEY_ESCAPE;
        threadSleepDuration = 10l;
    }
    
    public void updateCube(){
        xplace += dx;
        if (xplace < -windowRatio*2) xplace = -windowRatio*2;
        if (xplace > windowRatio*2) xplace = windowRatio*2;
        yplace += dy;
        //if (yplace < -1) yplace = -1;
        if (yplace > -1f) yplace = -1f;
    }
    
    public void updateCubeVelocity(){
        float x = 0;
        float y = 0;
        if(input.getAction(GLFW_KEY_A) == GLFW_PRESS || input.getAction(GLFW_KEY_A) == GLFW_REPEAT){
            x -= .05f;
        }
        if(input.getAction(GLFW_KEY_D) == GLFW_PRESS || input.getAction(GLFW_KEY_D) == GLFW_REPEAT){
            x += .05f;
        }
        if(input.getAction(GLFW_KEY_S) == GLFW_PRESS || input.getAction(GLFW_KEY_S) == GLFW_REPEAT){
            y += 2f;
        }
        if(input.getAction(GLFW_KEY_W) == GLFW_PRESS || input.getAction(GLFW_KEY_W) == GLFW_REPEAT){
            y -= 2f;
        }
        dx = x;
        dy = y;
    }
    
    // Core implementation --------------------------------------------------------------------- //
    @Override
    protected void startup() {
        input.getInputEvent(GLFW_KEY_A).subscribe( new IKeyEventListener(){
            @Override public void actionPerformed(KeyEventPublisher event, Object action) { updateCubeVelocity();}});
        
        input.getInputEvent(GLFW_KEY_D).subscribe( new IKeyEventListener(){
            @Override public void actionPerformed(KeyEventPublisher event, Object action) { updateCubeVelocity();}});
        
        input.getInputEvent(GLFW_KEY_S).subscribe( new IKeyEventListener(){
            @Override public void actionPerformed(KeyEventPublisher event, Object action) { updateCubeVelocity();}});
        
        input.getInputEvent(GLFW_KEY_W).subscribe( new IKeyEventListener(){
            @Override public void actionPerformed(KeyEventPublisher event, Object action) { updateCubeVelocity();}});
        
    	glEnable(GL_CULL_FACE);
    	glCullFace(GL_FRONT);
    	glFrontFace(GL_CCW);
        glDepthRange(0.0f, 1.0f);
    	
        gm = new GraphicsManager();
        cube = Object3DFactory.getCube();
        gm.add(cube = Object3DFactory.getCube());
        
        long window = GLFW.glfwGetCurrentContext();
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
        float width = widthBuffer.get();
        float height = heightBuffer.get();

        menu = new StartMenu(gm, input, 
        		0, 0, width, height,
        		0, 0, width, height);

        cube.setView(Matrix4f.scale(0, 0, 0));
        cube.setProjection(Matrix4f.scale(0, 0, 0));
        
    }

    @Override
    protected void teardown() {
    	menu.delete();
    }

    @Override
    protected void resizeViewport(int width, int height) {
        glViewport(0, 0, width, height);
        if(menu.isNewPressed()){
        	menu.delete();
            cube.setProjection(Matrix4f.perspective(20, windowRatio, 1, 600));
        	menu.add(new MenuSprite(gm, Object2DFactory.getBanner(), 0, 0, width, height));
        	menu.clearNewPressed();
        }
    }

    @Override
    protected void updateLogic(long timePassed) {
        previousAngle = angle;
        angle += timePassed * angelPerSecond/1000.0;
        updateCube();
    }

    @Override
    synchronized protected void draw(long timePassed) {
        glClearColor(1,1,1, 1.0f); 
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        float lerpAngle = (1f - timePassed) * previousAngle + timePassed * angle;
        Matrix4f model = Matrix4f.rotate(lerpAngle, 0f, 0f, 1f);
        model = model.multiply(Matrix4f.rotate(previousAngle + timePassed * angle, .6f, 1f, 1f));
        
        cube.setModel(Matrix4f.translate(xplace, 0, yplace).multiply(Matrix4f.scale(.5f, .5f, .5f).multiply(model)));
        
        menu.update();
        
        gm.draw();
    }
}
//---------------------------------------------------------------------------------------------- //
