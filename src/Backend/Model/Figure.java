package Backend.Model;

import javafx.scene.paint.Color;

public abstract class Figure {

    private final Point startPoint, endPoint;
    //
    private Color strokeColor, fillColor;
    private double strokeBorder;
    //movimiento de figuras
    private Point firstPoint;
    private Point previousEvenPoint;


    public Figure(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public double getDiffX() {
        return getEndPoint().getX() - getStartPoint().getX();
    }

    public double getDiffY() {
        return getEndPoint().getY() - getStartPoint().getY();
    }
//    public double getWidth() {
//        return Math.abs(getStartPoint().getX() - getEndPoint().getX());
//    }
//
//    public double getHeight() {
//        return Math.abs(getStartPoint().getY() - getEndPoint().getY());
//    }
//*---------------------------------------------------------------------------------------------
//    public void toDraw(Drawable d) {
//        d.apply( getStartPoint().getX(), getStartPoint().getY(), getWidth(), getHeight() );
//    }
    public void moveFigure(Point startPoint, Point evenPoint) {
        if(firstPoint != startPoint){
            firstPoint = startPoint;
            this.startPoint.plusX(evenPoint.getX() - startPoint.getX());
            this.startPoint.plusY(evenPoint.getY() - startPoint.getY());
            endPoint.plusX(evenPoint.getX() - startPoint.getX());
            endPoint.plusY(evenPoint.getY() - startPoint.getY());
        }else{
            this.startPoint.plusX(evenPoint.getX() - previousEvenPoint.getX());
            this.startPoint.plusY(evenPoint.getY() - previousEvenPoint.getY());
            endPoint.plusX(evenPoint.getX() - previousEvenPoint.getX());
            endPoint.plusY(evenPoint.getY() - previousEvenPoint.getY());
        }
        previousEvenPoint = evenPoint;
    }
//    public void moveFigure(double diffX, double diffY) {
//        startPoint.plusX(diffX);
//        startPoint.plusY(diffY);
//        endPoint.plusX(diffX);
//        endPoint.plusY(diffY);
//    }
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

}
