package graphicspkg;

import silvertiger.tutorial.lwjgl.math.Matrix4f;
import utilpkg.FileInterpreter;

public class TexProgram extends ADefinedProgram {

    int uniProjection;
    int uniTex;
    
    public TexProgram(){
        createProgram(FileInterpreter.fileToString("shaders/texprogram.vert"),
                FileInterpreter.fileToString("shaders/texprogram.frag"));
    }
    

    @Override
    protected void bindUniformLocations() {
        uniProjection = getUniformLocation("projection");
        uniTex = getUniformLocation("texImage");
    }
    public void setProjection(Matrix4f projection){
        setUniform(uniProjection, projection);
    }
    public void setTex(){
        setUniform(uniTex, 0);
    }

}
