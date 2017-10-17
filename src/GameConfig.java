
import javafx.scene.control.Alert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class GameConfig {


    private int _fieldX;
    private int _fieldY;
    private boolean _torus;

    private Cell[] _initCells;


    /**
     *
     * @param fieldX
     * @param fieldY
     * @param torus
     * @param initCells
     * @throws InvalidGameConfigException
     */
    public GameConfig(int fieldX, int fieldY, boolean torus, Cell[] initCells) throws InvalidGameConfigException{
        _fieldX = fieldX;
        _fieldY = fieldY;
        _torus = torus;
        _initCells = initCells;
        if(!checkConfig()){
            throw new InvalidGameConfigException("Game config is invalid");
        }
    }

    /**
     *
     * @param path
     */
    public GameConfig(String path) throws InvalidGameConfigException {

        JSONParser jsonObjParser = new JSONParser();
        try {
            Object obj = jsonObjParser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) obj;

            long fieldX = (long)jsonObject.get("fieldX");
            long fieldY = (long)jsonObject.get("fieldY");
            Cell[] initCells;
            int arrayCount = 0;
            boolean torus = (boolean)jsonObject.get("torus");

            JSONArray initCellsJson= (JSONArray)jsonObject.get("initCells");
            initCells = new Cell[initCellsJson.size()];
            Iterator<JSONObject> jsonArrayIterator = initCellsJson.iterator();
            while(jsonArrayIterator.hasNext()){
                CellState cellState = CellState.ALIVE;;
                JSONObject subJsonObject = jsonArrayIterator.next();
                String state = (String)(subJsonObject.get("state"));
                switch (state){
                    case "DEAD":
                        cellState = CellState.DEAD;
                        break;
                    case "LOCKED":
                        cellState = CellState.LOCKED;
                        break;
                    case "INFECTED":
                        cellState = CellState.INFECTED;
                        break;
                }
                long x = (long)subJsonObject.get("xpos");
                long y = (long)subJsonObject.get("ypos");
                CellPosition pos = new CellPosition((int)x, (int)y);
                initCells[arrayCount] = new Cell(cellState, pos);
                arrayCount++;
            }
            _fieldX = (int)fieldX;
            _fieldY = (int)fieldY;
            _torus = torus;
            _initCells = initCells;
            if(!checkConfig()){
                throw new InvalidGameConfigException("Game config is invalid");
            }
        } catch (FileNotFoundException e) {
            alert("File not found!");
            e.printStackTrace();
        } catch (ParseException e) {
            alert("JSON ParseException");
            e.printStackTrace();
        } catch (IOException e) {
            alert("IO Exception");
            e.printStackTrace();
        }
    }

    private void alert(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File Error");
        alert.setContentText(msg);
        alert.showAndWait();
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
