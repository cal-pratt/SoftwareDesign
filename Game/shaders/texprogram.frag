#version 330

in vec3 vertexColor;
in vec2 textureCoord;

out vec4 fragColor;

uniform sampler2D texImage;

void main() {
    vec4 textureColor = texture(texImage, textureCoord);
    if(textureColor.a == 0){
    	discard;
    }
    else {
	    fragColor = vec4(vertexColor, 1) * textureColor;
	}
}