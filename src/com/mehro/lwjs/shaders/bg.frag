#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	vec3 position;
} fs_in;

uniform vec2 cursor;

uniform sampler2D tex;

uniform float amount;

void main()
{
	color = texture(tex, fs_in.tc);
	color *= amount / (length(cursor - fs_in.position.xy) + 2.5) + 0.5;
}
