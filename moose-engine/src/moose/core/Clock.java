package moose.core;

/**
 * Acting as MooseEngine's heartbeat, the {@code Clock} manages the system's temporal logic.
 * 
 * <p>Running on a dedicated worker thread, it calculates the {@code elapsedTime} between loops 
 * and sequentially executes the program's update logic. Through the use of a strict 
 * {@code deltaTime} accumulator, it ensures that calculations such as 2D physics or AI logic 
 * simulate at a consistent, deterministic pace, completely independent of the visual frame rate.</p>
 * 
 * @author Boardedmind
 * @version 0.1.0-alpha
 * @since 0.1.0-alpha
 */

class Clock {
    private double ticks = 60.0;
    private final double tickRate = 1_000_000_000.0/ticks;
    private final double timeCap = 250_000_000.0;

    private long startTime = System.nanoTime();
    private double accumulatedTime = 0;
    private double alpha;
    
    public void tick() {
        long currentTime = System.nanoTime();
        accumulatedTime += currentTime - startTime;        
        startTime = currentTime;

        if (accumulatedTime >= timeCap) accumulatedTime = timeCap;
    }

    public boolean hasTicksLeft() {
        if (accumulatedTime >= tickRate) {
            accumulatedTime -= tickRate;
            alpha = accumulatedTime/tickRate;
            return true;
        } else return false;
    }

    public float getDelta() {
        return (float) tickRate/1_000_000_000.0f;
    }

    public float getAlpha() {
        return (float) alpha;
    }
}