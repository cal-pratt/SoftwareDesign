package creaturepkg;

public interface IMapElement {
	
	public void positionOnMap(float x, float y);
	public float getPosX();
	public float getPosY();
	public void setPosX(float x);
	public void setPosY(float y);
	public void delete();
}
