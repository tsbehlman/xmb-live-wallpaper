precision lowp float;

attribute vec2 position;

uniform vec2 resolution;

varying highp vec2 pos;
varying float gradient;

void main() {
	pos = position * resolution;
	gradient = 1.0 - position.y * 0.625;
	gl_Position = vec4( position, 0.0, 1.0 );
}