package Backend.Model;

public class Rectangle extends Figure {

    public Rectangle(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return eventPoint.getX() > getStartPoint().getX() &&
                eventPoint.getX() < getEndPoint().getX() &&
                eventPoint.getY() > getStartPoint().getY() &&
                eventPoint.getY() < getEndPoint().getY();
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", getStartPoint(), getEndPoint());
    }

}
