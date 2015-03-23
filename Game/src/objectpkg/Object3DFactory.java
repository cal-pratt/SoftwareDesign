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
    
    public static APcObject3D getUfo() {
        return new APcObject3D(){
            @Override
            public String getFilename() {
                return "meshdata/ufo.ply";
            }
        };
    }
    
}


