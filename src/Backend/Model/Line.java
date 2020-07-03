package Backend.Model;

import javafx.scene.paint.Color;

public class Line extends Figure {

    public Line(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    @Override
    public boolean belongs(Point point) {
        return false;
    }

    @Override
    public String toString() {
        return String.format("Linea [ %s , %s ]", getStartPoint(), getEndPoint());
    }

}