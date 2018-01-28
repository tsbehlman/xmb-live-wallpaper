precision lowp float;

uniform vec3 color;

void main() {
	gl_FragColor = vec4( ( 1.0 - gl_PointCoord.y * 0.625 ) * color, 1.0 );
}