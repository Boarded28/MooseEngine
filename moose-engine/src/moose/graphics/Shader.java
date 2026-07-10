package moose.graphics;

import static org.lwjgl.opengl.GL40.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class Shader {
    
    private int shaderProgramID;

    public Shader(String vertexPath, String fragmentPath) {
        String vertexSource = fileReader(vertexPath);
        String fragmentSource = fileReader(fragmentPath);

        int vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == 0) {
            System.out.println("Shader. Unable to compile vertex shader " + glGetShaderInfoLog(vertexID));
        }

        int fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == 0) {
            System.out.println("Shader. Unable to compile fragment shader " + glGetShaderInfoLog(fragmentID));
        }

        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        if (glGetProgrami(shaderProgramID, GL_LINK_STATUS) == 0) {
            System.out.println("Shader. Unable to link shader program " + glGetShaderInfoLog(shaderProgramID));            
        }

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
    }

    public void use() {
        glUseProgram(shaderProgramID);
    }

    public int getLocation(String name) {
        return glGetUniformLocation(shaderProgramID, name);
    }

    private String fileReader(String pathToRead) {
        InputStream stream = Shader.class.getClassLoader().getResourceAsStream(pathToRead);
        StringBuilder src = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            while((line = reader.readLine()) != null) {
                src.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Unable to read file " + pathToRead);
        }
        return src.toString();
    }

    public void destroy() {
        if (shaderProgramID != 0) {
            if (glGetInteger(GL_CURRENT_PROGRAM) == shaderProgramID) {
                glUseProgram(0);
            }

            glDeleteProgram(shaderProgramID);
            shaderProgramID = 0;
            System.out.println("ShaderProgram resources freed");
        }
    }
}
