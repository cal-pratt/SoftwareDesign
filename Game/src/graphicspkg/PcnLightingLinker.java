/** PcnLightingLinker
 * Handles activity between PcnLightingBuffer, APcObject3D and PcnLightingProgram objects
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */
package graphicspkg;
 

import objectpkg.APcObject3D;

class PcnLightingLinker implements IGraphicLinker {
	private PcnLightingBuffer mesh;
	private APcObject3D object;
	private PcnLightingProgram program;
	
	public PcnLightingLinker(APcObject3D object, PcnLightingBuffer mesh, PcnLightingProgram program){
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
