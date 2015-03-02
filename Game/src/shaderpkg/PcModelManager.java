package shaderpkg;

import layerpkg.APcObject3D;

public class PcModelManager {
	private PcMesh mesh;
	private APcObject3D object;
	private PcProgram program;
	
	public PcModelManager(APcObject3D object, PcMesh mesh, PcProgram program){
		this.object = object;
		this.mesh = mesh;
		this.program = program;
	}
	
    public void set(){
    	program.use();
    	program.setModel(object.getModel());
    	program.setView(object.getView());
    	program.setProjection(object.getProjection());
    	mesh.bindVertexArrayObject();
    }
    public void draw(){
    	mesh.draw();
    }
}
