package Backend.Model;

public class Line extends Figure {

    public Line(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    // Restorna siempre falso porque la linea no se puede seleccionar con un click
    @Override
    public boolean containsPoint(Point point) {
        return false;
    }

    @Override
    public String toString() {
        return String.format("Linea [ %s , %s ]", getStartPoint(), getEndPoint());
    }

}