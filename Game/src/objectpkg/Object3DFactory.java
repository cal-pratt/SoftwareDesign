package objectpkg;

public class Object3DFactory {
	public static APcObject3D getCube(){
		return new Cube();
	}
	public static APcObject3D getTriangle(){
		return new Triangle();
	}
	public static APcObject3D getSquare(){
		return new Square();
	}
	public static APcObject3D getMonkey() {
		return new MonkeyEnemy();
	}

    public static APcObject3D getSpaceShipTop() {
        return new SpaceShipTop();
    }
    public static APcObject3D getSpaceShipBottom() {
        return new SpaceShipBottom();
    }
    public static APcObject3D getLaser() {
        return new Laser();
    }
    
}


