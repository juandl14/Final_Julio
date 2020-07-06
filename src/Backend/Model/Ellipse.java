package Backend.Model;

public class Ellipse extends Figure {

    public Ellipse(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    protected Point getCenterPoint(){
        return new Point(getStartPoint().getX()+(getWidth()/2), getStartPoint().getY()+(getHeight()/2) );
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return Math.pow((eventPoint.getX() - getCenterPoint().getX())/(getWidth()/2), 2) + Math.pow((eventPoint.getY() - getCenterPoint().getY())/(getHeight()/2), 2) <= 1;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, RadioX: %.2f, RadioY: %.2f]", getCenterPoint(), getWidth()/2, getHeight()/2);
    }

}
