package Backend.Model;

public class Ellipse extends Figure {

    public Ellipse(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    protected Point getCenterPoint(){
        return new Point(getStartPoint().getX()+(getDiffX()/2), getStartPoint().getY()+(getDiffY()/2) );
    }

    @Override
    public boolean containsPoint(Point eventPoint) {
        return Math.pow((eventPoint.getX() - getCenterPoint().getX())/(getDiffX()/2), 2) + Math.pow((eventPoint.getY() - getCenterPoint().getY())/(getDiffY()/2), 2) <= 1;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, RadioX: %.2f, RadioY: %.2f]", getCenterPoint(), getDiffX()/2, getDiffY()/2);
    }
}
