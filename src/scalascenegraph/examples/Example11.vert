struct wave {
  float A;
  vec2 dir;
  float w;
  float phi;
};
	    
const wave wave0 = wave(0.05, vec2(1.0, 0.0), 10.0, 5.0);
const wave wave1 = wave(0.025, vec2(0.0, 1.0), 8.0, 10.0);
const wave wave2 = wave(0.01, vec2(-1.0, -1.0), 2.0, 20.0);
	    
const vec3 lightPos = vec3(0.0, 0.0, 1.0);
	    
uniform float t;

/**
 * inspired from http://http.developer.nvidia.com/GPUGems/gpugems_ch01.html
 */
void main (void)
{
  vec4 v = gl_Vertex;
  vec2 xy = vec2(v.x, v.y);
  wave waves[3];
  waves[0] = wave0;
  waves[1] = wave1;
  waves[2] = wave2;
	    
  float dhdx = 0.0;
  float dhdy = 0.0;
  for (int i = 0; i < 3; i++) {
    float A = waves[i].A;
    vec2 dir = waves[i].dir;
    float w = waves[i].w;
    float phi = waves[i].phi;
    v.z += A * sin(dot(dir, xy)*w + phi*t);
			
    dhdx += w * dot(dir, vec2(v.x, 0.0)) * A * cos(dot(dir, xy)*w + phi*t);
    dhdy += w * dot(dir, vec2(0.0, v.y)) * A * cos(dot(dir, xy)*w + phi*t);
  }
	    
  gl_Position = gl_ModelViewProjectionMatrix * v;
	    
  vec3 N = normalize(gl_NormalMatrix * vec3(-dhdx, -dhdy, 1.0));
  vec3 L = normalize(gl_NormalMatrix * lightPos);
  float diffuse = max(0.0, dot(N, L));
	    
  gl_FrontColor = vec4(1.0, 1.0, 1.0, 0.4) * diffuse;
  gl_TexCoord[0] = gl_MultiTexCoord0;
}
