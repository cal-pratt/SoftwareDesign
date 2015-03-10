package mainpkg;

import creaturepkg.MonkeyEnemy;
import creaturepkg.Player;
import menupkg.PauseMenu;
import menupkg.PlayerOverlay;
import menupkg.StartMenu;
import eventpkg.ButtonEventPublisher;
import eventpkg.IButtonEventListener;
import eventpkg.IKeyEventListener;
import eventpkg.KeyEventPublisher;
import objectpkg.APcObject3D;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import graphicspkg.GraphicsManager;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;


// Class definition ---------------------------------------------------------------------------- //
public class MainCore extends ACore {
    
    StartMenu startMenu;
    PauseMenu pauseMenu;
    PlayerOverlay overlay;
    
    MonkeyEnemy monkey;
    APcObject3D floor;
    
    GraphicsManager gm;
    Player player;
    
    private IKeyEventListener testCallback2;
    private IKeyEventListener testCallback1;
    private IKeyEventListener testCallback3;
    
    private IButtonEventListener continueCallback;
    private IButtonEventListener newCallback;
    private boolean newGamePressed = false; 
    private boolean continuePressed = false;
    private boolean allowUpdates = false;
    
    
    // Customize core setup -------------------------------------------------------------------- //
    public MainCore(){
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
        
        testCallback2 = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
            	player.doDamage(1);
            }
        };
        
        testCallback3 = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
                allowUpdates = false;
                pauseMenu.show();
            }
        };
        
        testCallback1 = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            {
                allowUpdates = false;
                startMenu.show();
                overlay.hide();
            }
        };
        
        input.getKeyInputEvent(GLFW_KEY_X).subscribe(testCallback1);
        input.getKeyInputEvent(GLFW_KEY_K).subscribe(testCallback2);
        input.getKeyInputEvent(GLFW_KEY_P).subscribe(testCallback3);
    	
        gm = new GraphicsManager(windowWidth, windowHeight);
        gm.add(floor = Object3DFactory.getSquare());
        
        player = new Player(gm, input);
        player.setPosY(-10);
        monkey = new MonkeyEnemy(gm, player);
        
        startMenu = new StartMenu(gm, input);
        pauseMenu = new PauseMenu(gm, input);

        newCallback = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, Object e) {
                newGamePressed = true;
            }
        };
        
        continueCallback = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, Object e) {
                continuePressed = true;
            }
        };
        
        startMenu.getNewgameButtonEvent().subscribe(newCallback);
        
        pauseMenu.getContinueButtonEvent().subscribe(continueCallback);
        
        overlay = new PlayerOverlay(gm, player);
        
        startMenu.show();
    }

    @Override
    protected void teardown() {
    	startMenu.delete();
        input.getKeyInputEvent(GLFW_KEY_X).unsubscribe(testCallback1);
    }

    @Override
    protected void resizeViewport() {
        glViewport(0, 0, (int)windowWidth, (int)windowHeight);
        gm.updateBounds(windowWidth, windowHeight);
    }

    @Override
    protected void updateActions(long timePassed) {
        
        if(allowUpdates){
            player.updateActions(timePassed);
            monkey.updateActions(timePassed);
        }
        
        if(newGamePressed){
            allowUpdates = true;
            newGamePressed = false;
            startMenu.hide();
            pauseMenu.hide();
            overlay.show();
        }else if(continuePressed){
            allowUpdates = true;
            continuePressed = false;
            startMenu.hide();
            pauseMenu.hide();
            overlay.show();
        }
    }

    @Override
    synchronized protected void draw(long timePassed) {
        glClearColor(0,0,0, 1.0f); 
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        
        Matrix4f projection = Matrix4f.perspective(35, windowRatio, 1, 1000).multiply(Matrix4f.rotate(0, 1, 0, 0));
        projection = projection.multiply(Matrix4f.rotate(-45, 1, 0, 0).multiply(
                Matrix4f.translate(-player.getPosX(), 50 - player.getPosY(), -50)));
        
        gm.setPcProjection(projection);
        
        startMenu.updateView(new Matrix4f());
        pauseMenu.updateView(new Matrix4f());
        overlay.updateView(new Matrix4f());
        
        floor.updateModel(Matrix4f.translate(0, 0, -2).multiply(
                Matrix4f.scale(100, 100, 100)));
        
        player.updateModel(Matrix4f.translate( 0, -4, 0).multiply(
                Matrix4f.rotate(0, 0, 1, 0).multiply(Matrix4f.scale(1f, 1f, 1f))));
        
        monkey.updateModel(Matrix4f.translate( 0, 0, 1).multiply(
                Matrix4f.rotate(0, 0, 1, 0).multiply(Matrix4f.scale(1f, 1f, 1f))));
        
        gm.draw();
    }
    
}
//---------------------------------------------------------------------------------------------- //
