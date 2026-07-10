package moose.core;

/**
 * Provides static bootstrapping methods to initialize and run the MooseEngine framework.
 * 
 * <p>Applications must implement the {@link Lifecycle} interface and boot the framework 
 * using this launcher within their main execution entry point.</p>
 * 
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * import moose.core.*;
 * 
 * public class MyMainClass implements Lifecycle {
 *     public static void main(String[] args) {
 *         MooseLauncher.launch(new MyMainClass());
 *         MooseLauncher.start();
 *     }
 * }
 * }</pre>
 * 
 * @author boardedmind
 * @since 0.1.0-alpha
 * @version 0.2.0-alpha
 */
public class MooseLauncher {    

    /**
     * Registers a lifecycle target using a default window configuration profile.
     * 
     * @param lifecycle The core simulation loop implementation to attach.
     */
    public static void launch(Lifecycle lifecycle) {
        MooseEngine engine = MooseEngine.getInstance();
        engine.addWindow(new Window(lifecycle, new MooseConfig(), moose.graphics.GraphicsManager.createDrawable()));
        engine.registerBinder(new moose.graphics.GraphicsManager());
    }

    /**
     * Registers a lifecycle target using tailored window and display properties.
     * 
     * @param lifecycle The core simulation loop implementation to attach.
     * @param config    The custom configuration properties to apply to the window.
     * @see MooseConfig
     */
    public static void launch(Lifecycle lifecycle, MooseConfig config) {
        MooseEngine engine = MooseEngine.getInstance();
        engine.addWindow(new Window(lifecycle, config, moose.graphics.GraphicsManager.createDrawable()));
        engine.registerBinder(new moose.graphics.GraphicsManager());
    }

    /**
     * Starts the underlying engine subsystems and initiates the main application thread loop.
     * 
     * <p>This method blocks execution on the calling thread until all windows are closed.</p>
     */
    public static void start() {
        MooseEngine.getInstance().start();        
    }
}
