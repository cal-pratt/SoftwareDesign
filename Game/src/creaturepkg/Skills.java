/** Skills
 * Different skills accessible through the skills menu
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package creaturepkg;

import menupkg.MenuButton;
import menupkg.SkillMenu;
import eventpkg.GameEvents.ButtonEventPublisher;
import eventpkg.GameEvents.IButtonEventListener;

public class Skills {
	SkillMenu skillMenu;
    Player player;

    private boolean firePressed = false;
    private boolean icePressed = false;
    private boolean strongFirePressed = false;
    private boolean strongIcePressed = false;
    private boolean doubleFirePressed = false;
    private boolean doubleIcePressed = false;
    private boolean tripleFirePressed = false;
    private boolean starPressed = false;
    private boolean tripleIcePressed = false;
    
    public boolean getFirePressed(){
    	return firePressed;
    } 
    public void setFirePressed(){
    	if(icePressed == true)
    		return;
    	
    	else if(player.getSkillPoints() >= 5) {
    		if(firePressed == false){
    			player.setFirePressed();
        		player.setSkillPoints(player.getSkillPoints() - 5);
        		firePressed = true;
    		}
    	}
    }
    
    public boolean getIcePressed(){
    	return icePressed;
    } 
    public void setIcePressed(){
    	if(firePressed == true)
    		return;
    	
    	else if(player.getSkillPoints() >= 5) {
    		if(icePressed == false){
    			player.setIcePressed();
        		player.setSkillPoints(player.getSkillPoints() - 5);
        		icePressed = true;
    		}
    	}
    }
    
    public boolean getStrongFirePressed(){
    	return strongFirePressed;
    } 
    public void setStrongFirePressed(){
    	if(firePressed == true){
    		if(strongFirePressed == false){
    			if(player.getSkillPoints() >= 5) {
        			player.setStrongFirePressed();
            		player.setSkillPoints(player.getSkillPoints() - 5);
            		strongFirePressed = true;
            	}
    		}
    	}
    }
    
    public boolean getStrongIcePressed(){
    	return strongIcePressed;
    } 
    public void setStrongIcePressed(){
    	if(icePressed == true){
    		if(strongIcePressed == false){
    			if(player.getSkillPoints() >= 5) {
        			player.setStrongIcePressed();
            		player.setSkillPoints(player.getSkillPoints() - 5);
            		strongIcePressed = true;
            	}
    		}
    	}
    }
    
    public boolean getDoubleFirePressed(){
    	return doubleFirePressed;
    } 
    public void setDoubleFirePressed(){
    	if(strongFirePressed == true){
    		if(doubleFirePressed == false){
    			if(player.getSkillPoints() >= 10) {
        			player.setDoubleFirePressed();
            		player.setSkillPoints(player.getSkillPoints() - 10);
            		doubleFirePressed = true;
            	}
    		}
    	}
    }
    
    public boolean getDoubleIcePressed(){
    	return doubleIcePressed;
    } 
    public void setDoubleIcePressed(){
    	if(strongIcePressed == true){
    		if(doubleIcePressed == false){
    			if(player.getSkillPoints() >= 10) {
        			player.setDoubleIcePressed();
            		player.setSkillPoints(player.getSkillPoints() - 10);
            		doubleIcePressed = true;
            	}
    		}
    	}
    }
    
    public boolean getTripleFirePressed(){
    	return tripleFirePressed;
    } 
    public void setTripleFirePressed(){
    	if(doubleFirePressed == true){
    		if(tripleFirePressed == false){
    			if(player.getSkillPoints() >= 15) {
        			player.setTripleFirePressed();
            		player.setSkillPoints(player.getSkillPoints() - 15);
            		tripleFirePressed = true;
            	}
    		}
    	}
    }
    
    public boolean getTripleIcePressed(){
    	return tripleIcePressed;
    } 
    public void setTripleIcePressed(){
    	if(doubleIcePressed == true){
    		if(tripleIcePressed == false){
    			if(player.getSkillPoints() >= 15) {
        			player.setTripleIcePressed();
            		player.setSkillPoints(player.getSkillPoints() - 15);
            		tripleIcePressed = true;
            	}
    		}
    	}
    }
    
    public boolean getStarPressed(){
    	return starPressed;
    } 
    public void setStarPressed(){
    	if(tripleFirePressed == true || tripleIcePressed == true){
    		if(starPressed == false){
    			if(player.getSkillPoints() >= 25){
        			player.setStarPressed();
    				player.setSkillPoints(player.getSkillPoints() - 15);
    				starPressed = true;
    			}
    		}
    	}
    }
    
    private IButtonEventListener fireCallBack = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	setFirePressed();
        }
    };
    private IButtonEventListener iceCallBack = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	setIcePressed();
        }
    };
    private IButtonEventListener strongerFireCallBack = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	setStrongFirePressed();
        }
    };
    private IButtonEventListener strongerIceCallBack = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	setStrongIcePressed();
        }
    };
    private IButtonEventListener doubleFireCallBack = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	setDoubleFirePressed();
        }
    };
    private IButtonEventListener doubleIceCallBack = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	setDoubleIcePressed();
        }
    };
    private IButtonEventListener tripleFireCallBack = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	setTripleFirePressed();
        }
    };
    private IButtonEventListener tripleIceCallBack = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	setTripleIcePressed();
        }
    };
    private IButtonEventListener starCallBack = new IButtonEventListener(){
        @Override
        public void actionPerformed(ButtonEventPublisher sender, MenuButton e) {
        	setStarPressed();
        }
    };
    
    public Skills(SkillMenu skillMenu, Player player){
    	this.player = player;
    	this.skillMenu = skillMenu;
        
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
    
    public void delete(){
        skillMenu.getFireSkillButtonEvent().unsubscribe(fireCallBack);
        skillMenu.getIceSkillButtonEvent().unsubscribe(iceCallBack);
        skillMenu.getStrongerFireSkillButtonEvent().unsubscribe(strongerFireCallBack);
        skillMenu.getStrongerIceSkillButtonEvent().unsubscribe(strongerIceCallBack);
        skillMenu.getDoubleFireSkillButtonEvent().unsubscribe(doubleFireCallBack);
        skillMenu.getDoubleIceSkillButtonEvent().unsubscribe(doubleIceCallBack);
        skillMenu.getTripleFireSkillButtonEvent().unsubscribe(tripleFireCallBack);
        skillMenu.getTripleIceSkillButtonEvent().unsubscribe(tripleIceCallBack);
        skillMenu.getStarSkillButtonEvent().unsubscribe(starCallBack);
    }
}
