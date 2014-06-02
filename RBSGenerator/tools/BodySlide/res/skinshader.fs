uniform sampler2D texUnit0;

uniform bool bShowTexture = true;

void main (void)
{
    vec4 color;
    if(bShowTexture) {
        color = gl_Color+.20;
        color *= texture2D(texUnit0, gl_TexCoord[0].xy)+.05;
	    //color += 0.085;
        color *= 2.6;
	    color = clamp(color, 0.0, 1.0);
    } else {
        color = gl_Color;
    }
    gl_FragColor = color;
}