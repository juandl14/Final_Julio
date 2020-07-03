package Backend.Model;

public class Circle extends Ellipse {

    public Circle(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    public double getRadius() {
        return getxAxis();
    }

    @Override
    public boolean belongs(Point eventPoint) {
       return Math.sqrt(Math.pow(getCenterPoint().getX() - eventPoint.getX(), 2) + Math.pow(getCenterPoint().getY() - eventPoint.getY(), 2)) < getRadius();
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s , Radio: %.2f}]", getCenterPoint(), getRadius());
    }


}
