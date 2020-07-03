package Backend.Model;

public class Ellipse extends Figure {

    private final Point topLeft, bottomRight;
    private final double xAxis, yAxis;

    public Ellipse(Point topLeft, Point bottomRight){
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        xAxis = bottomRight.getX() - topLeft.getX();
        yAxis = bottomRight.getY() - topLeft.getY();
    }

    public Point getCenterPoint(){
        return new Point(topLeft.getX()+(xAxis/2), topLeft.getY()+(yAxis/2) );
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public double getxAxis() {
        return xAxis;
    }

    public double getyAxis() {
        return yAxis;
    }

    @Override
    public void moveFigure(double newX, double newY) {
        topLeft.plusX(newX);
        topLeft.plusY(newY);
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return Math.pow((eventPoint.getX() - getCenterPoint().getX())/(yAxis/2), 2) + Math.pow((eventPoint.getY() - getCenterPoint().getY())/(yAxis/2), 2) <= 1;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, Rx: %.2f, Ry: %.2f]", getCenterPoint(), xAxis, yAxis);
    }

}
