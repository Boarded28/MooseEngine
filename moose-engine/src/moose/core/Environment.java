package moose.core;

/**
 * The central orchestration interface for the MooseEngine core lifecycle.
 * 
 * <p>This interface serves as the primary subsystem coordinator, driving execution by 
 * linking directly to the {@link Clock#logicUpdate(float)} loop.</p>
 * 
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * @Override
 * public void update(float deltaTime) {
 *     // Place your logic here
 *     myObject.x += myObject.speed * deltaTime;
 * }
 * }</pre>
 * 
 * <p>The {@code deltaTime} parameter represents the strict 'fixed time step' provided by 
 * the accumulator model. This ensures updates, such as physics calculations, remain 
 * entirely consistent and deterministic regardless of rendering frame rates.</p>
 * 
 * @see Clock
 * @author Boardedmind
 * @version 1.0.1
 * @since 1.0.0
 */

public interface Environment {

    /**
     * Updates the simulation or game state using a fixed time step.
     * 
     * <p>This method is automatically invoked by the framework's internal {@code Chronometer} 
     * loop on a strict, constant interval. It is intended to contain all state mutation 
     * logic, such as 2D physics transformations, collision detection, and AI updates.</p>
     * 
     * <p>Because this execution is bound to a fixed accumulator step, the provided 
     * {@code deltaTime} value remains constant across invocations. This guarantees that your 
     * simulation behaves identically and deterministically on all hardware, regardless of 
     * fluctuating rendering frame rates.</p>
     * 
     * <h3>Implementation Note:</h3>
     * <p>Do not place rendering, drawing, or visual buffer operations inside this method. 
     * Use this method strictly for calculations and state modifications.</p>
     * 
     * @param deltaTime the constant, fractional time step (in seconds) used to scale 
     *                  physics and simulation logic calculations
     * @see Environment
     * @see Clock
     * @since 1.0.0
    */
    void update(float deltaTime);
}
