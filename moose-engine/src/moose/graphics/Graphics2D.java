package moose.graphics;

/**
 * Static utility interface for executing 2D rendering commands on the active context thread.
 *
 * @author boardedmind
 * @since 0.2.0-alpha
 * @version 0.2.0-alpha
 */
public class Graphics2D {

    /**
     * Sets the active drawing color state for upcoming geometric shapes on the current thread.
     *
     * @param r The red channel value bounded from 0.0f to 1.0f.
     * @param g The green channel value bounded from 0.0f to 1.0f.
     * @param b The blue channel value bounded from 0.0f to 1.0f.
     * @param a The alpha transparency value bounded from 0.0f to 1.0f.
     */
    public static void paintColor(float r, float g, float b, float a) {
        GraphicsManager.getActiveContext().paintColor(r, g, b, a);
    }

    /**
     * Configures the clearing color values applied to the active background frame buffer.
     *
     * @param r The red channel value bounded from 0.0f to 1.0f.
     * @param g The green channel value bounded from 0.0f to 1.0f.
     * @param b The blue channel value bounded from 0.0f to 1.0f.
     * @param a The alpha transparency value bounded from 0.0f to 1.0f.
     */
    public static void paintBackground(float r, float g, float b, float a) {
        GraphicsManager.getActiveContext().paintBackground(r, g, b, a);
    }
    
    /**
     * Renders a flat 2D rectangle primitive using the thread's active context pipeline.
     *
     * @param x      The lower-left horizontal coordinate alignment in pixels.
     * @param y      The lower-left vertical coordinate alignment in pixels.
     * @param width  The horizontal span layout dimension in pixels.
     * @param height The vertical span layout dimension in pixels.
     */
    public static void drawRect(float x, float y, float width, float height) {
        GraphicsManager.getActiveContext().drawRectangle(x, y, width, height);
    }
}
