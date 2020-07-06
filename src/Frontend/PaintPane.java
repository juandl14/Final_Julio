package Frontend;

import Backend.AreaSelected;
import Backend.CanvasState;
import Backend.Drawable;
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
	ToggleButton toFrontButton = new ToggleButton("Al Frente");
	ToggleButton toBackButton = new ToggleButton("Al Fondo");
	ColorPicker strokeColorPicker = new ColorPicker(Color.BLACK);
	Slider strokeSlider = new Slider(1,50,1);
	Label strokeLabel = new Label("Borde");
	Label fillLabel = new Label("Relleno");
	ColorPicker fillColorPicker = new ColorPicker(Color.YELLOW);

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	AreaSelected areaSelected;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {

		this.canvasState = canvasState;
		this.statusPane = statusPane;
		this.areaSelected = new AreaSelected(new Point(0,0), canvasState.figures());

		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, ellipseButton,
				squareButton, lineButton, eraseButton, toBackButton, toFrontButton};
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

			if (selectionButton.isSelected()) {
				if (!startPoint.equals(endPoint) && areaSelected.isEmpty()) {
					areaSelected = new AreaSelected(new Rectangle(startPoint, endPoint), canvasState.figures());
				} else if (startPoint.equals(endPoint)) {
					areaSelected =  new AreaSelected(new Point(startPoint.getX(), startPoint.getY()), canvasState.figures());
				}
				selectionLabel();
			} else {
				Figure newFigure;
				if (lineButton.isSelected()) {
					newFigure = new Line(startPoint, endPoint);
				} else if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
					return;
				} else if (rectangleButton.isSelected()) {
					newFigure = new Rectangle(startPoint, endPoint);
				} else if (circleButton.isSelected()) {
					newFigure = new Circle(startPoint, endPoint);
				} else if (ellipseButton.isSelected()) {
					newFigure = new Ellipse(startPoint, endPoint);
				} else if (squareButton.isSelected()) {
					newFigure = new Square(startPoint, endPoint);
				} else {
					return;
				}
				newFigure.setStrokeBorder(strokeSlider.getValue());
				newFigure.setStrokeColor(strokeColorPicker.getValue());
				newFigure.setFillColor(fillColorPicker.getValue());
				canvasState.addFigure(newFigure);
			}

			startPoint = null;
			redrawCanvas();

		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();

			for(Figure figure : canvasState.figures()) {
				if(figure.containsPoint(eventPoint)) {
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

		// Se deseleccionan las figuras si te presiona un boton para crear una nueva figura y el boton de selecion
		selectionButton.setOnAction(click -> clearSelection());

		rectangleButton.setOnAction(click -> clearSelection());

		circleButton.setOnAction(click -> clearSelection());

		ellipseButton.setOnAction(click -> clearSelection());

		squareButton.setOnAction(click -> clearSelection());

		lineButton.setOnAction(click -> clearSelection());

		toFrontButton.setOnAction(click -> {
			if (!areaSelected.isEmpty())
				areaSelected.figures().forEach(canvasState::toFront);
			redrawCanvas();
		});

		toBackButton.setOnAction(click -> {
			if (!areaSelected.isEmpty())
				areaSelected.figures().forEach(canvasState::toBack);
			redrawCanvas();
		});

		eraseButton.setOnAction(click -> {
			if (!areaSelected.isEmpty()) {
				areaSelected.figures().forEach(canvasState::removeFigure);
				areaSelected.clear();
			}
			redrawCanvas();
		});

		strokeColorPicker.setOnAction(click -> {
			if (!areaSelected.isEmpty()){
				areaSelected.figures().forEach(figure -> figure.setStrokeColor(strokeColorPicker.getValue()));
			}
			redrawCanvas();
		});

		fillColorPicker.setOnAction(click -> {
			if (!areaSelected.isEmpty()){
				areaSelected.figures().forEach(figure -> figure.setFillColor(fillColorPicker.getValue()));
			}
			redrawCanvas();
		});

		strokeSlider.setOnMouseDragged(slide -> {
			if (!areaSelected.isEmpty()){
				areaSelected.figures().forEach(figure -> figure.setStrokeBorder(strokeSlider.getValue()));
			}
			redrawCanvas();
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				if(!areaSelected.isEmpty()){
					areaSelected.figures().forEach(figure -> figure.moveFigure(startPoint, eventPoint));
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
			if(areaSelected.contains(figure)) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(figure.getStrokeColor());
			}

			gc.setFill(figure.getFillColor());
			gc.setLineWidth(figure.getStrokeBorder());

			if (figure instanceof Rectangle) {
				toDraw(figure, (x, y, w, h) -> gc.fillRect(x, y, w, h));
				toDraw(figure, (x, y, w, h) -> gc.strokeRect(x, y, w, h));
			} else if(figure instanceof Ellipse) {
				toDraw(figure, (x, y, w, h) -> gc.fillOval(x, y, w, h));
				toDraw(figure, (x, y, w, h) -> gc.strokeOval(x, y, w, h));
			} else if (figure instanceof Line){
				toDraw(figure, (x1, y1, x2, y2) -> gc.strokeLine(x1, y1, x2+x1, y2+y1));
			}
		}
	}

	private void selectionLabel() {
		if (!areaSelected.isEmpty()) {
			StringBuilder label = new StringBuilder("Se seleccionó: ");
			for (Figure selected : areaSelected.figures()) {
				label.append(selected.toString());
			}
			statusPane.updateStatus(label.toString());
		} else {
			statusPane.updateStatus("Ninguna figura encontrada");
		}
	}

	private void clearSelection() {
		areaSelected.clear();
		redrawCanvas();
	}

	public void toDraw(Figure figure, Drawable d){
		d.apply(figure.getStartPoint().getX(), figure.getStartPoint().getY(), figure.getDiffX(), figure.getDiffY());
	}

}
