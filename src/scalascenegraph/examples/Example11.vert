struct wave {
  float A;     // amplitude
  vec2 dir;    // direction
  float w;     // frequency
  float phi;   // speed
};
	    
const wave wave0 = wave(0.05, vec2(1.0, 0.0), 10.0, 1.0);
const wave wave1 = wave(0.025, vec2(0.0, 1.0), 8.0, 2.0);
const wave wave2 = wave(0.01, vec2(-1.0, -1.0), 2.0, 4.0);
	    
const vec3 lightPos = vec3(0.0, 0.0, 0.0); // world coordinates
	    
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
  for (int i = 0; i < 1; i++) {
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
  vec3 L = normalize(lightPos - vec3(v.x, v.y, v.z));
  vec3 H = normalize(L);

  const float specularExp = 64.0;

  float NdotL = dot(N, L);
  float NdotH = max(0.0, dot(N, H));

  vec4 specular = vec4(0.0);
  if (NdotL > 0.0) {
	specular = vec4(pow(NdotH, specularExp));
  }
	    
  gl_FrontColor = vec4(1.0, 1.0, 1.0, 1.0) * specular;
  gl_TexCoord[0] = gl_MultiTexCoord0;
}
