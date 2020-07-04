package Backend;

import Backend.Model.Figure;
import Backend.Model.Point;
import Backend.Model.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class AreaSelected {

    private final List<Figure> selectedFigures = new ArrayList<>();

    public AreaSelected(Rectangle areaSelected, Iterable<Figure> figureList) {
        for (Figure figure : figureList) {
            if (areaSelected.belongs(figure.getStartPoint()) && areaSelected.belongs(figure.getEndPoint())) {
                selectedFigures.add(figure);
            }
        }
    }

    public AreaSelected(Point eventPoint, Iterable<Figure> figureList) {
        boolean found = false;
        Figure selected = null;
        for (Figure figure : figureList) {
            if(figure.belongs(eventPoint)) {
                found = true;
                selected = figure;
            }
        }
        if (found) {
            selectedFigures.add(selected);
        }
    }

    public Iterable<Figure> figures() {
        return new ArrayList<>(selectedFigures);
    }

    public boolean isEmpty() {
        return selectedFigures.isEmpty();
    }

    public void clear() {
        selectedFigures.clear();
    }

    public boolean contains(Figure figure) {
        return selectedFigures.contains(figure);
    }

}
