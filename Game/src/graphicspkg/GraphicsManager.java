package graphicspkg;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import objectpkg.APcObject3D;
import objectpkg.ATexObject2D;
import objectpkg.IGraphicsObject;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector3f;
import silvertiger.tutorial.lwjgl.math.Vector4f;

public class GraphicsManager {
    
    float width, height;

    private Map<APcObject3D, PcGraphicLinker> graphicPcLinkers = new LinkedHashMap<>();
    private Map<String, PcGraphicsBuffer> pcBuffers = new LinkedHashMap<>();
    private int pcProgramCount = 0;
    private PcProgram pcProgram;
    
    private Matrix4f cameraProjection;
    
    private Map<APcObject3D, PcnLightingLinker> pcnLightingLinkers = new LinkedHashMap<>();
    private Map<String, PcnLightingBuffer> pcnLightingBuffers = new LinkedHashMap<>();
    private int pcnLightingProgramCount = 0;
    private PcnLightingProgram pcnLightingProgram;
    
    Vector3f lightPos = new Vector3f(0,0,5);
    Vector4f lightIntensity = new Vector4f(2f,2f,2f,2f);
    Vector4f ambientIntensity = new Vector4f(0.6f,0.6f,0.6f,1f);

    private Map<ATexObject2D, TexGraphicLinker> graphicTexLinkers = new LinkedHashMap<>();
    private Map<String, TexGraphicsBuffer> texBuffers = new LinkedHashMap<>();
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
        cameraProjection = new Matrix4f();
    }

    
    public void setCameraProjection(Matrix4f cameraProjection){
        this.cameraProjection = cameraProjection;
    }
    
    public void setLightPos(Vector3f lightPos){
        this.lightPos = lightPos;
    }

    public void setLightIntensity(Vector4f lightIntensity){
        this.lightIntensity = lightIntensity;
    }

    public void setAmbientIntensity(Vector4f ambientIntensity){
        this.ambientIntensity = ambientIntensity;
    }
    
	public void add(APcObject3D pcObject, boolean lighting){
		if(lighting){
			if(pcnLightingProgramCount++ == 0){
				pcnLightingProgram = new PcnLightingProgram();
			}
			if(!pcnLightingBuffers.containsKey(pcObject.getFilename())){
				pcnLightingBuffers.put(pcObject.getFilename(), 
						new PcnLightingBuffer(pcnLightingProgram, pcObject.getFilename()));
			}
			pcnLightingLinkers.put(pcObject, new PcnLightingLinker(
					pcObject,pcnLightingBuffers.get(pcObject.getFilename()), pcnLightingProgram));
		}
		else{
			if(pcProgramCount++ == 0){
				pcProgram = new PcProgram();
			}
			if(!pcBuffers.containsKey(pcObject.getFilename())){
				pcBuffers.put(pcObject.getFilename(), 
						new PcGraphicsBuffer(pcProgram, pcObject.getFilename()));
			}
			graphicPcLinkers.put(pcObject, new PcGraphicLinker(
					pcObject,pcBuffers.get(pcObject.getFilename()), pcProgram));
		}
	}
	
	public void remove(APcObject3D pcObject){
		if(graphicPcLinkers.containsKey(pcObject)){
			if(--pcProgramCount == 0){
				for(String meshFile : pcBuffers.keySet()){
					pcBuffers.get(meshFile).delete();
				}
				pcProgram.delete();
				pcBuffers.clear();
			}
			graphicPcLinkers.remove(pcObject);
		}
		else if(pcnLightingLinkers.containsKey(pcObject)){
			if(--pcnLightingProgramCount == 0){
				for(String meshFile : pcnLightingBuffers.keySet()){
					pcnLightingBuffers.get(meshFile).delete();
				}
				pcnLightingProgram.delete();
				pcnLightingBuffers.clear();
			}
			pcnLightingLinkers.remove(pcObject);
		}
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
		for(APcObject3D pcObject : new HashSet<APcObject3D>(pcnLightingLinkers.keySet())){
			remove(pcObject);
		}
        for(ATexObject2D texObject : new HashSet<ATexObject2D>(graphicTexLinkers.keySet())){
            remove(texObject);
        }
	}
	
	
	public void draw(){
		if(pcProgramCount > 0){
	    	pcProgram.use();
	    	pcProgram.setProjection(cameraProjection);
			for(IGraphicsObject object : graphicPcLinkers.keySet()){
				graphicPcLinkers.get(object).set();
				graphicPcLinkers.get(object).draw();
			}
		}
		
		if(pcnLightingProgramCount > 0){
			pcnLightingProgram.use();
			pcnLightingProgram.setProjection(cameraProjection);
			pcnLightingProgram.setLightPos(lightPos);
			pcnLightingProgram.setLightIntensity(lightIntensity);
			pcnLightingProgram.setAmbientIntensity(ambientIntensity);
			for(IGraphicsObject object : pcnLightingLinkers.keySet()){
				pcnLightingLinkers.get(object).set();
				pcnLightingLinkers.get(object).draw();
			}
		}

		if(texProgramCount > 0){
	    	texProgram.use();
			for(IGraphicsObject object : graphicTexLinkers.keySet()){
				graphicTexLinkers.get(object).set();
				graphicTexLinkers.get(object).draw();
			}
		}
	}
}
