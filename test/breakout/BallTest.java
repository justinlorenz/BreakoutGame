package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import breakout.Bricks.Brick;
import breakout.Bricks.Projectile;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


public class BallTest extends DukeApplicationTest {

  public static final String STARTING_LEVEL_FILEPATH = "blockLevels/level1.txt/";
  public static final int SIZE = 800;
  public static final int STARTING_SCORE = 0;
  public static final int STARTING_LIVES = 3;
  public static final int STARTING_LEVEL_NUMBER = 1;
  public static final int STARTING_BALL_X = SIZE - 400;
  public static final int STARTING_BALL_Y = SIZE - 400;
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


  @Test
  public void projectileCollisionPaddleTest() {
    javafxRun(() -> myGame.getAllProjectiles().add(new Projectile(400, 200, myGame)));
    Projectile projectile = myGame.getAllProjectiles().get(0);
    javafxRun(() -> myGame.getMyPaddle().getPaddleRectangle().setX(400));
    while (projectile.getProjectile().getCenterY() + projectile.getProjectile().getRadiusY()
        < myGame.getMyPaddle().getPaddleRectangle().getY()) {
      projectile.moveFallingSpike();
    }
    javafxRun(() -> projectile.checkProjectileCollision());
    assertEquals(2, myGame.getStatusDisplay().getMyProgress().getLives());
  }

  @Test
  public void bounceOffCollisionTest() {
    double xSpeed = ball.getXSpeed();
    ball.bounceOffCollision("WallSide");
    assertEquals(xSpeed * -1, ball.getXSpeed());
  }

  @Test
  public void checkBrickCollision() {
    Brick brick = new Brick(1, Color.RED, myGame);
    javafxRun(() -> brick.buildThisBrick(myGame.getRoot(), 4, 300, 300));
    javafxRun(() -> ball.getBallCircle().setCenterX(350));
    javafxRun(() -> ball.getBallCircle().setCenterY(290));
    double ySpeed = ball.getYSpeed();
    javafxRun(() -> ball.checkTypeCollision(brick));
    assertEquals(ySpeed * -1, ball.getYSpeed());
  }
}
