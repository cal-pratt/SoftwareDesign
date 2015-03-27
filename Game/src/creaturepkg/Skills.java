/** Skills
 * Different skills accessible through the skills menu
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package creaturepkg;

import inputpkg.UserInput;
import menupkg.MenuButton;
import menupkg.SkillMenu;
import eventpkg.GameEvents.ButtonEventPublisher;
import eventpkg.GameEvents.IButtonEventListener;
import graphicspkg.GraphicsManager;

public class Skills {
	SkillMenu skillMenu;
    GraphicsManager gm;
    Player player;
    
    private IButtonEventListener fireCallBack;
    private IButtonEventListener iceCallBack;
    private IButtonEventListener strongerFireCallBack;
    private IButtonEventListener strongerIceCallBack;
    private IButtonEventListener doubleFireCallBack;
    private IButtonEventListener doubleIceCallBack;
    private IButtonEventListener tripleFireCallBack;
    private IButtonEventListener tripleIceCallBack;
    private IButtonEventListener starCallBack;
    
    boolean firePressed = false;
    boolean icePressed = false;
    boolean strongFirePressed = false;
    boolean strongIcePressed = false;
    boolean doubleFirePressed = false;
    boolean doubleIcePressed = false;
    boolean tripleFirePressed = false;
    boolean tripleIcePressed = false;
    boolean starPressed = false;
    
    int skillPoints = player.getSkillPoints();

    public void playerSkills() {
    	fireCallBack = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            	if(icePressed == true)
            		return;
            	
            	else if(skillPoints >= 5) {
            		if(firePressed == false){
                		player.setSkillPoints(skillPoints - 5);
                		firePressed = true;
            		}
            	}
            }
        };
        iceCallBack = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            	if(firePressed == true)
            		return;
            	
            	else if(skillPoints >= 5) {
            		if(icePressed == false){
                		player.setSkillPoints(skillPoints - 5);
                		icePressed = true;
            		}
            	}
            }
        };
        strongerFireCallBack = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            	if(firePressed == true){
            		if(strongFirePressed == false){
            			if(skillPoints >= 5) {
                    		player.setSkillPoints(skillPoints - 5);
                    		strongFirePressed = true;
                    	}
            		}
            	}
            }
        };
        strongerIceCallBack = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            	if(icePressed == true){
            		if(strongIcePressed == false){
            			if(skillPoints >= 5) {
                    		player.setSkillPoints(skillPoints - 5);
                    		strongIcePressed = true;
                    	}
            		}
            	}
            }
        };
        doubleFireCallBack = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            	if(strongFirePressed == true){
            		if(doubleFirePressed == false){
            			if(skillPoints >= 10) {
                    		player.setSkillPoints(skillPoints - 10);
                    		doubleFirePressed = true;
                    	}
            		}
            	}
            }
        };
        doubleIceCallBack = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            	if(strongIcePressed == true){
            		if(doubleIcePressed == false){
            			if(skillPoints >= 10) {
                    		player.setSkillPoints(skillPoints - 10);
                    		doubleIcePressed = true;
                    	}
            		}
            	}
            }
        };
        tripleFireCallBack = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            	if(doubleFirePressed == true){
            		if(tripleFirePressed == false){
            			if(skillPoints >= 15) {
                    		player.setSkillPoints(skillPoints - 15);
                    		tripleFirePressed = true;
                    	}
            		}
            	}
            }
        };
        tripleIceCallBack = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            	if(doubleIcePressed == true){
            		if(tripleIcePressed == false){
            			if(skillPoints >= 15) {
                    		player.setSkillPoints(skillPoints - 15);
                    		tripleIcePressed = true;
                    	}
            		}
            	}
            }
        };
        starCallBack = new IButtonEventListener(){
            @Override
            public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
            	if(tripleFirePressed == true || tripleIcePressed == true){
            		if(starPressed == false){
            			if(skillPoints >= 25){
            				player.setSkillPoints(skillPoints - 15);
            				starPressed = true;
            			}
            		}
            	}
            }
        };
        
        skillMenu.getFireSkillButtonEvent().subscribe(fireCallBack);
        skillMenu.getIceSkillButtonEvent().subscribe(iceCallBack);
        skillMenu.getStrongerFireSkillButtonEvent().subscribe(strongerFireCallBack);
        skillMenu.getStrongerIceSkillButtonEvent().subscribe(strongerIceCallBack);
        skillMenu.getDoubleFireSkillButtonEvent().subscribe(doubleFireCallBack);
        skillMenu.getDoubleIceSkillButtonEvent().subscribe(doubleIceCallBack);
        skillMenu.getTripleFireSkillButtonEvent().subscribe(tripleFireCallBack);
        skillMenu.getTripleIceSkillButtonEvent().subscribe(tripleIceCallBack);
        skillMenu.getStarSkillButtonEvent().subscribe(starCallBack);
    }
}
