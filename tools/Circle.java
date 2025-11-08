package tools;

public class Circle {
    public float x, y, r;
    
    public Circle(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Circle cloneCircle() {
        return new Circle(x, y, r);
    }
}
