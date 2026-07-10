package sandbox;

import moose.core.*;
import moose.graphics.Graphics2D;

public class TestRun {

    public static void main(String[] args) {
        MooseLauncher.launch(new WindowTestA());
        MooseLauncher.launch(new WindowTestB());
        MooseLauncher.start();
    }
}

class WindowTestA implements Lifecycle {
    float r, g, b, a;
    double time;
    boolean allow = true;

    public void update(float dt) {
        time += dt;
        r = (float) (Math.sin(time) * 0.5 + 0.5);
        g = (float) (Math.sin(time + 2) * 0.5 + 0.5);
        b = (float) (Math.sin(time + 4) * 0.5 + 0.5);
        
        if (time >= 20 && allow) {
            MooseLauncher.launch(new WindowTestA());
            allow = false;
        }
    }

    public void render() {
        Graphics2D.paintBackground(r, g, b, 1);
    }
}

class WindowTestB implements Lifecycle {
    float r, g, b, a;
    double time;

    public void update(float dt) {
        time += dt;
        b = (float) (Math.sin(time) * 0.5 + 0.5);
        g = (float) (Math.sin(time + 2) * 0.5 + 0.5);
        r = (float) (Math.sin(time + 4) * 0.5 + 0.5);
    }

    public void render() {
        Graphics2D.paintBackground(r, g, b, 1);
    }
}