package graphicspkg;

import silvertiger.tutorial.lwjgl.math.Matrix4f;
import utilpkg.FileInterpreter;

public class TexProgram extends ADefinedProgram {

    int uniModel;
    int uniView;
    int uniProjection;
    int uniTex;
    
    public TexProgram(){
        createProgram(FileInterpreter.fileToString("shaders/texprogram.vert"),
                FileInterpreter.fileToString("shaders/texprogram.frag"));
    }
    

    @Override
    protected void bindUniformLocations() {
        uniModel = getUniformLocation("model");
        uniView = getUniformLocation("view");
        uniProjection = getUniformLocation("projection");
        uniTex = getUniformLocation("texImage");
    }
    
    public void setModel(Matrix4f model){
        setUniform(uniModel, model);
    }
    public void setView(Matrix4f view){
        setUniform(uniView, view);
    }
    public void setProjection(Matrix4f projection){
        setUniform(uniProjection, projection);
    }
    public void setTex(){
        setUniform(uniTex, 0);
    }

}
