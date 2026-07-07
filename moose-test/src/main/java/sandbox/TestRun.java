package sandbox;

import com.mooseengine.core.MooseLauncher;
import com.mooseengine.core.Nexus;

public class TestRun implements Nexus {
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