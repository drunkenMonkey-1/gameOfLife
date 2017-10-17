import javafx.scene.paint.Color;

public enum CellState {
    DEAD(Color.BLACK, "DEAD"), ALIVE(Color.WHITE, "ALIVE"), INFECTED(Color.RED, "INFECTED"), LOCKED(Color.TRANSPARENT, "LOCKED");

    private Color _color;

    private String _stateString;

    private CellState(Color color, String state){
        _color = color;
        _stateString = state;
    }

    public Color get_color() {
        return _color;
    }
    public String get_stateString() { return _stateString; }
}
