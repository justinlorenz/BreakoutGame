package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class StatusDisplayTest extends DukeApplicationTest {

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

  @Override
  public void start(Stage stage) {
    Scene gameScene = myGame.setUpGameScene();
    myGame.createNewLevel(STARTING_LEVEL_FILEPATH, STARTING_SCORE, STARTING_LIVES,
        STARTING_LEVEL_NUMBER);
    stage.setScene(gameScene);
    stage.setTitle(StatusDisplay.TITLE);
    stage.show();
    myGame.togglePause();
    myGame.step();
  }

  @Test
  public void scoreDisplayStartTest() {
    assertEquals("Score: 0", myGame.getStatusDisplay().getScoreDisplay().getText());
  }

  @Test
  public void scoreDisplayUpdateTest() {
    myGame.getStatusDisplay().getMyProgress().addScore();
    javafxRun(myGame::step);
    assertEquals("Score: 1", myGame.getStatusDisplay().getScoreDisplay().getText());
  }

  @Test
  public void livesDisplayStartTest() {
    assertEquals("Lives Left: 3", myGame.getStatusDisplay().getLivesDisplay().getText());
  }

  @Test
  public void livesDisplayUpdateTest() {
    myGame.getStatusDisplay().getMyProgress().lostLive();
    javafxRun(() -> myGame.step());
    assertEquals("Lives Left: 2", myGame.getStatusDisplay().getLivesDisplay().getText());
  }

  @Test
  public void powerDisplayStartTest() {
    assertEquals("Current Power: ", myGame.getStatusDisplay().getPowerUpDisplay().getText());
  }

  @Test
  public void levelNumDisplayStartTest() {
    assertEquals("Level Number: 1", myGame.getStatusDisplay().getLevelNumDisplay().getText());
  }

  @Test
  public void levelNumDisplayUpdateTest() {
    myGame.getStatusDisplay().getMyProgress().movedUpLevel();
    javafxRun(() -> myGame.step());
    assertEquals("Level Number: 2", myGame.getStatusDisplay().getLevelNumDisplay().getText());
  }

  @Test
  public void highScoreDisplayStartTest() {
    assertEquals("Current Highscore: 90,  Justin",
        myGame.getStatusDisplay().getHighestScoreDisplay().getText());
  }


}
