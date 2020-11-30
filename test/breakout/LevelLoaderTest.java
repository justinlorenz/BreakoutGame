package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import breakout.Bricks.Brick;
import breakout.Bricks.ProjectileBrick;
import breakout.Bricks.UnbreakableBrick;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class LevelLoaderTest extends DukeApplicationTest {

  public static final String STARTING_LEVEL_FILEPATH = "blockLevels/level1.txt/";
  public static final int STARTING_SCORE = 0;
  public static final int STARTING_LIVES = 3;
  public static final int STARTING_LEVEL_NUMBER = 1;
  public static final Color UNBREAKABLE_COLOR = Color.GRAY;
  public static final Color PROJECTILE_BRICK_COLOR = Color.RED;
  public static final int UNBREAKABLE_STRENGTH = 1;
  public static final int PROJECTILE_BRICK_STRENGTH = 2;
  private final Game myGame = new Game();

  @Override
  public void start(Stage stage) {
    Scene gameScene = myGame.setUpGameScene();
    myGame.createNewLevel(STARTING_LEVEL_FILEPATH, STARTING_SCORE, STARTING_LIVES,
        STARTING_LEVEL_NUMBER);
    stage.setScene(gameScene);
    stage.setTitle(StatusDisplay.TITLE);
    stage.show();
  }

  @Test
  public void loadFakeLevelEdgeCaseTest() {
    myGame.createNewLevel("FakeLevel.txt", 1, 1, 1);
    assertEquals(0, myGame.getMyLevel()
        .getNumOfImpossibles()); //didn't know how to check system.out.print, but verified edge case by running this test as well
  }

  @Test
  public void getBrickTypeUnbreakableTest() {
    Brick brick = new UnbreakableBrick(UNBREAKABLE_STRENGTH, UNBREAKABLE_COLOR, myGame);
    assertEquals(brick.getBrickStrength(),
        myGame.getMyLevel().getBrickType("unbreakable").getBrickStrength());
  }

  @Test
  public void getBrickTypeProjectileTest() {
    Brick brick = new ProjectileBrick(PROJECTILE_BRICK_STRENGTH, PROJECTILE_BRICK_COLOR, myGame);
    assertEquals(brick.getBrickStrength(),
        myGame.getMyLevel().getBrickType("projectile").getBrickStrength());
  }

  @Test
  public void getBrickTypeDefaultTest() {
    Brick brick = new Brick(1, Color.RED, myGame);
    assertEquals(brick.getBrickStrength(),
        myGame.getMyLevel().getBrickType("1,RED").getBrickStrength());
  }


  @Test
  public void clearAllBricksTest() {
    javafxRun(() -> myGame.getMyLevel().clearAllBricks());
    assertEquals(true, !myGame.getRoot().getChildren()
        .contains(myGame.getMyLevel().returnBrickList().get(0).getBrickRectangle()));
  }

  @Test
  public void clearSingleBrickTest() {
    int size = myGame.getMyLevel().returnBrickList().size();
    javafxRun(() -> myGame.getMyLevel().clearSingleBrick());
    assertEquals(true, myGame.getMyLevel().returnBrickList().size() + 1 == size);
  }
}
