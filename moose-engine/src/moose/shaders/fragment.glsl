#version 330 core

out vec4 FragmentColor;
uniform vec4 uColor;

void main() {
    FragmentColor = uColor;
}