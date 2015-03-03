package graphicspkg;

import objectpkg.ATexObject2D;

class TexGraphicLinker implements IGraphicLinker {
    private TexGraphicsBuffer buffer;
    private ATexObject2D object;
    private TexProgram program;
    
    public TexGraphicLinker(ATexObject2D object, TexGraphicsBuffer buffer, TexProgram program){
        this.object = object;
        this.buffer = buffer;
        this.object.setRawSize(buffer.getWidth(), buffer.getHeight());
        this.program = program;
    }
    
    public void set(){
    	buffer.bind();
        program.use();
        program.setModel(object.getModel());
        program.setView(object.getView());
        program.setProjection(object.getProjection());
    }
    public void draw(){
        buffer.draw();
    }
}
