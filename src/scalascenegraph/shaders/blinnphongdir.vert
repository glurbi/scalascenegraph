#version 330 core

uniform mat4 mvp;
uniform mat4 mv;
uniform vec3 color;
uniform vec3 lightDir;
uniform vec4 ambientLight;

in vec3 vPosition;
in vec3 vNormal;
out vec4 vColor;

void main(void) 
{ 
	vec3 normalEye;
	float dotProduct;
	
	/* We transform the normal in eye coordinates. */
	normalEye = vec3(mv * vec4(vNormal, 0.0f));
	
	/* We compute the dot product of the normal in eye coordinates by the light direction.
       The value will be positive when the diffuse light should be ignored, negative otherwise. */
	dotProduct = dot(normalEye, lightDir);

	gl_Position = mvp * vec4(vPosition, 1.0f);
    
    vec4 ambient = vec4(color, 1.0f) * ambientLight;
    vec4 diffuse = -min(dotProduct, 0.0f) * vec4(color, 1.0f);
	vColor = ambient + diffuse;
}
