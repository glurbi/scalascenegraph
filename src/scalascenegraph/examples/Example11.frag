uniform sampler2D texture;

void main (void)
{
  vec4 texel = texture2D(texture, gl_TexCoord[0].xy);
  gl_FragColor = vec4(mix(vec3(texel), vec3(gl_Color), gl_Color.a), 1.0);
}
