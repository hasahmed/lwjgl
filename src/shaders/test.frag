#version 330 core
out vec4 color;
uniform vec4 incolor;
void main(){
    color = incolor;
    //color.a = 0.0f;
}