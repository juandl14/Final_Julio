package Backend.Model;

public class Ellipse extends Figure {

    private final Point centerPoint;
    private final double xAxis, yAxis;

    public Ellipse(Point startPoint, Point endPoint){
        xAxis = (endPoint.getX() - startPoint.getX())/2;
        yAxis = (endPoint.getY() - startPoint.getY())/2;
        centerPoint = new Point(startPoint.getX()+xAxis, startPoint.getY()+yAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getxAxis() {
        return xAxis;
    }

    public double getyAxis() {
        return yAxis;
    }

    @Override
    public void moveFigure(double newX, double newY) {
        centerPoint.plusX(newX);
        centerPoint.plusY(newY);
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return Math.pow((eventPoint.getX() - centerPoint.getX())/xAxis, 2) + Math.pow((eventPoint.getY() - centerPoint.getY())/yAxis, 2) <= 1;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, Rx: %.2f, Ry: %.2f]", centerPoint, xAxis, yAxis);
    }

}
