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
 * @version 1.0.1
 * @since 1.0.0
 */

class Clock implements Runnable {

    private Thread engine_thread;

    private double ticks = 60.0;
    private final double tickRate = 1_000_000_000/ticks;
    private final double timeCap = 250_000_000;

    private boolean isRunning = false;

    private Environment updateable;

    public Clock(Environment updateable) {
        engine_thread = new Thread(this);
        this.updateable = updateable;
    }

    public void start() {
        engine_thread.start();
        isRunning = engine_thread.isAlive();
    }

    private void logicUpdate(float delta) {
        updateable.update(delta);
    }

    @Override
    public void run() {
        tickUpdate();
    }

    private void tickUpdate() {
        double accumulatedTime = 0;
        long startTime = System.nanoTime();

        while(isRunning) {
            long currentTime = System.nanoTime();            
            double deltaTime = currentTime - startTime;
            startTime = currentTime;

            accumulatedTime += deltaTime;

            float deltaF = (float) tickRate/1_000_000_000.0f;

            // safety check for massive lag spike to prevent over ticking
            if(accumulatedTime >= timeCap) accumulatedTime = timeCap;

            while(accumulatedTime >= tickRate) {
                // update                
                logicUpdate(deltaF);
                // drain accumulated time
                accumulatedTime -= tickRate;
            }
        }
    }

}