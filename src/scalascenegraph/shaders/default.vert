#version 400

in vec3 positionAttribute;

void main(void) 
{
	gl_Position = vec4(positionAttribute, 1.0f);
}
