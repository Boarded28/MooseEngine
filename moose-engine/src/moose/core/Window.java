package moose.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.opengl.GLCapabilities;

import moose.graphics.Drawable;

class Window {

    private int width, height;
    private final Lifecycle lifecycle;
    private final GLCapabilities cacheCapabilities;
    private final Drawable drawable;
    private long windowID = 0;

    public Window(Lifecycle lifecycle, MooseConfig config, Drawable drawable) {
        this.lifecycle = lifecycle;
        this.width = config.width;
        this.height = config.height;
        this.drawable = drawable;
        // apply behaviors
        glfwWindowHint(GLFW_RESIZABLE, config.resizable);
        glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, 0);
        // create window
        windowID = glfwCreateWindow(config.width, config.height, config.title, 0, 0);
        if(windowID == 0) {
            throw new IllegalStateException("Unable to create GLFW window!");
        }
        glfwSetFramebufferSizeCallback(windowID, (windowID, width, height) -> {
            long currentContext = glfwGetCurrentContext();

            if (currentContext != windowID) {
                glfwMakeContextCurrent(windowID);
            }

            drawable.resize(width, height);

            if (currentContext != windowID && currentContext != 0) {
                glfwMakeContextCurrent(currentContext);
            }
        });
        glfwMakeContextCurrent(windowID);
        cacheCapabilities = org.lwjgl.opengl.GL.createCapabilities();
        drawable.init(width, height);
        drawable.show(windowID);
    }

    public Long getID() {
        return windowID;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public GLCapabilities getCacheCapabilities() {
        return cacheCapabilities;
    }

    public void update(float deltaTime) {
        lifecycle.update(deltaTime);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        lifecycle.render();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowID);
    }

    public void swapBuffers() {
        glfwSwapBuffers(windowID);
    }

    public void destroy() {
        if (windowID == 0) return;

        long currentContext = glfwGetCurrentContext();
        if (currentContext != windowID) {
            glfwMakeContextCurrent(windowID);            
        }
        
        if (drawable != null) {
            drawable.destroy();
        }

        org.lwjgl.opengl.GL.setCapabilities(null);
        org.lwjgl.glfw.Callbacks.glfwFreeCallbacks(windowID);
        glfwMakeContextCurrent(0);
        glfwDestroyWindow(windowID);
        windowID = 0;
    }
}
