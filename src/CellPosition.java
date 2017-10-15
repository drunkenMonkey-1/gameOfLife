public class CellPosition {


    private int _x, _y;

    public CellPosition(int x, int y){
        _x = x;
        _y = y;
    }

    public boolean isCellInBound(int fieldX, int fieldY){
        if((_x >= 0 && _x <= fieldX) && (_y >= 0 && _y <= fieldY)){
            return true;
        }
        return false;
    }

    public int get_x() {
        return _x;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public int get_y() {
        return _y;
    }

    public void set_y(int _y) {
        this._y = _y;
    }


}
