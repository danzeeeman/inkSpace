
//#version 120
#extension GL_ARB_texture_rectangle: enable

#ifdef GL_ES
precision mediump float;
#endif


uniform sampler2D ink;
varying float dist;
varying float topp;
varying float endPct;
uniform float drawTrans;

// 500x300

//uniform sampler2DRect ink;

void main() {
    
    float y = topp*0.5f + 0.5f;
    
    
    float x = mod(dist/2.0f, 1000.0f) / 500.0f;
    
    if (x > 1.0f) x = 2.0f - x;
    
    vec2 st = vec2(x, (17.0f + y * (83.0f - 17.0f))/300.0f);
    vec4 pix  = texture2D(ink, st);
    

    
    //pix *= 1.1;
    if (drawTrans > 0.5){
        gl_FragColor = vec4(0.1f, 0.1f, 0.1f, 1.0f); //vec4(); //pix * 0.2f + 0.8f * vec4(1.0f);
    } else {
        gl_FragColor = pix; // * 0.2 + 0.8 * vec4(1.0);
    }
}