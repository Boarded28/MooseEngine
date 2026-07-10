package moose.core;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

class MooseEngine {
 
    private final Clock clock;
    private List<Window> activeWindows;
    private List<Window> pendingWindows;
    private ContextBinder binder;

    private static MooseEngine instance = null;

    public MooseEngine() {
        clock = new Clock();
        activeWindows = new ArrayList<>();
        pendingWindows = new ArrayList<>();
        initHardwareCapabilities();
    }

    public static MooseEngine getInstance() {
        if (instance == null) {
            instance = new MooseEngine();
        }
        return instance;
    }

    public void registerBinder(ContextBinder binder) {
        this.binder = binder;
    }

    public void start() {
        if (activeWindows.isEmpty()) {
            activeWindows.addAll(pendingWindows);
            pendingWindows.clear();
        }
        runEngineLoop();
        terminate();
    }

    private void initHardwareCapabilities() {
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);        
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    }

    public void addWindow(Window window) {        
        pendingWindows.add(window);
        System.out.println("New window added: " + window);
    }

    private void runEngineLoop() {
        while (!activeWindows.isEmpty()) {
            // add all pending windows to the active windows
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