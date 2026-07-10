package moose.graphics;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import moose.core.ContextBinder;

public class GraphicsManager implements ContextBinder {

    private static Drawable activeContext;
    
    @Override
    public void bind(Long id, GLCapabilities cache, Drawable drawable) {
        glfwMakeContextCurrent(id);
        GL.setCapabilities(cache);
        activeContext = drawable;
    }

    public static Drawable createDrawable() {
        return new MooseGraphics();
    }

    public static Drawable getActiveContext() {
        return activeContext;
    }
}
