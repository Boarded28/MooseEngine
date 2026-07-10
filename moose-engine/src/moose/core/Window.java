package moose.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.opengl.GLCapabilities;
import moose.graphics.Drawable;

/**
 * Manages an individual OS window lifecycle, its OpenGL rendering context, and input callbacks.
 *
 * @author boardedmind
 * @since 0.2.0-alpha
 * @version 0.2.0-alpha
 */
class Window {

    private int width, height;
    private final Lifecycle lifecycle;
    private final GLCapabilities cacheCapabilities;
    private final Drawable drawable;
    private long windowID = 0;

    /**
     * Allocates native GLFW window resources, setups resize callbacks, and initializes the graphics pipeline.
     *
     * @param lifecycle The simulation runtime hooks to update and render.
     * @param config    The layout and behaviors configuration parameters.
     * @param drawable  The underlying rendering canvas abstraction.
     * @throws IllegalStateException If the native GLFW window handle allocation fails.
     */
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

    /**
     * Gets the unique native memory address handle for this window.
     *
     * @return The GLFW native window ID handle.
     */
    public Long getID() {
        return windowID;
    }

    /**
     * Gets the canvas element assigned to execute drawing pipelines within this context.
     *
     * @return The drawable target instance.
     */
    public Drawable getDrawable() {
        return drawable;
    }

    /**
     * Gets the thread-mapped OpenGL capabilities schema compiled for this window profile.
     *
     * @return The cached capability definitions block.
     */
    public GLCapabilities getCacheCapabilities() {
        return cacheCapabilities;
    }

    /**
     * Evaluates state routines for the application layer.
     *
     * @param deltaTime The elapsed timing frame slice in seconds.
     */
    public void update(float deltaTime) {
        lifecycle.update(deltaTime);
    }

    /**
     * Clears depth and color framebuffers before passing execution to the application canvas layer.
     */
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        lifecycle.render();
    }

    /**
     * Checks if the window has been flagged for termination by the OS or user interaction.
     *
     * @return True if the window should exit its routine; false otherwise.
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(windowID);
    }

    /**
     * Swaps the front and back drawing framebuffers to display the rendered image.
     */
    public void swapBuffers() {
        glfwSwapBuffers(windowID);
    }

    /**
     * Teardown native bindings, flushes callbacks, disposes draw pipeline allocations, and kills the window.
     */
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
