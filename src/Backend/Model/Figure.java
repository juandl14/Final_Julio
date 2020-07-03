package Backend.Model;

public abstract class Figure {

    private final Point startPoint, endPoint;

    public Figure(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public void moveFigure(double diffX, double diffY) {
        startPoint.plusX(diffX);
        startPoint.plusY(diffY);
        endPoint.plusX(diffX);
        endPoint.plusY(diffY);
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }
    
    public abstract boolean belongs(Point eventPoint);




}
