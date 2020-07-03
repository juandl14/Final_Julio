package Backend.Model;

public class Circle extends Ellipse {

    public Circle(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s , Radio: %.2f}]", getCenterPoint(), getRadius());
    }

    public double getRadius() {
        return getxAxis();
    }

}
