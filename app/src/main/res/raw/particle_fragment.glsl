precision lowp float;

varying float alpha;

void main() {
	vec2 cxy = gl_PointCoord * 2.0 - 1.0;
	float radius = dot( cxy, cxy );

	gl_FragColor = vec4( vec3( alpha * max( 0.0, 1.0 - radius * radius ) ), 1.0 );
}