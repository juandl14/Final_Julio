package Backend.Model;

public class Ellipse extends Figure {

    public Ellipse(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    protected Point getCenterPoint(){
        return new Point(getStartPoint().getX()+(getxAxis()/2), getStartPoint().getY()+(getyAxis()/2) );
    }


    public double getxAxis() {
        return getEndPoint().getX() - getStartPoint().getX();
    }

    public double getyAxis() {
        return getEndPoint().getY() - getStartPoint().getY();
    }


    @Override
    public boolean belongs(Point eventPoint) {
        return Math.pow((eventPoint.getX() - getCenterPoint().getX())/(getxAxis()/2), 2) + Math.pow((eventPoint.getY() - getCenterPoint().getY())/(getyAxis()/2), 2) <= 1;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, RadioX: %.2f, RadioY: %.2f]", getCenterPoint(), getxAxis() / 2, getyAxis() / 2);
    }

}
