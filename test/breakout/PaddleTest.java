package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class PaddleTest extends DukeApplicationTest {

  public static final String STARTING_LEVEL_FILEPATH = "blockLevels/level1.txt/";
  public static final int SIZE = 800;
  public static final int STARTING_SCORE = 0;
  public static final int STARTING_LIVES = 3;
  public static final int STARTING_LEVEL_NUMBER = 1;
  public static final double PADDLE_SIZE_THICKNESS = 10;
  public static final double PADDLE_SIZE_LENGTH = 120;
  // standard steps to do for all test applications so factor it out here
  private final Game myGame = new Game();
  private Ball ball;
  private Paddle myPaddle;


  @Override
  public void start(Stage stage) {
    Scene gameScene = myGame.setUpGameScene();
    myGame.createNewLevel(STARTING_LEVEL_FILEPATH, STARTING_SCORE, STARTING_LIVES,
        STARTING_LEVEL_NUMBER);
    stage.setScene(gameScene);
    stage.setTitle(StatusDisplay.TITLE);
    stage.show();
    ball = myGame.getAllBalls().get(0);
    myPaddle = myGame.getMyPaddle();
  }

  @Test
  public void testInitialPositionAndSizeOfPaddle() {
    assertEquals(340, myPaddle.getPaddleRectangle().getX());
    assertEquals(750, myPaddle.getPaddleRectangle().getY());
    assertEquals(120, myPaddle.getThisPaddleLength());
  }

  @Test
  public void testPaddleMovesWithKeyPress() {
    press(KeyCode.RIGHT);
    myGame.step();
    assertEquals(340, myPaddle.getPaddleRectangle().getX(), 0.01);
    press(KeyCode.LEFT);
    myGame.step();
    assertEquals(340, myPaddle.getPaddleRectangle().getX(), 0.01);
    assertEquals(750, myPaddle.getPaddleRectangle().getY());
  }

  @Test
  public void paddleRightWallCollisionTest() {
    myPaddle.getPaddleRectangle().setX(880);
    myPaddle.checkPaddleWallCollision(SIZE);
    assertEquals(myPaddle.getPaddleRectangle().getX(), SIZE - myPaddle.getThisPaddleLength() - 1);
  }

  @Test
  public void paddleLeftWallCollisionTest() {
    myPaddle.getPaddleRectangle().setX(-10);
    myPaddle.checkPaddleWallCollision(SIZE);
    assertEquals(myPaddle.getPaddleRectangle().getX(), 0);
  }

  @Test
  public void removePaddleTest() {
    javafxRun(() -> myPaddle.removePaddle(myGame.getRoot()));
    assertTrue(!myGame.getRoot().getChildren().contains(myPaddle.getPaddleRectangle()));
  }

  @Test
  public void ballPaddleCollisionTest() {
    javafxRun(() -> ball.getBallCircle().setCenterY(200));
    javafxRun(() -> ball.getBallCircle().setCenterX(400));
    while (ball.getBallCircle().getCenterY() + ball.getBallCircle().getRadius() < myPaddle
        .getPaddleRectangle().getY()) {
      ball.moveBall();
    }
    double originalSpeed = ball.getYSpeed();
    myPaddle.checkPaddleBallCollision(ball);
    assertEquals((int) originalSpeed, (int) ball.getYSpeed() * -1);
  }

  @Test
  public void findAngleOffPaddleCollisionTest() {
    javafxRun(() -> ball.getBallCircle().setCenterX(320));
    javafxRun(() -> myPaddle.getPaddleRectangle().setX(300));
    assertEquals(136, (int) myPaddle.findAngleOffPaddle(ball.getBallCircle()));
  }

}
