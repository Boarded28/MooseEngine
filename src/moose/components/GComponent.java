package moose.components;

/**
 * The root class for every GUI component
 */
public abstract class GComponent {
    // dimensions
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    // characteristics
    protected boolean isVisible;
    protected boolean isOpaque;
    
    
    /** 
     * Sets the position of the component relative to the screen or component
     */
    protected void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /** 
     * Sets the size of the component
     */
    protected void setDimension(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /** 
     * Change visibility of the component
     */
    protected void shouldBeVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /** 
     * Change background transparency, setting to false allows see through
     */
    protected void shouldBeOpaque(boolean isOpaque) {
        this.isOpaque = isOpaque;
    }

    /** 
     * Returns the width of the component
     */
    protected float getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the component
     */
    protected float getHeight() {
        return this.height;
    }
}