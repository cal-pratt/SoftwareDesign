package graphicspkg;

import objectpkg.ATexObject2D;

class TexGraphicLinker implements IGraphicLinker {
    private TexGraphicsBuffer mesh;
    private ATexObject2D object;
    private TexProgram program;
    
    public TexGraphicLinker(ATexObject2D object, TexGraphicsBuffer mesh, TexProgram program){
        this.object = object;
        this.mesh = mesh;
        this.program = program;
    }
    
    public void set(){
        mesh.bind();
        program.use();
        program.setModel(object.getModel());
        program.setView(object.getView());
        program.setProjection(object.getProjection());
    }
    public void draw(){
        mesh.draw();
    }
}
