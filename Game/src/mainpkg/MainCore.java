package mainpkg;

import creaturepkg.ACreature;
import creaturepkg.GameMapManager;
import creaturepkg.IGameMap;
import creaturepkg.GameMap;
import creaturepkg.MonkeyEnemy;
import creaturepkg.Player;
import creaturepkg.Portal;
import creaturepkg.UfoEnemy;
import inputpkg.Key;
import menupkg.PauseMenu;
import menupkg.PlayerOverlay;
import menupkg.SkillMenu;
import menupkg.StartMenu;
import menupkg.MenuButton;
import eventpkg.GameEvents.*;
import objectpkg.APcObject3D;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;
import graphicspkg.GraphicsManager;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;


// Class definition ---------------------------------------------------------------------------- //
public class MainCore extends ACore {
    
	SkillMenu skillMenu;
    StartMenu startMenu;
    PauseMenu pauseMenu;
    PlayerOverlay overlay;
    
    GameMapManager gmm;
    
    private IKeyEventListener testCallback2 = new IKeyEventListener(){
        @Override 
        public void actionPerformed(KeyEventPublisher event, Key action){
        }
    };
    
    ACreature ufo;
    ACreature monkey2;
    APcObject3D floor;
    
    GraphicsManager gm;
    
    Player player;

    private IKeyEventListener testCallback3 = new IKeyEventListener(){
        @Override 
        public void actionPerformed(KeyEventPublisher event, Key action) 
        { 
            allowUpdates = false;
            pauseMenu.show();
        }
    };
    
    private IKeyEventListener testCallback4 = new IKeyEventListener(){
        @Override 
        public void actionPerformed(KeyEventPublisher event, Key action) 
        { 
        }
    };
    
    private IKeyEventListener testCallback1 = new IKeyEventListener(){
        @Override 
        public void actionPerformed(KeyEventPublisher event, Key action) 
        {
            allowUpdates = false;
            startMenu.show();
            overlay.hide();
        }
    };
    private IButtonEventListener continueCallback = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            newGamePressed = true;
        }
    };
    private IButtonEventListener newCallback  = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            continuePressed = true;
        }
    };
    
    IButtonEventListener closeSkillCallBack = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	skillPressed = true;
        }
    };
    IKeyEventListener openSkillCallBack = new IKeyEventListener(){
        @Override
        public void actionPerformed(KeyEventPublisher sender, Key e) {
        	if(e.getAction() == GLFW_PRESS){
        		skillPressed = true;
        	}
        }
    };
    
    
    private boolean newGamePressed = false; 
    private boolean continuePressed = false;
    private boolean allowUpdates = false;
    private boolean skillPressed = false;
    private boolean skillopen = false;
    
    
    // Customize core setup -------------------------------------------------------------------- //
    public MainCore(){
        windowWidth = 1800;
        windowHeight = 900;
        windowFullscreen = GL_FALSE;
        windowTitle = "Komputers r kewl";
        exitKey = GLFW_KEY_ESCAPE;
        threadSleepDuration = 5l;
    }
    
    // Core implementation --------------------------------------------------------------------- //
    @Override
    protected void startup() {

        glEnable(GL_CULL_FACE);
        glCullFace(GL_FRONT);
        glFrontFace(GL_CW);
        glDepthRange(0.0f, 1.0f);
        
        
        input.getKeyInputEvent(GLFW_KEY_X).subscribe(testCallback1);
        input.getKeyInputEvent(GLFW_KEY_1).subscribe(testCallback2);
        input.getKeyInputEvent(GLFW_KEY_P).subscribe(testCallback3);
    	
        gm = new GraphicsManager(windowWidth, windowHeight);
        gm.add(floor = Object3DFactory.getSquare());
        
        GameMap gameMaps0 = new GameMap(gm, new Vector2f(-50, -50), new Vector2f(50, 50));
        GameMap gameMaps1 = new GameMap(gm, new Vector2f(-50, -50), new Vector2f(50, 50));
        GameMap gameMaps2 = new GameMap(gm, new Vector2f(-50, -50), new Vector2f(50, 50));
        
        Portal portal0 = new Portal(gameMaps0);
        Portal portal1 = new Portal(gameMaps1);
        portal0.setExit(portal1);
        portal1.setExit(portal0);
        portal0.setPosition(new Vector2f(50, 0));
        portal1.setPosition(new Vector2f(-50, 0));
        
        Portal portal2 = new Portal(gameMaps0);
        Portal portal3 = new Portal(gameMaps2);
        portal2.setExit(portal3);
        portal3.setExit(portal2);
        portal2.setPosition(new Vector2f(0, 50));
        portal3.setPosition(new Vector2f(-50, 0));
        
        gameMaps0.addMapElement(portal0);
        gameMaps0.addMapElement(portal2);
        gameMaps2.addMapElement(portal3);
        gameMaps0.addMapElement(player = new Player(input));
        gameMaps0.addMapElement(ufo = new UfoEnemy(player));
        gameMaps0.addMapElement(new UfoEnemy(player));
        gameMaps1.addMapElement(portal1);
        gameMaps1.addMapElement(monkey2 = new MonkeyEnemy(player));
        
        gmm = new GameMapManager(player, gameMaps0);
        gmm.addMap(gameMaps1);
        gmm.addMap(gameMaps2);
        
        ufo.setPosition(new Vector2f(20, -5));
        player.setPosition(new Vector2f(0, -50));
        
        startMenu = new StartMenu(gm, input);
        startMenu.getNewgameButtonEvent().subscribe(newCallback);
        startMenu.show();
        
        pauseMenu = new PauseMenu(gm, input);
        pauseMenu.getContinueButtonEvent().subscribe(continueCallback);

        skillMenu = new SkillMenu(gm, input, player);
        skillMenu.getReturnToGameButtonEvent().subscribe(closeSkillCallBack);
        input.getKeyInputEvent(GLFW_KEY_M).subscribe(openSkillCallBack);
        
        overlay = new PlayerOverlay(gm, player);
        
        
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
        	gmm.updateActions(timePassed);
            if(skillPressed){
            	skillPressed = false;
            	if (skillopen){
            		skillMenu.hide();
            	}
            	else{
            		skillMenu.show();
            	}
            	skillopen = !skillopen;
            }
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

        
        Matrix4f projection = Matrix4f.perspective(35, windowRatio, 1, 1000);
        projection = projection.multiply(Matrix4f.rotate(-45, 1, 0, 0).multiply(
                Matrix4f.translate(-player.getPosition().x, 60 - player.getPosition().y, -70)));
        
        gm.setPcProjection(projection);
        
        startMenu.updateView(new Matrix4f());
        pauseMenu.updateView(new Matrix4f());
        overlay.updateView(new Matrix4f());
        skillMenu.updateView(new Matrix4f());
        
        floor.updateModel(Matrix4f.translate(0, 0, -2).multiply(
                Matrix4f.scale(100, 100, 100)));
        
        gmm.updateModel();
        
        gm.draw();
    }
    
}
//---------------------------------------------------------------------------------------------- //
