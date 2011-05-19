#version 400

uniform mat4 mvp;

in vec3 position;

flat out vec4 color;

void main(void) 
{
	gl_Position = mvp * vec4(position, 1.0f);
    float red = clamp(dot(vec3(1.0f, 0.0f, 0.0f), normalize(position)), 0.0f, 1.0f);
    float green = clamp(dot(vec3(0.0f, 1.0f, 0.0f), normalize(position)), 0.0f, 1.0f);
    float blue = clamp(dot(vec3(0.0f, 0.0f, 1.0f), normalize(position)), 0.0f, 1.0f);
    color = vec4(red, green, blue, 1.0f);
}
