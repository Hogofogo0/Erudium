#version 150

in vec3 TexCoords;
out vec4 fragColor;

uniform samplerCube skyboxTex;
uniform float uTime;

mat3 rotateY(float angle) {
    float s = sin(angle);
    float c = cos(angle);
    return mat3(
    c, 0.0, -s,
    0.0, 1.0,  0.0,
    s, 0.0,  c
    );
}

void main() {
    vec3 rotated = rotateY(uTime * 0.02) * TexCoords;
    fragColor = texture(skyboxTex, rotated);
}
