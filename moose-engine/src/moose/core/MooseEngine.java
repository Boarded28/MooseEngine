package moose.core;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Core runtime coordinator and main subsystem manager for MooseEngine.
 * 
 * <p>Implements a singleton control model to initialize platform hardware bindings, 
 * orchestrate window life cycles, and run the primary timing loop.</p>
 * 
 * @author boardedmind
 * @since 0.2.0-alpha
 * @version 0.2.0-alpha
 */
class MooseEngine {
 
    private final Clock clock;
    private List<Window> activeWindows;
    private List<Window> pendingWindows;
    private ContextBinder binder;

    private static MooseEngine instance = null;

    /**
     * Initializes engine timing systems, structures tracking lists, and configures platform hints.
     */
    public MooseEngine() {
        clock = new Clock();
        activeWindows = new ArrayList<>();
        pendingWindows = new ArrayList<>();
        initHardwareCapabilities();
    }

    /**
     * Gets the global singleton instance of the engine execution manager.
     * 
     * @return The active engine coordinator instance.
     */
    public static MooseEngine getInstance() {
        if (instance == null) {
            instance = new MooseEngine();
        }
        return instance;
    }

    /**
     * Registers a context rendering binder to handle graphics context swapping during the render cycle.
     * 
     * @param binder The pipeline binding manager instance.
     */
    public void registerBinder(ContextBinder binder) {
        this.binder = binder;
    }

    /**
     * Commits all initial window queues, boots the execution loop, and disposes resources on shutdown.
     */
    public void start() {
        if (activeWindows.isEmpty()) {
            activeWindows.addAll(pendingWindows);
            pendingWindows.clear();
        }
        runEngineLoop();
        terminate();
    }

    /**
     * Initialized native GLFW system targets and enforces OpenGL 3.3 Core profile specifications.
     */
    private void initHardwareCapabilities() {
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);        
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    }

    /**
     * Stages a newly declared window component to be added to the runtime thread during the next tick.
     * 
     * @param window The target window instance to monitor and display.
     */
    public void addWindow(Window window) {        
        pendingWindows.add(window);
        System.out.println("New window added: " + window);
    }

    /**
     * Executes the primary execution loop routing user inputs, timing slices, physics, and graphics.
     * 
     * <p>Polls native events, executes fixed ticks via the timing accumulator, binds active 
     * graphics contexts sequentially, and performs display buffer swaps.</p>
     */
    private void runEngineLoop() {
        while (!activeWindows.isEmpty()) {
            if (!pendingWindows.isEmpty()) {
                activeWindows.addAll(pendingWindows);
                pendingWindows.clear();
            }

            glfwPollEvents();

            clock.tick();
            while (clock.hasTicksLeft()) {            
                for (int i=activeWindows.size()-1; i>=0;i--) {
                    Window window = activeWindows.get(i);                    
                    if (window.shouldClose()) {                        
                        window.destroy();
                        activeWindows.remove(i);
                        continue;
                    }                                                    
                    window.update(clock.getDelta());                    
                }
            }

            for (int i=0; i<activeWindows.size(); i++) {
                Window window = activeWindows.get(i);

                binder.bind(window.getID(), window.getCacheCapabilities(), window.getDrawable());

                window.render();
                glfwSwapBuffers(window.getID());
            }
        }        
    }

    /**
     * Clears context state assignments, tears down GLFW sub-systems, and cleans up native callback hooks.
     */
    private void terminate() {
        System.out.println("Shutting down MooseEngine...");
        glfwMakeContextCurrent(0);        
        glfwTerminate();
        var callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }
}
