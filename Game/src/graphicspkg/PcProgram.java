package graphicspkg;

import silvertiger.tutorial.lwjgl.math.*;
import utilpkg.FileInterpreter;


class PcProgram extends ADefinedProgram{
    
    int uniModel;
    int uniProjection;
    
    public PcProgram(){
        createProgram(FileInterpreter.fileToString("shaders/depthpc.vert"),
                FileInterpreter.fileToString("shaders/depthpc.frag"));
    }
    

    @Override
    protected void bindUniformLocations() {
        uniModel = getUniformLocation("model");
        uniProjection = getUniformLocation("projection");
    }
    
    public void setModel(Matrix4f model){
        setUniform(uniModel, model);
    }
    public void setProjection(Matrix4f projection){
        setUniform(uniProjection, projection);
    }
}
