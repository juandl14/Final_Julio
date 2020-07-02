package Backend.Model;

public class Line extends Figure{

    private final Point top, bottom;

    public Line(Point top, Point bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    public Point getTop() {
        return top;
    }

    public Point getBottom() {
        return bottom;
    }

    @Override
    public String toString() {
        return String.format("Linea [ %s , %s ]", top, bottom);
    }

}
