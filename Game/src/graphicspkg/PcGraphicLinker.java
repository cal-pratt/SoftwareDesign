package graphicspkg;

import objectpkg.APcObject3D;

class PcGraphicLinker implements IGraphicLinker {
	private PcGraphicsBuffer mesh;
	private APcObject3D object;
	private PcProgram program;
	
	public PcGraphicLinker(APcObject3D object, PcGraphicsBuffer mesh, PcProgram program){
		this.object = object;
		this.mesh = mesh;
		this.program = program;
	}
	
    public void set(){
    	program.setModel(object.getModel());
    	mesh.bind();
    }
    public void draw(){
    	mesh.draw();
    }
}
