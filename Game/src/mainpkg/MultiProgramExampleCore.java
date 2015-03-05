package mainpkg;

import menupkg.MenuSprite;
import menupkg.StartMenu;

import eventpkg.IKeyEventListener;
import eventpkg.KeyEventPublisher;
import objectpkg.APcObject3D;
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
    APcObject3D skyCube;
    APcObject3D floor;
    GraphicsManager gm;
    
    float xplace = 0;
    float zplace = 0;
    float dx = 0;
    float dy = 0;
    
    private IKeyEventListener inputCallback;
    private IKeyEventListener testCallback;
    
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
    
    // Core implementation --------------------------------------------------------------------- //
    @Override
    protected void startup() {

        glEnable(GL_CULL_FACE);
        glCullFace(GL_FRONT);
        glFrontFace(GL_CCW);
        glDepthRange(0.0f, 1.0f);
        
        inputCallback = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
                updateCubeVelocity();
            }
        };
        
        testCallback = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
                menu.show();
            }
        };
        
        input.getInputEvent(GLFW_KEY_A).subscribe(inputCallback);
        input.getInputEvent(GLFW_KEY_D).subscribe(inputCallback);
        input.getInputEvent(GLFW_KEY_S).subscribe(inputCallback);
        input.getInputEvent(GLFW_KEY_W).subscribe(inputCallback);
        input.getInputEvent(GLFW_KEY_X).subscribe(testCallback);
    	
        gm = new GraphicsManager();
        
        gm.add(cube = Object3DFactory.getCube());
        gm.add(skyCube = Object3DFactory.getCube());
        gm.add(floor = Object3DFactory.getSquare());

        menu = new StartMenu(gm, input, 
        		0, 0, windowWidth, windowHeight,
        		0, 0, windowWidth, windowHeight);
        
        menu.show();

        cube.setView(Matrix4f.scale(0, 0, 0));
        skyCube.setView(Matrix4f.scale(0, 0, 0));
        floor.setView(Matrix4f.scale(0, 0, 0));
        
        cube.setProjection(Matrix4f.scale(0, 0, 0));
        skyCube.setProjection(Matrix4f.scale(0, 0, 0));
        floor.setProjection(Matrix4f.scale(0, 0, 0));
        
    }

    @Override
    protected void teardown() {
    	menu.delete();
    	input.getInputEvent(GLFW_KEY_A).unsubscribe(inputCallback);
        input.getInputEvent(GLFW_KEY_D).unsubscribe(inputCallback);
        input.getInputEvent(GLFW_KEY_S).unsubscribe(inputCallback);
        input.getInputEvent(GLFW_KEY_W).unsubscribe(inputCallback);
        input.getInputEvent(GLFW_KEY_X).unsubscribe(testCallback);
    }

    @Override
    protected void resizeViewport() {
        glViewport(0, 0, (int)windowWidth, (int)windowHeight);
    }

    @Override
    protected void updateLogic(long timePassed) {
        previousAngle = angle;
        angle += timePassed * angelPerSecond/1000.0;
        
        updateCube();
        
        if(menu.isNewPressed()){
        	Matrix4f perspective = Matrix4f.perspective(20, windowRatio, 1, 600);
            cube.setProjection(perspective);
            skyCube.setProjection(perspective);
            floor.setProjection(perspective);
            menu.hide();
        }
        menu.reset();
    }

    @Override
    synchronized protected void draw(long timePassed) {
        glClearColor(.2f,.4f,.6f, 1.0f); 
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        float lerpAngle = (1f - timePassed) * previousAngle + timePassed * angle;
        Matrix4f model = Matrix4f.rotate(lerpAngle, 0f, 0f, 1f);
        model = model.multiply(Matrix4f.rotate(previousAngle + timePassed * angle, .6f, 1f, 1f));
        
        cube.setModel(Matrix4f.translate(0, 0, -10).multiply(Matrix4f.scale(.5f, .5f, .5f).multiply(model)));
        skyCube.setModel(Matrix4f.translate(30, 5f, 10).multiply(Matrix4f.scale(6f, 6f, 6f).multiply(model)));
        floor.setModel(Matrix4f.translate(0f, -1, -10f).multiply(Matrix4f.rotate(90, 1f, 0f, 0f).multiply(Matrix4f.scale(10f,1000f,1f))));
        
        Matrix4f projection = Matrix4f.perspective(20, windowRatio, 1, 10000).multiply(Matrix4f.rotate(xplace*60, 0, 1, 0));
        projection = projection.multiply(Matrix4f.rotate(xplace*60, 0, 1, 0).multiply(Matrix4f.translate(0, 0, zplace)));
        
        cube.setProjection(projection);
        skyCube.setProjection(projection);
        floor.setProjection(projection);
        
        
        menu.update();
        
        gm.draw();
    }
    

    
    public void updateCube(){
        xplace += dx;
        zplace -= dy;
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
}
//---------------------------------------------------------------------------------------------- //
