import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainWnd extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Game of Life Config
        GameConfig gameCfg = null;
        Cell[] initCells = new Cell[7];
        initCells[0] = new Cell(CellState.ALIVE, new CellPosition(20, 24));
        initCells[1] = new Cell(CellState.ALIVE, new CellPosition(21, 24));
        initCells[2] = new Cell(CellState.ALIVE, new CellPosition(24, 24));
        initCells[3] = new Cell(CellState.ALIVE, new CellPosition(25, 24));
        initCells[4] = new Cell(CellState.ALIVE, new CellPosition(26, 24));
        initCells[5] = new Cell(CellState.ALIVE, new CellPosition(21, 22));
        initCells[6] = new Cell(CellState.ALIVE, new CellPosition(23, 23));
        try {
            gameCfg = new GameConfig(50, 50, false, initCells);
        } catch (InvalidGameConfigException e) {
            e.printStackTrace();
        }

        GameOfLife game = new GameOfLife(gameCfg);
        Group gameRoot = game.getRoot();

        Button next = new Button("Next Gen");
        gameRoot.getChildren().add(next);

        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.nextGeneration(1);
            }
        });



        Scene scene = new Scene(gameRoot, game.getConfig().get_fieldX() * 10, game.getConfig().get_fieldY() * 10, Color.TRANSPARENT);
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
