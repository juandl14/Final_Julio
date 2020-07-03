package Backend.Model;

public abstract class Figure {

    public abstract void moveFigure(double newX, double newY);

    public abstract boolean belongs(Point eventPoint);
}
