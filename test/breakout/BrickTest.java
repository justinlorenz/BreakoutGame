package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import breakout.Bricks.Brick;
import breakout.Bricks.Projectile;
import breakout.Bricks.ProjectileBrick;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class BrickTest extends DukeApplicationTest {

  public static final String STARTING_LEVEL_FILEPATH = "blockLevels/level1.txt/";
  public static final int SIZE = 800;
  public static final int STARTING_SCORE = 0;
  public static final int STARTING_LIVES = 3;
  public static final int STARTING_LEVEL_NUMBER = 1;
  public static final double PADDLE_SIZE_THICKNESS = 10;
  public static final double PADDLE_SIZE_LENGTH = 120;
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
  public void buildThisBrickTest() {
    Brick brick = new Brick(1, Color.RED, myGame);
    javafxRun(() -> brick.buildThisBrick(myGame.getRoot(), 4, 40, 40));
    assertEquals(1, brick.getBrickStrength());
    assertEquals(40, brick.getXPos());
    assertEquals(40, brick.getXPos());
    assertEquals("1", brick.getCurrHealthDisplay().getText());
  }

  @Test
  public void brickIsHitTest() {
    Brick brick = new Brick(1, Color.RED, myGame);
    javafxRun(() -> brick.buildThisBrick(myGame.getRoot(), 4, 40, 40));
    javafxRun(() -> brick.checkIfBroke(myGame.getRoot()));
    assertEquals(true, !myGame.getRoot().getChildren().contains(brick.getBrickRectangle()));
  }

  @Test
  public void clearBrickTest() {
    Brick brick = new Brick(1, Color.RED, myGame);
    javafxRun(() -> brick.buildThisBrick(myGame.getRoot(), 4, 40, 40));
    javafxRun(() -> brick.clearBrick());
    assertEquals(true, !myGame.getRoot().getChildren().contains(brick.getBrickRectangle()));
  }

  @Test
  public void updateStatusDisplayWhenHitTest() {
    Brick brick = new Brick(2, Color.RED, myGame);
    javafxRun(() -> brick.buildThisBrick(myGame.getRoot(), 4, 40, 40));
    javafxRun(() -> brick.checkIfBroke(myGame.getRoot()));
    javafxRun(() -> myGame.step());
    assertEquals("1", brick.getCurrHealthDisplay().getText());
  }

  @Test
  public void createProjectileTest() {
    Brick brick = new ProjectileBrick(1, Color.RED, myGame);
    javafxRun(() -> brick.checkIfBroke(myGame.getRoot()));
    assertEquals(true, myGame.getAllProjectiles().size() > 0);
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


}
