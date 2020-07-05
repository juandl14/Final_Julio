package Backend.Model;

import javafx.scene.paint.Color;

public abstract class Figure {

    private final Point startPoint, endPoint;
    private Color strokeColor, fillColor;
    private double strokeBorder;

    public Figure(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public void moveFigure(double diffX, double diffY) {
        startPoint.plusX(diffX);
        startPoint.plusY(diffY);
        endPoint.plusX(diffX);
        endPoint.plusY(diffY);
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public double getStrokeBorder() {
        return strokeBorder;
    }

    public void setStrokeBorder(double strokeBorder) {
        this.strokeBorder = strokeBorder;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public abstract boolean belongs(Point eventPoint);

    public abstract void toDraw(Drawable d);

}
