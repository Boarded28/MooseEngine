package moose.core;

/**
 * The main entry point for starting the MooseEngine framework.
 * 
 * <p>The main class of the game or simulation must implement the {@link Lifecycle} interface. 
 * Then, within the application's {@code main} method, this framework must be initialized 
 * using the launcher:</p>
 * 
 * <pre>{@code
 * import moose.core.*;
 * 
 * public class MyMainClass implements Lifecycle {
 *     public static void main(String[] args) {
 *         MooseLauncher.launch(new MyMainClass());
 *     }
 * }
 * }</pre>
 * 
 * @author boardedmind
 * @version 0.1.0-alpha
 * @since 0.1.0-alpha
 */
public class MooseLauncher {    

    /**
     * Launch the program with default values, only passing the {@link Lifecycle}.
     * @param lifecycle
     */
    public static void launch(Lifecycle lifecycle) {
        MooseEngine engine = MooseEngine.getInstance();
        engine.addWindow(new Window(lifecycle, new MooseConfig(), moose.graphics.GraphicsManager.createDrawable()));
        engine.registerBinder(new moose.graphics.GraphicsManager());
    }


    /**
     * Launch the program with user configured values, passing {@link Lifecycle} and {@link MooseConfig}.
     * @param lifecycle
     * @param config
     * @see MooseConfig
     */
    public static void launch(Lifecycle lifecycle, MooseConfig config) {
        MooseEngine engine = MooseEngine.getInstance();
        engine.addWindow(new Window(lifecycle, config, moose.graphics.GraphicsManager.createDrawable()));
        engine.registerBinder(new moose.graphics.GraphicsManager());
    }

    public static void start() {
        MooseEngine.getInstance().start();        
    }

}