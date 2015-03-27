package objectpkg;

public class Object3DFactory {
	public static APcObject3D getCube(){
		return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/cube.ply";
            }
		};
	}
	public static APcObject3D getTriangle(){
		return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/triangle.ply";
            }
        };
	}
	public static APcObject3D getHeart(){
		return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/heart.ply";
            }
        };
	}

	public static APcObject3D getBorder(){
		return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/border.ply";
            }
        };
	}
	
	public static APcObject3D getStarsphere(){
		return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/starsphere.ply";
            }
        };
	}
	public static APcObject3D getSquare(){
		return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/square.ply";
            }
        };
	}
	public static APcObject3D getMonkey() {
		return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/MONKAY.ply";
            }
        };
	}

    public static APcObject3D getSpaceShipTop() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/spaceshiptop.ply";
            }
        };
    }
    public static APcObject3D getSpaceShipBottom() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/spaceshipbottom.ply";
            }
        };
    }
    public static APcObject3D getLaser() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/laser.ply";
            }
        };
    }
    public static APcObject3D getFireLaser() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/fireLaser.ply";
            }
        };
    }
    public static APcObject3D getSimpleLaser() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/simpleLaser.ply";
            }
        };
    }
    public static APcObject3D getIceLaser() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/iceLaser.ply";
            }
        };
    }
    public static APcObject3D getStarLaser() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/star.ply";
            }
        };
    }
    public static APcObject3D getUfo() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/ufo.ply";
            }
        };
    }
    public static APcObject3D getPlayerShip() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/playerShip.ply";
            }
        };
    }
    public static APcObject3D getCoolShip() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/coolship.ply";
            }
        };
    }
    public static APcObject3D getSecondBossShip() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/bossShip2.ply";
            }
        };
    }
    public static APcObject3D getAlien() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/alien.ply";
            }
        };
    }
    public static APcObject3D getPortal() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/portal.ply";
            }
        };
    }
    
}


