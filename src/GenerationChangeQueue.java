import java.util.LinkedList;

public class GenerationChangeQueue {

    private LinkedList<Generation> _updatedCells;

    public GenerationChangeQueue(){
        _updatedCells = new LinkedList<>();
    }

    public void addCellToQueue(Generation cellGen){
        _updatedCells.add(cellGen);
    }
    public void submitChangedCells(){
        for(Generation cell : _updatedCells){
            cell.setState();
        }
        _updatedCells.clear();
    }
}
