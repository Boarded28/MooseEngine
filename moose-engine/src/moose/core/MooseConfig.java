package moose.core;

/**
 * Configuration settings for the MooseEngine window and display lifecycle.
 *
 * @author boardedmind
 * @since 0.2.0-alpha
 * @version 0.2.0-alpha
 */

public class MooseConfig {

    int width;
    int height;
    String title;
    int vsync;
    int resizable;

    public MooseConfig() {
        this.width = 800;
        this.height = 600;
        this.title = "MooseEngine v0.1.0-alpha";
        this.vsync = 0;
        this.resizable = 0;
    }

    public MooseConfig setSize(int width, int height) {
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("Window dimensions must be greater than zero!");
        this.width = width;
        this.height = height;
        return this;
    }

    public MooseConfig setTitle(String title) {
        this.title = title;
        return this;
    }

    public MooseConfig setVsync(boolean vsync) {
        this.vsync = vsync ? 1 : 0;
        return this;
    }

    public MooseConfig setResizable(boolean resizable) {
        this.resizable = resizable ? 1 : 0;
        return this;
    }

}
