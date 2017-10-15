import javafx.scene.Group;

public class GameOfLife {


    public static GameConfig _config;
    private static Cell[][] _gameGrid;

    public GameOfLife(GameConfig cfg){
        _config = cfg;
        _gameGrid = new Cell[_config.get_fieldX()][_config.get_fieldY()];
        initGameGrid();
    }

    public void initGameGrid(){
        for(int x = 0; x < _config.get_fieldX(); x++){
            for(int y = 0; y < _config.get_fieldY(); y++){
                _gameGrid[x][y] = new Cell(CellState.DEAD, new CellPosition(x, y));
            }
        }
        for(int i = 0; i < _config.get_initCells().length; i++){
            int xInit = _config.get_initCells()[i].getPos().get_x();
            int yInit = _config.get_initCells()[i].getPos().get_y();
            _gameGrid[xInit][yInit].setNextState(_config.get_initCells()[i].getState());
        }
        Cell._gameQueue.submitChangedCells();
    }

    public GameConfig getConfig() {
        return _config;
    }
    public Group getRoot(){
        return Cell.root;
    }

    public static Cell[][] getGameGrid() {
        return _gameGrid;
    }

    /**
     *
     * @param steps
     */
    public void nextGeneration(int steps){
        while(steps > 0){
            for(int x = 0; x < _config.get_fieldX(); x++){
                for(int y = 0; y < _config.get_fieldY(); y++){
                   CellGrid smallGrid = new CellGrid(_gameGrid[x][y]);
                   int adCells = smallGrid.getNumberOfAliveNeighbours();
                   if(adCells < 2 && _gameGrid[x][y].getState() != CellState.DEAD){
                       _gameGrid[x][y].setNextState(CellState.DEAD);
                   }
                   else if(adCells == 3 && _gameGrid[x][y].getState() != CellState.ALIVE) {
                       _gameGrid[x][y].setNextState(CellState.ALIVE);
                   }
                   else if(adCells > 3 && _gameGrid[x][y].getState() != CellState.DEAD){
                        _gameGrid[x][y].setNextState(CellState.DEAD);
                   }
                }
            }
            Cell._gameQueue.submitChangedCells();
            steps--;
        }
    }
}
