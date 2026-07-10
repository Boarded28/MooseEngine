package moose.core;

/**
 * Orchestrates the central runtime execution loop for the engine.
 * 
 * <p>Coordinates core subsystems by linking directly into the 
 * internal engine execution loop.</p>
 * 
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * @Override
 * public void update(float deltaTime) {
 *     position.x += speed * deltaTime;
 * }
 * 
 * @Override
 * public void render() {
 *     MooseGraphics.drawRectangle(position.x, position.y, 50, 50);
 * }
 * }</pre>
 * 
 * @author Boardedmind
 * @since 0.1.0-alpha
 * @version 0.2.0-alpha
 * @see Clock
 */
public interface Lifecycle {

    /**
     * Updates the simulation or game state using a fixed time step.
     * 
     * <p>Invoked on a strict, constant interval by the engine accumulator loop. 
     * Handles deterministic state logic like physics calculations, transforms, 
     * collision detection, and AI routines.</p>
     * 
     * <p><b>Constraints:</b> Do not place rendering, drawing, or visual buffer 
     * operations inside this method.</p>
     * 
     * @param deltaTime The constant, fractional time step in seconds.
     * @since 0.1.0-alpha
     */
    void update(float deltaTime);

    /**
     * Renders the current frame visual elements to the screen buffer.
     * 
     * <p>Invoked continuously by the engine loop immediately following the 
     * update cycle. Handles canvas drawing, matrix transformations, and 
     * graphical pipeline flushing.</p>
     * 
     * <p><b>Constraints:</b> Do not modify game or simulation states inside 
     * this method to maintain rendering and logic decoupling.</p>
     * 
     * @since 0.2.0-alpha
     */
    void render();
}
