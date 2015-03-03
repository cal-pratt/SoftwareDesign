package graphicspkg;

import java.util.HashMap;
import java.util.Map;

import objectpkg.APcObject3D;
import objectpkg.ATexObject2D;
import objectpkg.IObject3D;

public class GraphicsManager {
	Map<IObject3D, IGraphicLinker> graphicLinks = new HashMap<IObject3D, IGraphicLinker>();
	Map<String, PcGraphicsBuffer> pcMeshMap = new HashMap<String, PcGraphicsBuffer>();
    Map<String, TexGraphicsBuffer> texMeshMap = new HashMap<String, TexGraphicsBuffer>();
	
	int basicProgramCount = 0;
	PcProgram basicProgram;
	
	int texProgramCount = 0;
    TexProgram texProgram;
	
	public void add(APcObject3D object){
		if(basicProgramCount++ == 0){
			basicProgram = new PcProgram();
		}
		if(!pcMeshMap.containsKey(object.getFilename())){
			pcMeshMap.put(object.getFilename(), new PcGraphicsBuffer(basicProgram, object.getFilename()));
		}
		graphicLinks.put(object, new PcGraphicLinker(object,pcMeshMap.get(object.getFilename()), basicProgram));
	}
	
	public void remove(APcObject3D object){
		if(--basicProgramCount == 0){
			for(String meshFile : pcMeshMap.keySet()){
				pcMeshMap.get(meshFile).delete();
			}
			basicProgram.delete();
			pcMeshMap.clear();
		}
		graphicLinks.remove(object);
	}
	
	public void add(ATexObject2D object){
        if(texProgramCount++ == 0){
            texProgram = new TexProgram();
        }
        if(!texMeshMap.containsKey(object.getFilename())){
            texMeshMap.put(object.getFilename(), new TexGraphicsBuffer(texProgram, object.getFilename()));
        }
        graphicLinks.put(object, new TexGraphicLinker(object,texMeshMap.get(object.getFilename()), texProgram));
    }
    
    public void remove(ATexObject2D object){
        if(--texProgramCount == 0){
            for(String meshFile : texMeshMap.keySet()){
                texMeshMap.get(meshFile).delete();
            }
            texProgram.delete();
            texMeshMap.clear();
        }
        graphicLinks.remove(object);
    }
	
	public void delete(){
		for(String meshFile : pcMeshMap.keySet()){
			pcMeshMap.get(meshFile).delete();
		}
		basicProgram.delete();
        texProgram.delete();
		pcMeshMap.clear();
        texMeshMap.clear();
		graphicLinks.clear();
	}
	
	public void draw(){
		for(IObject3D object : graphicLinks.keySet()){
			graphicLinks.get(object).set();
			graphicLinks.get(object).draw();
		}
	}
}
