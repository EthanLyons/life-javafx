package edu.cnm.deepdive.life.controller;

import edu.cnm.deepdive.ca.model.Cell;
import edu.cnm.deepdive.ca.model.World;
import edu.cnm.deepdive.life.view.WorldView;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

public class Life {

  private static final int WORLD_SIZE = 200;
  private World world;
  private Random rng;
  private Cell[][] terrain;

  @FXML
  private ScrollPane viewScroller;
  @FXML
  private WorldView terrainView;
  @FXML
  private ToggleButton toggleRun;
  @FXML
  private Slider densitySlider;
  @FXML
  private Button reset;

  @FXML
  private void initialize() {
    rng = new Random();
    terrain = new Cell[WORLD_SIZE][WORLD_SIZE];
    reset(null);
  }

  @FXML
  private void toggleRun(ActionEvent actionEvent) {
    // TODO Start or stop the Game of Life model.
  }

  @FXML
  private void reset(ActionEvent actionEvent) {
    world = new World(WORLD_SIZE, densitySlider.getValue() / 100, rng);
    updateDisplay();
  }

  private void updateDisplay() {
    world.copyTerrain(terrain);
    terrainView.draw(terrain);
  }
}
