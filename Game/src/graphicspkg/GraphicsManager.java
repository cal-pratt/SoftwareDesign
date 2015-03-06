package graphicspkg;

import java.util.LinkedHashMap;
import java.util.Map;

import objectpkg.APcObject3D;
import objectpkg.ATexObject2D;
import objectpkg.IObject3D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class GraphicsManager {
	Map<IObject3D, IGraphicLinker> graphicTexLinkers = new LinkedHashMap<IObject3D, IGraphicLinker>();
	Map<IObject3D, IGraphicLinker> graphicPcLinkers = new LinkedHashMap<IObject3D, IGraphicLinker>();
	Matrix4f pcProjection;
	Map<String, PcGraphicsBuffer> pcBuffers = new LinkedHashMap<String, PcGraphicsBuffer>();
    Map<String, TexGraphicsBuffer> texBuffers = new LinkedHashMap<String, TexGraphicsBuffer>();
	
	int basicProgramCount = 0;
	PcProgram basicProgram;
	
	int texProgramCount = 0;
    TexProgram texProgram;
	
	public void add(APcObject3D object){
		if(basicProgramCount++ == 0){
			basicProgram = new PcProgram();
		}
		if(!pcBuffers.containsKey(object.getFilename())){
			pcBuffers.put(object.getFilename(), new PcGraphicsBuffer(basicProgram, object.getFilename()));
		}
		graphicPcLinkers.put(object, new PcGraphicLinker(object,pcBuffers.get(object.getFilename()), basicProgram));
	}
	
	public void remove(APcObject3D object){
		if(--basicProgramCount == 0){
			for(String meshFile : pcBuffers.keySet()){
				pcBuffers.get(meshFile).delete();
			}
			basicProgram.delete();
			pcBuffers.clear();
		}
		graphicPcLinkers.remove(object);
	}
	
	public void add(ATexObject2D object){
        if(texProgramCount++ == 0){
            texProgram = new TexProgram();
        }
        if(!texBuffers.containsKey(object.getFilename())){
            texBuffers.put(object.getFilename(), new TexGraphicsBuffer(texProgram, object.getFilename()));
        }
        graphicTexLinkers.put(object, new TexGraphicLinker(object,texBuffers.get(object.getFilename()), texProgram));
    }
    
    public void remove(ATexObject2D object){
        if(--texProgramCount == 0){
            for(String meshFile : texBuffers.keySet()){
                texBuffers.get(meshFile).delete();
            }
            texProgram.delete();
            texBuffers.clear();
        }
        graphicTexLinkers.remove(object);
    }
	
	public void delete(){
		for(String meshFile : pcBuffers.keySet()){
			pcBuffers.get(meshFile).delete();
		}
		basicProgram.delete();
        texProgram.delete();
		pcBuffers.clear();
        texBuffers.clear();
        graphicPcLinkers.clear();
        graphicTexLinkers.clear();
	}
	
	public void setPcProjection(Matrix4f m){
		pcProjection = m;
	}
	
	public void draw(){
		if(basicProgramCount > 0){
	    	basicProgram.use();
	    	basicProgram.setProjection(pcProjection);
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
