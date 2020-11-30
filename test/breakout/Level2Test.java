package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class Level2Test extends DukeApplicationTest {

  public static final int SIZE = 800;
  public static final int STARTING_BALL_X = SIZE - 400;
  public static final int STARTING_BALL_Y = SIZE - 400;
  public static final String LEVEL2_PATH = "blockLevels/level2.txt/";
  private static final String STARTING_LEVEL_FILEPATH = "blockLevels/level1.txt/";
  // standard steps to do for all test applications so factor it out here
  private final Game myGame = new Game();
  Ball ball;

  @Override
  public void start(Stage stage) {
    Scene gameScene = myGame.setUpGameScene();
    stage.setScene(gameScene);
    myGame.createNewLevel(Game.STARTING_LEVEL_FILEPATH, 0, 3, 1);
    stage.setTitle(StatusDisplay.TITLE);
    stage.show();
    myGame.getStatusDisplay().getMyProgress().addScore();
    myGame.getStatusDisplay().getMyProgress().addLives();
    int totalScore = myGame.getStatusDisplay().getMyProgress().getScore();
    int numLives = myGame.getStatusDisplay().getMyProgress().getLives();
    myGame.createNewLevel(LEVEL2_PATH, totalScore, numLives, 2);
    ball = myGame.getAllBalls().get(0);
  }

  @Test
  public void secondLevelKeptScoreTest() {
    assertEquals(1, myGame.getStatusDisplay().getMyProgress().getScore());
  }

  @Test
  public void secondLevelKeptNumLivesTest() {
    assertEquals(4, myGame.getStatusDisplay().getMyProgress().getLives());
  }

  @Test
  public void secondLevelCurrentLevelNumTest() {
    assertEquals(2, myGame.getStatusDisplay().getMyProgress().getLevelNum());
  }


  @Test
  public void numOfUnbreakablesSecondLevelTest() {
    assertEquals(4, myGame.getMyLevel().getNumOfImpossibles());
  }


  @Test
  public void correctAmountOfBlocksTest() {
    assertEquals(42, myGame.getMyLevel().returnBrickList().size());
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
  public void testNumOfProjectiles() {
    assertEquals(5, myGame.getMyLevel().getNumOfProjectiles());
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
