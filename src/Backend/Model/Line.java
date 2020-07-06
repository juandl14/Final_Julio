package Backend.Model;

public class Line extends Figure {

    public Line(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    @Override
    public boolean belongs(Point point) {
        return false;
    }
//    @Override
//    public void toDraw(Drawable d) {
//        d.apply( getStartPoint().getX(), getStartPoint().getY(), getEndPoint().getX(), getEndPoint().getY() );
//    }
    @Override
    public String toString() {
        return String.format("Linea [ %s , %s ]", getStartPoint(), getEndPoint());
    }

}