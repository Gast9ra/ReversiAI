
package reversi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Reversi extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("ReversiGUI.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void stop() {
        System.out.println("Stage is closing");
        if (ReversiController.game != null && !ReversiController.game.isFinished()) {
            ReversiController.game.finishGame();
            ReversiController.game = null;
            System.exit(0);

        }
    }

}
