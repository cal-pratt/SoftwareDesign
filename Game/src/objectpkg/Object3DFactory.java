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
}


