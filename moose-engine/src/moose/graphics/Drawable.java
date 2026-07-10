package moose.graphics;

/**
 * Defines a rendering canvas and primitive drawing pipeline for window contexts.
 *
 * @author boardedmind
 * @since 0.2.0-alpha
 * @version 0.2.0-alpha
 */
public interface Drawable {

    /**
     * Initializes internal graphics assets, shaders, and rendering pipeline layouts.
     *
     * @param width  The viewport surface initialization width in pixels.
     * @param height The viewport surface initialization height in pixels.
     */
    public void init(int width, int height);

    /**
     * Disposes and releases all allocated native graphics memory and buffer objects.
     */
    public void destroy();

    /**
     * Makes the allocated window surface visible to the user viewport.
     *
     * @param windowID The unique native memory handle address of the target window.
     */
    public void show(long windowID);

    /**
     * Adjusts internal coordinate projections and rendering viewport scale sizes.
     *
     * @param width  The target new drawing width in pixels.
     * @param height The target new drawing height in pixels.
     */
    public void resize(int width, int height);

    /**
     * Configures the clearing color values applied to the background frame buffer.
     *
     * @param r The red channel value bounded from 0.0f to 1.0f.
     * @param g The green channel value bounded from 0.0f to 1.0f.
     * @param b The blue channel value bounded from 0.0f to 1.0f.
     * @param a The alpha transparency value bounded from 0.0f to 1.0f.
     */
    public void paintBackground(float r, float g, float b, float a);

    /**
     * Sets the active color state used to shade upcoming geometric primitives.
     *
     * @param r The red channel value bounded from 0.0f to 1.0f.
     * @param g The green channel value bounded from 0.0f to 1.0f.
     * @param b The blue channel value bounded from 0.0f to 1.0f.
     * @param a The alpha transparency value bounded from 0.0f to 1.0f.
     */
    public void paintColor(float r, float g, float b, float a);
    
    /**
     * Appends a flat 2D rectangle primitive into the active rendering command queue.
     *
     * @param x      The lower-left pixel horizontal screen alignment coordinate.
     * @param y      The lower-left pixel vertical screen alignment coordinate.
     * @param width  The total horizontal span thickness layout dimension in pixels.
     * @param height The total vertical span thickness layout dimension in pixels.
     */
    public void drawRectangle(float x, float y, float width, float height);
    
}
