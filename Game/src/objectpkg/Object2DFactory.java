package objectpkg;

public class Object2DFactory {
    public static ATexObject2D getContinueReleased(){
        return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/continue.png";
            }
        };
    } 
    public static ATexObject2D getBanner(){
        return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/banner.png";
            }
        };
    } 
    public static ATexObject2D getNewgameReleased(){
        return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/newgame.png";
            }
        };
    }
    
    public static ATexObject2D getSpace(){
        return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/space.png";
            }
        };
    }
    public static ATexObject2D getNewgameClicked(){
    	return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/newgameClicked.png";
            }
        };
    }
    public static ATexObject2D getContinueClicked(){
    	return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/continueClicked.png";
            }
        };
    }

    public static ATexObject2D getHealth(){
    	return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/health.png";
            }
        };
    }

    public static ATexObject2D getNoHealth(){
    	return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/nohealth.png";
            }
        };
    }
    

    public static ATexObject2D getPause(){
        return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/pause2.png";
            }
        };
    }
    
    public static ATexObject2D getSkillMenu(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillmenufireiceprelim.png";
			}
		};
    }
    
    public static ATexObject2D getSkillsButton(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsbutton.png";
			}
		};
    }
    
    public static ATexObject2D getSkillFire(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsfire.png";
			}
		};
    }
    
    public static ATexObject2D getSkillIce(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsice.png";
			}
		};
    }
    
    public static ATexObject2D getSkillArrowRed(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsarrowred.png";
			}
		};
    }
    
    public static ATexObject2D getSkillArrowBlue(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsarrowblue.png";
			}
		};
    }
    
    public static ATexObject2D getSkillDoubleRed(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsdoublered.png";
			}
		};
    }
    
    public static ATexObject2D getSkillDoubleBlue(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsdoubleblue.png";
			}
		};
    }
    
    public static ATexObject2D getSkillThreeRed(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsthreered.png";
			}
		};
    }
    
    public static ATexObject2D getSkillThreeBlue(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsthreeblue.png";
			}
		};
    }
    
    public static ATexObject2D getSkillStar(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsstar.png";
			}
		};
    }
    
    public static ATexObject2D getSkillExit(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsexit.png";
			}
		};
    }
    public static ATexObject2D getSkillFireClicked(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsfireclicked.png";
			}
		};
    }
    
    public static ATexObject2D getSkillIceClicked(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsiceclicked.png";
			}
		};
    }
    
    public static ATexObject2D getSkillArrowRedClicked(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsarrowredclicked.png";
			}
		};
    }
    
    public static ATexObject2D getSkillArrowBlueClicked(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsarrowblueclicked.png";
			}
		};
    }
    
    public static ATexObject2D getSkillDoubleRedClicked(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsdoubleredclicked.png";
			}
		};
    }
    
    public static ATexObject2D getSkillDoubleBlueClicked(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsdoubleblueclicked.png";
			}
		};
    }
    
    public static ATexObject2D getSkillThreeRedClicked(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsthreeredclicked.png";
			}
		};
    }
    
    public static ATexObject2D getSkillThreeBlueClicked(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsthreeblueclicked.png";
			}
		};
    }
    
    public static ATexObject2D getSkillStarClicked(){
    	return new ATexObject2D() {
			@Override
			public String getFilename() {
				return "sprites/skillsstarclicked.png";
			}
		};
    }
}
