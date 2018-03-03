precision lowp float;

uniform vec3 color;
uniform highp vec2 resolution;
uniform sampler2D bayerTexture;

varying vec2 pos;
varying float gradient;

const float colorDepth = 255.0;
const float inverseColorDepth = 1.0 / colorDepth;

float luma( vec3 color ) {
	return dot( color, vec3( 0.299, 0.587, 0.114 ) );
}

float dither( vec2 position, float diff ) {
	float threshold = texture2D( bayerTexture, mod( position, 8.0 ) / 16.0 ).a;
	return float( diff < threshold );
}

vec3 dither( vec2 position, vec3 color ) {
	vec3 diff = inverseColorDepth - mod( color, inverseColorDepth );
	return mix( color, color + diff, dither( position, luma( diff * colorDepth ) ) );
}

void main() {
	gl_FragColor = vec4( dither( pos, gradient * color ), 1.0 );
}