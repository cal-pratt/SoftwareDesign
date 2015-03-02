package graphicspkg;

import java.util.HashMap;
import java.util.Map;

import objectpkg.APcObject3D;
import objectpkg.IObject3D;

public class GraphicsManager {
	Map<IObject3D, IGraphicLinker> graphicLinks = new HashMap<IObject3D, IGraphicLinker>();
	Map<String, PcGraphicsBuffer> pcMeshMap = new HashMap<String, PcGraphicsBuffer>();
	
	int basicProgramCount = 0;
	PcProgram basicProgram;
	
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
	
	public void delete(){
		for(String meshFile : pcMeshMap.keySet()){
			pcMeshMap.get(meshFile).delete();
		}
		basicProgram.delete();
		pcMeshMap.clear();
		graphicLinks.clear();
	}
	
	public void draw(){
		for(IObject3D object : graphicLinks.keySet()){
			graphicLinks.get(object).set();
			graphicLinks.get(object).draw();
		}
	}
}
