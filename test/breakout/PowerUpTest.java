package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import breakout.Powerups.LongPaddle;
import breakout.Powerups.MultiBall;
import breakout.Powerups.PowerUpMaker;
import breakout.Powerups.ScoreMultiplier;
import breakout.Powerups.SlowBall;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class PowerUpTest extends DukeApplicationTest {

  public static final String STARTING_LEVEL_FILEPATH = "blockLevels/level1.txt/";
  public static final int STARTING_SCORE = 0;
  public static final int STARTING_LIVES = 3;
  public static final int STARTING_LEVEL_NUMBER = 1;
  private final Game myGame = new Game();
  Ball ball;
  PowerUpMaker controller;

  @Override
  public void start(Stage stage) {
    Scene gameScene = myGame.setUpGameScene();
    myGame.createNewLevel(STARTING_LEVEL_FILEPATH, STARTING_SCORE, STARTING_LIVES,
        STARTING_LEVEL_NUMBER);
    stage.setScene(gameScene);
    stage.setTitle(StatusDisplay.TITLE);
    stage.show();
    ball = myGame.getAllBalls().get(0);
    controller = myGame.getPowerUpController();
  }

  @Test
  public void createdListOfPowerUp() {
    assertEquals(4, controller.getAllPowerUps().size());
  }

  @Test
  public void createRandomPowerUpTest() {
    javafxRun(() -> controller.createAPowerUp(400, 400, true));
    boolean createdAPowerUp = false;
    if (controller.getCurrPowerUp().getPowerUpName().equals("SlowBall") ||
        controller.getCurrPowerUp().getPowerUpName().equals("MultiBall") ||
        controller.getCurrPowerUp().getPowerUpName().equals("x3 Score") ||
        controller.getCurrPowerUp().getPowerUpName().equals("LongPaddle")) {
      createdAPowerUp = true;
    }
    assertTrue(createdAPowerUp);
  }

  @Test
  public void startPowerUpTest() {
    javafxRun(() -> controller.createAPowerUp(400, 700, true));
    while (controller.getPowerUpFalling()) {
      javafxRun(() -> controller.getCurrPowerUp().movePowerUpBall());
    }
    assertTrue(controller.getPowerUpHappening());
    assertFalse(myGame.getRoot().getChildren().contains(controller.getCurrPowerUp()));
  }

  @Test
  public void destroyPowerUpTest() {
    javafxRun(() -> controller.createAPowerUp(400, 400, true));
    javafxRun(() -> controller.getCurrPowerUp().destroyPowerUp());
    assertEquals(true,
        !myGame.getRoot().getChildren().contains(controller.getCurrPowerUp().getPowerUpBall()));
  }

  @Test
  public void createExtraBallTest() {
    javafxRun(() -> controller.createAPowerUp(400, 400, true));
    javafxRun(
        () -> controller.getCurrPowerUp().makeANewBall(1 / 2, 5, myGame.getAllBalls().get(0)));
    assertEquals(1, myGame.getAllBalls().size());
  }

  @Test
  public void makeLongPaddleTest() {
    LongPaddle paddle = new LongPaddle("LongPaddle", myGame.getRoot(), myGame, controller);
    javafxRun(paddle::activatePowerUp);
    assertEquals(180, myGame.getMyPaddle().getThisPaddleLength());
  }

  @Test
  public void deactivateLongPaddleTest() {
    LongPaddle paddle = new LongPaddle("LongPaddle", myGame.getRoot(), myGame, controller);
    javafxRun(paddle::activatePowerUp);
    javafxRun(paddle::deactivatePowerUp);
    assertEquals(120, myGame.getMyPaddle().getThisPaddleLength());
  }

  @Test
  public void makeSlowBallTest() {
    double ySpeed = myGame.getAllBalls().get(0).getYSpeed();
    SlowBall slowBall = new SlowBall("SlowBall", myGame.getRoot(), myGame, controller);
    javafxRun(() -> slowBall.activatePowerUp());
    assertEquals(ySpeed / 2, myGame.getAllBalls().get(0).getYSpeed());
  }

  @Test
  public void deactivateSlowBallTest() {
    double ySpeed = myGame.getAllBalls().get(0).getYSpeed();
    SlowBall slowBall = new SlowBall("SlowBall", myGame.getRoot(), myGame, controller);
    javafxRun(() -> slowBall.activatePowerUp());
    javafxRun(slowBall::deactivatePowerUp);
    assertEquals(ySpeed, myGame.getAllBalls().get(0).getYSpeed());
  }

  @Test
  public void makeMultiBallTest() {
    MultiBall multiBall = new MultiBall("MultiBall", myGame.getRoot(), myGame, controller);
    javafxRun(() -> multiBall.activatePowerUp());
    assertEquals(3, myGame.getAllBalls().size());
  }

  @Test
  public void deactivateMultiBallTest() {
    MultiBall multiBall = new MultiBall("MultiBall", myGame.getRoot(), myGame, controller);
    javafxRun(() -> multiBall.activatePowerUp());
    assertEquals(3, myGame.getAllBalls().size());
    javafxRun(() -> multiBall.deactivatePowerUp());
    assertEquals(1, myGame.getAllBalls().size());
  }

  @Test
  public void scoreMultiplierTest() {
    ScoreMultiplier multiplier = new ScoreMultiplier("x3 Score", myGame.getRoot(), myGame,
        controller);
    javafxRun(() -> multiplier.activatePowerUp());
    myGame.getStatusDisplay().getMyProgress().addScore();
    assertEquals(3, myGame.getStatusDisplay().getMyProgress().getScore());
  }

  @Test
  public void deactivateScoreMultiplierTest() {
    ScoreMultiplier multiplier = new ScoreMultiplier("x3 Score", myGame.getRoot(), myGame,
        controller);
    javafxRun(() -> multiplier.activatePowerUp());
    javafxRun(() -> multiplier.deactivatePowerUp());
    myGame.getStatusDisplay().getMyProgress().addScore();
    assertEquals(1, myGame.getStatusDisplay().getMyProgress().getScore());
  }


}
