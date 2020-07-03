package Backend.Model;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public double getWidth() {
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }

    public double getHeight() {
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }

    @Override
    public void moveFigure(double newX, double newY) {
        topLeft.plusX(newX);
        topLeft.plusY(newY);
        bottomRight.plusX(newX);
        bottomRight.plusY(newY);
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return eventPoint.getX() > topLeft.getX() &&
                eventPoint.getX() < bottomRight.getX() &&
                eventPoint.getY() > topLeft.getY() &&
                eventPoint.getY() < bottomRight.getY();
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

}
