#version 150

in vec3 Position;
out vec3 TexCoords;

uniform mat4 ProjMat;
uniform mat4 ModelViewMat;

void main() {
    TexCoords = Position;
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
}
