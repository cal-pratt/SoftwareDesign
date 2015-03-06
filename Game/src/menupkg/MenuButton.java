package menupkg;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import eventpkg.ButtonEventPublisher;
import eventpkg.IKeyEventListener;
import eventpkg.KeyEventPublisher;
import inputpkg.UserInput;
import graphicspkg.GraphicsManager;
import objectpkg.ATexObject2D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class MenuButton implements IMenuItem{
	private ButtonEventPublisher eventPublisher;
	private IKeyEventListener callBack;
	private GraphicsManager gm;
	
	private UserInput input;
	private ATexObject2D release, press;
	
	private float posX, posY;
	private float width, height;
	
	private boolean pressed = false;
	private boolean hidden = true;
	
	public MenuButton(GraphicsManager gm, UserInput input,
			ATexObject2D release, ATexObject2D press,
			float posX, float posY, float width, float height){
		
		eventPublisher = new ButtonEventPublisher();
		
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		
		this.gm = gm;
		
		this.input = input;
		this.release = release;
		this.press = press;

		gm.add(release);
		gm.add(press);
				
		callBack = new IKeyEventListener(){
			@Override
			public void actionPerformed(KeyEventPublisher sender, Object e) {
				checkInput((int)e);
			}
		};
	}
	
	private void checkInput(int action){
		if(input.getMouseX() > posX && input.getMouseX() < posX + width 
				&& input.getMouseY() > posY && input.getMouseY() < posY + height){
			if(input.getAction(GLFW_MOUSE_BUTTON_1) == GLFW_PRESS){
				setPressed(true);
			}
			else if(input.getAction(GLFW_MOUSE_BUTTON_1)  != GLFW_REPEAT){
				boolean prevPressed = pressed;
				setPressed(false);
				if(prevPressed){
					eventPublisher.publish(true);
				}
			}
		}
		else{
			setPressed(false);
		}
	}
	
	public ButtonEventPublisher getEventPublisher(){
		return eventPublisher;
	}
	
	private void setPressed(boolean pressed){
		this.pressed = pressed;
	}

	@Override
	public void setPosition(float posX, float posY){
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public void setSize(float width, float height ){
		this.width = width;
		this.height = height;
	}

	@Override
	public void updateView(Matrix4f m){
		m = m.multiply(Matrix4f.translate(posX, posY,0));
		if(hidden){
			press.setProjection(Matrix4f.scale(0, 0, 0));
			release.setProjection(Matrix4f.scale(0, 0, 0));
		}
		else if(pressed){
			press.setProjection(m.multiply(Matrix4f.scale(width/press.getWidth(), height/press.getHeight(), 1)));
			release.setProjection(Matrix4f.scale(0, 0, 0));
		}
		else{
			release.setProjection(m.multiply(Matrix4f.scale(width/release.getWidth(), height/release.getHeight(), 1)));
			press.setProjection(Matrix4f.scale(0, 0, 0));
		}
	}

	@Override
	public ATexObject2D getSprite(){
		return (pressed) ? press : release;
	}

	@Override
	public void delete() {
		gm.remove(release);
		gm.remove(press);
		input.getInputEvent(GLFW_MOUSE_BUTTON_1).unsubscribe(callBack);
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getX() {
		return posX;
	}

	@Override
	public float getY() {
		return posY;
	}

	@Override
	public void reset() {
		setPressed(false);
	}

	@Override
	public void hide() {
		if(!hidden){
			hidden = true;
			input.getInputEvent(GLFW_MOUSE_BUTTON_1).unsubscribe(callBack);
		}
	}

	@Override
	public void show() {
		if(hidden){
			hidden = false;
			input.getInputEvent(GLFW_MOUSE_BUTTON_1).subscribe(callBack);
		}
	}
}
