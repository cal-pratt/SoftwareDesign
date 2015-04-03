package objectpkg;


import silvertiger.tutorial.lwjgl.math.Matrix4f;

public abstract class ATexObject2D implements IGraphicsObject {
    
    protected Matrix4f projection;
    
    protected float width, height;
    
    public void setProjection(Matrix4f projection){
        this.projection = projection;
    }
    public abstract String getFilename();
    
    public Matrix4f getProjection(){
        return this.projection;
    }
    
    public void setRawSize(float width, float height){
        this.width = width;
        this.height = height;
    }

    public float getWidth(){
    	return width;
    }
    
    public float getHeight(){
    	return height;
    }
}
