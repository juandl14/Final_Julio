package Backend.Model;

public class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, radius,  radius);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s , Radio: %.2f}]", centerPoint, MayorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getRadius() {
        return getMayorAxis();
    }

}
