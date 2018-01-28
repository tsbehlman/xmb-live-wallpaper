precision lowp float;

varying float alpha;

void main() {
	gl_FragColor = vec4( alpha, alpha, alpha, 1.0 );
}