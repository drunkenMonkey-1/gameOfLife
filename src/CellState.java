import javafx.scene.paint.Color;

public enum CellState {
    DEAD(Color.BLACK), ALIVE(Color.WHITE), INFECTED(Color.RED), LOCKED(Color.TRANSPARENT);

    private Color _color;

    private CellState(Color color){
        _color = color;
    }
    public Color get_color() {
        return _color;
    }
}
