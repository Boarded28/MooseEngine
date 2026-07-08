package moose.core;

/**
 * The main entry point for starting the MooseEngine framework.
 * 
 * <p>The main class of the game or simulation must implement the {@link Nexus} interface. 
 * Then, within the application's {@code main} method, this framework must be initialized 
 * using the launcher:</p>
 * 
 * <pre>{@code
 * import com.mooseengine.core.*;
 * 
 * public class MyMainClass implements Nexus {
 *     public static void main(String[] args) {
 *         MooseLauncher.launch(new MyMainClass());
 *     }
 * }
 * }</pre>
 * 
 * @author boardedmind
 * @version 1.0.0
 * @since 1.0.0
 */

public class MooseLauncher {

    public static void launch(Environment environment) {
        Clock engine = new Clock(environment);
        engine.start();
    }
}