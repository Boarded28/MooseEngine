package moose.graphics;

public class Graphics2D {

    public static void paintColor(float r, float g, float b, float a) {
        GraphicsManager.getActiveContext().paintColor(r, g, b, a);
    }

    public static void paintBackground(float r, float g, float b, float a) {
        GraphicsManager.getActiveContext().paintBackground(r, g, b, a);
    }
    
    public static void drawRect(float x, float y, float width, float height) {
        GraphicsManager.getActiveContext().drawRectangle(x, y, width, height);
    }
}