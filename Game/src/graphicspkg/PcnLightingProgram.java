/** PcnLightingProgram
 * Loads the custom FragmentLightingPCN program and defines the location for its uniforms
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */
package graphicspkg;

import silvertiger.tutorial.lwjgl.math.*;
import utilpkg.FileInterpreter;

import silvertiger.tutorial.lwjgl.math.Vector4f;

class PcnLightingProgram extends ADefinedProgram{
    
    int uniModel;
    int uniProjection;
    int uniLightPos;
    int uniLightIntensity;
    int uniAmbientIntensity;
    
    public PcnLightingProgram(){
        createProgram(FileInterpreter.fileToString("shaders/FragmentLightingPCN.vert"),
                FileInterpreter.fileToString("shaders/FragmentLightingPCN.frag"));
    }

    @Override
    protected void bindUniformLocations() {
        uniModel = getUniformLocation("model");
        uniProjection = getUniformLocation("projection");
        uniLightPos = getUniformLocation("modelSpaceLightPos");
        uniLightIntensity = getUniformLocation("lightIntensity");
        uniAmbientIntensity = getUniformLocation("ambientIntensity");
    }
    
    public void setModel(Matrix4f model){
        setUniform(uniModel, model);
    }
    
    public void setProjection(Matrix4f projection){
        setUniform(uniProjection, projection);
    }

    public void setLightPos(Vector3f lightPos){
        setUniform(uniLightPos, lightPos);
    }

    public void setLightIntensity(Vector4f lightIntensity){
        setUniform(uniLightIntensity, lightIntensity);
    }

    public void setAmbientIntensity(Vector4f ambientIntensity){
        setUniform(uniAmbientIntensity, ambientIntensity);
    }
}

