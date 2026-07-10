package moose.graphics;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import moose.core.ContextBinder;

/**
 * Manages active OpenGL thread capabilities and global rendering context bindings.
 * 
 * <p>Serves as the concrete bridge between core window thread operations and 
 * graphics rendering subsystems.</p>
 *
 * @author boardedmind
 * @since 0.2.0-alpha
 * @version 0.2.0-alpha
 */
public class GraphicsManager implements ContextBinder {

    private static Drawable activeContext;
    
    /**
     * Swaps the active GLFW context thread and updates current OpenGL hardware capabilities.
     * 
     * @param id       The native window ID memory handle address to target.
     * @param cache    The hardware capabilities configuration map compiled for the target window profile.
     * @param drawable The graphics pipeline component to assign as the active context profile.
     */
    @Override
    public void bind(Long id, GLCapabilities cache, Drawable drawable) {
        glfwMakeContextCurrent(id);
        GL.setCapabilities(cache);
        activeContext = drawable;
    }

    /**
     * Instantiates a new concrete graphics rendering pipeline component.
     * 
     * @return A newly allocated canvas drawing instance.
     */
    public static Drawable createDrawable() {
        return new MooseGraphics();
    }

    /**
     * Gets the currently bound canvas drawing pipeline target for the execution thread.
     * 
     * @return The active rendering context instance.
     */
    public static Drawable getActiveContext() {
        return activeContext;
    }
}
