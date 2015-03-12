package creaturepkg;


public interface IMapElement {
    public void updateActions(float timepassed);
    public void updateModel();
	public float getPosX();
	public float getPosY();
	public void setPosX(float x);
	public void setPosY(float y);
	public void delete();
	public void attachMap(IMap owner);
    public void detachMap();
	public boolean isAlive();
}
