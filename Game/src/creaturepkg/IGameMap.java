/** IGameMap
 * An interface outlining what each map should be able to do
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package creaturepkg;

import silvertiger.tutorial.lwjgl.math.Vector2f;
import graphicspkg.GraphicsManager;

public interface IGameMap {
	
    public void addMapElement(ACreature creature);
    public void addMapElement(Projectile proj);
    public void addMapElement(Portal portal);
    public void addMapElement(HealthItem healthItem);
    public void removeMapElement(ACreature creature);
    public void removeMapElement(Projectile proj);
    public void removeMapElement(Portal portal);
    public void removeMapElement(HealthItem healthItem);
    public void updateActions(Player player, float timepassed);
    public void updateModel();
    public GraphicsManager getGraphicsManager();
    public Vector2f getMaxBoundary();
    public Vector2f getMinBoundary();
	public void attachMapElements();
	public void detachMapElements();
	
	public boolean worpPossible();
	public Portal getExitPortal();
	public void delete();
}
