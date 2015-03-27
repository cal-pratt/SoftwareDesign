#version 330

in vec3 position;
in vec3 color;
in vec3 normal;

out vec4 diffuseColor;
out vec3 vertexNormal;
out vec3 modelSpacePosition;
out vec3 LightPos;

uniform vec3 modelSpaceLightPos;
uniform mat4 model;
uniform mat4 projection;

void main()
{
	gl_Position = projection * (model * vec4(position, 1.0));
	modelSpacePosition = position;
	LightPos = (projection * vec4(modelSpaceLightPos, 1)).xyz;
	vertexNormal = normal;
	//vertexNormal = (projection * vec4(normal, -1)).xyz;
	//LightPos = modelSpaceLightPos;
	diffuseColor = vec4(color,1);
}
