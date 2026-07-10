package moose.core;

import org.lwjgl.opengl.GLCapabilities;

import moose.graphics.Drawable;

public interface ContextBinder {

    public void bind(Long id, GLCapabilities cache, Drawable drawable);
    
}