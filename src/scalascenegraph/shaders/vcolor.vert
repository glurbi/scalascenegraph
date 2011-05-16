#version 400

uniform mat4 mvp;

in vec3 vPosition;

out vec4 color;

void main(void) 
{
	gl_Position = mvp * vec4(position, 1.0f);
    color = vec4(position, 1.0f);
}
