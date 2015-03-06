package graphicspkg;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import objectpkg.APcObject3D;
import objectpkg.ATexObject2D;
import objectpkg.IObject3D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class GraphicsManager {
    
    float width, height;

    private Map<APcObject3D, PcGraphicLinker> graphicPcLinkers = new LinkedHashMap<APcObject3D, PcGraphicLinker>();
    private Map<String, PcGraphicsBuffer> pcBuffers = new LinkedHashMap<String, PcGraphicsBuffer>();
    private int pcProgramCount = 0;
    private PcProgram pcProgram;
    
    private Matrix4f pcProjection;
    

    private Map<ATexObject2D, TexGraphicLinker> graphicTexLinkers = new LinkedHashMap<ATexObject2D, TexGraphicLinker>();
    private Map<String, TexGraphicsBuffer> texBuffers = new LinkedHashMap<String, TexGraphicsBuffer>();
    private int texProgramCount = 0;
    private TexProgram texProgram;
    
    public GraphicsManager(float width, float height){
        this.width = width;
        this.height = height;
    }
    
    public float getWidth(){
        return width;
    }
    
    public float getHeight(){
        return height;
    }
    
    public void updateBounds(float width, float height){
        this.width = width;
        this.height = height;
        pcProjection = new Matrix4f();
    }

    
    public void setPcProjection(Matrix4f m){
        pcProjection = m;
    }
    
	public void add(APcObject3D pcObject){
		if(pcProgramCount++ == 0){
			pcProgram = new PcProgram();
		}
		if(!pcBuffers.containsKey(pcObject.getFilename())){
			pcBuffers.put(pcObject.getFilename(), new PcGraphicsBuffer(pcProgram, pcObject.getFilename()));
		}
		graphicPcLinkers.put(pcObject, new PcGraphicLinker(pcObject,pcBuffers.get(pcObject.getFilename()), pcProgram));
	}
	
	public void remove(APcObject3D pcObject){
		if(--pcProgramCount == 0){
			for(String meshFile : pcBuffers.keySet()){
				pcBuffers.get(meshFile).delete();
			}
			pcProgram.delete();
			pcBuffers.clear();
		}
		graphicPcLinkers.remove(pcObject);
	}
	
    
	public void add(ATexObject2D texture){
        if(texProgramCount++ == 0){
            texProgram = new TexProgram();
        }
        if(!texBuffers.containsKey(texture.getFilename())){
            texBuffers.put(texture.getFilename(), new TexGraphicsBuffer(texProgram, texture.getFilename()));
        }
        graphicTexLinkers.put(texture, new TexGraphicLinker(texture,texBuffers.get(texture.getFilename()), texProgram));
    }
    
    public void remove(ATexObject2D texture){
        if(--texProgramCount == 0){
            for(String meshFile : texBuffers.keySet()){
                texBuffers.get(meshFile).delete();
            }
            texProgram.delete();
            texBuffers.clear();
        }
        graphicTexLinkers.remove(texture);
    }
	
	public void delete(){
		for(APcObject3D pcObject : new HashSet<APcObject3D>(graphicPcLinkers.keySet())){
			remove(pcObject);
		}
        for(ATexObject2D texObject : new HashSet<ATexObject2D>(graphicTexLinkers.keySet())){
            remove(texObject);
        }
	}
	
	
	public void draw(){
		if(pcProgramCount > 0){
	    	pcProgram.use();
	    	pcProgram.setProjection(pcProjection);
			for(IObject3D object : graphicPcLinkers.keySet()){
				graphicPcLinkers.get(object).set();
				graphicPcLinkers.get(object).draw();
			}
		}

		if(texProgramCount > 0){
	    	texProgram.use();
			for(IObject3D object : graphicTexLinkers.keySet()){
				graphicTexLinkers.get(object).set();
				graphicTexLinkers.get(object).draw();
			}
		}
	}
}
