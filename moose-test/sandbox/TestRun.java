package sandbox;

import moose.core.Environment;
import moose.core.MooseLauncher;

public class TestRun implements Environment {
    private float time = 0;

    public static void main(String[] args) {
        MooseLauncher.launch(new TestRun());
    }

    @Override
    public void update(float dt) {
        time += dt;
        System.out.println("Seconds passed: " + time);
    }
}
