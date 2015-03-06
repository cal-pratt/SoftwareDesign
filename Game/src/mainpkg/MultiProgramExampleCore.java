package mainpkg;

import creaturepkg.MonkeyEnemy;
import creaturepkg.Player;
import menupkg.MenuSprite;
import menupkg.PlayerOverlay;
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
    PlayerOverlay overlay;
    MonkeyEnemy monkey;
    APcObject3D floor;
    GraphicsManager gm;
    
    Player player;
    
    float xplace = 0;
    float zplace = 0;
    float dx = 0;
    float dy = 0;
    
    private IKeyEventListener inputCallback;
    private IKeyEventListener testCallback;
    private IKeyEventListener inputCallback2;
    
    private float previousAngle = 0f;
    private float angle = 0f;
    private final float angelPerSecond = 50f;
    
    float playerX;
    float playerY;
    
    
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
        glFrontFace(GL_CW);
        glDepthRange(0.0f, 1.0f);
        
        inputCallback = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
            	player.doDamage(1);
            }
        };
        
        inputCallback2 = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
            	player.doDamage(-1);
            }
        };
        
        testCallback = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
                menu.show();
                overlay.hide();
            }
        };
        
        input.getInputEvent(GLFW_KEY_X).subscribe(testCallback);
        
        input.getInputEvent(GLFW_KEY_K).subscribe(inputCallback);
        input.getInputEvent(GLFW_KEY_I).subscribe(inputCallback2);
        
    	
        gm = new GraphicsManager();
        gm.add(floor = Object3DFactory.getSquare());
        
        floor.setView(new Matrix4f());
        
        player = new Player(gm, input);
        player.setPosY(-10);
        
        monkey = new MonkeyEnemy(gm, player);
        
        menu = new StartMenu(gm, input, 
        		0, 0, windowWidth, windowHeight,
        		0, 0, windowWidth, windowHeight);
        
        overlay = new PlayerOverlay(gm, player, 
        		0, 0, windowWidth, windowHeight,
        		0, 0, windowWidth, windowHeight);
        
        menu.show();
    }

    @Override
    protected void teardown() {
    	menu.delete();
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
            menu.hide();
            overlay.show();
        }
        menu.reset();
    }

    @Override
    synchronized protected void draw(long timePassed) {
        glClearColor(0,0,0, 1.0f); 
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        float lerpAngle = (1f - timePassed) * previousAngle + timePassed * angle;
        Matrix4f model = Matrix4f.rotate(lerpAngle, 0f, 0f, 1f);
        model = model.multiply(Matrix4f.rotate(previousAngle + timePassed * angle, .6f, 1f, 1f));;
        floor.setModel(Matrix4f.translate(0, 0, -2).multiply(Matrix4f.scale(100, 100, 100)));
        
        Matrix4f projection = Matrix4f.perspective(35, windowRatio, 1, 1000).multiply(Matrix4f.rotate(0, 1, 0, 0));
        projection = projection.multiply(Matrix4f.rotate(-45, 1, 0, 0).multiply(
        		Matrix4f.translate(-player.getPosX(), 50 - player.getPosY(), -50)));
        
        
        player.update(timePassed, projection, Matrix4f.translate( 0, -4, 0).multiply(
                Matrix4f.rotate(0, 0, 1, 0).multiply(Matrix4f.scale(1f, 1f, 1f))));
        monkey.update(timePassed, projection, Matrix4f.translate( 0, 4, 1).multiply(
                Matrix4f.rotate(0, 0, 1, 0).multiply(Matrix4f.scale(1f, 1f, 1f))));
        floor.setProjection(projection);
        
        menu.update();
        overlay.update();
        
        gm.draw();
    }
    

    
    public void updateCube(){
        xplace += dx;
        zplace -= dy;
    }
}
//---------------------------------------------------------------------------------------------- //
