#version 330
in vec2 position;
in vec3 color;
in vec2 texcoord;

out vec3 vertexColor;
out vec2 textureCoord;

uniform mat4 projection;

void main() {
    vertexColor = color;
    textureCoord = texcoord;
    gl_Position = projection * vec4(position, -1.0, 1.0);
}