package creaturepkg;

import silvertiger.tutorial.lwjgl.math.Matrix4f;

public interface IMapElement {
    public void updateActions(float timepassed);
    public void updateModel(Matrix4f model);
	public float getPosX();
	public float getPosY();
	public void setPosX(float x);
	public void setPosY(float y);
	public void delete();
}
