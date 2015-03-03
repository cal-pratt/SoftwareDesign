package graphicspkg;

import java.util.HashMap;
import java.util.Map;

import objectpkg.APcObject3D;
import objectpkg.ATexObject2D;
import objectpkg.IObject3D;

public class GraphicsManager {
	Map<IObject3D, IGraphicLinker> graphicLinks = new HashMap<IObject3D, IGraphicLinker>();
	Map<String, PcGraphicsBuffer> pcBufferMap = new HashMap<String, PcGraphicsBuffer>();
    Map<String, TexGraphicsBuffer> texBufferMap = new HashMap<String, TexGraphicsBuffer>();
	
	int basicProgramCount = 0;
	PcProgram basicProgram;
	
	int texProgramCount = 0;
    TexProgram texProgram;
	
	public void add(APcObject3D object){
		if(basicProgramCount++ == 0){
			basicProgram = new PcProgram();
		}
		if(!pcBufferMap.containsKey(object.getFilename())){
			pcBufferMap.put(object.getFilename(), new PcGraphicsBuffer(basicProgram, object.getFilename()));
		}
		graphicLinks.put(object, new PcGraphicLinker(object,pcBufferMap.get(object.getFilename()), basicProgram));
	}
	
	public void remove(APcObject3D object){
		if(--basicProgramCount == 0){
			for(String meshFile : pcBufferMap.keySet()){
				pcBufferMap.get(meshFile).delete();
			}
			basicProgram.delete();
			pcBufferMap.clear();
		}
		graphicLinks.remove(object);
	}
	
	public void add(ATexObject2D object){
        if(texProgramCount++ == 0){
            texProgram = new TexProgram();
        }
        if(!texBufferMap.containsKey(object.getFilename())){
            texBufferMap.put(object.getFilename(), new TexGraphicsBuffer(texProgram, object.getFilename()));
        }
        graphicLinks.put(object, new TexGraphicLinker(object,texBufferMap.get(object.getFilename()), texProgram));
    }
    
    public void remove(ATexObject2D object){
        if(--texProgramCount == 0){
            for(String meshFile : texBufferMap.keySet()){
                texBufferMap.get(meshFile).delete();
            }
            texProgram.delete();
            texBufferMap.clear();
        }
        graphicLinks.remove(object);
    }
	
	public void delete(){
		for(String meshFile : pcBufferMap.keySet()){
			pcBufferMap.get(meshFile).delete();
		}
		basicProgram.delete();
        texProgram.delete();
		pcBufferMap.clear();
        texBufferMap.clear();
		graphicLinks.clear();
	}
	
	public void draw(){
		for(IObject3D object : graphicLinks.keySet()){
			graphicLinks.get(object).set();
			graphicLinks.get(object).draw();
		}
	}
}
