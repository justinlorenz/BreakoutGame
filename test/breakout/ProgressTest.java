package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import breakout.Bricks.Brick;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ProgressTest extends DukeApplicationTest {

  public static final String STARTING_LEVEL_FILEPATH = "blockLevels/level1.txt/";
  public static final int SIZE = 800;
  public static final int STARTING_SCORE = 0;
  public static final int STARTING_LIVES = 3;
  public static final int STARTING_LEVEL_NUMBER = 1;
  // standard steps to do for all test applications so factor it out here
  private final Game myGame = new Game();
  private Scene gameScene;

  @Override
  public void start(Stage stage) {
    gameScene = myGame.setUpGameScene();
    myGame.createNewLevel(STARTING_LEVEL_FILEPATH, STARTING_SCORE, STARTING_LIVES,
        STARTING_LEVEL_NUMBER);
    stage.setScene(gameScene);
    stage.setTitle(StatusDisplay.TITLE);
    stage.show();
  }

  @Test
  public void scoreProgressTestStart() {
    assertEquals(0, myGame.getStatusDisplay().getMyProgress().getScore());
  }

  @Test
  public void scoreProgressTestUpdate() {
    myGame.getStatusDisplay().getMyProgress().addScore();
    assertEquals(1, myGame.getStatusDisplay().getMyProgress().getScore());
  }

  @Test
  public void scoreBrickTestStart() {
    assertEquals(27, myGame.getStatusDisplay().getMyProgress().getBlocksLeft());
  }

  @Test
  public void scoreBrickTestUpdate() {
    myGame.getStatusDisplay().getMyProgress().updateNumBricks(new Brick(4, Color.RED, myGame));
    assertEquals(26, myGame.getStatusDisplay().getMyProgress().getBlocksLeft());
  }

  @Test
  public void livesProgressTestStart() {
    assertEquals(3, myGame.getStatusDisplay().getMyProgress().getLives());
  }

  @Test
  public void livesProgressTestUpdate() {
    myGame.getStatusDisplay().getMyProgress().lostLive();
    myGame.getStatusDisplay().getMyProgress().lostLive();
    assertEquals(1, myGame.getStatusDisplay().getMyProgress().getLives());
  }

  @Test
  public void createUserInputField() {
    javafxRun(
        () -> myGame.getStatusDisplay().getMyProgress().createUserInputField(myGame.getRoot()));
    assertEquals(250, myGame.getStatusDisplay().getMyProgress().getLayout().getLayoutX());
  }

}
