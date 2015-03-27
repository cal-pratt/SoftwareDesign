#version 330

in vec4 diffuseColor;
in vec3 vertexNormal;
in vec3 modelSpacePosition;
in vec3 LightPos;

out vec4 fragColor;

uniform vec4 lightIntensity;
uniform vec4 ambientIntensity;

void main()
{
	vec3 lightDir = normalize(LightPos - modelSpacePosition);

	float cosAngIncidence = dot(normalize(vertexNormal), lightDir);
	cosAngIncidence = clamp(cosAngIncidence, 0, 1);
	
	fragColor = (diffuseColor * lightIntensity * cosAngIncidence) +
		(diffuseColor * ambientIntensity);
}
