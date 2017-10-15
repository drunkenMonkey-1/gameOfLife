public class PositionConverter {

    private int _cellWidth;
    private int _cellHeight;

    public PositionConverter(int cellWidth, int cellHeight){
        _cellWidth = cellWidth;
        _cellHeight = cellHeight;
    }
    public CellPosition convertPos(CellPosition in){
        CellPosition coord = new CellPosition(in.get_x() * _cellWidth, in.get_y() * _cellHeight);
        return coord;
    }

}
