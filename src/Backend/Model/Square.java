package Backend.Model;

public class Square extends Rectangle{

    public Square(Point startPoint, Point endPoint) {
        super(startPoint, new Point(endPoint.getX(), startPoint.getY() + Math.abs(endPoint.getX() - startPoint.getX())));
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getStartPoint(), getEndPoint());
    }

}
