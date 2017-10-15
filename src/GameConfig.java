public class GameConfig {


    private int _fieldX;
    private int _fieldY;
    private boolean _torus;

    private Cell[] _initCells;


    public GameConfig(int fieldX, int fieldY, boolean torus, Cell[] initCells) throws InvalidGameConfigException{
        _fieldX = fieldX;
        _fieldY = fieldY;
        _torus = torus;
        _initCells = initCells;
        if(!checkConfig()){
            throw new InvalidGameConfigException("Game config is invalid");
        }
    }
    public boolean checkConfig(){
        if(_fieldX < 3 && _fieldY < 3){
            return false;
        } else {
            for(int i = 0; i < _initCells.length; i++){
                if(!(_initCells[i].getPos().isCellInBound(_fieldX, _fieldY))){
                    return false;
                }
            }
        }
        return true;
    }
    public int get_fieldX() {
        return _fieldX;
    }

    public int get_fieldY() {
        return _fieldY;
    }

    public boolean is_torus() {
        return _torus;
    }
    public Cell[] get_initCells() {
        return _initCells;
    }
}
