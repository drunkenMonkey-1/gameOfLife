import javafx.scene.Group;

import java.util.Random;

public class GameOfLife {

    public static GameConfig _config;
    private boolean _infect = false;
    private static Cell[][] _gameGrid;
    private int _infectionProbability;
    private Random rnd = new Random();

    public GameOfLife(GameConfig cfg){
        loadConfig(cfg);
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

    public void loadConfig(GameConfig cfg){
        _config = cfg;
        _gameGrid = new Cell[_config.get_fieldX()][_config.get_fieldY()];
        initGameGrid();
    }

    public void infectRandomCell(int infectionProbability){
        _infectionProbability = infectionProbability;
        _infect = true;
        nextGeneration(1);
        _infect = false;
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
                   int infCells = smallGrid.getInfectedCount();
                   if(adCells < 2 && _gameGrid[x][y].getState() != CellState.DEAD){
                       _gameGrid[x][y].setNextState(CellState.DEAD);
                   }
                   else if(adCells == 3 && _gameGrid[x][y].getState() != CellState.ALIVE) {
                       if(_infect == true){
                            if((rnd.nextInt()%100) <= _infectionProbability){
                                _gameGrid[x][y].setNextState(CellState.INFECTED);
                            }
                       } else {
                           _gameGrid[x][y].setNextState(CellState.ALIVE);
                       }
                   }
                   else if(adCells > 3 && _gameGrid[x][y].getState() != CellState.DEAD){
                        _gameGrid[x][y].setNextState(CellState.DEAD);
                   }
                   else if(infCells >= 1 && _gameGrid[x][y].getState() != CellState.DEAD && _gameGrid[x][y].getState() != CellState.INFECTED){
                       _gameGrid[x][y].setNextState(CellState.INFECTED);
                   }
                }
            }
            Cell._gameQueue.submitChangedCells();
            steps--;
        }
    }
}
