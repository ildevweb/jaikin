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

    // Linear interpolation
    public static Circle interpolate(Circle a, Circle b, float t) {
        float x = a.x * (1 - t) + b.x * t;
        float y = a.y * (1 - t) + b.y * t;
        return new Circle(x, y, 1f);
    }

}
