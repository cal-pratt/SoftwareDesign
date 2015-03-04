#version 330

in vec3 position;
in vec3 color;

smooth out vec3 vertexColor;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;


void main()
{
    vec4 cameraPos = model * vec4(position, 1);
    gl_Position = projection * cameraPos;
    vertexColor = color;
}