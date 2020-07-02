package Backend.Model;

public class Ellipse extends Figure {

    protected Point centerPoint;
    protected final double MayorAxis, MinorAxis;

    public Ellipse(Point centerPoint, double MayorAxis, double MinorAxis) {
        this.centerPoint = centerPoint;
        this.MayorAxis = MayorAxis;
        this.MinorAxis = MinorAxis;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getMayorAxis() {
        return MayorAxis;
    }

    public double getMinorAxis() {
        return MinorAxis;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, MayorAxis, MinorAxis);
    }

//    public double area() {
//        return Math.PI / 4 * MayorAxis * MinorAxis;
//    }
//    public double perimeter() {
//        return Math.PI / 2 * (MayorAxis + MinorAxis);
//    }

}
