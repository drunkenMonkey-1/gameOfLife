public class CellGrid {

    private Cell _rootCell;
    private Cell[][] _grid = new Cell[3][3];
    private int neighbourCount = 0;
    private int infectedCount = 0;

    public CellGrid(Cell rootCell){
        //generate cell grid
        for(int x = rootCell.getPos().get_x()-1; x <= rootCell.getPos().get_x()+1; x++){
            for(int y = rootCell.getPos().get_y()-1; y <= rootCell.getPos().get_y()+1; y++){
                if(x >= 0 && y >= 0 && x < GameOfLife._config.get_fieldX() && y < GameOfLife._config.get_fieldY()){
                    if(GameOfLife.getGameGrid()[x][y].getState() != CellState.DEAD){
                        neighbourCount++;
                    }
                    if(GameOfLife.getGameGrid()[x][y].getState() == CellState.INFECTED){
                        infectedCount++;
                    }
                }
            }
        }
        if(rootCell.getState() != CellState.DEAD){
            neighbourCount-=1;
        }
        if(rootCell.getState() == CellState.INFECTED){
            infectedCount-=1;
        }
    }

    public int getNumberOfAliveNeighbours(){
        return neighbourCount;
    }
    public int getInfectedCount() {
        return infectedCount;
    }
}
