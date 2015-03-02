package shaderpkg;

import java.util.HashMap;
import java.util.Map;

import layerpkg.APcObject3D;

public class GraphicsManager {
	Map<APcObject3D, PcModelManager> pcModelManagers = new HashMap<APcObject3D, PcModelManager>();
	Map<String, PcMesh> pcMeshMap = new HashMap<String, PcMesh>();
	
	int basicProgramCount = 0;
	PcProgram basicProgram;
	
	public void addBasicProgramObject(APcObject3D object){
		if(basicProgramCount++ == 0){
			basicProgram = new PcProgram();
		}
		if(!pcMeshMap.containsKey(object.getFilename())){
			pcMeshMap.put(object.getFilename(), new PcMesh(basicProgram, object.getFilename()));
		}
		pcModelManagers.put(object, new PcModelManager(object,pcMeshMap.get(object.getFilename()), basicProgram));
	}
	
	public void delBasicProgramObject(APcObject3D object){
		if(--basicProgramCount == 0){
			for(String meshFile : pcMeshMap.keySet()){
				pcMeshMap.get(meshFile).delete();
			}
			basicProgram.delete();
			pcMeshMap.clear();
		}
		pcModelManagers.remove(object);
	}
	
	public void delete(){
		for(String meshFile : pcMeshMap.keySet()){
			pcMeshMap.get(meshFile).delete();
		}
		basicProgram.delete();
		pcMeshMap.clear();
		pcModelManagers.clear();
	}
	
	public void draw(){
		for(APcObject3D object : pcModelManagers.keySet()){
			pcModelManagers.get(object).set();
			pcModelManagers.get(object).draw();
		}
	}
}
