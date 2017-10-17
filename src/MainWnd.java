import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainWnd extends Application {

    private int _ribbonPxSize = 20;
    private int _cellPxSize = 2;
    private String _defaultConfig = "default.json";
    private GameConfig gameCfg = null;
    private int _updateDelay = 3;
    private boolean _stop = true;
    private int _infectionProbability = 33; // 33 percent

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        // Game of Life Config

        CellPosition.set_cellPxSize(_cellPxSize);

//        // Write Configuration to JSON
//        JSONObject obj = new JSONObject();
//        obj.put("fieldX", 300);
//        obj.put("fieldY", 200);
//        obj.put("torus", false);
//        JSONArray array = new JSONArray();
//        for(int i = 0; i < initCells.length; i++){
//            JSONObject cell = new JSONObject();
//            cell.put("xpos", initCells[i].getPos().get_x());
//            cell.put("ypos", initCells[i].getPos().get_y());
//            cell.put("state", initCells[i].getState().get_stateString());
//            array.add(cell);
//        }
//        obj.put("initCells", array);
//
//        DataOutputStream dataOutStream = null;
//        try {
//            dataOutStream = new DataOutputStream(new FileOutputStream("default.json"));
//            byte[] outBytes = obj.toJSONString().getBytes();
//            dataOutStream.write(outBytes);
//            dataOutStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //
        try {
            gameCfg = new GameConfig(_defaultConfig);
        } catch (InvalidGameConfigException e) {
            e.printStackTrace();
        }

        GameOfLife game = new GameOfLife(gameCfg);
        Group controlGroup = new Group();
        Group gameRoot = game.getRoot();

        //Layout
        GridPane ribbonGridPane = new GridPane();
        ribbonGridPane.setVgap(3);
        ribbonGridPane.setHgap(3);
        ribbonGridPane.setMinSize(gameCfg.get_fieldX(), _ribbonPxSize);
        ribbonGridPane.setPadding(new Insets(3, 3, 3, 3));

        //Controls
        Button nextBtn = new Button("Next Gen");
        Button playBtn = new Button("Play");
        Button infectBtn = new Button("Infect Cells");
        TextField cfgPathField = new TextField();
        cfgPathField.setText(_defaultConfig);
        Button cfgBtn = new Button("Load Config");

        ribbonGridPane.add(nextBtn, 0, 0);
        ribbonGridPane.add(playBtn, 1, 0);
        ribbonGridPane.add(infectBtn, 2, 0);
        ribbonGridPane.add(cfgPathField, 3, 0);
        ribbonGridPane.add(cfgBtn, 4, 0);

        //Listener
        nextBtn.setOnAction(event -> {
            _stop = true;
            game.nextGeneration(1);
        });
        playBtn.setOnAction(event -> {
            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    _stop = false;
                    while(_stop == false){
                        game.nextGeneration(1);
                        Thread.sleep(_updateDelay);
                    }
                    return null;
                }
            };
            //task.messageProperty().addListener((obs, oldMessage, newMessage) -> );
            new Thread(task).start();
        });
        infectBtn.setOnAction(event -> game.infectRandomCell(_infectionProbability));
        cfgBtn.setOnAction(event -> {
            try {
                _stop = true;
                gameCfg = new GameConfig(cfgPathField.getText());
                game.loadConfig(gameCfg);
            } catch (InvalidGameConfigException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Config Error");
                alert.setContentText("Config file is invalid. Please check if init cells are inbound!");
                alert.showAndWait();
                e.printStackTrace();
            }
        });

        //root node
        controlGroup.getChildren().add(ribbonGridPane);

        VBox combi = new VBox(controlGroup, gameRoot);


        Scene scene = new Scene(combi, gameCfg.get_fieldX() * CellPosition.get_cellPxSize(),
                                          (gameCfg.get_fieldY() * CellPosition.get_cellPxSize()), Color.TRANSPARENT);

        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
