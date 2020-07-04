package Frontend;

import Backend.CanvasState;
import Backend.Model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton lineButton = new ToggleButton("Línea");
	ToggleButton eraseButton = new ToggleButton("Borrar");
	ColorPicker strokeColorPicker = new ColorPicker(Color.BLACK);
	Slider strokeSlider = new Slider(1,50,1);
	Label strokeLabel = new Label("Borde");
	Label fillLabel = new Label("Relleno");
	ColorPicker fillColorPicker = new ColorPicker(Color.YELLOW);

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {

		this.canvasState = canvasState;
		this.statusPane = statusPane;

		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, ellipseButton,
				squareButton, lineButton, eraseButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		strokeSlider.setShowTickMarks(true);
		strokeSlider.setShowTickLabels(true);
		buttonsBox.getChildren().add(strokeLabel);
		buttonsBox.getChildren().add(strokeSlider) ;
		buttonsBox.getChildren().add(strokeColorPicker) ;
		buttonsBox.getChildren().add(fillLabel);
		buttonsBox.getChildren().add(fillColorPicker) ;
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999999");
		buttonsBox.setPrefWidth(100);

		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if (startPoint == null) {
				return;
			}

			Figure newFigure;
			if (lineButton.isSelected()) {
				newFigure = new Line(startPoint, endPoint);
			} else if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return;
			} else if(rectangleButton.isSelected()) {
				newFigure = new Rectangle(startPoint, endPoint);
			} else if(circleButton.isSelected()) {
				newFigure = new Circle(startPoint, endPoint);
			} else if(ellipseButton.isSelected()) {
				newFigure = new Ellipse(startPoint, endPoint);
			} else if(squareButton.isSelected()) {
				newFigure = new Square(startPoint, endPoint);
			} else if(lineButton.isSelected()) {
				newFigure = new Line(startPoint, endPoint);
			} else {
				return ;
			}
			newFigure.setStrokeBorder(strokeSlider.getValue());
			newFigure.setStrokeColor(strokeColorPicker.getValue());
			newFigure.setFillColor(fillColorPicker.getValue());
			canvasState.addFigure(newFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figure.belongs(eventPoint)) {
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
					if(figure.belongs(eventPoint)) {
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

		eraseButton.setOnAction(click -> {
			if (selectedFigure != null) {
				canvasState.removeFigure(selectedFigure);
				selectedFigure = null;
			}
			redrawCanvas();
		});

		strokeColorPicker.setOnAction(click -> {
			if (selectedFigure != null){
				selectedFigure.setStrokeColor(strokeColorPicker.getValue());
			}
			redrawCanvas();
		});

		fillColorPicker.setOnAction(click -> {
			if (selectedFigure != null){
				selectedFigure.setFillColor(fillColorPicker.getValue());
			}
			redrawCanvas();
		});

		strokeSlider.setOnMouseDragged(slide -> {
			if (selectedFigure != null){
				selectedFigure.setStrokeBorder(strokeSlider.getValue());
			}
			redrawCanvas();
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 50;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 50;
				if(selectedFigure != null){
					selectedFigure.moveFigure(diffX,diffY);
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
				gc.setStroke(figure.getStrokeColor());
			}
			gc.setFill(figure.getFillColor());
			gc.setLineWidth(figure.getStrokeBorder());
			if (figure instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) figure;
				gc.fillRect(rectangle.getStartPoint().getX(), rectangle.getStartPoint().getY(),
						rectangle.getWidth(), rectangle.getHeight());
				gc.strokeRect(rectangle.getStartPoint().getX(), rectangle.getStartPoint().getY(),
						rectangle.getWidth(), rectangle.getHeight());
			}else if(figure instanceof Ellipse) {
				Ellipse ellipse = (Ellipse) figure;
				gc.fillOval(ellipse.getStartPoint().getX(), ellipse.getStartPoint().getY(),
						ellipse.getxAxis(), ellipse.getyAxis());
				gc.strokeOval(ellipse.getStartPoint().getX(), ellipse.getStartPoint().getY(),
						ellipse.getxAxis(), ellipse.getyAxis());
			}else if (figure instanceof Line){
				Line line =  (Line) figure ;
				gc.strokeLine(line.getStartPoint().getX(), line.getStartPoint().getY(),
						line.getEndPoint().getX(), line.getEndPoint().getY());
			}
		}
	}

}
