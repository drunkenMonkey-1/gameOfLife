public class CellGrid {

    private Cell _rootCell;
    private Cell[][] _grid = new Cell[3][3];
    private int neighbourCount = 0;

    public CellGrid(Cell rootCell){
        //generate cell grid
        for(int x = rootCell.getPos().get_x()-1; x <= rootCell.getPos().get_x()+1; x++){
            for(int y = rootCell.getPos().get_y()-1; y <= rootCell.getPos().get_y()+1; y++){
                if(x >= 0 && y >= 0 && x < GameOfLife._config.get_fieldX() && y < GameOfLife._config.get_fieldY()){
                    if(GameOfLife.getGameGrid()[x][y].getState() == CellState.ALIVE){
                        neighbourCount++;
                    }
                }
            }
        }
        if(rootCell.getState() == CellState.ALIVE){
            neighbourCount-=1;
        }
    }
    public int getNumberOfAliveNeighbours(){
        return neighbourCount;
    }
}
