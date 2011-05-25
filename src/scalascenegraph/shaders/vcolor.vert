#version 400

uniform mat4 mvp;

in vec3 position;
in vec4 color;

out vec4 gl_Color;

void main(void) 
{
	gl_Position = mvp * vec4(position, 1.0f);
    gl_Color = color;
}
