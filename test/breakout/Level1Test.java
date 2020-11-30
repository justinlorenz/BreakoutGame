package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class Level1Test extends DukeApplicationTest {

  public static final String STARTING_LEVEL_FILEPATH = "blockLevels/level1.txt/";
  public static final int SIZE = 800;
  public static final int STARTING_BALL_X = SIZE - 400;
  public static final int STARTING_BALL_Y = SIZE - 400;
  public static final int STARTING_SCORE = 0;
  public static final int STARTING_LIVES = 3;
  public static final int STARTING_LEVEL_NUMBER = 1;
  public static final int STARTING_LEVEL_BRICKS = 28;
  // standard steps to do for all test applications so factor it out here
  private final Game myGame = new Game();
  Ball ball;

  @Override
  public void start(Stage stage) {
    Scene gameScene = myGame.setUpGameScene();
    myGame.createNewLevel(STARTING_LEVEL_FILEPATH, STARTING_SCORE, STARTING_LIVES,
        STARTING_LEVEL_NUMBER);
    stage.setScene(gameScene);
    stage.setTitle(StatusDisplay.TITLE);
    stage.show();
    ball = myGame.getAllBalls().get(0);

  }

  @Test
  public void isFirstLevelTest() {
    assertEquals(STARTING_LEVEL_NUMBER, myGame.getStatusDisplay().getMyProgress().getLevelNum());
  }

  @Test
  public void onlyOneUnbreakable() {
    assertEquals(1, myGame.getMyLevel().getNumOfImpossibles());
  }

  @Test
  public void setStartingLivesTest() {
    assertEquals(STARTING_LIVES, myGame.getStatusDisplay().getMyProgress().getLives());
  }

  @Test
  public void setStartingScoreTest() {
    assertEquals(STARTING_SCORE, myGame.getStatusDisplay().getMyProgress().getScore());
  }

  @Test
  public void correctAmountOfBlocksTest() {
    assertEquals(STARTING_LEVEL_BRICKS, myGame.getMyLevel().returnBrickList().size());
  }

  @Test
  public void testTopLeftBrickStrength() {
    assertEquals(1, myGame.getMyLevel().returnBrickList().get(0).getBrickStrength());
  }


  @Test
  public void testTopLeftBrickX() {
    assertEquals(50, myGame.getMyLevel().returnBrickList().get(0).getXPos());
  }


  @Test
  public void testTopLeftBrickY() {
    assertEquals(50, myGame.getMyLevel().returnBrickList().get(0).getYPos());
  }

  @Test
  public void testBallResetX() {
    assertEquals(STARTING_BALL_X, ball.getBallCircle().getCenterX());
  }

  @Test
  public void testBallResetY() {
    assertEquals(STARTING_BALL_Y, ball.getBallCircle().getCenterX());
  }

  @Test
  public void testBallResetXSpeed() {
    assertEquals(0, ball.getXSpeed());
  }

  @Test
  public void testBallResetYSpeed() {
    assertEquals(10, ball.getYSpeed());
  }


}
