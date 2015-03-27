package mainpkg;

import creaturepkg.ACreature;
import creaturepkg.FirstBoss;
import creaturepkg.GameMapManager;
import creaturepkg.IGameMap;
import creaturepkg.GameMap;
import creaturepkg.MapElementState;
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
import silvertiger.tutorial.lwjgl.math.Vector3f;
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
    GraphicsManager gm;
    APcObject3D floor;
    APcObject3D border;
    float floorspin = 0;
    
    Player player;

    private boolean newGamePressed = false; 
    private boolean continuePressed = false;
    private boolean allowUpdates = false;
    private boolean skillPressed = false;
    private boolean skillopen = false;
    
    private IKeyEventListener testCallback2 = new IKeyEventListener(){
        @Override 
        public void actionPerformed(KeyEventPublisher event, Key action){
        }
    };
    
    
    private IKeyEventListener testCallback3 = new IKeyEventListener(){
        @Override 
        public void actionPerformed(KeyEventPublisher event, Key action) 
        { 
            allowUpdates = false;
            pauseMenu.show();
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
        	continuePressed = true;
        }
    };
    private IButtonEventListener newCallback  = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	newGamePressed  = true;
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
        glCullFace(GL_BACK);
        glFrontFace(GL_CCW);
        glDepthRange(0.0f, 1.0f);

        newGamePressed = false; 
        continuePressed = false;
        allowUpdates = false;
        skillPressed = false;
        skillopen = false;
        
        gm = new GraphicsManager(windowWidth, windowHeight);
        gm.add(floor = Object3DFactory.getStarsphere(), false);
        gm.add(border = Object3DFactory.getBorderSquare(), true);
        
        GameMap gameMaps[] = new GameMap[10];
        for(int i = 0; i < 10 ; i++){
        	gameMaps[i] = new GameMap(gm, new Vector2f(-50, -50), new Vector2f(50, 50));
        }
        
        gameMaps[0].addMapElement(player = new Player(input));
        player.setPosition(new Vector2f(0, -50));
        
        for(int i = 0; i < 9 ; i++){
        	Portal portalA = new Portal(gameMaps[i]);
            Portal portalB = new Portal(gameMaps[i+1]);
            portalA.setExit(portalB);
            portalB.setExit(portalA);
            portalA.setPosition(new Vector2f(0, 50));
            portalB.setPosition(new Vector2f(0, -50));
            gameMaps[i].addMapElement(portalA);
            gameMaps[i+1].addMapElement(portalB);
        }
        
        FirstBoss fb = new FirstBoss(player);
        gameMaps[0].addMapElement(fb);
        
        
        for(int i = 1; i < 4; i++){
        	for(int j = 1; j < i + 2; j++){
        		MonkeyEnemy monkey = new MonkeyEnemy(player);
        		gameMaps[i].addMapElement(monkey);
        		monkey.setPosition(new Vector2f(((float)j)/2*-20 + ((float)j - ((float)i)/2)*20, 0));
        	}
        }
        for(int i = 0; i < 4; i++){
        	for(int j = 1; j < i + 2; j++){
        		UfoEnemy ufo = new UfoEnemy(player);
        		gameMaps[4+i].addMapElement(ufo);
        		ufo.setPosition(new Vector2f(((float)j)/2*-20 + ((float)j - ((float)i)/2)*20, 0));
        	}
        }
        

        for(int i = 1; i < 2; i++){
        	for(int j = 1; j < i*10 + 2; j++){
        		UfoEnemy ufo = new UfoEnemy(player);
        		gameMaps[8+i].addMapElement(ufo);
        		ufo.setPosition(new Vector2f(((float)j)/2*-20 + ((float)j - ((float)i*10)/2)*20, 0));
        	}
        }
		UfoEnemy ufo = new UfoEnemy(player);
		gameMaps[8].addMapElement(ufo);
		ufo.setPosition(new Vector2f(((float)1)/2*-20 + ((float)1 - ((float)1*10)/2)*20, 0));
		ufo.fireIncrement = 0;
        
        gmm = new GameMapManager(player, gameMaps[0]);
        for(int i = 1; i < 10 ; i++){
            gmm.addMap(gameMaps[i]);
        }
        
        startMenu = new StartMenu(gm, input);
        pauseMenu = new PauseMenu(gm, input);
        skillMenu = new SkillMenu(gm, input, player);
        overlay = new PlayerOverlay(gm, player);
        

        input.getKeyInputEvent(GLFW_KEY_X).subscribe(testCallback1);
        input.getKeyInputEvent(GLFW_KEY_1).subscribe(testCallback2);
        input.getKeyInputEvent(GLFW_KEY_P).subscribe(testCallback3);
        skillMenu.getReturnToGameButtonEvent().subscribe(closeSkillCallBack);
        input.getKeyInputEvent(GLFW_KEY_M).subscribe(openSkillCallBack);
        pauseMenu.getContinueButtonEvent().subscribe(continueCallback);
        startMenu.getNewgameButtonEvent().subscribe(newCallback);

        startMenu.show();


        
    }

    @Override
    protected void teardown() {
        input.getKeyInputEvent(GLFW_KEY_X).unsubscribe(testCallback1);
        input.getKeyInputEvent(GLFW_KEY_1).unsubscribe(testCallback2);
        input.getKeyInputEvent(GLFW_KEY_P).unsubscribe(testCallback3);
        skillMenu.getReturnToGameButtonEvent().unsubscribe(closeSkillCallBack);
        input.getKeyInputEvent(GLFW_KEY_M).unsubscribe(openSkillCallBack);
        pauseMenu.getContinueButtonEvent().unsubscribe(continueCallback);
        startMenu.getNewgameButtonEvent().unsubscribe(newCallback);
    	startMenu.delete();
    	pauseMenu.delete();
    	skillMenu.delete();
    	overlay.delete();
    	gmm.delete();
    	gm.delete();
    	player.delete();
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
        
        if(player.getState() == MapElementState.DEAD){
        	resetGame();
        }
    }

    @Override
    protected void draw(long timePassed) {
        glClearColor(0,0,0, 1.0f); 
        
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        
        Matrix4f projection = Matrix4f.perspective(35, windowRatio, 1, 1000);
        projection = projection.multiply(Matrix4f.rotate(-45, 1, 0, 0).multiply(
                Matrix4f.translate(-player.getPosition().x, 60 - player.getPosition().y, -70)));
        
        gm.setCameraProjection(projection);
        
        startMenu.updateView(new Matrix4f());
        pauseMenu.updateView(new Matrix4f());
        overlay.updateView(new Matrix4f());
        skillMenu.updateView(new Matrix4f());
        floorspin += timePassed*.1f;
        
        floor.updateModel(Matrix4f.translate(0, 10, -50).multiply(
                Matrix4f.scale(20f + 2.0f*(float)Math.sin(floorspin/1000f), 
                		20+ 2.0f*(float)Math.sin(floorspin/1000f), 0).multiply(
                        		Matrix4f.rotate(floorspin, 1,0, 0))));
        border.updateModel(Matrix4f.translate(0, 0,0).multiply(
                Matrix4f.scale(12,12,12).multiply(Matrix4f.rotate(0, 1,0, 0))));
        
        gmm.updateModel();
        gm.setLightPos(new Vector3f(100,1000,1000));
        gm.draw();
    }
    
}
//---------------------------------------------------------------------------------------------- //
