package features.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.*
import common.widgets.*
import org.jetbrains.skia.*

@Composable
fun HomeScreen() {
    AppScaffold(title = "Home") {
        item { Text("Home Screen") }
        item {
            Box(
                Modifier.padding(64.dp).size(300.dp).composed {
                    val time by
                        produceState(0f) {
                            while (true) {
                                withInfiniteAnimationFrameMillis { value = it / 1000f }
                            }
                        }
                    val effect = remember { RuntimeEffect.makeForShader(compositeSksl) }
                    val compositeShaderBuilder = remember(effect) { RuntimeShaderBuilder(effect) }
                    Modifier.drawWithCache {
                        compositeShaderBuilder.uniform(
                            name = "iResolution",
                            value1 = size.width,
                            value2 = size.height,
                        )
                        compositeShaderBuilder.uniform("iTime", time)
                        val shaderBrush = ShaderBrush(compositeShaderBuilder.makeShader())
                        onDrawBehind {
                            withTransform({ scale(scaleX = 1f, scaleY = -1f) }) {
                                drawRect(shaderBrush)
                            }
                        }
                    }
                }
            )
        }
        item {
            val angleX by
                rememberInfiniteTransition()
                    .animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec =
                            infiniteRepeatable(animation = tween(4000, easing = LinearEasing)),
                    )
            val angleY by
                rememberInfiniteTransition()
                    .animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec =
                            infiniteRepeatable(animation = tween(5300, easing = LinearEasing)),
                    )
            Box(
                Modifier.padding(64.dp).size(250.dp).graphicsLayer {
                    rotationX = angleX
                    rotationY = angleY
                }
            ) {
                Box(Modifier.background(Color.Green).fillMaxSize())
                Text("Box with graphicsLayer", Modifier.align(Alignment.Center), Color.Black)
            }
        }
    }
}

// https://github.com/gleb-skobinsky/shaders-demo/blob/main/shared/src/commonMain/kotlin/sksl.kt
// language=glsl
const val compositeSksl =
    """
    uniform float2 iResolution;      // Viewport resolution (pixels)
    uniform float  iTime;            // Shader playback time (s)
    
    const float cloudscale = 1.1;
    const float speed = 0.03;
    const float clouddark = 0.5;
    const float cloudlight = 0.3;
    const float cloudcover = 0.2;
    const float cloudalpha = 8.0;
    const float skytint = 0.5;
    const vec3 skycolour1 = vec3(0.2, 0.4, 0.6);
    const vec3 skycolour2 = vec3(0.4, 0.7, 1.0);

    const mat2 m = mat2( 1.6,  1.2, -1.2,  1.6 );

    vec2 hash( vec2 p ) {
        p = vec2(dot(p,vec2(127.1,311.7)), dot(p,vec2(269.5,183.3)));
        return -1.0 + 2.0*fract(sin(p)*43758.5453123);
    }

    float noise( in vec2 p ) {
        const float K1 = 0.366025404; // (sqrt(3)-1)/2;
        const float K2 = 0.211324865; // (3-sqrt(3))/6;
        vec2 i = floor(p + (p.x+p.y)*K1);
        vec2 a = p - i + (i.x+i.y)*K2;
        vec2 o = (a.x>a.y) ? vec2(1.0,0.0) : vec2(0.0,1.0); //vec2 of = 0.5 + 0.5*vec2(sign(a.x-a.y), sign(a.y-a.x));
        vec2 b = a - o + K2;
        vec2 c = a - 1.0 + 2.0*K2;
        vec3 h = max(0.5-vec3(dot(a,a), dot(b,b), dot(c,c) ), 0.0 );
        vec3 n = h*h*h*h*vec3( dot(a,hash(i+0.0)), dot(b,hash(i+o)), dot(c,hash(i+1.0)));
        return dot(n, vec3(70.0));
    }

    float fbm(vec2 n) {
        float total = 0.0, amplitude = 0.1;
        for (int i = 0; i < 7; i++) {
            total += noise(n) * amplitude;
            n = m * n;
            amplitude *= 0.4;
        }
        return total;
    }

    // -----------------------------------------------

    vec4 main(in vec2 fragCoord) {
        vec2 p = fragCoord.xy / iResolution.xy;
        vec2 uv = p*vec2(iResolution.x/iResolution.y,1.0);    
        float time = iTime * speed;
        float q = fbm(uv * cloudscale * 0.5);
        
        //ridged noise shape
        float r = 0.0;
        uv *= cloudscale;
        uv -= q - time;
        float weight = 0.8;
        for (int i=0; i<8; i++){
            r += abs(weight*noise( uv ));
            uv = m*uv + time;
            weight *= 0.7;
        }
        
        //noise shape
        float f = 0.0;
        uv = p*vec2(iResolution.x/iResolution.y,1.0);
        uv *= cloudscale;
        uv -= q - time;
        weight = 0.7;
        for (int i=0; i<8; i++){
            f += weight*noise( uv );
            uv = m*uv + time;
            weight *= 0.6;
        }
        
        f *= r + f;
        
        //noise colour
        float c = 0.0;
        time = iTime * speed * 2.0;
        uv = p*vec2(iResolution.x/iResolution.y,1.0);
        uv *= cloudscale*2.0;
        uv -= q - time;
        weight = 0.4;
        for (int i=0; i<7; i++){
            c += weight*noise( uv );
            uv = m*uv + time;
            weight *= 0.6;
        }
        
        //noise ridge colour
        float c1 = 0.0;
        time = iTime * speed * 3.0;
        uv = p*vec2(iResolution.x/iResolution.y,1.0);
        uv *= cloudscale*3.0;
        uv -= q - time;
        weight = 0.4;
        for (int i=0; i<7; i++){
            c1 += abs(weight*noise( uv ));
            uv = m*uv + time;
            weight *= 0.6;
        }
        
        c += c1;
        
        vec3 skycolour = mix(skycolour2, skycolour1, p.y);
        vec3 cloudcolour = vec3(1.1, 1.1, 0.9) * clamp((clouddark + cloudlight*c), 0.0, 1.0);
       
        f = cloudcover + cloudalpha*f*r;
        
        vec3 result = mix(skycolour, clamp(skytint * skycolour + cloudcolour, 0.0, 1.0), clamp(f + c, 0.0, 1.0));
        
        return vec4( result, 1.0 );
    }
"""
