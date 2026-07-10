package moose.core;

/**
 * Manages engine timing, fixed time-step accumulation, and frame interpolation.
 * 
 * <p>Tracks elapsed time between iterations to drive deterministic simulation 
 * updates independently of the rendering frame rate.</p>
 * 
 * @author Boardedmind
 * @since 0.1.0-alpha
 * @version 0.2.0-alpha
 */
class Clock {
    private double ticks = 60.0;
    private final double tickRate = 1_000_000_000.0/ticks;
    private final double timeCap = 250_000_000.0;

    private long startTime = System.nanoTime();
    private double accumulatedTime = 0;
    private double alpha;
    
    /**
     * Measures elapsed nanoseconds since the last invocation and increments the accumulator.
     * 
     * <p>Enforces an internal cap to prevent an accumulation spike (the "spiral of death") 
     * during heavy performance stutters.</p>
     */
    public void tick() {
        long currentTime = System.nanoTime();
        accumulatedTime += currentTime - startTime;        
        startTime = currentTime;

        if (accumulatedTime >= timeCap) accumulatedTime = timeCap;
    }

    /**
     * Checks if enough time has accumulated to execute a fixed simulation tick.
     * 
     * <p>Consumes one fixed time-step slice from the accumulator per call and 
     * calculates the remaining alpha interpolation ratio.</p>
     * 
     * @return True if a simulation tick should execute; false otherwise.
     */
    public boolean hasTicksLeft() {
        if (accumulatedTime >= tickRate) {
            accumulatedTime -= tickRate;
            alpha = accumulatedTime/tickRate;
            return true;
        } else return false;
    }

    /**
     * Gets the constant, fractional time step value used for simulation logic scaling.
     * 
     * @return The target fixed delta time step in seconds.
     */
    public float getDelta() {
        return (float) tickRate/1_000_000_000.0f;
    }

    /**
     * Gets the current frame interpolation fraction between simulation states.
     * 
     * <p>Used by the rendering system to smoothly blend object positions between 
     * the previous and current physics ticks.</p>
     * 
     * @return The interpolation ratio bounded between 0.0 and 1.0.
     */
    public float getAlpha() {
        return (float) alpha;
    }
}
