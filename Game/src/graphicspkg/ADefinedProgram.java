package graphicspkg;

import silvertiger.tutorial.lwjgl.graphic.Shader;
import silvertiger.tutorial.lwjgl.graphic.ShaderProgram;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;

abstract class ADefinedProgram extends ShaderProgram { 
    
    private Shader vertexShader;
    private Shader fragmentShader;
    
    protected abstract void bindUniformLocations();
    
    
    protected void createProgram(CharSequence vShaderFilename, CharSequence fShaderFilename) {
        vertexShader = new Shader(GL_VERTEX_SHADER, vShaderFilename);
        fragmentShader = new Shader(GL_FRAGMENT_SHADER, fShaderFilename);

        attachShader(vertexShader);
        attachShader(fragmentShader);
        bindFragmentDataLocation(0, "fragColor");
        link();
        use();
        
        bindUniformLocations();
		
    }
    @Override
    public void delete(){
        vertexShader.delete();
        fragmentShader.delete();
        super.delete();
    }
}
