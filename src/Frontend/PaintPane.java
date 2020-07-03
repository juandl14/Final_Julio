package Frontend;

import Backend.CanvasState;
import Backend.Model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton lineButton = new ToggleButton("Linea");

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, ellipseButton, squareButton, lineButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1); // cambia el ancho del contorno de las figuras
		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return ;
			}

			Figure newFigure = null;
			if(lineButton.isSelected()) {
				newFigure = new Line(startPoint, endPoint);
			} else if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			if(rectangleButton.isSelected()) {
				newFigure = new Rectangle(startPoint, endPoint);
			}else if(circleButton.isSelected()) {
				newFigure = new Circle(startPoint, endPoint);
			}else if(ellipseButton.isSelected()) {
				newFigure = new Ellipse(startPoint, endPoint);
			} else if(squareButton.isSelected()) {
				newFigure = new Square(startPoint, endPoint);
			}else if(lineButton.isSelected()) {
				newFigure = new Line(startPoint, endPoint);
			} else {
				return ;
			}
			canvasState.addFigure(newFigure);
			startPoint = null;
			redrawCanvas();
		});
		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if(figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure = figure;
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});
		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if(selectedFigure instanceof Rectangle) {
					Rectangle rectangle = (Rectangle) selectedFigure;
					rectangle.moveFigure(diffX, diffY);
				} else if(selectedFigure instanceof Ellipse) {
					Ellipse ellipse = (Ellipse) selectedFigure;
					ellipse.moveFigure(diffX, diffY);
				} else if (selectedFigure instanceof Line) {
					Line line = (Line) selectedFigure;
					line.moveFigure(diffX, diffY);
				}
				redrawCanvas();
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(fillColor);
			if (figure instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) figure;
				gc.fillRect(rectangle.getStartPoint().getX(), rectangle.getStartPoint().getY(),
						rectangle.getWidth(), rectangle.getHeight());
				gc.strokeRect(rectangle.getStartPoint().getX(), rectangle.getStartPoint().getY(),
						rectangle.getWidth(), rectangle.getHeight());
			}else if(figure instanceof Ellipse) {
				Ellipse ellipse = (Ellipse) figure;
				gc.fillOval(ellipse.getStartPoint().getX(), ellipse.getStartPoint().getY(), ellipse.getxAxis(), ellipse.getyAxis());
				gc.strokeOval(ellipse.getStartPoint().getX(), ellipse.getStartPoint().getY(), ellipse.getxAxis(), ellipse.getyAxis());
			}else if (figure instanceof Line){
				Line line =  (Line) figure ;
				gc.strokeLine(line.getStartPoint().getX() , line.getStartPoint().getY(), line.getEndPoint().getX(), line.getEndPoint().getY());
			}
		}
	}

	boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.belongs(eventPoint);
	}

}
