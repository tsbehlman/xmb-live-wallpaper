precision lowp float;

attribute vec3 seed;
uniform float time;
uniform float ratio;

varying float alpha;

float getWave( float x, float y ) {
	return cos( y * 4.0 ) * cos( time / 5.0 + x + y ) / 8.0 + cos( x * 2.0 - time ) * ratio / 2.0 - 0.28;
}

void main() {
	gl_PointSize = seed.z;

	float x = fract( time * ( seed.x - 0.5 ) / 15.0 + seed.y * 50.0 ) * 2.0 - 1.0;
    float y = sin( sign( seed.y ) * time * ( seed.y + 1.5 ) / 4.0 + seed.x * 100.0 );
    y /= ( 6.0 - seed.x * 5.0 * seed.y ) / ratio;

    float opacityVariance = mix(
        sin( time * ( seed.x + 0.5 ) * 12.0 + seed.y * 10.0 ),
        sin( time * ( seed.y + 1.5 ) * 6.0 + seed.x * 4.0 ),
        y * 0.5 + 0.5 ) * seed.x + seed.y;
    alpha = 0.75 * opacityVariance * opacityVariance;

	y += getWave( x, seed.y );

	gl_Position = vec4( x, y, 0.0, 1.0 );
}