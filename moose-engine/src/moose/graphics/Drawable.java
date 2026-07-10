package moose.graphics;

public interface Drawable {

    public void init(int width, int height);

    public void destroy();

    public void show(long windowID);

    public void resize(int width, int height);

    public void paintBackground(float r, float g, float b, float a);

    public void paintColor(float r, float g, float b, float a);
    
    public void drawRectangle(float x, float y, float width, float height);
    
}
