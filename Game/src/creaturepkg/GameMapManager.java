/** GameMapManager
 * Container for Game Maps, which handles their interactions
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package creaturepkg;

import graphicspkg.GraphicsManager;

import java.util.ArrayList;
import java.util.List;

import objectpkg.APcObject3D;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public class GameMapManager {
	private List<IGameMap> maps = new ArrayList<>();
    int mapIndex = 0;
    
    Player player;
    APcObject3D floor;
	
    public GameMapManager(Player player, IGameMap startMap){
    	this.player = player;
    	maps.add(startMap);
    	mapIndex = 0;
    	maps.get(mapIndex).attachMapElements();
    	maps.get(mapIndex).updateActions(player, 0);
    }
    
    public void addMap(IGameMap map){
    	this.maps.add(map);
    }
    
    
    public void updateActions(float timepassed){
    	maps.get(mapIndex).updateActions(player, timepassed);
    	if(maps.get(mapIndex).worpPossible()){
    		switchMaps(maps.get(mapIndex).getExitPortal());
    	}
    }
    
    public void updateModel(){
    	maps.get(mapIndex).updateModel();
    }
    
	public void switchMaps(Portal exit){
		if (maps.indexOf(exit.getMap()) == this.mapIndex || 
				maps.indexOf(exit.getMap()) == maps.size()){
			return;
		}
		maps.get(mapIndex).detachMapElements();
		maps.get(mapIndex).removeMapElement(player);
    	this.mapIndex = maps.indexOf(exit.getMap());
    	maps.get(mapIndex).addMapElement(player);
    	maps.get(mapIndex).attachMapElements();
    	maps.get(mapIndex).updateActions(player, 0);
    	player.setPosition(exit.getPosition());
    	
	}
	
	
}

