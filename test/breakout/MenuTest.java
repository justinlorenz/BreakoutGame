package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import breakout.Menus.BeatLevelMenu;
import breakout.Menus.GameOverMenu;
import breakout.Menus.GameWonMenu;
import breakout.Menus.Menu;
import breakout.Menus.StartMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class MenuTest extends DukeApplicationTest {

  public static final String STARTING_LEVEL_FILEPATH = "blockLevels/level1.txt/";
  public static final int SIZE = 800;
  // standard steps to do for all test applications so factor it out here
  private final Game myGame = new Game();
  private Stage myStage;

  @Override
  public void start(Stage stage) {
    myStage = stage;
  }

  @Test
  public void testStartMenu() {
    Menu myStartMenu = new StartMenu(myGame);
    String startMenuString = myStartMenu.getMenuString();
    assertEquals("Breakout!", startMenuString);
  }

  @Test
  public void testBeatLevelMenu() {
    Menu myBeatLevelMenu = new BeatLevelMenu(myGame);
    String beatLevelMenuString = myBeatLevelMenu.getMenuString();
    assertEquals("Level Beat!", beatLevelMenuString);
  }

  @Test
  public void testGameOverMenu() {
    Menu myGameOverMenu = new GameOverMenu(myGame);

    String gameOverMenuString = myGameOverMenu.getMenuString();
    assertEquals("Game Over!", gameOverMenuString);
  }

  @Test
  public void testGameWonMenu() {
    Menu myGameWonMenu = new GameWonMenu(myGame);
    String gameWonMenuString = myGameWonMenu.getMenuString();
    assertEquals("You Win!", gameWonMenuString);
  }

  @Test
  public void testSetUpScene() {
    Menu myStartMenu = new StartMenu(myGame);
    Scene menuScene = myStartMenu.setUpScene();
    assertNotNull(menuScene);
  }

}
