package edu.cnm.deepdive.life.controller;

import edu.cnm.deepdive.ca.model.Cell;
import edu.cnm.deepdive.ca.model.World;
import edu.cnm.deepdive.life.view.WorldView;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;

public class Life {

  public static final int INITIAL_DENSITY = 25;
  private static final int WORLD_SIZE = 200;

  private World world;
  private Random rng;
  private Cell[][] terrain;
  private boolean running;
  private Updater updater;
  private long initialTerrainViewWidth;
  private long initialTerrainViewHeight;

  @FXML
  private ScrollPane viewScroller;
  @FXML
  private WorldView terrainView;
  @FXML
  private ToggleButton toggleRun;
  @FXML
  private Slider densitySlider;
  @FXML
  private Tooltip sliderValue;
  @FXML
  private Button reset;
  @FXML
  private CheckBox toggleFit;

  @FXML
  private void initialize() {
    rng = new Random();
    updater = new Updater();
    terrain = new Cell[WORLD_SIZE][WORLD_SIZE];
    initialTerrainViewHeight = Math.round(terrainView.getHeight());
    initialTerrainViewWidth = Math.round(terrainView.getWidth());
    reset(null);
  }

  @FXML
  private void toggleRun(ActionEvent actionEvent) {
    if (toggleRun.isSelected()) {
      running = true;
      reset.setDisable(true);
      updater.start();
      new Runner().start();
    } else {
      stop();
    }
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

  private void stop() {
    running = false;
    updater.stop();
    toggleRun.setSelected(false);
    reset.setDisable(false);
  }

  @FXML
  private void toggleFit(ActionEvent actionEvent) {
    if (toggleFit.isSelected()) {
      terrainView.setHeight(viewScroller.getHeight() - 2);
      terrainView.setWidth(viewScroller.getWidth() - 2);
    } else {
      terrainView.setHeight(initialTerrainViewHeight);
      terrainView.setWidth(initialTerrainViewWidth);
    }
    if (!running) {
      updateDisplay();
    }
  }

  private class Runner extends Thread {

    private static final int HISTORY_LENGTH = 24;

    private Deque<Long> history = new LinkedList<>();

    @Override
    public void run() {
      while (running) {
        world.tick();
        long checksum = world.getChecksum();
        if (history.contains(checksum)) {
          Platform.runLater(() -> Life.this.stop());
        } else {
          history.addLast(checksum);
          if (history.size() > HISTORY_LENGTH) {
            history.removeFirst();
          }
        }
      }
    }

  }

  public class Updater extends AnimationTimer {

    @Override
    public void handle(long now) {
      updateDisplay();
    }

  }

}
