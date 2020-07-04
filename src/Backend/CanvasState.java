package Backend;

import Backend.Model.Figure;
import java.util.ArrayList;
import java.util.List;

public class CanvasState {

    private final List<Figure> list = new ArrayList<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public void toFront(Figure figure){
        removeFigure(figure);
        addFigure(figure);
    }

    public void toBack(Figure figure ){
        removeFigure(figure);
        list.add(0,figure);
    }

    public void removeFigure(Figure figure) { list.remove(figure); }

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
    }

}
