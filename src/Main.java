//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
package src;

import javafx.application.Application;
import javafx.stage.Stage;
import src.view.JavaFXView;

public class Main extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        JavaFXView view = new JavaFXView(primaryStage);
        view.showMainMenu();
    }
}