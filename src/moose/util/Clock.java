package moose.util;

public class Clock {

    private static Clock instance;
    private static float delta = 0;
    private static long lastFrameTime = 0;

    // use this to call
    public static Clock getInstance() {
        if (instance == null) {
            instance = new Clock();
        }
        return instance;
    }

    public static void update() {
        long currentFrameTime = System.nanoTime();
        delta = (float)(currentFrameTime - lastFrameTime)/1_000_000_000.0f;
        lastFrameTime = currentFrameTime;
    }

    public static float getDelta() {
        return delta;
    }
}