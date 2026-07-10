#version 330 core
layout (location = 0) in vec3 Position;
uniform mat4 uProjection;

void main() {
    gl_Position = uProjection * vec4(Position, 1.0f);
}