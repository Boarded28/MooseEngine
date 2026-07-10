package moose.graphics;

import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL40.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

class MooseGraphics implements Drawable {

    private boolean isInitialized = false;

    private Shader shapeShader;
    private int vao, vbo, ebo;
    private int uPosition, uColor;
    private float[] clearColor;
    private float[] projectionMatrix;
    private final int BUFFER_SIZE = 64 * 1024; // 64kb memory

    public void init(int width, int height) {
        if(!isInitialized) {
            String vertex   = "moose/shaders/vertex.glsl",
                   fragment = "moose/shaders/fragment.glsl";
            shapeShader = new Shader(vertex, fragment);
            
            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, BUFFER_SIZE, GL_DYNAMIC_DRAW);

            ebo = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, BUFFER_SIZE, GL_DYNAMIC_DRAW);

            glVertexAttribPointer(0, 3, GL_FLOAT, false, 3*Float.BYTES, 0);
            glEnableVertexAttribArray(0);

            glBindVertexArray(0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);            

            uPosition = shapeShader.getLocation("uProjection");

            projectionMatrix = new float[]{
                2.0f/width, 0.0f, 0.0f, 0.0f,
                0.0f, -2.0f/height, 0.0f, 0.0f,
                0.0f, 0.0f, -1.0f, 0.0f,
                -1.0f, 1.0f, 0.0f, 1.0f
            };

            uColor = shapeShader.getLocation("uColor");

            glClearColor(0, 0, 0, 1);
            clearColor = new float[]{1.0f, 1.0f, 1.0f, 1.0f};

            isInitialized = true;
        } else {
            System.out.println("Graphics. Already initialized! Skipping.");
        }
    }

    public void show(long windowID) {
        glfwShowWindow(windowID);
    }

    public void resize(int width, int height) {
        glViewport(0, 0, width, height);
        glClearColor(0, 0, 0, 1);
        // update the matrix
        projectionMatrix = new float[]{
            2.0f/width, 0.0f, 0.0f, 0.0f,
            0.0f, -2.0f/height, 0.0f, 0.0f,
            0.0f, 0.0f, -1.0f, 0.0f,
            -1.0f, 1.0f, 0.0f, 1.0f
        };
    }

    public void paintBackground(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
        glClear(GL_COLOR_BUFFER_BIT);
    }


    public void paintColor(float r, float g, float b, float a) {
        clearColor[0] = r;
        clearColor[1] = g;
        clearColor[2] = b;
        clearColor[3] = a;
    }

    public void drawRectangle(float x, float y, float width, float height) {
        shapeShader.use();
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        float[] vertices = {
          //x           y           z
            x,          y,          0,
            x+width,    y,          0,
            x+width, y+height,      0,
            x,       y+height,      0,
        };
        int[] indices = {
            0, 1, 3,
            3, 1, 2,
        };

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer vertexBuffer = stack.floats(vertices);
            IntBuffer indicesBuffer = stack.ints(indices);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertexBuffer);
            glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, indicesBuffer);
        }

        glUniform4fv(uColor, clearColor);
        glUniformMatrix4fv(uPosition, false, projectionMatrix);                
        
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        glUseProgram(0);
        glBindVertexArray(0); 
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void destroy() {
        if (!isInitialized) return;

        if (vao != 0) {
            glDeleteVertexArrays(vao);
            vao = 0;
        }

        if (vbo != 0) {
            glDeleteBuffers(vbo);
            vbo = 0;
        }

        if (ebo != 0) {
            glDeleteBuffers(ebo);
            ebo = 0;
        }

        if (shapeShader != null) {
            shapeShader.destroy();
        }

        isInitialized = false;
        System.out.println("MooseGraphics resources freed");
    }
}
