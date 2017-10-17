import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Cell implements Generation{

    private int _age;

    private CellState _state;
    private CellState _nextState;
    private Rectangle _shape;

    private CellPosition _pos;
    public static GenerationChangeQueue _gameQueue = new GenerationChangeQueue();
    public static Group root = new Group();
    private static PositionConverter posConverter = new PositionConverter(CellPosition.get_cellPxSize(), CellPosition.get_cellPxSize());


    public Cell(CellState initState, CellPosition initPos){
        _nextState = CellState.LOCKED;
        _state = initState;
        _pos = initPos;

        // Graphics Stuff
        CellPosition graphicCoord = posConverter.convertPos(_pos);
        _shape = new Rectangle();
        _shape.setX(graphicCoord.get_x());
        _shape.setY(graphicCoord.get_y());
        _shape.setHeight(CellPosition.get_cellPxSize());
        _shape.setWidth(CellPosition.get_cellPxSize());
        _shape.setFill(_state.get_color());
        root.getChildren().add(_shape);
    }

    public void setNextState(CellState _nextState) {
        this._nextState = _nextState;
        _gameQueue.addCellToQueue(this);
    }

    public CellState getState() {
        return _state;
    }

    public CellPosition getPos() {
        return _pos;
    }

    @Override
    public void setState() {
        _state = _nextState;
        _nextState = CellState.LOCKED;
        _shape.setFill(_state.get_color());
    }
    @Override
    public String toString(){
        return getPos().get_x() + ";" + getPos().get_y() + ";" + _state.get_stateString();
    }
}
