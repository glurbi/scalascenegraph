#version 400

uniform mat4 mvp;
uniform vec4 currentColor;

in vec3 position;

out vec4 color;

void main(void) 
{
	gl_Position = mvp * vec4(position, 1.0f);
    color = currentColor;
}
