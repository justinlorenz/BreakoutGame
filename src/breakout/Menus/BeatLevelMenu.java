package breakout.Menus;

import breakout.Game;
import java.io.FileNotFoundException;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;


public class BeatLevelMenu extends Menu {

  private static final boolean NEED_USER_INPUT = false;
  private final String textFilePath = "blockLevels/menuText/beatLevel.txt";

  public BeatLevelMenu(Game game) {
    super(game, NEED_USER_INPUT);
    try {
      this.makeMenuText(textFilePath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void setKeyInput(Scene scene) {
    scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.SPACE) {
        this.getGame().initializeNewLevel();
      }
    });
  }

}
