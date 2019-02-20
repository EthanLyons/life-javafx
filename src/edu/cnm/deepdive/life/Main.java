package edu.cnm.deepdive.life;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {

    launch(args);

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();
    ResourceBundle bundle = ResourceBundle.getBundle("res/ui-strings");
    FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource("res/life.fxml"), bundle);
    Parent parent = fxmlLoader.load();
    Scene scene new Scene(parent);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Game of Life");
    primaryStage.setResizable(false);
    primaryStage.show();



  }
}