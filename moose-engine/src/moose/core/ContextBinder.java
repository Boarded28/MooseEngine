package moose.core;

import org.lwjgl.opengl.GLCapabilities;
import moose.graphics.Drawable;

/**
 * Binds graphical contexts to specific window targets and rendering routines.
 *
 * @author boardedmind
 * @since 0.2.0-alpha
 * @version 0.2.0-alpha
 */
public interface ContextBinder {

    /**
     * Binds a rendering lifecycle target to an active window context.
     *
     * @param id       The unique native memory address or handle of the target window.
     * @param cache    The OpenGL capabilities instance mapped to the active context thread.
     * @param drawable The graphics pipeline component containing instructions to execute.
     */
    public void bind(Long id, GLCapabilities cache, Drawable drawable);
    
}
