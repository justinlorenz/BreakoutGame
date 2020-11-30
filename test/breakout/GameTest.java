package breakout;


import static javafx.scene.input.KeyCode.D;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


public class GameTest extends DukeApplicationTest {

  // standard steps to do for all test applications so factor it out here
  private final Game myGame = new Game();
  private Scene myScene;
  private Circle myBall;
  private Stage stage;

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    // create game's scene with all shapes in their initial positions and show it
    myScene = myGame.setUpGameScene();
    myGame.createNewLevel(Game.STARTING_LEVEL_FILEPATH, Game.STARTING_SCORE, Game.STARTING_LIVES,
        Game.STARTING_LEVEL_NUMBER);
    stage.setScene(myScene);
    stage.show();

    // find individual items within game by ID (must have been set in your code using setID())
    myBall = lookup("#ball0").query();
  }


  @Test
  public void testPauseButton() {
    javafxRun(() -> myGame.pauseGame());

    press(myScene, KeyCode.SPACE);
    double[] prevPosition = {myBall.getCenterX(), myBall.getCenterY()};
    javafxRun(() -> myGame.step());
    double[] currentPosition = {myBall.getCenterX(), myBall.getCenterY()};
    assertNotEquals(prevPosition, currentPosition);

    press(myScene, KeyCode.SPACE);
    double[] pausedPos = {myBall.getCenterX(), myBall.getCenterY()};
    javafxRun(() -> myGame.step());
    double[] newPausedPos = {myBall.getCenterX(), myBall.getCenterY()};
    assertArrayEquals(pausedPos, newPausedPos);
  }

  @Test
  public void testResetButton() {
    javafxRun(() -> myGame.resumeGame());
    javafxRun(() -> myGame.step());
    assertNotEquals(Game.STARTING_BALL_Y, myBall.getCenterY());
    press(myScene, KeyCode.R);
    assertEquals(Game.STARTING_BALL_Y, myBall.getCenterY());
  }

  @Test
  public void testAddLivesButton() {
    javafxRun(() -> myGame.pauseGame());
    int prevLives = myGame.getStatusDisplay().getMyProgress().getLives();
    assertEquals(Game.STARTING_LIVES, prevLives);
    press(myScene, KeyCode.L);
    int newLives = myGame.getStatusDisplay().getMyProgress().getLives();
    assertEquals(Game.STARTING_LIVES + 1, newLives);
  }

  @Test
  public void testPowerUpButton() {
    assertFalse(myGame.getPowerUpController().getPowerUpFalling());
    press(myScene, KeyCode.P);
    assertTrue(myGame.getPowerUpController().getPowerUpFalling());
  }

  @Test
  public void testAddScoreButton() {
    double prevScore = myGame.getStatusDisplay().getMyProgress().getScore();
    press(myScene, KeyCode.U);
    double newScore = myGame.getStatusDisplay().getMyProgress().getScore();
    assertTrue(newScore > prevScore);
  }


  @Test
  public void deleteOneBlock() {
    int brickX = myGame.getMyLevel().returnBrickList().get(0).getXPos();
    press(myScene, D);
    assertTrue(brickX != myGame.getMyLevel().returnBrickList().get(0).getXPos());
  }


}
