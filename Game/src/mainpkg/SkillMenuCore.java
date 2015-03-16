package mainpkg;


//3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import inputpkg.Key;
import creaturepkg.Player;
import menupkg.SkillMenu;
import menupkg.MenuButton;
import eventpkg.GameEvents.*;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import graphicspkg.GraphicsManager;

// Class definition ---------------------------------------------------------------------------- //
public class SkillMenuCore extends ACore {
    
    SkillMenu skillMenu;
    GraphicsManager gm;
    Player player;
    // Input is part of ACore
    
    private IButtonEventListener closeMenuCallBack;
    private boolean closePressed = false;
    
    
    // Customize core setup -------------------------------------------------------------------- //
    public SkillMenuCore(){
        // TODO Edit for your resolution
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
        
        gm = new GraphicsManager(windowWidth, windowHeight);
        
       // player = new Player(gm, input);
        
        // TODO update this constructor with any other values you may need.
        skillMenu = new SkillMenu(gm, input, player);

        closeMenuCallBack = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
                closePressed = true;
                skillMenu.hide();
            }
        };
        
        skillMenu.getReturnToGameButtonEvent().subscribe(closeMenuCallBack);
        
        // TODO subscribe to a SkillMenu Button event with the close closeCallBack
        
        // TODO Create other callbacks you may need and subscribe them to publishers
        
       // skillMenu.show();
    }

    @Override
    protected void teardown() {
        skillMenu.delete();
        skillMenu.getReturnToGameButtonEvent().unsubscribe(closeMenuCallBack);
    }

    @Override
    protected void resizeViewport() {
        glViewport(0, 0, (int)windowWidth, (int)windowHeight);
        gm.updateBounds(windowWidth, windowHeight);
    }

    @Override
    protected void updateActions(long timePassed) {
        
        player.updateActions(timePassed);
        // If our close button event got fired then we hide the skill menu
        if(closePressed){
            skillMenu.hide();
        }
    }

    @Override
    synchronized protected void draw(long timePassed) {
        glClearColor(0,0,0, 1.0f); 
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Any mesh in blender is centered at position 0,0,0
        // In order to draw it elsewhere we create a matrix which will  scale, rotate or move the player
        // Every point in the blender file has this translation applied to it
        player.updateModel(Matrix4f.translate( 0, -4, 0).multiply(
                Matrix4f.rotate(0, 0, 1, 0).multiply(Matrix4f.scale(1f, 1f, 1f))));
        
        // projection is a matrix used to center the model infront of the camera
        // there is only one 3D shader program so we only have to update one camera
        Matrix4f projection = Matrix4f.perspective(35, windowRatio, 1, 1000).multiply(Matrix4f.rotate(0, 1, 0, 0));
        projection = projection.multiply(Matrix4f.rotate(-45, 1, 0, 0).multiply(
                Matrix4f.translate(-player.getPosX(), 50 - player.getPosY(), -50)));
        
        gm.setPcProjection(projection);
        
        // The view matrix scales images into a box in-front of the screen. 
        // An identity is used because we don't want to scale, rotate or move the menu
        skillMenu.updateView(new Matrix4f());
        
        // graphics manager looks at these values what we set in the models and updates openGL
        gm.draw();
    }
    
}
