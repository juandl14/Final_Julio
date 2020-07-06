package Backend.Model;

public class Circle extends Ellipse {

    public Circle(Point startPoint, Point endPoint) {
        super(startPoint, new Point(endPoint.getX(),startPoint.getY() + Math.abs(startPoint.getX() - endPoint.getX())));
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s , Radio: %.2f}]", getCenterPoint(), getWidth()/2);
    }

}